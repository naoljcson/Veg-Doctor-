package com.example.vegdoc.view.disorders

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
        _binding = FragmentDisordersBinding.inflate(inflater, container, false)

        vegetableId = args.id
        (activity as MainActivity).setTitle(args.name)

        problemViewModel = ViewModelProvider(this, )[ProblemViewModel::class.java]

        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        GlobalScope.launch(Dispatchers.Main) {
            peasts = problemViewModel.allProblemsByVegetableId(vegetableId, "Pest")
            diseases = problemViewModel.allProblemsByVegetableId(vegetableId,"Disease")
            disorders = problemViewModel.allProblemsByVegetableId(vegetableId,"Disorder")

            with(binding){
                numberOfPeasts.text = "Number Of Elements : ${peasts.count()}"
                numberOfDiseases.text  = "Number Of Elements : ${diseases.count()}"
                numberOfDisOrders.text  = "Number Of Elements : ${disorders.count()}"

                peastCard.setOnClickListener { openDisorderDetail("Pest") }
                diseaseCard.setOnClickListener { openDisorderDetail("Disease")  }
                disorderCard.setOnClickListener { openDisorderDetail("Disorder")  }
            }
        }
    }

    private fun openDisorderDetail(type: String){
        Navigation.createNavigateOnClickListener(R.id.action_nav_disorder_to_problemListFragment,
            bundleOf("id" to vegetableId,"type" to type)).onClick(view)
    }
}
