package com.example.movieapp.ui.home

import android.content.Intent
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

import com.example.movieapp.data.network.TMDBServiceBuilder
import com.example.movieapp.data.network.TheMovieDBApi


import kotlinx.android.synthetic.main.activity_main.*


import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.ui.home.adapters.MoviesAdapter
import com.example.movieapp.ui.web.WebActivity
import com.example.movieapp.utils.Toast
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


     @Inject
     lateinit var movieService:TheMovieDBApi
      lateinit var adapter: MoviesAdapter
      @Inject
      lateinit var repository:MovieRepository
     lateinit var factory:MainActivityViewModelFactory
    lateinit var movieRepository: MovieRepository
    lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        try {
            //movieService = TMDBServiceBuilder(this).buildService(TheMovieDBApi::class.java)
            movieRepository = MovieRepository(movieService)
            factory =MainActivityViewModelFactory(repository)
            viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)

          //  viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

                .apply {
                    this.setLifecycleOwner(this@MainActivity)
                    this.mainviewmodel = viewModel as MainActivityViewModel

                    (viewModel as MainActivityViewModel).getAllPopularMovies()
                    (viewModel as MainActivityViewModel).movieEntity.observe(
                        this@MainActivity,
                        Observer {
                            var data = it.results
                            movie_list_recycler.also {
                                it.layoutManager = LinearLayoutManager(this@MainActivity)
                                it.setHasFixedSize(true)
                                adapter = MoviesAdapter(data)
                                it.adapter = adapter
                            }
                        })
                }

        } catch (e: NoConnectivityException) {
            Toast("No Internet !!")
        } catch (exception: Exception) {
            Toast(exception.message.toString())
        }

        web_view_btn?.setOnClickListener { startActivity(Intent(this,WebActivity::class.java)) }
    }


}