package com.example.vegdoc.view.disorderDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.example.vegdoc.MainActivity
import com.example.vegdoc.databinding.FragmentDisorderDetailBinding
import com.example.vegdoc.util.PreferenceHelper
import com.example.vegdoc.view.ProblemListFragmentArgs
import com.example.vegdoc.view.ui.main.SectionsPagerAdapter
import com.example.vegdoc.viewModel.ProblemViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DisorderDetailFragment : Fragment() {
    private var _binding: FragmentDisorderDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ProblemListFragmentArgs by navArgs()
    private lateinit var problemViewModel: ProblemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDisorderDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter = SectionsPagerAdapter(requireContext(), childFragmentManager,args.id)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

        problemViewModel = ViewModelProvider(this)[ProblemViewModel::class.java]

        GlobalScope.launch(Dispatchers.Main) {
            val selectedProblem = problemViewModel.problemsById(args.id)
            val title = if (PreferenceHelper(requireContext()).language == "am")
                selectedProblem.amharicName
            else
                selectedProblem.name
            (activity as MainActivity).setTitle(title)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}