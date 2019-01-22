package com.tools.simplejetpackdemo.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tools.simplejetpackdemo.data.NetworkState
import com.tools.simplejetpackdemo.data.Result
import com.tools.simplejetpackdemo.utils.glide.GlideRequests

class PostsAdapter(private val glide: GlideRequests):PagedListAdapter<Result,RecyclerView.ViewHolder>(POST_COMPARATOR) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GirlsViewHolder.create(parent,glide)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GirlsViewHolder).bind(getItem(position))
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
                    oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean = oldItem == newItem

        }
    }

}