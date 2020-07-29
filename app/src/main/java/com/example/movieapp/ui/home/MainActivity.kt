package com.example.movieapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.internal.NoConnectivityException

import com.example.movieapp.network.TMDBServiceBuilder
import com.example.movieapp.network.TheMovieDBApi


import kotlinx.android.synthetic.main.activity_main.*


import com.example.movieapp.model.moviedata.Result
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.ui.home.adapters.MoviesAdapter
import com.example.movieapp.utils.Toast
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var factory: MainActivityViewModelFactory
    private val TAG = "MainActivity"
    lateinit var _movieList: List<Result>
    lateinit var adapter: MoviesAdapter

    //  lateinit var movieList: MovieEntity
    lateinit var movieRepository: MovieRepository
    lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_main)
        try {
            val movieService = TMDBServiceBuilder(this).buildService(TheMovieDBApi::class.java)
            movieRepository = MovieRepository(movieService)
            factory = MainActivityViewModelFactory(movieRepository)
            viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
                this.setLifecycleOwner(this@MainActivity)
                this.mainviewmodel = viewModel as MainActivityViewModel

                (viewModel as MainActivityViewModel).getAllPopularMovies()
                (viewModel as MainActivityViewModel).movieEntity.observe(this@MainActivity, Observer {
                    var data = it.results
                    movie_list_recycler.also {
                        it.layoutManager = LinearLayoutManager(this@MainActivity)
                        it.setHasFixedSize(true)
                        adapter = MoviesAdapter(data)
                        it.adapter = adapter
                    }
                })
            }

        }
        catch (e:NoConnectivityException)
        {
            Toast("No Internet !!")
        }


    }


}