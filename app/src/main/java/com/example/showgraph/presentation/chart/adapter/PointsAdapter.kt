package com.example.showgraph.presentation.chart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.showgraph.databinding.ItemPointBinding
import com.example.showgraph.domain.model.Point
import com.example.showgraph.presentation.base.BaseViewHolder

internal class PointsAdapter(
) : ListAdapter<Point, PointsAdapter.PointViewHolder>(
    DiffCallback
) {

    companion object {
        private object DiffCallback : DiffUtil.ItemCallback<Point>() {
            override fun areItemsTheSame(oldItem: Point, newItem: Point) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Point, newItem: Point) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder =
        LayoutInflater.from(parent.context)
            .let { ItemPointBinding.inflate(it, parent, false) }
            .let {
                PointViewHolder(it)
            }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    internal class PointViewHolder(
        binding: ItemPointBinding,
    ) : BaseViewHolder<Point, ItemPointBinding>(binding) {

        override fun bind(item: Point, position: Int) {
            with(binding) {
                this.pointTextView.text = "(${item.x}, ${item.y})"
            }
        }
    }
}
