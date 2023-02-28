package com.muco.squatdepthassist.ui.viewmodel

import androidx.camera.core.CameraSelector
import androidx.lifecycle.ViewModel
import com.muco.squatdepthassist.data.model.Device
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {

    var lensFacing = CameraSelector.DEFAULT_FRONT_CAMERA
    var device = Device.CPU

    fun flipCameraLens() {
        lensFacing = when (lensFacing) {
            CameraSelector.DEFAULT_BACK_CAMERA -> CameraSelector.DEFAULT_FRONT_CAMERA
            else -> CameraSelector.DEFAULT_BACK_CAMERA
        }
    }
}