package com.tools.simplejetpackdemo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import com.tools.simplejetpackdemo.R
import com.tools.simplejetpackdemo.adapter.GirlsAdapter
import com.tools.simplejetpackdemo.data.GirlData
import com.tools.simplejetpackdemo.databinding.ActivityMainBinding
import com.tools.simplejetpackdemo.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainActivityViewModel
    private var girlsAdapter: GirlsAdapter? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        binding.mainViewModel = mainViewModel
        binding.setLifecycleOwner(this)
        subscribeToModel()
    }

    private fun subscribeToModel() {
        mainViewModel.getLiveObservableData().observe(this, Observer<Result<GirlData, FuelError>> { t ->
            t.fold({
                Log.e("main", "data=${it.results}")
                girlsAdapter = GirlsAdapter(it.results)
                binding.adapter = girlsAdapter

            }, {
                Log.e("main", "error=${it.message}")
            })
        })
    }
}
