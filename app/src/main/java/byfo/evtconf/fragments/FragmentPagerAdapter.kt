package byfo.evtconf.fragments

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import byfo.evtconf.R


class FragmentPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    // This determines the fragment for each tab
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MainScheduleFragment()
            1 -> TournamentFragment()
            else -> TwitchChatFragment()
        }
    }

    // This determines the number of tabs
    override fun getCount(): Int {
        return 3
    }

    // This determines the title for each tab
    override fun getPageTitle(position: Int): CharSequence? {
        // Generate title based on item position
        return when (position) {
            0 -> mContext.getString(R.string.category_main_event_info)
            1 -> mContext.getString(R.string.category_tournaments)
            2 -> mContext.getString(R.string.category_twitch_chat)
            else -> null
        }
    }

}