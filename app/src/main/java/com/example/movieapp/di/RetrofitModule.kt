package com.example.movieapp.di

import com.example.movieapp.data.network.TheMovieDBApi
import com.example.movieapp.repository.MovieRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder():Gson{
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gson:Gson):Retrofit.Builder{

        return Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/")
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideTmdbApiService(retrofit: Retrofit.Builder):TheMovieDBApi
    {
        return retrofit.build().create(TheMovieDBApi::class.java)
    }
    @Singleton
    @Provides
    fun provideRepository(api:TheMovieDBApi):MovieRepository{
        return  MovieRepository(api)
    }
}