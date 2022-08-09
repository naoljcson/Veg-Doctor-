package com.example.vegdoc.view.disorders

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.vegdoc.MainActivity
import com.example.vegdoc.R
import com.example.vegdoc.databinding.FragmentDisordersBinding
import com.example.vegdoc.model.Problem
import com.example.vegdoc.view.home.HomeFragmentArgs
import com.example.vegdoc.viewModel.ProblemViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DisordersFragment : Fragment() {

   private var _binding: FragmentDisordersBinding? = null
   private val binding get() = _binding!!

    private lateinit var problemViewModel: ProblemViewModel
    private  var vegetableId: Int = 0

    private  var peasts:  List<Problem> = emptyList()
    private  var diseases:  List<Problem> = emptyList()
    private  var disorders:  List<Problem> = emptyList()

    private val args: HomeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentDisordersBinding.inflate(inflater, container, false)

        vegetableId = args.id
        problemViewModel = ViewModelProvider(this, )[ProblemViewModel::class.java]

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if(activity != null){
            (activity as MainActivity).setTitle(args.name)
        }
    }

    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        GlobalScope.launch(Dispatchers.Main) {
            peasts = problemViewModel.allProblemsByVegetableId(vegetableId, "Pest")
            diseases = problemViewModel.allProblemsByVegetableId(vegetableId,"Disease")
            disorders = problemViewModel.allProblemsByVegetableId(vegetableId,"Disorder")
            val numberOfElement = getString(R.string.numberOfElement)
            with(binding){
               numberOfPeasts.text =  "$numberOfElement ${peasts.count()}"
                numberOfDiseases.text  = "$numberOfElement ${diseases.count()}"
                numberOfDisOrders.text  ="$numberOfElement ${disorders.count()}"

                peastCard.setOnClickListener { openDisorderDetail("Pest",getString(R.string.pest)) }
                diseaseCard.setOnClickListener { openDisorderDetail("Disease",getString(R.string.disease))  }
                disorderCard.setOnClickListener { openDisorderDetail("Disorder",getString(R.string.disorder))  }
            }
        }
    }

    private fun openDisorderDetail(type: String,title: String){
        Navigation.createNavigateOnClickListener(R.id.action_nav_disorder_to_problemListFragment,
            bundleOf("id" to vegetableId,"type" to type,"title" to title)).onClick(view)
    }
}
