package byfo.evtconf

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import byfo.evtconf.spreadsheet.GetGoogleSpreadsheetTask
import java.net.URL


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.list_view)
        val url = URL("https://spreadsheets.google.com/feeds/list/1_Ol_0bP-S3GqEXEGPL3ODKmHAdWBBXcOdE3_M4phVe0/1/public/values?alt=json")

        listView.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(arg0: AdapterView<*>, arg1: View, arg2: Int, arg3: Long) {
                GetGoogleSpreadsheetTask(this@MainActivity, listView).execute(url)
                Log.d("kappa", "AdapterView<*>: $arg0 \n View: $arg1 \n Int: $arg2 \n Long: $arg3")
            }
        }

        GetGoogleSpreadsheetTask(this, listView).execute(url)
    }

}
