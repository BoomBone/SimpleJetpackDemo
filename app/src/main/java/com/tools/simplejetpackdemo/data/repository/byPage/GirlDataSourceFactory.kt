package com.tools.simplejetpackdemo.data.repository.byPage

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.tools.simplejetpackdemo.data.Result
import java.util.concurrent.Executor

class GirlDataSourceFactory(private val retryExecutor: Executor) : DataSource.Factory<Int, Result>() {

    val sourceLiveData = MutableLiveData<PageKeyedGirlsDataSource>()
    override fun create(): DataSource<Int, Result> {
        val dataSource = PageKeyedGirlsDataSource(retryExecutor)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }
}