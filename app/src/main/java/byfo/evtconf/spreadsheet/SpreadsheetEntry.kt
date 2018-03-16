package byfo.evtconf.spreadsheet

/**
 * Created by victo on 3/16/2018.
 */
interface SpreadsheetEntry {

    /**
     * A SpreadsheetEntry is considered "empty" when all fields are empty
     */
    fun isEmpty(): Boolean
}