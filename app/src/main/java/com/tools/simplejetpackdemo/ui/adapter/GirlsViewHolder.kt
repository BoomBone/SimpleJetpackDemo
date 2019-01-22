package com.tools.simplejetpackdemo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.tools.simplejetpackdemo.R
import com.tools.simplejetpackdemo.data.Result
import com.tools.simplejetpackdemo.utils.glide.GlideApp
import com.tools.simplejetpackdemo.utils.glide.GlideRequests

class GirlsViewHolder(view: View, private val glide: GlideRequests) : RecyclerView.ViewHolder(view) {

    val girlImg: ImageView = view.findViewById(R.id.mItemIv)

    fun bind(girl: Result?) {
        if (girl != null) {
            glide.load(girl.url)
                    .centerCrop()
                    .into(girlImg)
        }
    }

    companion object {
        fun create(parent: ViewGroup, glide: GlideRequests): GirlsViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_main, parent, false)
            return GirlsViewHolder(view, glide)
        }
    }
}