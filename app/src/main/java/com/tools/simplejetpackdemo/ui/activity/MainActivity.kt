package com.tools.simplejetpackdemo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tools.simplejetpackdemo.R
import com.tools.simplejetpackdemo.adapter.GirlsAdapter
import com.tools.simplejetpackdemo.data.GankDataRepository
import com.tools.simplejetpackdemo.databinding.ActivityMainBinding
import com.tools.simplejetpackdemo.viewmodel.MainActivityViewModel
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainActivityViewModel
    private var girlsAdapter: GirlsAdapter? = null
    private lateinit var binding: ActivityMainBinding
    private val NETWORK_IO = Executors.newFixedThreadPool(5)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("PrivatePropertyName")
                val respository = GankDataRepository(NETWORK_IO)
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(respository) as T
            }
        }).get(MainActivityViewModel::class.java)
        binding.mainViewModel = mainViewModel
        binding.setLifecycleOwner(this)
    }
}
