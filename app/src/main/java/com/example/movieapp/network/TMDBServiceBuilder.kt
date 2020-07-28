package com.example.movieapp.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body

object TMDBServiceBuilder {
    private val API_KEY="6bab6920aae24c6f79377a7786622ab6"
    private const val url= "https://api.themoviedb.org/3/movie/"
    private val requestInterceptor by lazy {
        Interceptor{ chain->
        val url= chain.request().url.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val request=chain.request().newBuilder().url(url).build()

        return@Interceptor chain.proceed(request)

    }
    }

    private val logger=HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private var ohHttp=OkHttpClient.Builder().addInterceptor(logger).addInterceptor(
        requestInterceptor)

    private val builder=Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(ohHttp.build())

    private val retrofit= builder.build()

    fun <T> buildService(serviceType:Class<T>):T
    {
        return retrofit.create(serviceType)
    }
}