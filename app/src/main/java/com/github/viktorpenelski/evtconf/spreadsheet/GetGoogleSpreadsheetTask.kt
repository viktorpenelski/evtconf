package com.github.viktorpenelski.evtconf.spreadsheet

import android.os.AsyncTask
import android.widget.Toast
import com.github.viktorpenelski.evtconf.EvtConfApplication
import com.github.viktorpenelski.evtconf.R
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestClientException
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

    // this is set to true in case of failing to complete remote call
    var networkError = false

    override fun doInBackground(vararg params: T): List<U> {

        if (params.isEmpty()) {
            return emptyList()
        }

        val cache = params[0]

        if (forceRefresh || cache.isNotUpToDate()) {
            val fetchedEntries = fetchRemoteEntries(cache)
            if (fetchedEntries.isNotEmpty()) {
                cache.updateEntries(fetchedEntries)
                return fetchedEntries
            }
        }

        val entries = cache.retrieveEntries()
        return entries
    }

    override fun onPostExecute(result: List<U>) {

        val list = result
                .filter { !it.isEmpty() }
                .toList()

        if (networkError) {
            val context = EvtConfApplication.getContext()
            Toast.makeText(context, context.getString(R.string.toast_no_network), Toast.LENGTH_LONG).show()
        }

        onFetched.onEntriesFetched(list)
    }

    private fun fetchRemoteEntries(cache: T): List<U> {

        val response = executeRemoteRequest(cache)
        val jsonEntries = response
                ?.getJSONObject("feed")?.getJSONArray("entry")
                ?: return emptyList()

        return jsonEntries.iterator().asSequence()
                .map { it -> cache.getFactory().createFromJsonObject(it) }
                .toList()
    }

    private fun executeRemoteRequest(cache: T) : JSONObject? {
        return try {
            val restTemplate = RestTemplate()
            restTemplate.messageConverters.add(StringHttpMessageConverter())
            val response = restTemplate.getForObject(cache.getUrl(), String::class.java)
            JSONObject(response)
        } catch (e: RestClientException) {
            networkError = true
            null
        }

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

