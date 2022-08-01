package com.example.vegdoc.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.vegdoc.R
import com.example.vegdoc.model.Product

//data class x(
//    var  title: String,
//    var products: List<Product>
//)

class CustomExpandableListAdapter(private val products: List<Product>) : BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return products.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return products.size
    }

    override fun getGroup(p0: Int): Any {
        return products[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return products[p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        val view = LayoutInflater.from(p3?.context).inflate(R.layout.list_group,p3,false)
        val titleView = view.findViewById<TextView>(R.id.listTitle)
        titleView.text = products[p0].tradeName
        titleView.setTypeface(null, Typeface.BOLD)
        return view
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        val view = LayoutInflater.from(p4?.context).inflate(R.layout.list_item,p4,false)
        val listView = view.findViewById<TextView>(R.id.expandedListItem)
        listView.text = products[p0].ingridientName
        return view
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return false
    }

}


