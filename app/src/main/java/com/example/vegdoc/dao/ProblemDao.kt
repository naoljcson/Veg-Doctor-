package com.example.vegdoc.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.vegdoc.model.Problem
import com.example.vegdoc.model.Vegetable


@Dao
interface ProblemDao {

    @Query("Select * from problem order by name ASC")
    fun allProblems(): LiveData<List<Problem>>

    @Query("SELECT * from problem WHERE vegetable_id = :vegetableId AND type = :type order by name ASC")
    suspend fun problemsByVegetableId(vegetableId: Int,type: String): List<Problem>

    @Query("SELECT * from problem WHERE id = :problemId ")
    fun problemsById(problemId: Int): Problem

    @Query("SELECT * from vegetable WHERE id = :vegetableId ")
    fun vegetableByProblemId(vegetableId: Int): Vegetable

}