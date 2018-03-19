package byfo.evtconf.spreadsheet.settings

import android.util.Log
import byfo.evtconf.EvtConfApplication
import byfo.evtconf.R
import byfo.evtconf.spreadsheet.GetGoogleSpreadsheetTask
import byfo.evtconf.spreadsheet.OnEntriesFetched
import byfo.evtconf.spreadsheet.mainstage.SettingsEntry
import byfo.evtconf.spreadsheet.mainstage.SettingsEntryCache

/**
 * Created by victo on 3/19/2018.
 */
class ActiveSettings private constructor() {

    private val cache = SettingsEntryCache.INSTANCE

    companion object {
        val INSTANCE: ActiveSettings by lazy { Holder.INSTANCE }
    }

    fun getTwitchChannel(): String {

        val entries = cache.retrieveEntries()

        return if (entries.isEmpty() || entries[0].twitchChannel.isEmpty()) {
            EvtConfApplication.getContext().getString(R.string.twitch_channel)
        } else {
            entries[0].twitchChannel
        }
    }

    fun getTopMessage(): String {
        val entries = cache.retrieveEntries()

        return if (entries.isEmpty() || entries[0].topMessage.isEmpty()) {
            ""
        } else {
            entries[0].topMessage
        }

    }

    private fun refreshCacheIfNeeded() {

        Log.d("CACHEEE", "isNotUpToDate: ${cache.isNotUpToDate()}")

        if (!cache.isNotUpToDate()) {
            return
        }

        GetGoogleSpreadsheetTask<SettingsEntryCache, SettingsEntry>(object : OnEntriesFetched<SettingsEntry> {
            override fun onEntriesFetched(entries: List<SettingsEntry>) {}
        }, false).execute(cache)

    }

    private object Holder {
        val INSTANCE = ActiveSettings()
    }
}