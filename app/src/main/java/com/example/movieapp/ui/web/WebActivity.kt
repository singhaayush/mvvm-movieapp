package com.example.movieapp.ui.web

import android.Manifest
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.webkit.*
import android.webkit.WebSettings.LOAD_CACHE_ELSE_NETWORK
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.utils.Toast
import kotlinx.android.synthetic.main.activity_web.*
import pub.devrel.easypermissions.EasyPermissions


class WebActivity : AppCompatActivity(){

    private var mPermissionRequest: PermissionRequest? = null
    private val url:String="https://live.teach-r.com/#/welcome"

    private val TAG = "WebActivity"
    companion object{
        lateinit var mProgressBar: ProgressBar

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

//       CookieManager.getInstance().removeSessionCookies(ValueCallback { Toast(it.toString()) })

        window.setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        );
        mProgressBar = findViewById(R.id.m_progress)
        mProgressBar.max = 90
        mProgressBar.progress = 0
        mProgressBar.visibility = View.VISIBLE


        web_view.also {
            it.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY;
            it.isScrollbarFadingEnabled = true;

            it.clearCache(true)
            it.webViewClient=object :WebViewClient(){
                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler,
                    error: SslError?
                ) {
                    Log.d(TAG, "onReceivedSslError: ${error.toString()}")
                    handler.proceed()
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                }

            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
               it.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else {
                it.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
            it.webChromeClient = ClassPlusLiveChromeClient(this)


            }



        val webSettings: WebSettings = web_view.settings
        webSettings.also {
            it.javaScriptEnabled = true
            it.setAppCacheEnabled(true)
            it.cacheMode=LOAD_CACHE_ELSE_NETWORK
            it.domStorageEnabled = true
            it.useWideViewPort = true;
            it.allowFileAccessFromFileURLs=true
            it.allowUniversalAccessFromFileURLs=true
            it.loadWithOverviewMode = true;
            it.useWideViewPort = true;
            it.setSupportZoom(true);
            it.builtInZoomControls = false;
            it.setRenderPriority(WebSettings.RenderPriority.HIGH)
            it.layoutAlgorithm=WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            it.saveFormData=true
            it.savePassword=true
            it.setGeolocationEnabled(true)
            it.useWideViewPort=true



        }
        web_view.loadUrl(url)
//        val cookies = CookieManager.getInstance().getCookie(url)
//        Log.d(TAG, "All the cookies in a string:$cookies")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBackPressed() {

        if (web_view.canGoBack()) {
            web_view.goBack()
        } else {
            super.onBackPressed()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)

    }



}





