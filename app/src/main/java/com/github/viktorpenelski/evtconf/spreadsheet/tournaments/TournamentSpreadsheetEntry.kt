package com.github.viktorpenelski.evtconf.spreadsheet.tournaments

import com.github.viktorpenelski.evtconf.spreadsheet.SpreadsheetEntry

/**
 * This class represents a single entry fetched from the Tournament sheet of the spreadsheet.
 */
data class TournamentSpreadsheetEntry(val title: String, val picture: String, val redirectUrl: String) : SpreadsheetEntry {

    override fun isEmpty(): Boolean {
        return title.isEmpty() && picture.isEmpty()
    }
}