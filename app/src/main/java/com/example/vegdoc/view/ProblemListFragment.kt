package com.example.vegdoc.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vegdoc.MainActivity
import com.example.vegdoc.R
import com.example.vegdoc.adapter.ProblemAdapter
import com.example.vegdoc.adapter.VegetableAdapter
import com.example.vegdoc.databinding.FragmentProblemListBinding
import com.example.vegdoc.model.Problem
import com.example.vegdoc.model.Vegetable
import com.example.vegdoc.view.disorders.DisordersFragmentArgs
import com.example.vegdoc.viewModel.ProblemViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ProblemListFragment : Fragment(), ProblemAdapter.OnRecyclerViewItemClickListener {
    // TODO: Rename and change types of parameters
    private val args: DisordersFragmentArgs by navArgs()
    private var _binding: FragmentProblemListBinding? = null
    private val binding get() = _binding!!
    private lateinit var problemAdapter: ProblemAdapter
    private val problems = mutableListOf<Problem>()

    private lateinit var problemViewModel: ProblemViewModel
    private  var vegetableId: Int = 0
    private  var type: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProblemListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vegetableId = args.id
        type = args.type
        (activity as MainActivity).setTitle(type)
        problemViewModel = ViewModelProvider(this, )[ProblemViewModel::class.java]
        val problemListRecyclerView: RecyclerView = binding.problemListRecyclerView
        problemListRecyclerView.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)

        problemAdapter = ProblemAdapter(problems,this)

        problemListRecyclerView.adapter = problemAdapter
        observeEvents()
    }

    private fun observeEvents() {
        problemViewModel.allProblems.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                problems.clear()
                list.forEach { problem ->
                    if(problem.vegetableId == vegetableId && problem.type == type)
                        problems.add(problem)
                }

                problemAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(index: Int) {
        val id = problems[index].id
        val name = problems[index].name
        Navigation.createNavigateOnClickListener(
            R.id.action_problemListFragment_to_disorderDetailFragment,
            bundleOf("id" to id,"name" to name)
        ).onClick(view)
    }
}