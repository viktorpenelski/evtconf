package byfo.evtconf.spreadsheet.mainstage

import byfo.evtconf.spreadsheet.SpreadsheetEntry


/**
 * This class represents a single entry fetched from the MainStage sheet of the spreadsheet.
 */
data class MainStageSpreadsheetEntry(val title: String, val time: String, val picture: String, val redirectUrl: String) : SpreadsheetEntry {

    override fun isEmpty(): Boolean {
        return title.isEmpty() || time.isEmpty() || picture.isEmpty() || redirectUrl.isEmpty()
    }
}