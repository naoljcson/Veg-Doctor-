package com.example.vegdoc.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vegdoc.adapter.CustomExpandableListAdapter
import com.example.vegdoc.databinding.FragmentSecondBinding
import com.example.vegdoc.model.Product
import com.example.vegdoc.util.Constants.PROBLEM_ID
import com.example.vegdoc.viewModel.ProductViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


//import com.example.vegdoc.view.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val products = mutableListOf<Product>()
    private lateinit var productViewModel: ProductViewModel
    private val binding get() = _binding!!



    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        if(arguments == null)  return binding.root

        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

//        productListRecyclerView.adapter = productAdapter
//        productAdapter = ProductAdapter(products)
        val adapter =
        GlobalScope.launch(Dispatchers.Main) {
            products.clear()
            products.addAll(productViewModel.allProductsByProblemId(requireArguments().getInt(PROBLEM_ID)))
//            productAdapter.notifyDataSetChanged()
            binding.expandableListView.setAdapter(CustomExpandableListAdapter(products))
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}