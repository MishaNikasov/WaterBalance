package com.nikasov.waterbalance.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.nikasov.waterbalance.R
import com.nikasov.waterbalance.data.intake.WaterIntake
import kotlinx.android.synthetic.main.item_water_intake.view.*
import java.text.SimpleDateFormat
import java.util.*

class WaterIntakeAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WaterIntake>() {

        override fun areItemsTheSame(oldItem: WaterIntake, newItem: WaterIntake): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WaterIntake, newItem: WaterIntake): Boolean {
            return oldItem.day == newItem.day
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WaterIntakeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_water_intake,
                parent,
                false
            ), interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WaterIntakeViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }
//todo: сделать сплэшш текущую колво воды таб лейаут 
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<WaterIntake>) {
        differ.submitList(list)
    }

    class WaterIntakeViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: WaterIntake) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
            val time = formatter.format(item.time)
            itemView.time.text = time
            val amount = "${item.amount} ml"
            itemView.amount.text = amount
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: WaterIntake)
    }
}

