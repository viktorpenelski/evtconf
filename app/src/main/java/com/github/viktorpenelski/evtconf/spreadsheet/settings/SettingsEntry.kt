package com.github.viktorpenelski.evtconf.spreadsheet.mainstage

import com.github.viktorpenelski.evtconf.spreadsheet.SpreadsheetEntry


/**
 * This class represents a single entry fetched from the MainStage sheet of the spreadsheet.
 */
data class SettingsEntry(val twitchChannel: String, val topMessage: String) : SpreadsheetEntry {

    override fun isEmpty(): Boolean {
        return twitchChannel.isEmpty() && topMessage.isEmpty()
    }
}