package com.github.viktorpenelski.evtconf.spreadsheet.settings

import com.github.viktorpenelski.evtconf.EvtConfApplication
import com.github.viktorpenelski.evtconf.R
import com.github.viktorpenelski.evtconf.spreadsheet.mainstage.SettingsEntryCache

/**
 * Created by victo on 3/19/2018.
 */
class RemoteSettings private constructor() {

    private val cache = SettingsEntryCache.INSTANCE

    companion object {
        val INSTANCE: RemoteSettings by lazy { Holder.INSTANCE }
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

    fun getEventMapUrl(): String {
        val entries = cache.retrieveEntries()

        return if (entries.isEmpty() || entries[0].eventMapUrl.isEmpty()) {
            ""
        } else {
            entries[0].eventMapUrl
        }

    }

    fun getTitleMain(): String {
        val entries = cache.retrieveEntries()

        return if (entries.isEmpty() || entries[0].eventMapUrl.isEmpty()) {
            EvtConfApplication.getContext().getString(R.string.title_default)
        } else {
            entries[0].titleMain
        }

    }


    private object Holder {
        val INSTANCE = RemoteSettings()
    }
}