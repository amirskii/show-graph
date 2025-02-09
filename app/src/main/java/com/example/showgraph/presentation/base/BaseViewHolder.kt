package com.example.showgraph.presentation.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T : Any, V : ViewBinding>(protected val binding: V) : RecyclerView.ViewHolder(binding.root) {
    open fun bind(item: T, position: Int){}

    open fun bind(item: T, position: Int, payloads: MutableList<Any>) {
        bind(item, position)
    }
}