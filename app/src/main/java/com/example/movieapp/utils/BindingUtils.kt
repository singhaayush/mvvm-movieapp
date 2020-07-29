package com.example.movieapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

private const val base_url="https://image.tmdb.org/t/p/w300"
@BindingAdapter("poster_image")
fun loadImage(view:ImageView,url:String)
{
Glide.with(view).load("$base_url$url").into(view)
}