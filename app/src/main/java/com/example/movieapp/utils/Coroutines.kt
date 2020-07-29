package com.example.movieapp.utils

import kotlinx.coroutines.*

object Coroutines {

    fun<T:Any> ioThenMain(work:suspend(()->T?),callback:((T?)->Unit)) =
      CoroutineScope(Dispatchers.Main).launch {
          val data= CoroutineScope(Dispatchers.IO).async rt@{  return@rt work()}.await()
          callback(data)
      }


}