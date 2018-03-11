package byfo.evtconf

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import byfo.evtconf.spreadsheet.Entry
import byfo.evtconf.spreadsheet.EntryListAdapter
import byfo.evtconf.spreadsheet.GetGoogleSpreadsheetTask
import byfo.evtconf.spreadsheet.OnFetched


class MainActivityOld : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        initializeListViewOnItemClickListener()
        initializeSwipeToRefresh()
        loadListView()
    }

    fun loadTwitchChatWebView(view: View) {
        val intent = Intent(this@MainActivityOld, WebViewActivity::class.java).apply {
            putExtra(WebViewActivity.EXTRAS_URL, "https://www.twitch.tv/popout/riotgames/chat")
            putExtra(WebViewActivity.EXTRAS_REQUEST_DESKTOP, true)
            putExtra(WebViewActivity.EXTRAS_ENABLE_JS, true)
        }

        startActivity(intent)
    }

    private fun initializeListViewOnItemClickListener() {
        findViewById<ListView>(R.id.list_view).apply {
            onItemClickListener = object : AdapterView.OnItemClickListener {

                override fun onItemClick(adapter: AdapterView<*>, arg1: View, position: Int, arg3: Long) {

                    val entryClicked = adapter.getItemAtPosition(position) as Entry
                    loadExternalUrlWebView(entryClicked.redirectUrl)

                    Log.d("kappa", "AdapterView<*>: $adapter \n View: $arg1 \n Int: $position \n Long: $arg3")
                }
            }
        }
    }

    private fun initializeSwipeToRefresh() {
        findViewById<SwipeRefreshLayout>(R.id.swiperefresh).apply {

            /*
             * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
             * performs a swipe-to-refresh gesture.
             */
            setOnRefreshListener {
                Log.i("MAIN", "onRefresh called from SwipeRefreshLayout")
                loadListView()
            }
        }
    }

    private fun loadListView() {
        GetGoogleSpreadsheetTask(object : OnFetched {
            override fun onEntriesFetched(entries: List<Entry>) {
                findViewById<ListView>(R.id.list_view).apply {
                    adapter = EntryListAdapter(this@MainActivityOld as Activity, entries)
                }
            }
        }).execute()

        findViewById<SwipeRefreshLayout>(R.id.swiperefresh).apply {
            isRefreshing = false
        }

    }

    private fun loadExternalUrlWebView(url: String) {
        val intent = Intent(this@MainActivityOld, WebViewActivity::class.java).apply {
            putExtra(WebViewActivity.EXTRAS_URL, url)
            putExtra(WebViewActivity.EXTRAS_ENABLE_JS, true)
        }

        startActivity(intent)
    }

}
