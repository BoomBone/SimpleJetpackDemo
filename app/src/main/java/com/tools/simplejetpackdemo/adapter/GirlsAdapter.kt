package com.tools.simplejetpackdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.tools.simplejetpackdemo.R
import com.tools.simplejetpackdemo.data.Result
import com.tools.simplejetpackdemo.utils.glide.GlideApp

internal class GirlsAdapter(val list: List<Result>) : RecyclerView.Adapter<GirlsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GirlsHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_main, parent, false)
        return GirlsHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: GirlsHolder, position: Int) {
        holder.bindTo(list[position].url)
    }

}

internal class GirlsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.mItemIv)
    fun bindTo(url: String) {
        GlideApp.with(itemView)
                .load(url)
                .fitCenter()
                .centerCrop()
                .into(imageView)
    }
}
