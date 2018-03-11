package byfo.evtconf

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class SimpleFragmentPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    // This determines the fragment for each tab
    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            UsefulInfoFragment()
        } else if (position == 1) {
            UsefulInfoFragment()
        } else if (position == 2) {
            UsefulInfoFragment()
        } else {
            UsefulInfoFragment()
        }
    }

    // This determines the number of tabs
    override fun getCount(): Int {
        return 4
    }

    // This determines the title for each tab
    override fun getPageTitle(position: Int): CharSequence? {
        // Generate title based on item position
        when (position) {
            0 -> return mContext.getString(R.string.category_usefulinfo)
            1 -> return mContext.getString(R.string.category_places)
            2 -> return mContext.getString(R.string.category_food)
            3 -> return mContext.getString(R.string.category_nature)
            else -> return null
        }
    }

}