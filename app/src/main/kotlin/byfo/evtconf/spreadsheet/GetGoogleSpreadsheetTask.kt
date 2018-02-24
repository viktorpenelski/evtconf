package byfo.evtconf.spreadsheet

import android.R
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import byfo.evtconf.MainActivity
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.net.URL

/**
 * Created by Vic on 2/24/2018.
 */

class GetGoogleSpreadsheetTask(val context: Context, val view: ListView) : AsyncTask<URL, Int, List<Entry>>() {

    override fun doInBackground(vararg urls: URL): List<Entry>? {

        val url = urls[0].toURI()

        val restTemplate = RestTemplate()
        restTemplate.messageConverters.add(StringHttpMessageConverter())

        val response = restTemplate.getForObject(url, String::class.java)

        val jsonResponse = JSONObject(response)
        Log.d("jsonObject", response.toString())

        val jsonEntries = jsonResponse.getJSONObject("feed").getJSONArray("entry")
        Log.d("allEntries", jsonEntries.toString())

        val entries = jsonEntries.iterator().asSequence().map {
            it -> Entry(
                it.getJSONObject("gsx\$title").getString("\$t") ,
                it.getJSONObject("gsx\$time").getString("\$t"),
                it.getJSONObject("gsx\$picture").getString("\$t"))
        }.toList()

        Log.d("YAY", entries.toString())

        return entries
    }

    override fun onPostExecute(result: List<Entry>) {

        val list = result.map {
            it -> "${it.time} - ${it.title}"
        }.toList()

        val adapter = ArrayAdapter<String>(context, R.layout.simple_list_item_1, list)
        view.adapter = adapter
    }

    /**
     * Custom iterator for JSONArray, mapping all entries as JSONObject.
     *
     * Since JSONArray does not have an iterator, a custom one has to be provided.
     * NOTE that mapping to JSONObject works here, because in the current payload, all elements
     * are JSONObjects, but it might not be the case for every JSONArray.
     */
    private operator fun JSONArray.iterator(): Iterator<JSONObject>
            = (0 until length()).asSequence()
            .map { get(it) as JSONObject }
            .iterator()
}

data class Entry(val title: String, val time: String, val picture: String)