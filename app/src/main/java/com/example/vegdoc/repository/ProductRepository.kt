package com.example.vegdoc.repository

import androidx.lifecycle.LiveData
import com.example.vegdoc.dao.ProductDao
import com.example.vegdoc.model.Product
import com.example.vegdoc.model.Vegetable


class ProductRepository(private val productDao: ProductDao) {
    // get all the products
    fun allProducts(): LiveData<List<Product>> = productDao.allProducts()

    // get all the products by problem id
    fun productsByProblemId(problemId: Int): List<Product> = productDao.productsByProblemId(problemId)
}