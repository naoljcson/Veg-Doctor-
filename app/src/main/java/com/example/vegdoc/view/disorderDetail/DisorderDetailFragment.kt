package com.example.vegdoc.view.disorderDetail

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.example.vegdoc.MainActivity
import com.example.vegdoc.databinding.FragmentDisorderDetailBinding
import com.example.vegdoc.view.ProblemListFragmentArgs
import com.example.vegdoc.view.disorders.DisordersFragmentArgs
import com.example.vegdoc.view.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout

/**
 * A simple [Fragment] subclass.
 * Use the [DisorderDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisorderDetailFragment : Fragment() {
    private var _binding: FragmentDisorderDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ProblemListFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDisorderDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sectionsPagerAdapter = SectionsPagerAdapter(requireContext(), childFragmentManager,args.id)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }

    override fun onResume() {
        super.onResume()
        if(activity != null){
            (activity as MainActivity).setTitle(args.name)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}