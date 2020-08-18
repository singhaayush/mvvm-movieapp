package com.example.movieapp.ui.web

import android.Manifest
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_web.*
import pub.devrel.easypermissions.EasyPermissions


class ClassPlusLiveChromeClient(private val context: WebActivity) : WebChromeClient() {

    companion object{

        private const val TAG = "ClassPlusLiveChromeClie"
        lateinit var mProgressBar:ProgressBar
    }
    init {
        mProgressBar=context.m_progress
    }



    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        if (newProgress == 100) {
            mProgressBar.visibility = View.INVISIBLE
        } else {
            if (mProgressBar.getVisibility() === View.INVISIBLE) {
                mProgressBar.visibility = View.VISIBLE
            }
            mProgressBar.progress = newProgress
        }

    }

    override fun onPermissionRequest(request: PermissionRequest?) {
        request?.grant(request.resources)
    }


    override fun onCloseWindow(window: WebView?) {
        //super.onCloseWindow(window)
        Log.d(TAG, "onCloseWindow: ")
        window?.clearCache(true)
    }

    override fun onReachedMaxAppCacheSize(
        requiredStorage: Long,
        quota: Long,
        quotaUpdater: WebStorage.QuotaUpdater?
    ) {
        context.finish()
        super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater)
    }

    
    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        if(consoleMessage?.message()?.contains("[object OverconstrainedError]")!!)
        {
            if(CookieManager.getInstance().hasCookies())
            {
                CookieManager.getInstance().removeAllCookies(ValueCallback {  })
                CookieManager.getInstance().removeSessionCookies(ValueCallback {  })


            }
            context.finish()
        }
        Log.d(TAG, "onConsoleMessage: ${consoleMessage?.message()}")
        return super.onConsoleMessage(consoleMessage)
    }





}