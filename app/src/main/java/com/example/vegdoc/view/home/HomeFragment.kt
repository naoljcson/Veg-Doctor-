package com.example.vegdoc.view.home

import android.content.Intent
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
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vegdoc.R
import com.example.vegdoc.adapter.VegetableAdapter
import com.example.vegdoc.databinding.FragmentHomeBinding
import com.example.vegdoc.model.Vegetable
import com.example.vegdoc.viewModel.VegetableViewModel


class HomeFragment : Fragment() , VegetableAdapter.OnRecyclerViewItemClickListener {

private var _binding: FragmentHomeBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: VegetableViewModel
    private lateinit var vegetableAdapter: VegetableAdapter
    private val vegetables = mutableListOf<Vegetable>()


  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root

      val vegetableRecyclerView: RecyclerView = binding.vegetableRecyclerView
      vegetableRecyclerView.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
      viewModel = ViewModelProvider(   this )[VegetableViewModel::class.java]

      vegetableAdapter = VegetableAdapter(vegetables,this)

      // Setting the Adapter with the recyclerview
      vegetableRecyclerView.adapter = vegetableAdapter

      observeEvents()
    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeEvents() {
        viewModel.allEvents.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                vegetables.clear()
                vegetables.addAll(list)
                vegetableAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onClick(index: Int) {
//        val navHostFragment = activity!!.supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
//        val bundle = bundleOf("VEGETABLE_ID" to vegetables[index].id,"VEGETABLE_NAME" to vegetables[index].name)
//        val navController = navHostFragment.navController
//        navController.navigate(R.id.nav_disorder, bundle)
        Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_nav_disorder, bundleOf("id" to vegetables[index].id,"name" to vegetables[index].name)).onClick(view)
    }
}