package com.example.vegdoc.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vegdoc.databinding.FragmentAboutAgtrainBinding

class AboutAgtrainFragment : Fragment() {
    private var _binding: FragmentAboutAgtrainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutAgtrainBinding.inflate(inflater, container, false)
        return binding.root
    }
}