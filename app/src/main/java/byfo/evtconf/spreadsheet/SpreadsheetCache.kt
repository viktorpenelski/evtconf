package byfo.evtconf.spreadsheet

import org.joda.time.DateTime

/**
 * Created by victo on 3/13/2018.
 */

class SpreadsheetCache {

    private var cachedEntries = listOf<SpreadsheetEntry>()
    private var lastCached = DateTime.now()

    companion object {
        val instance: SpreadsheetCache by lazy { Holder.INSTANCE }
    }

    fun isNotUpToDate(): Boolean {
        return cachedEntries.isEmpty() || lastCached.plusMinutes(30).isBeforeNow
    }

    fun updateEntries(spreadsheetEntries: List<SpreadsheetEntry>) {
        cachedEntries = spreadsheetEntries.toList()
        lastCached = DateTime.now()
    }

    fun retrieveEntries(): List<SpreadsheetEntry> {
        return cachedEntries
    }

    private object Holder {
        val INSTANCE = SpreadsheetCache()
    }


}