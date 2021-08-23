package br.com.capivaras.android_pagbank.features.home.homeViewPager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.capivaras.android_pagbank.R

class AdapterRecyclerviewFunctionalities(
    private val listServices: List<ItensList>,
    private val selectItemsFunctionalities: SelectItemsFunctionalities,
    private val positionViewPager: Int
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItensViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.icon_text_recycleview_funcionalits, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItensViewHolder) {
            holder.itensImage.setImageResource(listServices[position].icon)
            holder.itensText.text = listServices[position].title
            holder.itemView.setOnClickListener {
                selectItemsFunctionalities.onFunctionalityClicked(position, positionViewPager)
            }
        }
    }

    override fun getItemCount(): Int {
        return listServices.size
    }

    class ItensViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itensImage: ImageView = itemView.findViewById(R.id.icone_task)
        val itensText: TextView = itemView.findViewById(R.id.title_task)
    }

    data class ItensList(val title: String, val icon: Int)

    interface SelectItemsFunctionalities {
        fun onFunctionalityClicked(positionRecyclerView: Int, positionViewPager: Int)
    }
}
