package com.dayker.airsearch.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dayker.airsearch.databinding.ItemFlightBinding
import com.dayker.airsearch.model.Response


class MainAdapter(private val dataSet: List<Response>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: ItemFlightBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Response) {
            with(binding) {
                // textViews initialization
                tvAirport1.text = item.depIcao
                tvAirport2.text = item.arrIcao
                tvSpeed.text = "${item.speed} km/h"
                tvFlag.text = item.flag
                tvStatus.text = item.status
                tvFlightNumber.text = item.flightNumber
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFlightBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
        holder.itemView.setOnClickListener {

            val message = Toast.makeText(
                holder.itemView.context,
                "You have chosen ",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    override fun getItemCount() = dataSet.size

}