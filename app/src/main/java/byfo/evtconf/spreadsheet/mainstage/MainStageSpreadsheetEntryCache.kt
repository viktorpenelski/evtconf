package byfo.evtconf.spreadsheet.mainstage

import byfo.evtconf.spreadsheet.SpreadsheetEntryCache
import org.joda.time.DateTime

/**
 * Singleton class used to cache a list of SpreadsheetEntries.
 *
 * The cached list is stored in a local variable and is considered "outdated" if either
 * the list is empty or more than 30 minutes have elapsed since it was last updated.
 */
class MainStageSpreadsheetEntryCache private constructor() : SpreadsheetEntryCache<MainStageSpreadsheetEntry> {

    private val URL = "https://spreadsheets.google.com/feeds/list/1_Ol_0bP-S3GqEXEGPL3ODKmHAdWBBXcOdE3_M4phVe0/1/public/values?alt=json"
    private val factory = MainStageSpreadsheetEntryFactory()
    private var cachedMainStageEntries = listOf<MainStageSpreadsheetEntry>()
    private var lastCached = DateTime.now()

    companion object {
        val INSTANCE: MainStageSpreadsheetEntryCache by lazy { Holder.INSTANCE }
    }

    override fun isNotUpToDate(): Boolean {
        //TODO(vic): get rid of joda time
        return cachedMainStageEntries.isEmpty() || lastCached.plusMinutes(30).isBeforeNow
    }

    override fun updateEntries(entries: List<MainStageSpreadsheetEntry>) {
        cachedMainStageEntries = entries.toList()
        lastCached = DateTime.now()
    }

    override fun retrieveEntries(): List<MainStageSpreadsheetEntry> {
        return cachedMainStageEntries
    }

    override fun getUrl(): String {
        return URL
    }

    override fun getFactory(): MainStageSpreadsheetEntryFactory {
        return factory
    }

    private object Holder {
        val INSTANCE = MainStageSpreadsheetEntryCache()
    }

}


