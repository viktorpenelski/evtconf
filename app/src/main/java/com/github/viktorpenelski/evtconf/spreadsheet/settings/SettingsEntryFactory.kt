package com.github.viktorpenelski.evtconf.spreadsheet.mainstage

import com.github.viktorpenelski.evtconf.spreadsheet.SpreadsheetEntryFactory
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
            SettingsEntry("", "")
        }
    }

}