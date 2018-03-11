package byfo.evtconf


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.ListView
import byfo.evtconf.spreadsheet.Entry
import byfo.evtconf.spreadsheet.EntryListAdapter
import byfo.evtconf.spreadsheet.GetGoogleSpreadsheetTask
import byfo.evtconf.spreadsheet.OnFetched

/**
 * A simple [Fragment] subclass.
 */
class UsefulInfoFragment : Fragment() {

//    private lateinit var ent : List<Entry>
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        GetGoogleSpreadsheetTask(object : OnFetched {
//            override fun onEntriesFetched(entries: List<Entry>) {
//                ent = entries
//            }
//        }).execute()
//
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.activity_main_old, container, false)

        initializeListViewOnItemClickListener(rootView)
        initializeSwipeToRefresh(rootView)

        val twcButton = rootView.findViewById<ImageButton>(R.id.twitch_button)
        twcButton?.setOnClickListener {
            loadTwitchChatWebView()
        }

        return rootView
//        val textView = TextView(activity)
//        textView.setText(R.string.hello_blank_fragment)
//        return textView
    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        initializeListViewOnItemClickListener()
//        initializeSwipeToRefresh()
//        refreshListView()
//    }

    fun loadTwitchChatWebView() {
        val intent = Intent(activity, WebViewActivity::class.java).apply {
            putExtra(WebViewActivity.EXTRAS_URL, "https://www.twitch.tv/popout/riotgames/chat")
            putExtra(WebViewActivity.EXTRAS_REQUEST_DESKTOP, true)
            putExtra(WebViewActivity.EXTRAS_ENABLE_JS, true)
        }

        startActivity(intent)
    }

    private fun initializeListViewOnItemClickListener(view: View) {
        view.findViewById<ListView>(R.id.list_view).apply {
            onItemClickListener = object : AdapterView.OnItemClickListener {

                override fun onItemClick(adapter: AdapterView<*>, arg1: View, position: Int, arg3: Long) {

                    val entryClicked = adapter.getItemAtPosition(position) as Entry
                    loadExternalUrlWebView(entryClicked.redirectUrl)

                    Log.d("kappa", "AdapterView<*>: $adapter \n View: $arg1 \n Int: $position \n Long: $arg3")
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
                Log.i("MAIN", "onRefresh called from SwipeRefreshLayout")
                refreshListView(view)
            }
        }
    }

    private fun refreshListView(view: View) {
        GetGoogleSpreadsheetTask(object : OnFetched {
            override fun onEntriesFetched(entries: List<Entry>) {
                view.findViewById<ListView>(R.id.list_view).apply {
                    adapter = EntryListAdapter(activity, entries)
                }
            }
        }).execute()

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
