package com.github.viktorpenelski.evtconf.fragments

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.github.viktorpenelski.evtconf.R


class FragmentPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    companion object {
        const val MAIN_FRAGMENT = 0
        const val TOURNAMENT_FRAGMENT = 1
        const val TWITCH_FRAGMENT = 2
    }

    // This determines the fragment for each tab
    override fun getItem(position: Int): Fragment {
        return when (position) {
            MAIN_FRAGMENT -> {
                MainScheduleFragment()
            }
            TOURNAMENT_FRAGMENT -> {
                TournamentFragment()
            }
            else -> {
                TwitchChatFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    // This determines the title for each tab
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            MAIN_FRAGMENT -> mContext.getString(R.string.category_main_event_info)
            TOURNAMENT_FRAGMENT -> mContext.getString(R.string.category_tournaments)
            TWITCH_FRAGMENT -> mContext.getString(R.string.category_twitch_chat)
            else -> null
        }
    }

}