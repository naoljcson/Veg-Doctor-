package com.example.vegdoc.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.vegdoc.database.VegDoctorDatabase
import com.example.vegdoc.model.Problem
import com.example.vegdoc.model.Product
import com.example.vegdoc.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel( application: Application) : AndroidViewModel(application) {
    val allProducts: LiveData<List<Product>>
    val repository: ProductRepository

    init {
        val dao = VegDoctorDatabase.getDatabase(application).productDao()
        repository = ProductRepository(dao)
        allProducts = repository.allProducts()
    }

//    fun allProductsByProblemId(problemId: Int) =
//        viewModelScope.launch(Dispatchers.IO) { repository.productsByProblemId(problemId) }
//
    suspend fun allProductsByProblemId(problemId: Int): List<Product> {
        var products:  List<Product>
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {  products = repository.productsByProblemId(problemId)  }
        return products
    }
}