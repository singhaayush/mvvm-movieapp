package com.example.movieapp.repository

import android.util.Log
import com.example.movieapp.internal.NoConnectivityException
import com.example.movieapp.data.network.SafeApiRequest
import com.example.movieapp.data.network.TheMovieDBApi
import javax.inject.Inject

class MovieRepository constructor(
   val api:TheMovieDBApi

): SafeApiRequest() {


    private  val TAG = "MovieRepository"


    suspend fun getPopularMovies()=apiRequest {
        var map=HashMap<String,String>()
        map["language"]="en-US"
        map["page"]="2"
        map["api_key"]="6bab6920aae24c6f79377a7786622ab6"
        try {

            api.getAllPopularMovies(map) }
        catch(e:NoConnectivityException){
            Log.d(TAG, "getPopularMovies: Internet Missing")
              throw NoConnectivityException()
        }
        }


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

}