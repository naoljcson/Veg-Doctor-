package com.example.vegdoc.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.vegdoc.model.Vegetable

@Dao
interface VegetableDao {
    // adds a new entry to our database.
    // if some data is same/conflict, it'll be replace with new data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertVegetable(vegetable: Vegetable)

    // deletes an vegetable
    @Delete
     suspend fun deleteVegetable(vegetable: Vegetable)

    // updates an vegetable.
    @Update
     fun updateVegetable(vegetable: Vegetable)

    // read all the vegetable from eventTable
    // and arrange vegetable in ascending order
    // of their ids
    @Query("Select * from vegetable order by name ASC")
    fun getAllVegetables(): LiveData<List<Vegetable>>
    // why not use suspend ? because Room does not support LiveData with suspended functions.
    // LiveData already works on a background thread and should be used directly without using coroutines

    // delete all vegetable
    @Query("DELETE FROM vegetable")
    fun clearVegetable()
//
//    //you can use this too, to delete an vegetable by id.
    @Query("DELETE FROM vegetable WHERE id = :id")
     fun deleteVegetableById(id: Int)
}