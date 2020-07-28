package com.example.movieapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.model.moviedata.MovieEntity
import com.example.movieapp.network.TMDBServiceBuilder
import com.example.movieapp.network.TheMovieDBApi
import com.example.movieapp.ui.home.adapters.MovieListAdapter
import com.example.movieapp.utils.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import com.example.movieapp.model.moviedata.Result

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var _movieList: List<Result>
    lateinit var adapter:MovieListAdapter
    lateinit var movieList: MovieEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movie_list_recycler.layoutManager=LinearLayoutManager(this)
        movie_list_recycler.setHasFixedSize(true)

       // createServiceAndSetUi()

        //used coroutines here
        GlobalScope.launch (Dispatchers.IO){
            fetAndSetupUI()
            withContext(Dispatchers.Main,{movie_list_recycler.adapter=adapter})

        }

    }

    //without coroutines
    private fun createServiceAndSetUi() {
        val movieService=TMDBServiceBuilder.buildService(TheMovieDBApi::class.java)

        var map=HashMap<String,String>()
       // map["api_key"]="6bab6920aae24c6f79377a7786622ab6" should not be passed like this


        map["language"]="en-US"
        map["page"]="1"

        val requestCall=movieService.getAllPopularMovies(map)

        requestCall.enqueue(object : Callback<MovieEntity>{
            override fun onFailure(call: Call<MovieEntity>, t: Throwable) {
             Toast(t.message.toString())
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }

            override fun onResponse(call: Call<MovieEntity>, response: Response<MovieEntity>) {
                if(response.isSuccessful)
                {

                    movieList=response.body()!!
                    _movieList=movieList.results
                    adapter= MovieListAdapter(_movieList)
                    movie_list_recycler.adapter=adapter
                }
            }

        })
    }

    //with coroutine
    suspend fun fetAndSetupUI()
    {
        val movieService=TMDBServiceBuilder.buildService(TheMovieDBApi::class.java)

        var map=HashMap<String,String>()
       // map["api_key"]="6bab6920aae24c6f79377a7786622ab6"
        map["language"]="en-US"
        map["page"]="1"

        movieList=movieService.getAllPopularMovies(map).await()
        _movieList=movieList.results
        adapter= MovieListAdapter(_movieList)
       // movie_list_recycler.adapter=adapter

    }
}