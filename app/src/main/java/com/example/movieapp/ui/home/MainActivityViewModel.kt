package com.example.movieapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.utils.Coroutines
import kotlinx.coroutines.Job
import com.example.movieapp.model.moviedata.MovieEntity as MovieEntity

class MainActivityViewModel(private val repository: MovieRepository): ViewModel() {
    lateinit var job: Job
    private var _movieEntity= MutableLiveData<MovieEntity>()

    val movieEntity :LiveData<MovieEntity>
    get() =_movieEntity

     fun getAllPopularMovies()
    {
        job =Coroutines.ioThenMain(
            {repository.getPopularMovies()},
            {_movieEntity.value=it}
        )
    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized)job.cancel()
    }
}