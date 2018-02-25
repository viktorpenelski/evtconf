package byfo.evtconf.spreadsheet

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.ListView
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.net.URL


/**
 * Created by Vic on 2/24/2018.
 */

class GetGoogleSpreadsheetTask(val context: Context, val view: ListView) : AsyncTask<URL, Int, List<Entry>>() {

    private val TAG = "DownstreamTask"
    private val URL = "https://spreadsheets.google.com/feeds/list/1_Ol_0bP-S3GqEXEGPL3ODKmHAdWBBXcOdE3_M4phVe0/1/public/values?alt=json"


    override fun doInBackground(vararg urls: URL): List<Entry>? {

        val restTemplate = RestTemplate()
        restTemplate.messageConverters.add(StringHttpMessageConverter())

        val response = restTemplate.getForObject(URL, String::class.java)

        val jsonResponse = JSONObject(response)
        Log.d(TAG, response.toString())

        val jsonEntries = jsonResponse.getJSONObject("feed").getJSONArray("entry")

        val entries = jsonEntries.iterator().asSequence().map {
            it -> Entry.fromJSONObject(it)
        }.toList()

        Log.d(TAG, entries.toString())

        return entries
    }

    override fun onPostExecute(result: List<Entry>) {

        val list = result
                .filter { !it.isEmpty() }
                .toList()


        val adapter = EntryListAdapter(context as Activity, list)

        view.adapter = adapter
    }

    /**
     * Custom iterator for JSONArray, mapping all elements to JSONObject.
     *
     * Since JSONArray does not have an iterator, a custom one has to be provided.
     * NOTE that mapping to JSONObject works here, because in the current payload, all elements
     * are JSONObjects, but it might not be the case for every JSONArray.
     */
    private operator fun JSONArray.iterator(): Iterator<JSONObject> = (0 until length())
            .asSequence()
            .map { get(it) as JSONObject }
            .iterator()
}

