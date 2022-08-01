package com.example.vegdoc.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.vegdoc.database.VegDoctorDatabase
import com.example.vegdoc.model.Vegetable
import com.example.vegdoc.repository.VegetableRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class VegetableViewModel( application: Application) : AndroidViewModel(application) {

     val allEvents: LiveData<List<Vegetable>>
     val repository: VegetableRepository

    // initialize dao, repository and all events
    init {
        val dao = VegDoctorDatabase.getDatabase(application).vegetableDao()
        repository = VegetableRepository(dao)
        allEvents = repository.getAllVegetables()
    }

    fun insertVegetable(vegetable: Vegetable) =
        viewModelScope.launch(Dispatchers.IO) { repository.insertEvent(vegetable) }

    fun updateVegetable(vegetable: Vegetable) =
        viewModelScope.launch(Dispatchers.IO) { repository.updateVegetable(vegetable) }

    fun deleteVegetable(vegetable: Vegetable) =
        viewModelScope.launch(Dispatchers.IO) { repository.deleteVegetable(vegetable) }

    fun deleteVegetableById(id: Int) =
        viewModelScope.launch(Dispatchers.IO) { repository.deleteVegetableById(id) }

    fun clearVegetable() =
        viewModelScope.launch(Dispatchers.IO) { repository.clearVegetable() }
}