package com.example.vegdoc.adapter

import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vegdoc.R
import com.example.vegdoc.model.Notification
import com.example.vegdoc.model.Notifications
import java.io.File

class NotificationAdapter(
    private val dataSet: List<Notification>
) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView
        val title: TextView
        val body: TextView

        init {
            image = view.findViewById(R.id.notification_img)
            title = view.findViewById(R.id.notification_title)
            body = view.findViewById(R.id.notification_body)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_notification, viewGroup, false)
        )
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            var isShowing = false
            var isZoomed = false
            val notificationBody = dataSet[position].body
            if (notificationBody.length > 150)
                body.text = notificationBody.take(150)
            else
                body.text = notificationBody

            title.text = dataSet[position].title

            val imageAbsolutePath = dataSet[position].imageUrl
            image.visibility = View.GONE
            if (!imageAbsolutePath.isNullOrBlank()) {
                val imgFile = File(imageAbsolutePath)
                if (imgFile.exists()) {
                    val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                    image.visibility = View.VISIBLE
                    image.setImageBitmap(myBitmap)
                    image.setOnClickListener {
                        val display = itemView.context.resources.displayMetrics
                        if (!isZoomed) {
                            image.layoutParams =
                                LinearLayout.LayoutParams(display.widthPixels, display.heightPixels)
                        } else {
                            image.layoutParams =
                                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                        }
                        isZoomed = !isZoomed
                    }
                }
            }
            if (notificationBody.length > 150) {
                body.setOnClickListener {
                    if (isShowing) {
                        val bodyText = "${notificationBody.take(150)} ... "
                        body.text = bodyText
                    } else {
                        body.text = notificationBody
                    }
                    isShowing = !isShowing
                }
            }
        }
    }

}