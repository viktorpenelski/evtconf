package byfo.evtconf.spreadsheet

import android.util.Log
import org.json.JSONException

/**
 * Created by Viktor on 2/25/2018.
 */
data class SpreadsheetEntry(val title: String, val time: String, val picture: String, val redirectUrl: String) {

    /**
     * an SpreadsheetEntry is considered "empty" when either one of the elements that it cannot be used without -
     * title or time, is an empty string.
     *
     * @return true if both title and time are empty strings.
     */
    fun isEmpty(): Boolean {
        return title.isEmpty() || time.isEmpty()
    }

    companion object {

        private const val TITLE = "gsx\$title"
        private const val TIME = "gsx\$time"
        private const val PICTURE = "gsx\$picture"
        private const val LINK = "gsx\$url"

        private const val VALUE = "\$t"

        /**
         * Serialize an SpreadsheetEntry from given JSONObject.
         * This JSONObject has to follow google sheet's API.
         *
         * For reference check out the following GET request, looking for the array element "feed.entry":
         * https://spreadsheets.google.com/feeds/list/1_Ol_0bP-S3GqEXEGPL3ODKmHAdWBBXcOdE3_M4phVe0/1/public/values?alt=json
         */
        fun fromJSONObject(obj: org.json.JSONObject): SpreadsheetEntry {
            return try {
                SpreadsheetEntry(
                        obj.getJSONObject(TITLE).getString(VALUE),
                        obj.getJSONObject(TIME).getString(VALUE),
                        obj.getJSONObject(PICTURE).getString(VALUE),
                        obj.getJSONObject(LINK).getString(VALUE))
            } catch (e: JSONException) {
                Log.e("EntrySerialization", "Something went wrong during deserialization of remote json - ${e.stackTrace}")
                SpreadsheetEntry("", "", "", "")
            }
        }
    }
}