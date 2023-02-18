package com.muco.squatdepthassist.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.muco.squatdepthassist.R
import com.muco.squatdepthassist.databinding.FragmentCameraBinding
import com.muco.squatdepthassist.ui.viewmodel.CameraViewModel
import dagger.hilt.android.AndroidEntryPoint
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
            vm.flipCamera()
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

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(viewLifecycleOwner, vm.lensFacing, previewUseCase)
            } catch (e: java.lang.Exception) {
                Toast.makeText(requireContext(), R.string.AL_03, Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }


}