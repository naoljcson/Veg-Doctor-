package com.example.vegdoc.repository

import androidx.lifecycle.LiveData
import com.example.vegdoc.dao.NotificationDao
import com.example.vegdoc.dao.VegetableDao
import com.example.vegdoc.model.Notification
import com.example.vegdoc.model.Vegetable

class NotificationRepository(private val notificationDao: NotificationDao ){
    // get all the notifications
    fun allNotifications(): List<Notification> = notificationDao.allNotifications()

    // adds an notification to our database.
    suspend fun insertNotification(notification: Notification) {
        notificationDao.insertNotification(notification)
    }
}