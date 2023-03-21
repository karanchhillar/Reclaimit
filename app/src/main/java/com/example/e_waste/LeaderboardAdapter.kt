package com.example.e_waste

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//import kotlinx.android.synthetic.main.item_leaderboard.view.*


class LeaderboardAdapter(private val data: List<LeaderboardItem>) :
    RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.leaderboard_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bindItems(item)
    }

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = data[position]
//        holder.itemView.name.text = item.name
//        holder.itemView.score.text = item.score.toString()
//    }
//
//    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(user: LeaderboardItem) {
            val textViewName: TextView? = itemView.findViewById(R.id.leaderboard_name)
            val textViewAddress: TextView? = itemView.findViewById(R.id.leaderboard_score)
            textViewName?.text = user.name
            textViewAddress?.text = user.score.toString()
        }
    }
}

data class LeaderboardItem(val name: String, val score: Int)
