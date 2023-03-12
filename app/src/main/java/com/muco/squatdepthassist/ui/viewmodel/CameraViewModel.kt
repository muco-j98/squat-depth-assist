package com.muco.squatdepthassist.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.camera.core.CameraSelector
import androidx.lifecycle.ViewModel
import com.muco.squatdepthassist.data.model.Device
import com.muco.squatdepthassist.data.model.Person
import com.muco.squatdepthassist.ml.MoveNet
import com.muco.squatdepthassist.ml.MoveNet.Companion.PREVIEW_HEIGHT
import com.muco.squatdepthassist.ml.MoveNet.Companion.PREVIEW_WIDTH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {

    var lensFacing = CameraSelector.DEFAULT_BACK_CAMERA
    var device = Device.CPU
    private val lock = Any()
    private lateinit var moveNet: MoveNet
    private val _persons: MutableStateFlow<List<Person>> = MutableStateFlow(listOf())
    val persons: StateFlow<List<Person>> = _persons
    var bitmap: Bitmap? = null

    fun flipCameraLens() {
        lensFacing = when (lensFacing) {
            CameraSelector.DEFAULT_BACK_CAMERA -> CameraSelector.DEFAULT_FRONT_CAMERA
            else -> CameraSelector.DEFAULT_BACK_CAMERA
        }
    }

    fun setMoveNet(context: Context) {
        moveNet = MoveNet.create(context, device)
    }

    fun processImage(bitmap: Bitmap?) {
        bitmap?.let {
            moveNet.estimatePoses(bitmap).let {
                _persons.value = it
            }
        }
    }

    fun rotateBitmap() {
        bitmap?.let {
            // Create rotated version for portrait display
            val rotateMatrix = Matrix()
            rotateMatrix.postRotate(90.0f)

            val rotatedBitmap = Bitmap.createBitmap(
                it, 0, 0, PREVIEW_WIDTH, PREVIEW_HEIGHT,
                rotateMatrix, false
            )
            bitmap = rotatedBitmap
        }
    }
}