package com.example.movieapp.ui.web

import android.Manifest
import android.util.Log
import android.view.View
import android.webkit.GeolocationPermissions
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import pub.devrel.easypermissions.EasyPermissions


class ClassPlusLiveChromeClient(private val context: WebActivity) : WebChromeClient() {
    private val PERM_CAMERA =
        arrayOf<String>(Manifest.permission.CAMERA)
    private val PERM_AUDIO= arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAPTURE_AUDIO_OUTPUT)
    private val TAG = "ClassPlusLiveChromeClie"
    val mProgressBar = context.mProgressBar
    override fun onConsoleMessage(
        message: String?,
        lineNumber: Int,
        sourceID: String?
    ) {
        super.onConsoleMessage(message, lineNumber, sourceID)
        // Toast(message.toString())
        Log.d(TAG, "onConsoleMessage: ${message.toString()}")
        // it.reload()
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
        Log.i(TAG, "onPermissionRequest")
        if (hasAudioPermission() && hasAudioPermission()) {
            super.onPermissionRequest(request)


        }

        request?.grant(request.resources)
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
    override fun onGeolocationPermissionsShowPrompt(
        origin: String?,
        callback: GeolocationPermissions.Callback
    ) {
        // callback.invoke(String origin, boolean allow, boolean remember);
        callback.invoke(origin, true, false)
    }

}