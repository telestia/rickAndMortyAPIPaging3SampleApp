package com.example.rickandmortypagingtestmvvmapp.common.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.rickandmortypagingtestmvvmapp.R

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {

    val optionsBuilder =  RequestOptions().placeholder(placeHolderProgressBar(this.context))
        .error(R.mipmap.ic_launcher_round)

    if (url.isNullOrEmpty()) return
    Glide.with(this).load(url).placeholder(placeHolderProgressBar(this.context)) .into(this)
}
fun placeHolderProgressBar(context: Context) : CircularProgressDrawable{
    return  CircularProgressDrawable(context).apply {
        strokeWidth = 2f
        centerRadius = 5f
        start()
    }
}