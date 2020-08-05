package com.example.movieapp.utils

import com.example.movieapp.internal.NoConnectivityException
import kotlinx.coroutines.*
import java.lang.ClassCastException

object Coroutines {

    fun<T:Any> ioThenMain(work:suspend(()->T?),callback:((T?)->Unit)) =
      CoroutineScope(Dispatchers.Main).launch {
          try {
              val data= CoroutineScope(Dispatchers.IO).async rt@{  return@rt work()}.await()
              callback(data)
          }catch (e:NoConnectivityException)
          {
              throw NoConnectivityException()
          }
          catch (e:ClassCastException)
          {
             // throw NoConnectivityException()
          }

      }


}