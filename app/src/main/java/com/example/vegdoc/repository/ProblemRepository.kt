package com.example.vegdoc.repository

import androidx.lifecycle.LiveData
import com.example.vegdoc.dao.ProblemDao
import com.example.vegdoc.model.Problem
import com.example.vegdoc.model.Vegetable

class ProblemRepository(private val problemDao: ProblemDao) {
    // get all the problems
    fun allProblems(): LiveData<List<Problem>> = problemDao.allProblems()

    // get   problem by id
    fun problemsById(problemId: Int): Problem = problemDao.problemsById(problemId)

    // get vegetable by   problem  id
    fun vegetableByProblemId(vegetableId: Int): Vegetable = problemDao.vegetableByProblemId(vegetableId)

    // get all the problem by vegetable id
    suspend fun problemsByVegetableId(vegetableId: Int,type: String): List<Problem> = problemDao.problemsByVegetableId(vegetableId,type)
}