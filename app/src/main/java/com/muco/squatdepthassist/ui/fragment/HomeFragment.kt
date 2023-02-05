package com.muco.squatdepthassist.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.muco.squatdepthassist.databinding.FragmentHomeBinding
import com.muco.squatdepthassist.utils.safeNavigate

class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeButton.setOnClickListener {
            //Todo check if user has granted camera access
            findNavController().safeNavigate(HomeFragmentDirections.actionHomeFragmentToCameraFragment())
        }
    }

}