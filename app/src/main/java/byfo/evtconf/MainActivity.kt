package byfo.evtconf

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import byfo.evtconf.spreadsheet.Entry
import byfo.evtconf.spreadsheet.EntryListAdapter
import byfo.evtconf.spreadsheet.GetGoogleSpreadsheetTask
import byfo.evtconf.spreadsheet.OnFetched


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.list_view)

        listView.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(adapter: AdapterView<*>, arg1: View, position: Int, arg3: Long) {

                val entryClicked = adapter.getItemAtPosition(position) as Entry
                loadExternalUrlWebView(entryClicked.redirectUrl)

                Log.d("kappa", "AdapterView<*>: $adapter \n View: $arg1 \n Int: $position \n Long: $arg3")
            }
        }

        GetGoogleSpreadsheetTask(object : OnFetched {
            override fun onEntriesFetched(entries: List<Entry>) {
                listView.adapter = EntryListAdapter(this@MainActivity as Activity, entries)
            }
        }).execute()


    }

    fun loadTwitchChatWebView(view: View) {
        val intent = Intent(this@MainActivity, WebViewActivity::class.java)
        intent.putExtra(WebViewActivity.EXTRAS_URL, "https://www.twitch.tv/popout/riotgames/chat")
        intent.putExtra(WebViewActivity.EXTRAS_REQUEST_DESKTOP, true)
        intent.putExtra(WebViewActivity.EXTRAS_ENABLE_JS, true)

        startActivity(intent)

    }

    private fun loadExternalUrlWebView(url: String) {
        val intent = Intent(this@MainActivity, WebViewActivity::class.java)
        intent.putExtra(WebViewActivity.EXTRAS_URL, url)
        intent.putExtra(WebViewActivity.EXTRAS_ENABLE_JS, true)

        startActivity(intent)
    }

}
