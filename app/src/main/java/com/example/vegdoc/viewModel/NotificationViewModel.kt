package com.example.vegdoc.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.vegdoc.database.VegDoctorDatabase
import com.example.vegdoc.model.Notification
import com.example.vegdoc.model.Notifications
import com.example.vegdoc.model.Product
import com.example.vegdoc.model.Vegetable
import com.example.vegdoc.repository.NotificationRepository
import com.example.vegdoc.repository.VegetableRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationViewModel( application: Application) : AndroidViewModel(application) {

    val repository: NotificationRepository

    // initialize dao, repository and all events
    init {
        val dao = VegDoctorDatabase.getDatabase(application).notificationDao()
        repository = NotificationRepository(dao)
    }

    fun insertNotification(notification: Notification) =
        viewModelScope.launch(Dispatchers.IO) { repository.insertNotification(notification) }

    suspend fun allNotifications(): List<Notification> {
        var notifications:  List<Notification>
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {  notifications = repository.allNotifications()  }
        return notifications
    }
}