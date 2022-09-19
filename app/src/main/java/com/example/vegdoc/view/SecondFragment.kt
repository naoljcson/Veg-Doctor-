package com.example.vegdoc.view

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vegdoc.adapter.CustomExpandableListAdapter
import com.example.vegdoc.adapter.VegetableAdapter
import com.example.vegdoc.databinding.FragmentSecondBinding
import com.example.vegdoc.model.ActiveProduct
import com.example.vegdoc.model.Product
import com.example.vegdoc.util.Constants.PROBLEM_ID
import com.example.vegdoc.util.CustomDialogFragment
import com.example.vegdoc.util.PreferenceHelper
import com.example.vegdoc.viewModel.ProblemViewModel
import com.example.vegdoc.viewModel.ProductViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val products = mutableListOf<Product>()
    private lateinit var productViewModel: ProductViewModel
    private lateinit var problemViewModel: ProblemViewModel
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        problemViewModel = ViewModelProvider(this)[ProblemViewModel::class.java]

        val preference = PreferenceHelper(requireContext())
        blinkEffect()
        GlobalScope.launch(Dispatchers.Main) {
            val problemId = requireArguments().getInt(PROBLEM_ID)
            val activeProducts: MutableList<ActiveProduct> = mutableListOf()
            val allProducts = productViewModel.allProductsByProblemId(problemId)
            if(allProducts.any()){
                binding.productNotFound.visibility = View.GONE
                binding.productFound.visibility = View.VISIBLE
                products.clear()
                products.addAll(allProducts)
                val activeIngredients: List<Product> = products.distinctBy { it.ingridientName }
                activeIngredients.forEach { activeProduct ->
                    products.filter { it.ingridientName == activeProduct.ingridientName }.also {
                        activeProducts.add(ActiveProduct(activeProduct, it.toMutableList()))
                    }
                }
                binding.expandableListView.setAdapter(CustomExpandableListAdapter(activeProducts))
                val problem = problemViewModel.problemsById(problemId)
                val recommendation = if (preference.language == "am")
                    problem.amharicRecommendation
                else
                    problem.recommendation
                binding.recommendationButton.setOnClickListener {
                    val customDialogFragment = CustomDialogFragment(recommendation)
                    customDialogFragment.show(childFragmentManager, "Replace")
                }
            }else{
                binding.productNotFound.visibility = View.VISIBLE
                binding.productFound.visibility = View.GONE
            }

        }
    }

    val recyclerViewItemClickListener = object : VegetableAdapter.OnRecyclerViewItemClickListener {
        override fun onClick(index: Int) {
            TODO("Not yet implemented")
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Manage Blink effect of the button
    private fun blinkEffect() {
        val animator = ObjectAnimator.ofInt(
            binding.recommendationButton,
            "backgroundColor",
            Color.YELLOW,
            Color.WHITE,
            Color.WHITE
        )
        animator.duration = 1200
        animator.setEvaluator(ArgbEvaluator())
        animator.repeatMode = ValueAnimator.REVERSE
        animator.repeatCount = ValueAnimator.INFINITE
        animator.start()
    }
}