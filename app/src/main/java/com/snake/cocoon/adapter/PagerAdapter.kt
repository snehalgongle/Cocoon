package com.snake.cocoon.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.snake.cocoon.fragment.BookmarkFragment
import com.snake.cocoon.fragment.TopStoriesFragment


class PagerAdapter//Initializing tab count
//Constructor to the class
    (
    fm: FragmentManager?, //integer to count number of tabs
    var tabCount: Int
) :
    FragmentStatePagerAdapter(fm!!) {
    //Overriding method getItem
    override fun getItem(position: Int): Fragment {
        //Returning the current tabs
        return when (position) {
            0 -> {
                TopStoriesFragment()
            }
            else -> BookmarkFragment()
        }
    }

    //Overriden method getCount to get the number of tabs
    override fun getCount(): Int {
        return tabCount
    }
    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if (position == 0) {
            title = "Top-Stories"
        } else if (position == 1) {
            title = "BookMark"
        }
        return title
    }
}