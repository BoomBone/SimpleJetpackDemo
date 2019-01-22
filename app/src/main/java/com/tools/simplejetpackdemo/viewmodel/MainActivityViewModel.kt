package com.tools.simplejetpackdemo.viewmodel

import androidx.lifecycle.ViewModel
import com.tools.simplejetpackdemo.data.GankDataRepository

class MainActivityViewModel(private var gankDataRepository: GankDataRepository) : ViewModel() {

    val posts = gankDataRepository.postsOfGirl(30).pagedList
}