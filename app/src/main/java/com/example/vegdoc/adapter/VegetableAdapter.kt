package com.example.vegdoc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.vegdoc.MainActivity
import com.example.vegdoc.R
import com.example.vegdoc.model.Vegetable


class VegetableAdapter(private val dataSet: List<Vegetable>,private val listener: OnRecyclerViewItemClickListener ) :
    RecyclerView.Adapter<VegetableAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val vegetableImage: ImageView
        val primaryTitle: TextView

        init {
            // Define click listener for the ViewHolder's View.
            vegetableImage = view.findViewById(R.id.vegetableImage)
            primaryTitle = view.findViewById(R.id.primaryTitle)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.vegetable_item, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.primaryTitle.text = dataSet[position].name
        viewHolder.itemView.setOnClickListener {listener.onClick(position)  }

        val context = viewHolder.itemView.context
        val packageName = context.packageName
        val resId = context.resources.getIdentifier(dataSet[position].imageName, "drawable", packageName)
        viewHolder.vegetableImage.setImageResource(resId)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    interface OnRecyclerViewItemClickListener {
        fun onClick(index: Int)
    }

}
