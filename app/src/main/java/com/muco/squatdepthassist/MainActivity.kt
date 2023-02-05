package com.muco.squatdepthassist

import android.Manifest
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.muco.squatdepthassist.utils.Constants.RC_CAMERA
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber
import timber.log.Timber.Forest.plant

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        plant(Timber.DebugTree())
        requestCameraPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestCameraPermission()
        }
    }

    @AfterPermissionGranted(RC_CAMERA)
    private fun requestCameraPermission() {
        val perms = arrayOf(Manifest.permission.CAMERA)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this, getString(R.string.AL_02),
                RC_CAMERA, concat(*perms)
            )
        }
    }

    private fun concat(vararg strings: String): String {
        return strings.fold("") { acc, next -> "$acc$next" }
    }
}