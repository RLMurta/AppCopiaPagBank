package br.com.capivaras.android_pagbank.features.home.homeApi.recyclerView

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.features.home.homeApi.model.HomeResponse
import com.squareup.picasso.Picasso

class BenefitsAdapter(
    private val benefitsList: List<HomeResponse.Benefit>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CompleteCardView(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.benefits_card_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CompleteCardView) {
            holder.cardviewColor.setBackgroundColor(Color.parseColor(benefitsList[position].indicatorColor))
            Picasso.with(holder.image.context).load(benefitsList[position].image).into(holder.image)
            holder.title.text = benefitsList[position].title
            holder.description.text = benefitsList[position].message
            holder.link.text = benefitsList[position].textLink
        }
    }

    class CompleteCardView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardviewColor: TextView = itemView.findViewById(R.id.color_benefits)
        val image: ImageView = itemView.findViewById(R.id.image_benefits)
        val title: TextView = itemView.findViewById(R.id.title_benefits)
        val description: TextView = itemView.findViewById(R.id.description_benefits)
        val link: TextView = itemView.findViewById(R.id.link_benefits)
    }

    override fun getItemCount() = benefitsList.size
}
