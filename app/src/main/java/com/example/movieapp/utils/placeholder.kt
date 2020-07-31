package com.example.movieapp.utils

import android.Manifest
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks


class MainActivity : AppCompatActivity(), PermissionCallbacks {
    var myWebView: WebView? = null
    private val TAG = "TEST"
    private var mPermissionRequest: PermissionRequest? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myWebView = WebView(this)
        myWebView!!.settings.javaScriptEnabled = true
        myWebView!!.settings.allowFileAccessFromFileURLs = true
        myWebView!!.settings.allowUniversalAccessFromFileURLs = true

        //myWebView.setWebViewClient(new WebViewClient());
        myWebView!!.setWebChromeClient(object : WebChromeClient() {
            // Grant permissions for cam
            override fun onPermissionRequest(request: PermissionRequest) {
                Log.i(TAG, "onPermissionRequest")
                mPermissionRequest = request
                val requestedResources: Array<String> = request.resources
                for (r in requestedResources) {
                    if (r == PermissionRequest.RESOURCE_VIDEO_CAPTURE) {
                        // In this sample, we only accept video capture request.
                        val alertDialogBuilder: android.app.AlertDialog.Builder? = android.app.AlertDialog.Builder(
                            this@MainActivity
                        )
                            .setTitle("Allow Permission to camera")
                            .setPositiveButton("Allow",
                                DialogInterface.OnClickListener { dialog, which ->
                                    dialog.dismiss()
                                    mPermissionRequest!!.grant(
                                        arrayOf<String>(
                                            PermissionRequest.RESOURCE_VIDEO_CAPTURE
                                        )
                                    )
                                    Log.d(TAG, "Granted")
                                })
                            .setNegativeButton("Deny",
                                DialogInterface.OnClickListener { dialog, which ->
                                    dialog.dismiss()
                                    mPermissionRequest!!.deny()
                                    Log.d(TAG, "Denied")
                                })
                        val alertDialog: android.app.AlertDialog? = alertDialogBuilder?.create()
                        alertDialog?.show()
                        break
                    }
                }
            }

            override fun onPermissionRequestCanceled(request: PermissionRequest?) {
                super.onPermissionRequestCanceled(request)
                Toast.makeText(this@MainActivity, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        })
        if (hasCameraPermission()) {
            myWebView!!.loadUrl("Your URL")
            setContentView(myWebView)
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your camera so you can take pictures.",
                REQUEST_CAMERA_PERMISSION,
                *PERM_CAMERA
            )
        }
    }

    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(
            this@MainActivity,
            *PERM_CAMERA
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(
        requestCode: Int,
        perms: List<String>
    ) {
    }

    override fun onPermissionsDenied(
        requestCode: Int,
        perms: List<String>
    ) {
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 1
        val PERM_CAMERA =
            arrayOf<String>(Manifest.permission.CAMERA)
    }
}