package com.github.viktorpenelski.evtconf.spreadsheet.tournaments

import com.github.viktorpenelski.evtconf.spreadsheet.SpreadsheetEntryFactory
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by victo on 3/16/2018.
 */
class TournamentSpreadsheetEntryFactory : SpreadsheetEntryFactory<TournamentSpreadsheetEntry> {

    companion object {

        private const val TITLE = "gsx\$text"
        private const val PICTURE = "gsx\$picture"
        private const val LINK = "gsx\$url"

        private const val VALUE = "\$t"

    }

    override fun createFromJsonObject(obj: JSONObject): TournamentSpreadsheetEntry {
        return try {
            TournamentSpreadsheetEntry(
                    obj.getJSONObject(TITLE).getString(VALUE),
                    obj.getJSONObject(PICTURE).getString(VALUE),
                    obj.getJSONObject(LINK).getString(VALUE))
        } catch (e: JSONException) {
            TournamentSpreadsheetEntry("", "", "")
        }
    }

}