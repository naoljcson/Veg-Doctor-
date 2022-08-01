package com.example.vegdoc.repository

import androidx.lifecycle.LiveData
import com.example.vegdoc.dao.VegetableDao
import com.example.vegdoc.model.Vegetable

class VegetableRepository(private val vegetableDao: VegetableDao) {
    // get all the events
    fun getAllVegetables(): LiveData<List<Vegetable>> = vegetableDao.getAllVegetables()

    // adds an event to our database.
    suspend fun insertEvent(vegetable: Vegetable) {
        vegetableDao.insertVegetable(vegetable)
    }

    // deletes an event from database.
    suspend fun deleteVegetable(vegetable: Vegetable) {
        vegetableDao.deleteVegetable(vegetable)
    }

    // updates an event from database.
    suspend fun updateVegetable(vegetable: Vegetable) {
        vegetableDao.updateVegetable(vegetable)
    }

//    //delete an event by id.
    suspend fun deleteVegetableById(id: Int) = vegetableDao.deleteVegetableById(id)
//
//    // delete all events
    suspend fun clearVegetable() = vegetableDao.clearVegetable()
}
