package com.example.movieapp.ui.web

import android.Manifest
import android.annotation.TargetApi
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.webkit.*
import android.widget.ProgressBar
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import kotlinx.android.synthetic.main.activity_web.*
import pub.devrel.easypermissions.EasyPermissions
import java.lang.Exception


class WebActivity : AppCompatActivity(),EasyPermissions.PermissionCallbacks {

    private var mPermissionRequest: PermissionRequest? = null

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

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        );
        mProgressBar = findViewById(R.id.m_progress)
        mProgressBar.max = 90
        mProgressBar.progress = 0
        mProgressBar.visibility = View.VISIBLE
        web_view.also {
            it.webChromeClient = object : WebChromeClient() {
                override fun onPermissionRequestCanceled(request: PermissionRequest?) {
                    super.onPermissionRequestCanceled(request)

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
                    //super.onPermissionRequest(request)
                    Log.i(TAG, "onPermissionRequest")
                    request?.grant(request.resources)
//
//                    if(hasCameraPermission()&&hasAudioPermission())
//                        return
//                    mPermissionRequest = request
//                    val requestedResources: Array<String> = request!!.resources
//                    for (r in requestedResources) {
//
//                            when(r) {
//                                PermissionRequest.RESOURCE_VIDEO_CAPTURE -> {
//                                    val alertDialogBuilder: android.app.AlertDialog.Builder? =
//                                        android.app.AlertDialog.Builder(
//                                            this@WebActivity
//                                        )
//                                            .setTitle("Allow Permission to camera")
//                                            .setPositiveButton("Allow",
//                                                DialogInterface.OnClickListener { dialog, which ->
//                                                    dialog.dismiss()
//                                                    try {
//                                                        mPermissionRequest!!.grant(
//                                                            arrayOf<String>(
//                                                                PermissionRequest.RESOURCE_VIDEO_CAPTURE
//                                                            )
//                                                        )
//                                                        Log.d(TAG, "Granted")
//                                                    }
//                                                    catch (e:Exception)
//                                                    {
//                                                        Log.d(TAG, "onPermissionRequest: ${e.message.toString()}")
//                                                    }
//
//
//                                                })
//                                            .setNegativeButton("Deny",
//                                                DialogInterface.OnClickListener { dialog, which ->
//                                                    dialog.dismiss()
//                                                    try {
//                                                        mPermissionRequest!!.deny()
//                                                        Log.d(TAG, "Denied")
//                                                    }
//                                                    catch (e:Exception){
//                                                        Log.d(TAG, "onPermissionRequest: ${e.message.toString()}")
//
//                                                    }
//
//                                                })
//                                    val alertDialog: android.app.AlertDialog? =
//                                        alertDialogBuilder?.create()
//                                    alertDialog?.show()
//
//                                }
//                                PermissionRequest.RESOURCE_AUDIO_CAPTURE->{
//                                    val alertDialogBuilder: android.app.AlertDialog.Builder? =
//                                        android.app.AlertDialog.Builder(
//                                            this@WebActivity
//                                        )
//                                            .setTitle("Allow Permissions to audio ")
//                                            .setPositiveButton("Allow",
//                                                DialogInterface.OnClickListener { dialog, which ->
//                                                    dialog.dismiss()
//                                                    try {
//                                                        mPermissionRequest!!.grant(
//                                                            arrayOf<String>(
//                                                                PermissionRequest.RESOURCE_AUDIO_CAPTURE
//                                                            )
//                                                        )
//                                                        Log.d(TAG, "Granted")
//                                                    }
//                                                    catch (e:Exception)
//                                                    {
//
//                                                    }
//
//                                                })
//                                            .setNegativeButton("Deny",
//                                                DialogInterface.OnClickListener { dialog, which ->
//                                                    dialog.dismiss()
//                                                    try {
//                                                        mPermissionRequest!!.deny()
//                                                        Log.d(TAG, "Denied")
//                                                    }
//                                                    catch (e:Exception)
//                                                    {
//
//                                                    }
//
//                                                })
//                                    val alertDialog: android.app.AlertDialog? =
//                                        alertDialogBuilder?.create()
//                                    alertDialog?.show()
//
//
//                                }
//                            }
//                    }
                }
            }
            if(!(hasAudioPermission()&&hasCameraPermission()))
            {

                EasyPermissions.requestPermissions(
                    this,
                    "This app needs access to your camera so you can take pictures.",
                     REQUEST_CAMERA_PERMISSION,
                    *PERM_CAMERA
                )
                EasyPermissions.requestPermissions(
                    this,
                    "This app needs access to your audio so you can use microphone",
                    REQUEST_AUDIO_PERMISSION,
                    *PERM_AUDIO
                )


            }

            it.loadUrl("https://live.teach-r.com/#/welcome")


        }


        val webSettings: WebSettings = web_view.settings
        webSettings.also {
            it.javaScriptEnabled = true
            it.setRenderPriority(WebSettings.RenderPriority.HIGH)
            it.setRenderPriority(WebSettings.RenderPriority.HIGH);
            it.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK;
            it.domStorageEnabled = true
            it.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            it.domStorageEnabled = true;
            it.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS;
            it.useWideViewPort = true;
            it.setSavePassword(true);
            it.setSaveFormData(true);
            it.setEnableSmoothTransition(true);
            it.allowFileAccessFromFileURLs=true
            it.allowUniversalAccessFromFileURLs=true


        }
//        web_view.loadUrl("https://live.teach-r.com/#/session")

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





