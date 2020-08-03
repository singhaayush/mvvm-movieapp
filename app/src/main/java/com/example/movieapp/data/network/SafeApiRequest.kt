package com.example.movieapp.data.network

import okio.IOException
import retrofit2.Response

abstract class SafeApiRequest {
   suspend fun <T:Any> apiRequest(call: suspend()->Response<T>):T
   {
       val response=call.invoke()
       if(response.isSuccessful)
       {
           return response.body()!!
       }
       else{
           throw ApiException(response.code().toString())
       }
   }
}

class ApiException (message:String):IOException(message)