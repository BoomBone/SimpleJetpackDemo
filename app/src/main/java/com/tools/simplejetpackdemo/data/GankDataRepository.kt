package com.tools.simplejetpackdemo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.livedata.liveDataObject
import com.github.kittinunf.result.Result
import com.tools.simplejetpackdemo.data.GirlData
import com.tools.simplejetpackdemo.data.repository.byPage.GirlDataSourceFactory
import java.util.concurrent.Executor


class GankDataRepository constructor(private val networkExecutor: Executor) {

    fun postsOfGirl(pageSize: Int): Listing<com.tools.simplejetpackdemo.data.Result> {
        val sourceFactory = GirlDataSourceFactory(networkExecutor)
        val livePagedList = sourceFactory.toLiveData(
                pageSize = pageSize, fetchExecutor = networkExecutor
        )
        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }

        return Listing(pagedList = livePagedList,
                networkState = Transformations.switchMap(sourceFactory.sourceLiveData){
                    it.networkState
                },
                retry = {
                    sourceFactory.sourceLiveData.value?.retryAllFailed()
                },
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                },
                refreshState = refreshState
                )
    }


}