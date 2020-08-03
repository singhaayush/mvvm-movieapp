package com.example.movieapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.repository.MovieRepository
import javax.inject.Inject

class MainActivityViewModelFactory @Inject constructor(
    private val repository: MovieRepository
) :ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repository) as T
    }
}