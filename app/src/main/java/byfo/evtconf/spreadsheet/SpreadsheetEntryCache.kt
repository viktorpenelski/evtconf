package byfo.evtconf.spreadsheet

import org.joda.time.DateTime

/**
 * Singleton class used to cache a list of SpreadsheetEntries.
 *
 * The cached list is stored in a local variable and is considered "outdated" if either
 * the list is empty or more than 30 minutes have elapsed since it was last updated.
 */
class SpreadsheetEntryCache {

    private var cachedEntries = listOf<SpreadsheetEntry>()
    private var lastCached = DateTime.now()

    companion object {
        val instance: SpreadsheetEntryCache by lazy { Holder.INSTANCE }
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
        val INSTANCE = SpreadsheetEntryCache()
    }


}