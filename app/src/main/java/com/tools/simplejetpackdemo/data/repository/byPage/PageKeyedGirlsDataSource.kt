package com.tools.simplejetpackdemo.data.repository.byPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.livedata.liveDataObject
import com.tools.simplejetpackdemo.data.GankDataRepository
import com.tools.simplejetpackdemo.data.GirlData
import com.tools.simplejetpackdemo.data.NetworkState
import com.tools.simplejetpackdemo.data.Result
import java.util.concurrent.Executor

class PageKeyedGirlsDataSource(private val retryExecutor: Executor) : PageKeyedDataSource<Int, Result>() {


    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Result>) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        getLiveObservableData(params.requestedLoadSize, 1)
                .observeForever { it ->
                    it.fold({ data ->
                        retry = null
                        networkState.postValue(NetworkState.LOADED)
                        initialLoad.postValue(NetworkState.LOADED)
                        callback.onResult(data.results, 1, 2)
                    }, { err ->
                        retry = {
                            loadInitial(params, callback)
                        }
                        val error = NetworkState.error(err.message ?: "unknown error")
                        networkState.postValue(error)
                        initialLoad.postValue(error)
                    })
                }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        networkState.postValue(NetworkState.LOADING)
        getLiveObservableData(params.requestedLoadSize, params.key)
                .observeForever { it ->
                    it.fold({ data ->
                        retry = null
                        callback.onResult(data.results, params.key + 1)
                        networkState.postValue(NetworkState.LOADED)
                    }, { err ->
                        retry = {
                            loadAfter(params, callback)
                        }
                        networkState.postValue(
                                NetworkState.error("error code: ${err.response.statusCode}"))
                    })
                }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        // ignored, since we only ever append to our initial load
    }

    fun getLiveObservableData(size: Int, index: Int): LiveData<com.github.kittinunf.result.Result<GirlData, FuelError>> {
        val url = "https://gank.io/api/data/福利/$size/$index"
        return Fuel.get(url).liveDataObject(GirlData.Deserializer())
    }

}