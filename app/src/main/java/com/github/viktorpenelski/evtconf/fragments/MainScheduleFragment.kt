package com.github.viktorpenelski.evtconf.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.AdapterView
import com.github.viktorpenelski.evtconf.R
import com.github.viktorpenelski.evtconf.WebViewActivity
import com.github.viktorpenelski.evtconf.spreadsheet.GetGoogleSpreadsheetTask
import com.github.viktorpenelski.evtconf.spreadsheet.OnEntriesFetched
import com.github.viktorpenelski.evtconf.spreadsheet.mainstage.MainStageEntryListAdapter
import com.github.viktorpenelski.evtconf.spreadsheet.mainstage.MainStageSpreadsheetEntry
import com.github.viktorpenelski.evtconf.spreadsheet.mainstage.MainStageSpreadsheetEntryCache
import kotlinx.android.synthetic.main.base_fragment_refreshable_list_view.view.*

/**
 * First fragment visible ot user.
 *
 * Represents a timeline of the event.
 */
class MainScheduleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.base_fragment_refreshable_list_view, container, false)

        loadListView(rootView)
        initializeListViewOnItemClickListener(rootView)
        initializeSwipeToRefresh(rootView)

        return rootView
    }

    private fun initializeListViewOnItemClickListener(view: View) {
        view.list_view.apply {
            onItemClickListener = object : AdapterView.OnItemClickListener {

                override fun onItemClick(adapter: AdapterView<*>, arg1: View, position: Int, arg3: Long) {

                    val entryClicked = adapter.getItemAtPosition(position) as MainStageSpreadsheetEntry
                    if (URLUtil.isNetworkUrl(entryClicked.redirectUrl)) {
                        loadExternalUrlWebView(entryClicked.redirectUrl)
                    }

                }
            }
        }
    }

    private fun initializeSwipeToRefresh(view: View) {
        view.swiperefresh.apply {

            /*
             * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
             * performs a swipe-to-refresh gesture.
             */
            setOnRefreshListener {
                loadListView(view, true)
            }
        }
    }

    private fun loadListView(view: View, forceRefresh: Boolean = false) {
        GetGoogleSpreadsheetTask<MainStageSpreadsheetEntryCache, MainStageSpreadsheetEntry>(object : OnEntriesFetched<MainStageSpreadsheetEntry> {
            override fun onEntriesFetched(entries: List<MainStageSpreadsheetEntry>) {
                view.list_view.apply {
                    adapter = MainStageEntryListAdapter(activity!!, entries)
                }
            }
        }, forceRefresh).execute(MainStageSpreadsheetEntryCache.INSTANCE)

        view.swiperefresh.apply {
            isRefreshing = false
        }

    }

    private fun loadExternalUrlWebView(url: String) {
        val intent = Intent(activity, WebViewActivity::class.java).apply {
            putExtra(WebViewActivity.EXTRAS_URL, url)
            putExtra(WebViewActivity.EXTRAS_ENABLE_JS, true)
        }

        startActivity(intent)
    }


}
