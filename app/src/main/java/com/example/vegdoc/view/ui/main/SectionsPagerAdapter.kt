package com.example.vegdoc.view.ui.main


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.vegdoc.R
import com.example.vegdoc.util.Constants.PROBLEM_ID
import com.example.vegdoc.view.FirstFragment
import com.example.vegdoc.view.SecondFragment


//import com.example.vegdoc.view.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager,private val problemId: Int) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val args = Bundle()
        args.putInt(PROBLEM_ID, problemId)
        var fragment:Fragment = FirstFragment()
        if (position == 0){
            fragment.arguments = args
        }else if(position == 1){
            fragment = SecondFragment()
            fragment.arguments = args
        }else if(position == 2){
            fragment = SecondFragment()
            fragment.arguments = args
        }

        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }



}