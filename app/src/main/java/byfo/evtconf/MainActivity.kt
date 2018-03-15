package byfo.evtconf

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import byfo.evtconf.fragments.FragmentPagerAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.app_name_long)

        setContentView(R.layout.activity_main)
        val viewPager = findViewById<View>(R.id.viewpager) as ViewPager

        // set how many offscreen tabs of the viewPager should be cached
        viewPager.offscreenPageLimit = 2

        // Create an adapter that knows which fragment should be shown on each page
        val adapter = FragmentPagerAdapter(this, supportFragmentManager)

        // Set the adapter onto the view pager
        viewPager.adapter = adapter

        // Give the TabLayout the ViewPager
        val tabLayout = findViewById<View>(R.id.sliding_tabs) as TabLayout
        tabLayout.setupWithViewPager(viewPager)
    }
}