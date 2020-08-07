package com.example.movieapp.di

import android.content.Context
import android.util.Log
import com.example.movieapp.data.network.ConnectivityInterceptor
import com.example.movieapp.data.network.ConnectivityInterceptorImpl
import com.example.movieapp.data.network.TheMovieDBApi
import com.example.movieapp.internal.NoConnectivityException
import com.example.movieapp.repository.MovieRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {
    private const val TAG = "RetrofitModule"
//    @Singleton
//    @Provides
//    fun provideConnectivityInterceptor(connectivityInterceptorImpl: ConnectivityInterceptorImpl): ConnectivityInterceptor{
//        return connectivityInterceptorImpl
//    }
//


    @LoggerInterceptor
    @Singleton
    @Provides
    fun providesOkhttpLogger():OkHttpClient.Builder{
        val logger= HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val builder=OkHttpClient.Builder().addInterceptor(logger)
        return builder
    }



    @InternetConnectivityInterceptor
    @Singleton
    @Provides
    fun providesOkhttpInternet(@LoggerInterceptor okHttpClient: OkHttpClient.Builder,connectivityInterceptorImpl: ConnectivityInterceptorImpl):OkHttpClient{

        try {
            okHttpClient.addInterceptor(connectivityInterceptorImpl)
            return okHttpClient.build()
        }
        catch (e:NoConnectivityException)
        {
            Log.d(TAG, "providesOkhttpInternet: No Internet found")
        }

        return okHttpClient.build()
    }
    @Singleton
    @Provides
    fun provideGsonBuilder():Gson{
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gson:Gson,@InternetConnectivityInterceptor okHttpClient: OkHttpClient):Retrofit.Builder{

        return Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
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
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggerInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InternetConnectivityInterceptor