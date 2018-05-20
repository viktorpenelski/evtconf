package com.github.viktorpenelski.evtconf

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.github.viktorpenelski.evtconf.fragments.FragmentPagerAdapter
import com.github.viktorpenelski.evtconf.spreadsheet.GetGoogleSpreadsheetTask
import com.github.viktorpenelski.evtconf.spreadsheet.OnEntriesFetched
import com.github.viktorpenelski.evtconf.spreadsheet.mainstage.SettingsEntry
import com.github.viktorpenelski.evtconf.spreadsheet.mainstage.SettingsEntryCache
import com.github.viktorpenelski.evtconf.spreadsheet.settings.RemoteSettings
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    lateinit var mFirebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        viewpager.apply {
            // how many offscreen tabs should be cached
            offscreenPageLimit = 2

            // Create an adapter that knows which fragment should be shown on each page
            adapter = FragmentPagerAdapter(this@MainActivity, supportFragmentManager)
        }
        // Give the TabLayout the ViewPager
        sliding_tabs.setupWithViewPager(viewpager)

        sliding_tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                mFirebaseAnalytics.setCurrentScreen(this@MainActivity, tab?.text.toString(), null)

            }
        })


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

                mFirebaseAnalytics.setCurrentScreen(this@MainActivity, "MAP BUTTON", null)

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

                toolbar.title = RemoteSettings.INSTANCE.getTitleMain()

                main_top_text.apply {
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