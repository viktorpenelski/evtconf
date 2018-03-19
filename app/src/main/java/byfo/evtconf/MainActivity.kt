package byfo.evtconf

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import byfo.evtconf.fragments.FragmentPagerAdapter
import byfo.evtconf.spreadsheet.GetGoogleSpreadsheetTask
import byfo.evtconf.spreadsheet.OnEntriesFetched
import byfo.evtconf.spreadsheet.mainstage.SettingsEntry
import byfo.evtconf.spreadsheet.mainstage.SettingsEntryCache
import byfo.evtconf.spreadsheet.settings.ActiveSettings

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


    override fun onStart() {
        super.onStart()
        loadSettings()
    }


    private fun loadSettings(forceRefresh: Boolean = false) {
        GetGoogleSpreadsheetTask<SettingsEntryCache, SettingsEntry>(object : OnEntriesFetched<SettingsEntry> {
            override fun onEntriesFetched(entries: List<SettingsEntry>) {
                findViewById<TextView>(R.id.main_top_text).apply {
                    val topMessage = ActiveSettings.INSTANCE.getTopMessage()
                    if (topMessage.isNotBlank()) {
                        text = topMessage
                        visibility = View.VISIBLE
                    } else {
                        visibility = View.GONE
                    }
                }
            }
        }, forceRefresh).execute(SettingsEntryCache.INSTANCE)

    }
}