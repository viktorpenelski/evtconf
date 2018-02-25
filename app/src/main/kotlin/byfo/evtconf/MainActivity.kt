package byfo.evtconf

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import byfo.evtconf.spreadsheet.Entry
import byfo.evtconf.spreadsheet.GetGoogleSpreadsheetTask


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.list_view)

        listView.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(adapter: AdapterView<*>, arg1: View, position: Int, arg3: Long) {
                GetGoogleSpreadsheetTask(this@MainActivity, listView).execute()

                val entryClicked = adapter.getItemAtPosition(position) as Entry
                loadExternalUrlWebView(entryClicked.redirectUrl)

                Log.d("kappa", "AdapterView<*>: $adapter \n View: $arg1 \n Int: $position \n Long: $arg3")
            }
        }

        GetGoogleSpreadsheetTask(this, listView).execute()
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
