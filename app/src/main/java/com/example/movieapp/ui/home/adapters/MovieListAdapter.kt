package com.example.movieapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.model.moviedata.Result

class MovieListAdapter (private val movieList:List<Result>): RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    class MovieListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var mTitle = itemView.findViewById<TextView?>(R.id.search_movie_title)
        var mRatings = itemView.findViewById<TextView?>(R.id.search_movie_ratings)
        var mOverview = itemView.findViewById<TextView?>(R.id.search_movie_overview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.movie_list_layout,parent,false)
        return MovieListViewHolder(view)
    }

    override fun getItemCount(): Int {
      return movieList.size
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.mRatings?.text= movieList[position].voteAverage.toString()
        holder.mTitle?.text=movieList[position].title
        holder.mOverview?.text=movieList[position].overview
    }
}