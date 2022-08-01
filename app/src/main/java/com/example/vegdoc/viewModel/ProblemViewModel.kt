package com.example.vegdoc.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.vegdoc.database.VegDoctorDatabase
import com.example.vegdoc.model.Problem
import com.example.vegdoc.model.Vegetable
import com.example.vegdoc.repository.ProblemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProblemViewModel( application: Application) : AndroidViewModel(application) {
    val allProblems: LiveData<List<Problem>>
    val repository: ProblemRepository

    init {
        val dao = VegDoctorDatabase.getDatabase(application).problemDao()
        repository = ProblemRepository(dao)
        allProblems = repository.allProblems()
    }

     suspend fun allProblemsByVegetableId(vegetableId: Int,type: String): List<Problem> {
         var problems: List<Problem>
         withContext(viewModelScope.coroutineContext + Dispatchers.IO) {  problems = repository.problemsByVegetableId(vegetableId,type)  }
        return problems
    }

    suspend fun problemsById(id: Int): Problem {
        var problem: Problem
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {  problem = repository.problemsById(id)  }
        return problem
      }


    suspend fun vegetableByProblemId(id: Int): Vegetable {
        var vegetable: Vegetable
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {  vegetable = repository.vegetableByProblemId(id)  }
        return vegetable
    }
    }





