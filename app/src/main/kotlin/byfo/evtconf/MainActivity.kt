package byfo.evtconf

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import byfo.evtconf.spreadsheet.GetGoogleSpreadsheetTask


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.list_view)

        listView.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(arg0: AdapterView<*>, arg1: View, arg2: Int, arg3: Long) {
                val intent = Intent(this@MainActivity, WebViewActivity::class.java)
                intent.putExtra(WebViewActivity.EXTRAS_URL, "https://www.twitch.tv/popout/riotgames/chat")
                intent.putExtra(WebViewActivity.EXTRAS_REQUEST_DESKTOP, true)
                intent.putExtra(WebViewActivity.EXTRAS_ENABLE_JS, true)

                startActivity(intent)

                Log.d("kappa", "AdapterView<*>: $arg0 \n View: $arg1 \n Int: $arg2 \n Long: $arg3")
            }
        }

        GetGoogleSpreadsheetTask(this, listView).execute()
    }


}
