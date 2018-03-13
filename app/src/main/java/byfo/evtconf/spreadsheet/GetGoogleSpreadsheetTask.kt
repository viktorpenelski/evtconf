package byfo.evtconf.spreadsheet

import android.os.AsyncTask
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestTemplate


/**
 * Created by Vic on 2/24/2018.
 */

class GetGoogleSpreadsheetTask(private val onEntry : OnFetched, private val forceRefresh : Boolean = false) : AsyncTask<Unit, Unit, List<Entry>>() {

    private val TAG = "DownstreamTask"
    private val URL = "https://spreadsheets.google.com/feeds/list/1_Ol_0bP-S3GqEXEGPL3ODKmHAdWBBXcOdE3_M4phVe0/1/public/values?alt=json"


    override fun doInBackground(vararg params : Unit): List<Entry>? {

        val cache = SpreadsheetCache.instance

        if (forceRefresh || cache.isNotUpToDate()) {
            val entries = remoteGetEntries()
            Log.d(TAG, entries.toString())
            cache.updateEntries(entries)

            return entries
        }

        val entries = cache.retrieveEntries()
        Log.d(TAG, entries.toString())
        return entries
    }

    override fun onPostExecute(result: List<Entry>) {

        val list = result
                .filter { !it.isEmpty() }
                .toList()

        onEntry.onEntriesFetched(list)
    }

    private fun remoteGetEntries() : List<Entry> {
        val restTemplate = RestTemplate()
        restTemplate.messageConverters.add(StringHttpMessageConverter())

        val response = restTemplate.getForObject(URL, String::class.java)

        val jsonResponse = JSONObject(response)
        Log.d(TAG, response.toString())

        val jsonEntries = jsonResponse.getJSONObject("feed").getJSONArray("entry")

        val entries = jsonEntries.iterator().asSequence().map {
            it -> Entry.fromJSONObject(it)
        }.toList()

        return entries
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

interface OnFetched {
    fun onEntriesFetched(entries: List<Entry>)
}

