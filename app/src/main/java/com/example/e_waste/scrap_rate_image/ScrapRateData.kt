package com.example.e_waste.scrap_rate_image

import com.example.e_waste.R

class ScrapRateData {
    fun loadAffirmations() : List<Affirmation>{
        return listOf<Affirmation>(
            Affirmation("Cell phones \n$5", R.drawable.image1, "Smart Phones \n$10", R.drawable.image2),
            Affirmation("Tablets \n$15", R.drawable.image3, "Laptops \n$20", R.drawable.image4),
            Affirmation("Desktop computers \n$25", R.drawable.image5, "Computer monitors \n$15", R.drawable.image6),
            Affirmation("Printers \n$10", R.drawable.image7, "Scanners \n$10", R.drawable.image8),
            Affirmation("Keyboards \n$5", R.drawable.image9, "Mice \n$5", R.drawable.image10),
            Affirmation("Speakers \n$5", R.drawable.image11, "Headphones \n$5", R.drawable.image12),
            Affirmation("Digital cameras \n$10", R.drawable.image13, "Video cameras \n$15", R.drawable.image14),
            Affirmation("MP3 players \n$5", R.drawable.image15, "DVD players \n$10", R.drawable.image16),
            Affirmation("TVs (under 32 inches) \n$15", R.drawable.image17, "TVs (32 inches and over) \n$25", R.drawable.image18),
            Affirmation("Gaming consoles \n$15", R.drawable.image19, "External hard drives \n$10", R.drawable.image20),
        )
    }
}