package byfo.evtconf.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.AdapterView
import android.widget.ListView
import byfo.evtconf.R
import byfo.evtconf.WebViewActivity
import byfo.evtconf.spreadsheet.mainstage.MainStageEntryListAdapter
import byfo.evtconf.spreadsheet.GetGoogleSpreadsheetTask
import byfo.evtconf.spreadsheet.OnEntriesFetched
import byfo.evtconf.spreadsheet.mainstage.MainStageSpreadsheetEntry
import byfo.evtconf.spreadsheet.mainstage.MainStageSpreadsheetEntryCache

/**
 * A simple [Fragment] subclass.
 */
class MainScheduleFragment : Fragment() {

    private val TAG = "F_MAIN_SCH"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_main_schedule, container, false)

        loadListView(rootView)
        initializeListViewOnItemClickListener(rootView)
        initializeSwipeToRefresh(rootView)

        Log.d(TAG, "ON CREATE VIEW")

        return rootView
    }

    private fun initializeListViewOnItemClickListener(view: View) {
        view.findViewById<ListView>(R.id.list_view).apply {
            onItemClickListener = object : AdapterView.OnItemClickListener {

                override fun onItemClick(adapter: AdapterView<*>, arg1: View, position: Int, arg3: Long) {

                    val entryClicked = adapter.getItemAtPosition(position) as MainStageSpreadsheetEntry
                    if (URLUtil.isNetworkUrl(entryClicked.redirectUrl)) {
                        loadExternalUrlWebView(entryClicked.redirectUrl)
                    }

                    Log.d(TAG, "AdapterView<*>: $adapter \n View: $arg1 \n Int: $position \n Long: $arg3")
                }
            }
        }
    }

    private fun initializeSwipeToRefresh(view: View) {
        view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh).apply {

            /*
             * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
             * performs a swipe-to-refresh gesture.
             */
            setOnRefreshListener {
                Log.i(TAG, "onRefresh called from SwipeRefreshLayout")
                loadListView(view, true)
            }
        }
    }

    private fun loadListView(view: View, forceRefresh: Boolean = false) {
        GetGoogleSpreadsheetTask<MainStageSpreadsheetEntryCache, MainStageSpreadsheetEntry>(object : OnEntriesFetched<MainStageSpreadsheetEntry> {
            override fun onEntriesFetched(entries: List<MainStageSpreadsheetEntry>) {
                view.findViewById<ListView>(R.id.list_view).apply {
                    adapter = MainStageEntryListAdapter(activity, entries)
                }
            }
        }, forceRefresh).execute(MainStageSpreadsheetEntryCache.INSTANCE)

        view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh).apply {
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
