package com.example.movieapp.network


import com.example.movieapp.model.moviedata.MovieEntity
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

//6bab6920aae24c6f79377a7786622ab6
//https://api.themoviedb.org/3/movie/
interface TheMovieDBApi {

    @GET("popular")
    suspend fun getAllPopularMovies(@QueryMap map: HashMap<String, String>):Response<MovieEntity>

}