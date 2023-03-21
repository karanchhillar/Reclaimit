package com.example.e_waste

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class leaderboardFragemt : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_leaderboard_fragemt, container, false)

        val leaderboard_list : RecyclerView = view.findViewById(R.id.leaderboard_list)

        leaderboard_list.layoutManager = LinearLayoutManager(activity)
        leaderboard_list.adapter = LeaderboardAdapter(getSampleData())
        return view
    }

    // Returns some sample data to populate the RecyclerView
    private fun getSampleData(): List<LeaderboardItem> {
        return listOf(
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("Jane Smith", 800),
            LeaderboardItem("Bob Johnson", 600),
            LeaderboardItem("Alice Brown", 400),
            LeaderboardItem("Joe Davis", 200),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
            LeaderboardItem("John Doe", 1000),
        )
    }

}