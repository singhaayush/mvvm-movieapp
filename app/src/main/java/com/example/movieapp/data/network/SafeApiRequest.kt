package com.example.movieapp.data.network

import android.util.Log
import com.example.movieapp.internal.NoConnectivityException
import okio.IOException
import retrofit2.Response

abstract class SafeApiRequest {
    private val TAG = "SafeApiRequest"
   suspend fun <T:Any> apiRequest(call: suspend()->Response<T>):T
   {
       try
       {val response=call.invoke()
           if(response.isSuccessful)
           {
               return response.body()!!
           }
           else{
               throw ApiException(response.code().toString()+"Response failed")
           }
       }
       catch (e:NoConnectivityException)
       {
           Log.d(TAG, "apiRequest: no internet ")
           throw NoConnectivityException()
       }
   }


}

class ApiException (message:String):IOException(message)