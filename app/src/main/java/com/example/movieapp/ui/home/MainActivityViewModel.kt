package com.example.movieapp.ui.home

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.movieapp.internal.NoConnectivityException
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.utils.Coroutines
import kotlinx.coroutines.Job
import javax.inject.Inject
import com.example.movieapp.data.moviedata.MovieEntity as MovieEntity


class MainActivityViewModel @Inject constructor(val repository: MovieRepository


): ViewModel() {


    lateinit var job: Job
    private var _movieEntity= MutableLiveData<MovieEntity>()

    val movieEntity :LiveData<MovieEntity>
    get() =_movieEntity

     fun getAllPopularMovies()
    {
        job =Coroutines.ioThenMain(
            {
                try {
                    repository.getPopularMovies()
                }
                catch (e:NoConnectivityException)
                {
                    throw NoConnectivityException()
                }

            },
            {_movieEntity.value=it}
        )
    }

    override fun onCleared() {
        super.onCleared()
        if(::job.isInitialized)job.cancel()
    }
}