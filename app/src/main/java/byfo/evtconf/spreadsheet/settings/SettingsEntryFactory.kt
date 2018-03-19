package byfo.evtconf.spreadsheet.mainstage

import android.util.Log
import byfo.evtconf.spreadsheet.SpreadsheetEntryFactory
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by victo on 3/16/2018.
 */
class SettingsEntryFactory : SpreadsheetEntryFactory<SettingsEntry> {

    companion object {

        private const val TWITCH_CHANNEL = "gsx\$twitchchannel"
        private const val TOP_MESSAGE = "gsx\$topmessage"
        private const val VALUE = "\$t"

    }

    override fun createFromJsonObject(obj: JSONObject): SettingsEntry {
        return try {
            SettingsEntry(
                    obj.getJSONObject(Companion.TWITCH_CHANNEL).getString(VALUE),
                    obj.getJSONObject(Companion.TOP_MESSAGE).getString(VALUE))
        } catch (e: JSONException) {
            Log.e("deserialization", "Something went wrong during deserialization of remote json - ${e.stackTrace}")
            SettingsEntry("", "")
        }
    }

}