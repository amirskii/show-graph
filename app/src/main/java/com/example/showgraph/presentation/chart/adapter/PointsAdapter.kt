package com.example.showgraph.presentation.chart.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.showgraph.databinding.ItemPointBinding
import com.example.showgraph.presentation.base.BaseViewHolder
import com.example.showgraph.presentation.model.PointPm

internal class PointsAdapter(
) : ListAdapter<PointPm, PointsAdapter.PointPmViewHolder>(
    DiffCallback
) {

    companion object {
        private object DiffCallback : DiffUtil.ItemCallback<PointPm>() {
            override fun areItemsTheSame(oldItem: PointPm, newItem: PointPm) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: PointPm, newItem: PointPm) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointPmViewHolder =
        LayoutInflater.from(parent.context)
            .let { ItemPointBinding.inflate(it, parent, false) }
            .let {
                PointPmViewHolder(it)
            }

    override fun onBindViewHolder(holder: PointPmViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    internal class PointPmViewHolder(
        binding: ItemPointBinding,
    ) : BaseViewHolder<PointPm, ItemPointBinding>(binding) {

        override fun bind(item: PointPm, position: Int) {
            with(binding) {
                if (position == 0) {
                    xValue.setTypeface(null, Typeface.BOLD)
                    yValue.setTypeface(null, Typeface.BOLD)
                }
                xValue.text = item.x
                yValue.text = item.y
            }
        }
    }
}
