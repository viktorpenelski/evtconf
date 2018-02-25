package byfo.evtconf.spreadsheet

import android.util.Log
import org.json.JSONException

/**
 * Created by Viktor on 2/25/2018.
 */
data class Entry(val title: String, val time: String, val picture: String) {

    /**
     * an Entry is considered "empty" when either one of the elements that it cannot be used without -
     * title or time, is an empty string.
     *
     * @return true if both title and time are empty strings.
     */
    fun isEmpty() : Boolean {
        return title.isEmpty() && time.isEmpty()
    }

    companion object {

        /**
         * Serialize an Entry from given JSONObject.
         * This JSONObject has to follow google sheet's API.
         *
         * For reference check out the following GET request, looking for the array element "feed.entry":
         * https://spreadsheets.google.com/feeds/list/1_Ol_0bP-S3GqEXEGPL3ODKmHAdWBBXcOdE3_M4phVe0/1/public/values?alt=json
         */
        fun fromJSONObject(obj: org.json.JSONObject) : Entry {
            return try {
                Entry(
                        obj.getJSONObject("gsx\$title").getString("\$t") ,
                        obj.getJSONObject("gsx\$time").getString("\$t"),
                        obj.getJSONObject("gsx\$picture").getString("\$t"))
            } catch (e: JSONException) {
                Log.e("EntrySerialization", "Something went wrong during deserialization of remote json - ${e.stackTrace}")
                Entry("", "", "")
            }
        }
    }
}