package com.example.vegdoc.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vegdoc.R
import com.example.vegdoc.adapter.VegetableAdapter
import com.example.vegdoc.databinding.FragmentHomeBinding
import com.example.vegdoc.model.Vegetable
import com.example.vegdoc.util.PreferenceHelper
import com.example.vegdoc.viewModel.VegetableViewModel


class HomeFragment : Fragment(), VegetableAdapter.OnRecyclerViewItemClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: VegetableViewModel
    private lateinit var vegetableAdapter: VegetableAdapter
    private val vegetables = mutableListOf<Vegetable>()
    private lateinit var preference: PreferenceHelper


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val vegetableRecyclerView: RecyclerView = binding.vegetableRecyclerView
        vegetableRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProvider(this)[VegetableViewModel::class.java]

        vegetableAdapter = VegetableAdapter(vegetables, this)
        preference = PreferenceHelper(requireContext())
        vegetableRecyclerView.adapter = vegetableAdapter

        observeEvents()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeEvents() {
        viewModel.allEvents.observe(viewLifecycleOwner) { list ->
            list?.let {
                vegetables.clear()
                vegetables.addAll(list)
                vegetableAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onClick(index: Int) {
        val vegetableName = if (preference.language == "am")
            vegetables[index].amharicName
        else
            vegetables[index].name

        Navigation.createNavigateOnClickListener(
            R.id.action_nav_home_to_nav_disorder,
            bundleOf("id" to vegetables[index].id, "name" to vegetableName)
        ).onClick(view)
    }
}