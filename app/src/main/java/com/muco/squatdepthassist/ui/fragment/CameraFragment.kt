package com.muco.squatdepthassist.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.muco.squatdepthassist.R
import com.muco.squatdepthassist.databinding.FragmentCameraBinding
import com.muco.squatdepthassist.ml.MoveNet
import com.muco.squatdepthassist.ui.viewmodel.CameraViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@AndroidEntryPoint
class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private lateinit var cameraExecutor: ExecutorService
    private val vm: CameraViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        cameraExecutor = Executors.newSingleThreadExecutor()
        startCamera()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.flipCameraBtn.setOnClickListener {
            vm.flipCameraLens()
            startCamera()
        }
    }

    private fun startCamera() {
        val processCameraProvider = ProcessCameraProvider.getInstance(requireContext())
        processCameraProvider.addListener({
            try {
                val cameraProvider = processCameraProvider.get()
                val previewUseCase = Preview.Builder().build()
                previewUseCase.setSurfaceProvider(binding.cameraPreviewView.surfaceProvider)
                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                MoveNet.create(requireContext(), vm.device)

                imageAnalysis.setAnalyzer(
                    ContextCompat.getMainExecutor(requireContext())
                ) { image ->
                    Timber.tag("jhnn").i("test")
                    image.close()
                }

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(viewLifecycleOwner, vm.lensFacing, imageAnalysis, previewUseCase)
            } catch (e: java.lang.Exception) {
                Toast.makeText(requireContext(), R.string.AL_03, Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }


}