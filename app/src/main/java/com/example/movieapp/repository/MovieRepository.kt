package com.example.movieapp.repository

import com.example.movieapp.model.moviedata.MovieEntity
import com.example.movieapp.network.SafeApiRequest
import com.example.movieapp.network.TheMovieDBApi
import retrofit2.Response

class MovieRepository(
    private val api:TheMovieDBApi

): SafeApiRequest() {


//    suspend fun getPopularMovies():Response<MovieEntity>{
//        var map=HashMap<String,String>()
//       // map["api_key"]="6bab6920aae24c6f79377a7786622ab6" should not be passed like this
//
//
//        map["language"]="en-US"
//        map["page"]="1"
//        return api.getAllPopularMovies(map)
//
//    }

    suspend fun getPopularMovies()=apiRequest {
        var map=HashMap<String,String>()
        map["language"]="en-US"
        map["page"]="2"
        api.getAllPopularMovies(map) }

}