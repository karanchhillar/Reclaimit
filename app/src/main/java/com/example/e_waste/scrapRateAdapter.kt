package com.example.e_waste

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class scrapRateAdapter(private val data: List<ScrapRateItem>) :
    RecyclerView.Adapter<scrapRateAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): scrapRateAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.scrap_rate_single_item, parent, false)
        return scrapRateAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: scrapRateAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bindItems(item)
    }

    override fun getItemCount(): Int = data.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(user: ScrapRateItem) {
            val textViewName: TextView? = itemView.findViewById(R.id.scrap_rate_name)
            val textViewAddress: TextView? = itemView.findViewById(R.id.scrap_rate_score)
            textViewName?.text = user.name
            textViewAddress?.text = user.score.toString()
        }
    }
}

data class ScrapRateItem(val name: String, val score: Double)