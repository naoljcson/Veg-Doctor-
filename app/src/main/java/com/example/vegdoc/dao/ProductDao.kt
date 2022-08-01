package com.example.vegdoc.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.vegdoc.model.Problem
import com.example.vegdoc.model.Product
import com.example.vegdoc.model.Vegetable


@Dao
interface ProductDao {

    @Query("Select * from product order by name ASC")
    fun allProducts(): LiveData<List<Product>>

    @Query("SELECT * from product WHERE problem_id = :problemId order by name ASC")
    fun productsByProblemId(problemId: Int): List<Product>
}