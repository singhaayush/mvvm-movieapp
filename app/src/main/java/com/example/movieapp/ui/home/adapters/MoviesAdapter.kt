package com.example.movieapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.MovieListLayoutBinding
import com.example.movieapp.model.moviedata.Result

class MoviesAdapter(private val movieList: List<Result>) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    class MoviesViewHolder(movieListLayoutBinding: MovieListLayoutBinding) :
        RecyclerView.ViewHolder(movieListLayoutBinding.root) {
        var movieListLayoutBinding=movieListLayoutBinding

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.movie_list_layout,
                parent,
                false
            )
        )


    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.movieListLayoutBinding.result=movieList[position]
    }


}