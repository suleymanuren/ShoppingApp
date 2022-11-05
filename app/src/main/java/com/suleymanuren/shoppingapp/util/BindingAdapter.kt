package com.suleymanuren.shoppingapp.util

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


// THIS PAGE IS FOR ITEM LIST XML
object BindingAdapter {

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(view: ImageView, url: String?) {
        Glide.with(view.context)
            .load("$url")
            .into(view)
    }

    // FOR EXAMPLE PRICE IS DOUBLE AT PRODUCT MODEL BUT WE WANT TO SHOW IT AS STRING
    // SO WE USE THIS BINDING ADAPTER TO CONVERT IT TO STRING
    @JvmStatic
    @BindingAdapter("productPrice")
    fun productPrice(view: TextView, price: Double?) {
        view.text = "$${price}"
    }
    @JvmStatic
    @BindingAdapter("productRating")
    fun productRating(view: RatingBar, rate: Double?) {
        view.rating = rate?.toFloat() ?: 0f
    }
    @JvmStatic
    @BindingAdapter("productRatingCount")
    fun productRatingCount(view: TextView, count: Int?) {
        view.text = count.toString()
    }
    @JvmStatic
    @BindingAdapter("productCount")
    fun productCount(view: TextView, count: Double?) {
        view.text = count.toString()
    }


}