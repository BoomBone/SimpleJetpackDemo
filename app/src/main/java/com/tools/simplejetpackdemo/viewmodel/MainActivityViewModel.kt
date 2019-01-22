package com.tools.simplejetpackdemo.viewmodel

import androidx.lifecycle.ViewModel
import com.tools.simplejetpackdemo.data.GankDataRepository

class MainActivityViewModel(private var gankDataRepository: GankDataRepository) : ViewModel() {

    private val repoResult = gankDataRepository.postsOfGirl(30)
    val posts = repoResult.pagedList
    val refreshState = repoResult.refreshState

    fun refresh() {
        repoResult.refresh.invoke()
    }
}