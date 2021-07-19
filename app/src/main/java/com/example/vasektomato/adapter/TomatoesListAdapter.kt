package com.example.vasektomato.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vasektomato.R
import com.example.vasektomato.db.model.Tomato

class TomatoesListAdapter (val onItemClickListener: OnItemClickListener): ListAdapter<Tomato, TomatoesListAdapter.TomatoViewHolder>(TomatoesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TomatoViewHolder {
        return TomatoViewHolder.create(parent, onItemClickListener)
    }

    override fun onBindViewHolder(holder: TomatoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind("  " + current.name)
    }

    class TomatoViewHolder(itemView: View, val onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val tomatoItemView: TextView = itemView.findViewById(R.id.textView)

        init {
            tomatoItemView.setOnClickListener(this)
        }

        fun bind(text: String?) {
            tomatoItemView.text = text
        }

        companion object {
            fun create(
                parent: ViewGroup,
                onItemClickListener: OnItemClickListener
            ): TomatoViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return TomatoViewHolder(view, onItemClickListener)
            }
        }

        override fun onClick(v: View?) {
            onItemClickListener.onItemClick(adapterPosition)
        }
    }

    class TomatoesComparator : DiffUtil.ItemCallback<Tomato>() {
        override fun areItemsTheSame(oldItem: Tomato, newItem: Tomato): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Tomato, newItem: Tomato): Boolean {
            return oldItem.name == newItem.name
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}