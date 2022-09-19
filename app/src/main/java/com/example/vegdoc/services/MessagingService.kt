package com.example.vegdoc.services

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.example.vegdoc.R
import com.example.vegdoc.database.VegDoctorDatabase
import com.example.vegdoc.model.Notification
import com.example.vegdoc.util.InformManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class MessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "onMessageReceived() called with: remoteMessage = $remoteMessage")
        remoteMessage.notification?.also {
            notifyUser(this, it.title, it.body, it.imageUrl.toString())
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    private fun notifyUser(context: Context, title: String?, message: String?, url: String?) {
        var filePath = ""
        if (url != null && url.length > 10) {
            Log.d(TAG, "MessagingService 2 url  = $url")
            val image: Bitmap = Glide.with(context)
                .asBitmap()
                .load(url) // sample image
                .placeholder(R.drawable.agtrain_logo) // need placeholder to avoid issue like glide annotations
                .error(R.drawable.agtrain_logo) // need error to avoid issue like glide annotations
                .submit()
                .get()
            filePath = saveImage(image)
        }


        VegDoctorDatabase.getDatabase(application).notificationDao()
            .insertNotification(Notification(null, title ?: "", message ?: "", filePath))
        with(InformManager(context)) {
            val builder: NotificationCompat.Builder = showNotification(title, message)
            manager.notify(2, builder.build())
        }
    }

    private fun saveImage(image: Bitmap): String {
        val mTimeStamp: String = SimpleDateFormat("ddMMyyyy_HHmm").format(Date())
        var fileName = "snap_$mTimeStamp.jpg"
        try {
            val wrapper = ContextWrapper(applicationContext)
            var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
            file = File(file, fileName)
            val out = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.JPEG, 85, out)
            fileName = file.absolutePath
            out.flush()
            out.close()
        } catch (e: Exception) {
            println(e)
        }
        return fileName
    }

    companion object {
        private const val TAG = "MessagingService"
    }
}