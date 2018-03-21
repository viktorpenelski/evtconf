package com.github.viktorpenelski.evtconf

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.github.viktorpenelski.evtconf.fragments.FragmentPagerAdapter
import com.github.viktorpenelski.evtconf.spreadsheet.GetGoogleSpreadsheetTask
import com.github.viktorpenelski.evtconf.spreadsheet.OnEntriesFetched
import com.github.viktorpenelski.evtconf.spreadsheet.mainstage.SettingsEntry
import com.github.viktorpenelski.evtconf.spreadsheet.mainstage.SettingsEntryCache
import com.github.viktorpenelski.evtconf.spreadsheet.settings.RemoteSettings

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // sets the title visible on the main screen, doesn't change the app name itself.
        title = getString(R.string.app_name_long)

        setContentView(R.layout.activity_main)
        val viewPager = findViewById<ViewPager>(R.id.viewpager).also {
            // how many offscreen tabs should be cached
            it.offscreenPageLimit = 2

            // Create an adapter that knows which fragment should be shown on each page
            it.adapter = FragmentPagerAdapter(this, supportFragmentManager)

        }
        // Give the TabLayout the ViewPager
        findViewById<TabLayout>(R.id.sliding_tabs).setupWithViewPager(viewPager)
    }

    override fun onStart() {
        super.onStart()
        loadSettings()
    }

    private fun loadSettings(forceRefresh: Boolean = false) {
        GetGoogleSpreadsheetTask<SettingsEntryCache, SettingsEntry>(object : OnEntriesFetched<SettingsEntry> {
            override fun onEntriesFetched(entries: List<SettingsEntry>) {
                findViewById<TextView>(R.id.main_top_text).apply {
                    val topMessage = RemoteSettings.INSTANCE.getTopMessage()
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