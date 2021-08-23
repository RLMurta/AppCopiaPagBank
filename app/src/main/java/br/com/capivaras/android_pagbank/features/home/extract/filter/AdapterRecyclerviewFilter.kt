package br.com.capivaras.android_pagbank.features.home.extract.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.capivaras.android_pagbank.R

class AdapterRecyclerviewFilter(
    private val listFilter: List<String>,
    private val selectItemsFilterListener: SelectItemsFilterListener,
    private var positionSelect: Int
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_days_filter_recycleview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemsViewHolder) {
            holder.setData(position, listFilter, positionSelect)

            holder.itemView.setOnClickListener {
                selectItemsFilterListener.onPositionItemFilterSelected(position)
                positionSelect = position
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = listFilter.size

    class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemsText: TextView = itemView.findViewById(R.id.checkedText_last_days)
        val itemsCheck: ImageView = itemView.findViewById(R.id.imageViewCheck)

        fun setData(position: Int, listFilter: List<String>, positionSelect: Int) {
            itemsText.text = listFilter[position]
            if (position == positionSelect) itemsCheck
                .setImageResource(R.drawable.ic_check)
            else itemsCheck
                .setImageResource(0)
        }
    }

    interface SelectItemsFilterListener {
        fun onPositionItemFilterSelected(position: Int)
    }
}
