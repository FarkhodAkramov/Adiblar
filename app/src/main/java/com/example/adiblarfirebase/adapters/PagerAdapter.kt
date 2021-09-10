package com.example.adiblarfirebase.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.adiblarfirebase.MumtozAdabiyotList

class PagerAdapter(var list: List<String>, fm: FragmentManager) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return MumtozAdabiyotList.newInstance(list[position])

    }

    override fun getPageTitle(position: Int): CharSequence? {
        var typeList = arrayOf("Mumtoz adabiyoti", "O’zbek adabiyoti", "Jahon adabiyoti")
        return  typeList[position]
    }

}