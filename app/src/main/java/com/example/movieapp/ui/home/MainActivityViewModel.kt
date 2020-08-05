package com.example.movieapp.ui.home

import android.util.Log
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


class MainActivityViewModel @ViewModelInject constructor(
    private val repository: MovieRepository
    //@Assisted savedStateHandle: SavedStateHandle


) : ViewModel() {
    private val status:Boolean=true
    private  val TAG = "MainActivityViewModel"
    lateinit var job: Job
    private var _movieEntity = MutableLiveData<MovieEntity>()

    val movieEntity: LiveData<MovieEntity>
        get() = _movieEntity

    fun getAllPopularMovies() {
        job = Coroutines.ioThenMain(
            {
                try {
                    repository.getPopularMovies()
                } catch (e: NoConnectivityException) {

                    Log.d(TAG, "getAllPopularMovies: no internet ")
                    //throw NoConnectivityException()
                }

            },
            {try {
                _movieEntity.value = it as MovieEntity?
            }
            catch (e: NoConnectivityException) {

                Log.d(TAG, "getAllPopularMovies: no internet ")
                throw NoConnectivityException()
            }

                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (::job.isInitialized) job.cancel()
    }
}