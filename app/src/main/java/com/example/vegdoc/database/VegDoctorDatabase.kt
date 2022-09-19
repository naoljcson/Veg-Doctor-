package com.example.vegdoc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vegdoc.dao.NotificationDao
import com.example.vegdoc.dao.ProblemDao
import com.example.vegdoc.dao.ProductDao
import com.example.vegdoc.dao.VegetableDao
import com.example.vegdoc.model.Notification
import com.example.vegdoc.model.Problem
import com.example.vegdoc.model.Product
import com.example.vegdoc.model.Vegetable
import com.example.vegdoc.util.Constants.DB_NAME


@Database(entities = [Vegetable::class, Problem::class, Product::class,Notification::class], version = 7)
abstract class VegDoctorDatabase : RoomDatabase() {

    abstract fun vegetableDao(): VegetableDao
    abstract fun problemDao(): ProblemDao
    abstract fun productDao(): ProductDao
    abstract fun notificationDao(): NotificationDao

    companion object {
        // Volatile annotation means any change to this field
        // are immediately visible to other threads.
        @Volatile
        private var INSTANCE: VegDoctorDatabase? = null

        fun getDatabase(context: Context): VegDoctorDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VegDoctorDatabase::class.java,
                    DB_NAME
                )
                    .createFromAsset("database/veg_doctor.db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}