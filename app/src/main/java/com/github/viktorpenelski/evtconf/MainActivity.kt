package com.github.viktorpenelski.evtconf

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
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

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        val viewPager = findViewById<ViewPager>(R.id.viewpager).also {
            // how many offscreen tabs should be cached
            it.offscreenPageLimit = 2

            // Create an adapter that knows which fragment should be shown on each page
            it.adapter = FragmentPagerAdapter(this, supportFragmentManager)

        }
        // Give the TabLayout the ViewPager
        findViewById<TabLayout>(R.id.sliding_tabs).setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_map_btn -> {

                val mapUrl = RemoteSettings.INSTANCE.getEventMapUrl()

                if (mapUrl.isBlank()) {
                    return false
                }

                val intent = Intent(this@MainActivity, WebViewActivity::class.java).apply {
                    putExtra(WebViewActivity.EXTRAS_URL, mapUrl)
                }
                startActivity(intent)

                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        loadSettings()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
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