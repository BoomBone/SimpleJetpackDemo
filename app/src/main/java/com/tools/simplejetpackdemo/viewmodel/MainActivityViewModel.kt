package com.tools.simplejetpackdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.tools.simplejetpackdemo.data.GankDataRepository
import com.tools.simplejetpackdemo.data.GirlData

class MainActivityViewModel : ViewModel() {
    val user = User("Jack", 12)
    fun getLiveObservableData(): LiveData<Result<GirlData, FuelError>> {
        return GankDataRepository.getLiveObservableData("20", "1")
    }

}