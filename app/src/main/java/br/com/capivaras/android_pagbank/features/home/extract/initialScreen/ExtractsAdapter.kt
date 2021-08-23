package br.com.capivaras.android_pagbank.features.home.extract.initialScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.R.*
import br.com.capivaras.android_pagbank.util.Constants
import br.com.capivaras.android_pagbank.util.Constants.Companion.CARD_EXTRACT
import br.com.capivaras.android_pagbank.util.Constants.Companion.DATE_EXTRACT
import java.text.NumberFormat

class ExtractsAdapter(
    private val listTransactionItem: List<ExtractViewModel.TransactionItemAdapter>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = listTransactionItem.size

    override fun getItemViewType(position: Int): Int {

        return when (listTransactionItem[position]) {

            is ExtractViewModel.TransactionHeader ->
                DATE_EXTRACT
            else ->
                CARD_EXTRACT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == DATE_EXTRACT) {
            return DateViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(layout.date_extract_card, parent, false)
            )
        } else {
            return ExtractViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(layout.extract_card, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is DateViewHolder) {
            val element = listTransactionItem[position] as ExtractViewModel.TransactionHeader
            holder.bind(element)
        } else if (holder is ExtractViewHolder) {
            val element = listTransactionItem[position] as ExtractViewModel.TransactionBody
            holder.bind(element)
        }
    }

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val date: TextView = itemView.findViewById(id.extractsData)
        fun bind(element: ExtractViewModel.TransactionHeader) {
            date.text = element.date
        }
    }

    class ExtractViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val greyColor = ResourcesCompat.getColor(itemView.resources, R.color.grey, null)
        private val redColor = ResourcesCompat.getColor(itemView.resources, R.color.red, null)
        private val greenColor = ResourcesCompat.getColor(itemView.resources, R.color.pagGreen, null)

        private val processedIcon: ImageView = itemView.findViewById(id.iconProcessedExtract)
        private val processedTime: TextView = itemView.findViewById(id.timeProcessedExtract)
        private val processedStatus: TextView = itemView.findViewById(id.statusProcessedExtract)
        private val processedValue: TextView = itemView.findViewById(id.extractsCardValue)
        private val typeDescription: TextView = itemView.findViewById(id.type_description)
        private val extractsCardButton: ImageView = itemView.findViewById(id.extractsCardButton)

        fun bind(element: ExtractViewModel.TransactionBody) {
            processedStatus.text = element.extractInfo.type
            typeDescription.text = element.extractInfo.typeDescription
            processedTime.text = element.extractInfo.time
            processedValue.text = NumberFormat.getCurrencyInstance().format(element.extractInfo.value)

            processedIcon.setImageResource(drawable.ic_baseline_more_horiz_grey)

            if (element.extractInfo.type == Constants.EXPENSE) {
                processedValue.setTextColor(redColor)
                processedStatus.setTextColor(redColor)
                processedIcon.setImageResource(drawable.ic_baseline_more_horiz_red)
                extractsCardButton.setImageResource(drawable.ic_baseline_arrow_forward_red)
            } else if (element.extractInfo.time.contains(Constants.CANCELED)) {
                processedValue.setTextColor(greyColor)
                processedStatus.setTextColor(greyColor)
                processedIcon.setImageResource(drawable.ic_baseline_more_horiz_grey)
                extractsCardButton.setImageResource(drawable.ic_baseline_arrow_forward_grey)
            } else {
                processedValue.setTextColor(greenColor)
                processedStatus.setTextColor(greenColor)
                processedIcon.setImageResource(drawable.ic_baseline_more_horiz_gree)
                extractsCardButton.setImageResource(drawable.paggreen_ic_baseline_arrow_forward_ios_24)
            }
        }

    }
}
