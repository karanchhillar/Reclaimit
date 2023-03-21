package com.example.e_waste.scrap_rate_image

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Affirmation(
//    @StringRes val stringResourceId: Int,
    val stringResourceId: String,
    @DrawableRes val imageResourceId: Int,
//    @StringRes val stringResourceId1: Int,
    val stringResourceId1: String,
    @DrawableRes val imageResourceId1: Int
    )
{

}