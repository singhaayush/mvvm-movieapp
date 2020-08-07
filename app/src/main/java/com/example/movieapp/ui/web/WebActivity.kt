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


class WebActivity : AppCompatActivity(),EasyPermissions.PermissionCallbacks {

    private var mPermissionRequest: PermissionRequest? = null
    private val url:String="https://live.teach-r.com/#/welcome"

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 1
        private const val REQUEST_AUDIO_PERMISSION=2
        private val PERM_CAMERA =
            arrayOf<String>(Manifest.permission.CAMERA)
        private val PERM_AUDIO= arrayOf(Manifest.permission.RECORD_AUDIO,Manifest.permission.CAPTURE_AUDIO_OUTPUT)
    }

    private val TAG = "WebActivity"
    lateinit var mProgressBar: ProgressBar
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

                       if(!(hasAudioPermission()&&hasCameraPermission()))
                       {

                           EasyPermissions.requestPermissions(
                               this@WebActivity,
                               "This app needs access to your camera so you can take pictures.",
                               REQUEST_CAMERA_PERMISSION,
                               *PERM_CAMERA
                           )
                           EasyPermissions.requestPermissions(
                               this@WebActivity,
                               "This app needs access to your audio so you can use microphone",
                               REQUEST_AUDIO_PERMISSION,
                               *PERM_AUDIO
                           )



                       }

                    super.onPageStarted(view, url, favicon)
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
            it.domStorageEnabled = true;
            it.useWideViewPort = true;
            it.allowFileAccessFromFileURLs=true
            it.allowUniversalAccessFromFileURLs=true
            it.loadWithOverviewMode = true;
            it.useWideViewPort = true;
            it.setSupportZoom(true);
            it.builtInZoomControls = false;

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

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }
    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(
            this,
            *PERM_CAMERA
        )
    }
    private  fun hasAudioPermission():Boolean{
        return EasyPermissions.hasPermissions(
            this,
            *PERM_AUDIO
        )
    }

}





