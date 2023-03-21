package com.example.e_waste.scrap_rate_image

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_waste.R
import com.example.e_waste.scrapRateAdapter

class ScrapRateImageRecycler : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrap_rate_image_recycler)

        val myDataset = ScrapRateData().loadAffirmations()

        val scrapRateList : RecyclerView = findViewById(R.id.scrap_rate_image_list)

        scrapRateList.layoutManager = LinearLayoutManager(this)
        scrapRateList.adapter = scrap_rate_image_adapter(this,myDataset)

        // for increasing performance
//        scrapRateList.setHasFixedSize(true)

    }
}