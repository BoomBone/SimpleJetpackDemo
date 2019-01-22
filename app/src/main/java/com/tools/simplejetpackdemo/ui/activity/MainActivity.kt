package com.tools.simplejetpackdemo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tools.simplejetpackdemo.R
import com.tools.simplejetpackdemo.adapter.GirlsAdapter
import com.tools.simplejetpackdemo.data.GankDataRepository
import com.tools.simplejetpackdemo.data.NetworkState
import com.tools.simplejetpackdemo.databinding.ActivityMainBinding
import com.tools.simplejetpackdemo.ui.adapter.PostsAdapter
import com.tools.simplejetpackdemo.utils.glide.GlideApp
import com.tools.simplejetpackdemo.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainActivityViewModel
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
        initAdapter()
        initSwipeToRefresh()
    }

    private fun initSwipeToRefresh() {
        mainViewModel.refreshState.observe(this, Observer {
            mMainSrl.isRefreshing = it == NetworkState.LOADING
        })

        mMainSrl.setOnRefreshListener {
            mainViewModel.refresh()
        }
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        val adapter = PostsAdapter(glide)
        binding.adapter = adapter
        mainViewModel.posts.observeForever {
            adapter.submitList(it)
        }
    }
}
