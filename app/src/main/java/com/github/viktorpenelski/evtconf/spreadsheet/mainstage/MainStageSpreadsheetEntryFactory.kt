package com.github.viktorpenelski.evtconf.spreadsheet.mainstage

import com.github.viktorpenelski.evtconf.spreadsheet.SpreadsheetEntryFactory
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
                MainStageSpreadsheetEntry("", "", "", "")
            }
        }

}