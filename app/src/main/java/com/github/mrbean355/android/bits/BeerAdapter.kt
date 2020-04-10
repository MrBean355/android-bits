package com.github.mrbean355.android.bits

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_beer.view.*

class BeerAdapter : ListAdapter<Beer, BeerAdapter.ViewHolder>(DiffCallback()) {
    private val selection = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_beer, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.name
        holder.tagLine.text = item.tagLine
        holder.itemView.setBackgroundColor(if (item.id in selection) {
            ContextCompat.getColor(holder.itemView.context, R.color.colorSelected)
        } else {
            Color.TRANSPARENT
        })
        holder.itemView.setOnClickListener {
            val clickedItem = getItem(holder.adapterPosition)
            if (clickedItem.id in selection) {
                selection -= clickedItem.id
            } else {
                selection += clickedItem.id
            }
            notifyItemChanged(holder.adapterPosition)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.beer_name
        val tagLine: TextView = itemView.beer_tag_line
    }

    class DiffCallback : DiffUtil.ItemCallback<Beer>() {

        override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
            return oldItem == newItem
        }

    }
}
