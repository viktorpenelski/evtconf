package byfo.evtconf.spreadsheet.mainstage

import android.util.Log
import byfo.evtconf.spreadsheet.SpreadsheetEntryFactory
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by victo on 3/16/2018.
 */
class MainStageSpreadsheetEntryFactory : SpreadsheetEntryFactory<MainStageSpreadsheetEntry> {

    companion object {

        private const val TITLE = "gsx\$title"
        private const val TIME = "gsx\$time"
        private const val PICTURE = "gsx\$picture"
        private const val LINK = "gsx\$url"

        private const val VALUE = "\$t"

    }

    override fun createFromJsonObject(obj: JSONObject) : MainStageSpreadsheetEntry {
            return try {
                MainStageSpreadsheetEntry(
                        obj.getJSONObject(TITLE).getString(VALUE),
                        obj.getJSONObject(TIME).getString(VALUE),
                        obj.getJSONObject(PICTURE).getString(VALUE),
                        obj.getJSONObject(LINK).getString(VALUE))
            } catch (e: JSONException) {
                Log.e("deserialization", "Something went wrong during deserialization of remote json - ${e.stackTrace}")
                MainStageSpreadsheetEntry("", "", "", "")
            }
        }

}