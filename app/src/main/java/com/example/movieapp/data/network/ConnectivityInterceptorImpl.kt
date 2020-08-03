package com.example.movieapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.movieapp.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(var context:Context):ConnectivityInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
       if(!isOnline())
       {
           throw NoConnectivityException()
       }
        return chain.proceed(chain.request())
    }
    fun isOnline():Boolean{
      val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE)
        as ConnectivityManager

        val networkInfo=connectivityManager.activeNetworkInfo
        return networkInfo!=null && networkInfo.isConnected
    }
}