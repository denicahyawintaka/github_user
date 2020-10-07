package com.example.githubuser.detail

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.githubuser.R

class SectionsPagerAdapter(
    private val mContext: Context,
    private val username: String,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment.newInstance(username = username)
            1 -> fragment = FollowingFragment.newInstance(username = username)
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getCount(): Int {
        return tabTitles.size
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTitles[position])
    }
}
