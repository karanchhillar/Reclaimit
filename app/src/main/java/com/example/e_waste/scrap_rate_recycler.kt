package com.example.e_waste

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class scrap_rate_recycler : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrap_rate_recycler)

        val scrapRate_list : RecyclerView = findViewById(R.id.scrap_rate_list)

        scrapRate_list.layoutManager = LinearLayoutManager(this)
        scrapRate_list.adapter = scrapRateAdapter(getSampleData())
    }
//    private fun getSampleData(): List<ScrapRateItem> {
//        return listOf(
//            ScrapRateItem("John Doe", 1000),
//            ScrapRateItem("Jane Smith", 800),
//            ScrapRateItem("Bob Johnson", 600),
//            ScrapRateItem("Alice Brown", 400),
//            ScrapRateItem("Joe Davis", 200),
//            ScrapRateItem("John Doe", 1000),
//            ScrapRateItem("Jane Smith", 800),
//            ScrapRateItem("Bob Johnson", 600),
//            ScrapRateItem("Alice Brown", 400),
//            ScrapRateItem("Joe Davis", 200),
//            ScrapRateItem("John Doe", 1000),
//            ScrapRateItem("Jane Smith", 800),
//            ScrapRateItem("Bob Johnson", 600),
//            ScrapRateItem("Alice Brown", 400),
//            ScrapRateItem("Joe Davis", 200),
//            ScrapRateItem("John Doe", 1000),
//            ScrapRateItem("Jane Smith", 800),
//            ScrapRateItem("Bob Johnson", 600),
//            ScrapRateItem("Alice Brown", 400),
//            ScrapRateItem("Joe Davis", 200),
//            ScrapRateItem("John Doe", 1000),
//            ScrapRateItem("Jane Smith", 800),
//            ScrapRateItem("Bob Johnson", 600),
//            ScrapRateItem("Alice Brown", 400),
//            ScrapRateItem("Joe Davis", 200),
//        )
//    }
    private fun getSampleData(): List<ScrapRateItem> {
        return listOf(
            ScrapRateItem("Cell phone", 5.0),
            ScrapRateItem("Laptop", 20.0),
            ScrapRateItem("Computer monitor", 15.0),
            ScrapRateItem("Television (up to 32 inches)", 30.0),
            ScrapRateItem("Television (32-50 inches)", 50.0),
            ScrapRateItem("Television (over 50 inches)", 100.0),
            ScrapRateItem("Printer", 10.0),
            ScrapRateItem("Scanner", 5.0),
            ScrapRateItem("Keyboard", 2.0),
            ScrapRateItem("Mouse", 2.0),
            ScrapRateItem("External hard drive", 5.0),
            ScrapRateItem("Stereo system", 20.0),
            ScrapRateItem("DVD player", 5.0),
            ScrapRateItem("Video game console", 10.0),
            ScrapRateItem("Digital camera", 5.0),
            ScrapRateItem("Camcorder", 10.0),
            ScrapRateItem("Power drill", 5.0),
            ScrapRateItem("Vacuum cleaner", 10.0),
            ScrapRateItem("Microwave", 10.0),
            ScrapRateItem("Toaster", 2.0),
            ScrapRateItem("Blender", 5.0),
            ScrapRateItem("Electric razor", 2.0)
        )
    }
}