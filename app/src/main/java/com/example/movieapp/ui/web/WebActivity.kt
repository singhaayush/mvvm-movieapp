package com.example.movieapp.ui.web

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.example.movieapp.R
import com.example.movieapp.utils.Toast
import kotlinx.android.synthetic.main.activity_web.*


class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        web_view.also {
            it.webChromeClient= WebChromeClient()
            it.loadUrl("https://www.google.com")
        }

        val webSettings:WebSettings =web_view.settings
        webSettings.also { it.javaScriptEnabled=true }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBackPressed() {
          web_view.webChromeClient?.getVisitedHistory(ValueCallback {  })
        if(web_view.canGoBack()){web_view.goBack()}else{  super.onBackPressed()}

    }
}