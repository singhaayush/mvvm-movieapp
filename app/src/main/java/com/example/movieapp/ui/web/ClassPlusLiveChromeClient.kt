package com.example.movieapp.ui.web

import android.Manifest
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_web.*
import pub.devrel.easypermissions.EasyPermissions


class ClassPlusLiveChromeClient(private val context: WebActivity) : WebChromeClient() ,EasyPermissions.PermissionCallbacks {

    companion object{
        private const val REQUEST_CAMERA_PERMISSION = 1
        private const val REQUEST_AUDIO_PERMISSION=2
        private val PERM_CAMERA =
            arrayOf<String>(Manifest.permission.CAMERA,Manifest.permission.CAMERA)
        private val PERM_AUDIO= arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAPTURE_AUDIO_OUTPUT,Manifest.permission.MODIFY_AUDIO_SETTINGS)
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
        //   super.onPermissionRequest(request)
        if(hasAudioPermission()&&hasCameraPermission())
            request?.grant(request.resources)
        else
        {

            EasyPermissions.requestPermissions(
                context ,
                "This app needs access to your camera so you can take pictures.",
                REQUEST_CAMERA_PERMISSION,
                *PERM_CAMERA
            )
            EasyPermissions.requestPermissions(
                context,
                "This app needs access to your audio so you can use microphone",
                REQUEST_AUDIO_PERMISSION,
                *PERM_AUDIO
            )

            //  request?.grant(request.resources)
            super.onPermissionRequest(request)

        }
    }
    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(
            context,
            *PERM_CAMERA
        )
    }
    private  fun hasAudioPermission():Boolean{
        return EasyPermissions.hasPermissions(
            context,
            *PERM_AUDIO
        )
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

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsDenied: ")
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsGranted: ")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        context.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }



}