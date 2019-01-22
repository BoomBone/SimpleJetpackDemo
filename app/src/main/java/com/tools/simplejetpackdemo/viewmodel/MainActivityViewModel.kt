package com.tools.simplejetpackdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.tools.simplejetpackdemo.data.GankDataRepository
import com.tools.simplejetpackdemo.data.GirlData
import com.tools.simplejetpackdemo.data.Listing

class MainActivityViewModel(private var gankDataRepository: GankDataRepository) : ViewModel() {
    private val repoData = gankDataRepository.postsOfGirl(20)
    private val repoLiveData = MutableLiveData<Listing<com.tools.simplejetpackdemo.data.Result>>()
    val repoResult = fetchGankData()
    val posts = switchMap(repoResult) { it.pagedList }
    val networkState = switchMap(repoResult) { it.networkState }
    val refreshState = switchMap(repoResult) { it.refreshState }


    fun fetchGankData(): LiveData<Listing<com.tools.simplejetpackdemo.data.Result>> {
        repoLiveData.postValue(repoData)
        return repoResult
    }

    fun refresh(){
        repoResult.value?.refresh?.invoke()
    }

    fun retry(){
        val listing = repoResult?.value
        listing?.retry?.invoke()
    }

}