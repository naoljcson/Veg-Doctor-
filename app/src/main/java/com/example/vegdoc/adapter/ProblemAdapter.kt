package com.example.vegdoc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vegdoc.R
import com.example.vegdoc.model.Problem


class ProblemAdapter(private val dataSet: List<Problem>, private val listener: OnRecyclerViewItemClickListener ) :
    RecyclerView.Adapter<ProblemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         val primaryText: TextView
         val secondaryText: TextView

        init {
            // Define click listener for the ViewHolder's View.
            primaryText = view.findViewById(R.id.primaryText)
            secondaryText = view.findViewById(R.id.secondaryText)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.problem_item, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder){
            primaryText.text = dataSet[position].name
            secondaryText.text = dataSet[position].amharicName
            itemView.setOnClickListener {   listener.onClick(position) }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    interface OnRecyclerViewItemClickListener {
        fun onClick(index: Int)

    }

}