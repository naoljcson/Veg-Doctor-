package com.example.vegdoc.adapter

import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.vegdoc.R
import com.example.vegdoc.model.ActiveProduct
import com.example.vegdoc.model.Product
import com.example.vegdoc.util.Constants
import com.example.vegdoc.util.PreferenceHelper

//data class x(
//    var  title: String,
//    var products: List<Product>
//)

class CustomExpandableListAdapter(private val activeProducts: List<ActiveProduct>) :
    BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return activeProducts.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return activeProducts[p0].tradeNames.size
    }

    override fun getGroup(p0: Int): Any {
        return activeProducts[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return activeProducts[p0].tradeNames[p1]
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
        val view = LayoutInflater.from(p3?.context).inflate(R.layout.list_group, p3, false)
        val titleView = view.findViewById<TextView>(R.id.listTitle)
        if (p3 == null)
            return view
        val preferences = PreferenceHelper(p3.context)
        val activeProduct: Product = activeProducts[p0].activeIngredient
//        var title = "${activeProduct.ingridientName} (${activeProduct.classNo})"
//        if(preferences?.getString(Constants.CURRENT_LANGUAGE,"eng").equals("am"))
//            title = "${activeProduct.amharicIngridientName} ${activeProduct.classNoAmh}"
//            titleView.text = title
        titleView.text = if (preferences.language == "am")
            "${activeProduct.amharicIngridientName} ${activeProduct.classNoAmh}"
        else
            "${activeProduct.ingridientName} (${activeProduct.classNo})"
        titleView.setTypeface(null, Typeface.BOLD)
        return view
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        val view = LayoutInflater.from(p4?.context).inflate(R.layout.list_item, p4, false)
        val listView = view.findViewById<TextView>(R.id.expandedListItem)
        Log.i("Receiver", "getChildView: ${activeProducts[p0].tradeNames[p1].tradeName}")
        if (p4 == null)
            return view
        val preference = PreferenceHelper(p4.context)
        listView.text = if (preference.language == "en")
            activeProducts[p0].tradeNames[p1].tradeName
        else
            activeProducts[p0].tradeNames[p1].amharicTradeName
        return view
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return false
    }

}


