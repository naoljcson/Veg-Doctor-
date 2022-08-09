package com.example.vegdoc.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vegdoc.MainActivity
import com.example.vegdoc.R
import com.example.vegdoc.databinding.FragmentFirstBinding
import com.example.vegdoc.model.Problem
import com.example.vegdoc.model.Vegetable
import com.example.vegdoc.util.Constants.CURRENT_LANGUAGE
import com.example.vegdoc.util.Constants.PROBLEM_ID
import com.example.vegdoc.util.PreferenceHelper.defaultPrefs
import com.example.vegdoc.viewModel.ProblemViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//import com.example.vegdoc.view.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var problemViewModel: ProblemViewModel

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        if(arguments == null)  return binding.root
        val problemId = requireArguments().getInt(PROBLEM_ID);
        val preferences = defaultPrefs(requireContext())

        problemViewModel = ViewModelProvider(this, )[ProblemViewModel::class.java]
        GlobalScope.launch(Dispatchers.Main) {
            val selectedProblem = problemViewModel.problemsById(problemId)
            var description = selectedProblem.description
            if(preferences.getString(CURRENT_LANGUAGE,"eng").equals("am"))
                description = selectedProblem.amharicDescription
            binding.textviewFirst.text = description
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}