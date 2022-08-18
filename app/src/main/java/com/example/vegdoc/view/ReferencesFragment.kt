package com.example.vegdoc.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vegdoc.databinding.FragmentReferencesBinding


class ReferencesFragment : Fragment() {
   private var _binding: FragmentReferencesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      _binding = FragmentReferencesBinding.inflate(inflater,container,false)
        return binding.root
    }
}