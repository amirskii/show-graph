package com.example.showgraph

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PointsAdapter(private val points: List<Pair<Float, Float>>) : RecyclerView.Adapter<PointsAdapter.PointsViewHolder>() {
    class PointsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.pointTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_point, parent, false)
        return PointsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PointsViewHolder, position: Int) {
        val (x, y) = points[position]
        holder.textView.text = "(${x}, ${y})"
    }

    override fun getItemCount(): Int = points.size
}