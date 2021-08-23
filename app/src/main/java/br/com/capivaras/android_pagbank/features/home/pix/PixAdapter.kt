package br.com.capivaras.android_pagbank.features.home.pix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import br.com.capivaras.android_pagbank.R

const val FIRST_TITLE_CARD_POSITION = 0
const val SECOND_TITLE_CARD_POSITION = 4
const val TITLE_QRCODE = 1

class PixTransferOrPayAdapter(private val buttons: Buttons) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return 6
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TITLE_QRCODE -> TitleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.title_qr_code_card, parent, false)
            )
            CARD_PAY_QRCODE -> PayViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.transfer_or_pay_qrcode_card, parent, false)
            )
            else -> SellViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.sell_with_qrcode_card, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TitleViewHolder) {
            holder.setData(position)
        } else if (holder is PayViewHolder) {
            holder.setData(position)
        } else if (holder is SellViewHolder) {
            holder.setData()
        }
        holder.itemView.setOnClickListener {
            buttons.onButtonClicked(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == FIRST_TITLE_CARD_POSITION || position == SECOND_TITLE_CARD_POSITION) {
            return TITLE_QRCODE
        } else if (position > FIRST_TITLE_CARD_POSITION && position < SECOND_TITLE_CARD_POSITION) {
            return CARD_PAY_QRCODE
        } else {
            return CARD_SELL_QRCODE
        }
    }

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.payWithPixTextView)

        fun setData(position: Int) {
            when (position) {
                FIRST_TITLE_CARD_POSITION -> {
                    title.text = "Transferir ou pagar QR Code"
                }
                else -> {
                    title.text = "Vender"
                }
            }
        }
    }

    class PayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardIcon: ImageView = itemView.findViewById(R.id.payCardIcon)
        val cardTitle: TextView = itemView.findViewById(R.id.payCardTitle)
        val cardText: TextView = itemView.findViewById(R.id.payCardText)
        val layout: ConstraintLayout = itemView.findViewById(R.id.layoutTransferOrPayCard)

        fun setData(position: Int) {
            when (position) {
                TITLE_QRCODE -> {
                    cardIcon.setImageResource(R.drawable.ic_baseline_sync_24)
                    cardTitle.text = "Fazer transferência por Pix"
                    cardText.text = "Transfira utilizando a chave ou os dados bancários."
                }
                CARD_PAY_QRCODE -> {
                    cardIcon.setImageResource(R.drawable.pag_blue_ic_baseline_qr_code_24)
                    cardTitle.text = "Pagar com QR Code"
                    cardText.text = "Leia o QR Code para realizar o pagamento."
                }
                else -> {
                    cardIcon.setImageResource(R.drawable.ic_baseline_payments_24)
                    cardTitle.text = "Pagar com Pix Copia e Cola"
                    cardText.text = "Cole o código do QR que você copiou para pagar."
                }
            }
        }
    }

    class SellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardIcon: ImageView = itemView.findViewById(R.id.sellCardIcon)
        val cardTitle: TextView = itemView.findViewById(R.id.sellCardTitle)
        val cardText: TextView = itemView.findViewById(R.id.sellCardText)
        val layout: ConstraintLayout = itemView.findViewById(R.id.layoutSellWithQrCard)

        fun setData() {
            cardIcon.setImageResource(R.drawable.ic_baseline_local_atm_24)
            cardTitle.text = "Vender com QR Code"
            cardText.text = "Receba na hora com QR Code Pix com taxa de apenas 1,69%."
        }
    }

    companion object{
        const val CARD_PAY_QRCODE = 2
        const val CARD_SELL_QRCODE = 3
    }

    interface Buttons{
        fun onButtonClicked(position: Int)
    }
}
