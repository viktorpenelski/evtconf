package com.github.viktorpenelski.evtconf.spreadsheet

import android.os.AsyncTask
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestTemplate


/**
 * This class retrieves entries from a google spreadsheet.
 *
 * The URL is defined in a SpreadsheetEntryCache
 */
class GetGoogleSpreadsheetTask<T : SpreadsheetEntryCache<U>, U : SpreadsheetEntry>(
        private val onFetched: OnEntriesFetched<U>,
        private val forceRefresh: Boolean = false)
    : AsyncTask<T, Unit, List<U>>() {

    private val TAG = "DownstreamTask"

    override fun doInBackground(vararg params: T): List<U> {

        if (params.isEmpty()) {
            return emptyList()
        }

        val cache = params[0]

        if (forceRefresh || cache.isNotUpToDate()) {
            val entries = fetchRemoteEntries(cache)
            Log.d(TAG, entries.toString())
            cache.updateEntries(entries)

            return entries
        }

        val entries = cache.retrieveEntries()
        Log.d(TAG, entries.toString())
        return entries
    }

    override fun onPostExecute(result: List<U>) {

        val list = result
                .filter { !it.isEmpty() }
                .toList()

        onFetched.onEntriesFetched(list)
    }

    private fun fetchRemoteEntries(cache: T): List<U> {
        val restTemplate = RestTemplate()
        restTemplate.messageConverters.add(StringHttpMessageConverter())

        val response = restTemplate.getForObject(cache.getUrl(), String::class.java)

        val jsonResponse = JSONObject(response)
        Log.d(TAG, response.toString())

        val jsonEntries = jsonResponse.getJSONObject("feed").getJSONArray("entry")

        return jsonEntries.iterator().asSequence().map { it ->
            cache.getFactory().createFromJsonObject(it)
        }.toList()
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

