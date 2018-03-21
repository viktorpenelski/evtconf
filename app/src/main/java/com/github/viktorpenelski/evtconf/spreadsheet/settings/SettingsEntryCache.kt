package com.github.viktorpenelski.evtconf.spreadsheet.mainstage

import com.github.viktorpenelski.evtconf.spreadsheet.ActiveSpreadsheetProperties
import com.github.viktorpenelski.evtconf.spreadsheet.ActiveSpreadsheetProperties.Companion.PropertyTag.SETTINGS
import com.github.viktorpenelski.evtconf.spreadsheet.SpreadsheetEntryCache
import java.util.*

/**
 * Singleton class used to cache a list of SpreadsheetEntries.
 *
 * The cached list is stored in a local variable and is considered "outdated" if either
 * the list is empty or more than 30 minutes have elapsed since it was last updated.
 */
class SettingsEntryCache private constructor() : SpreadsheetEntryCache<SettingsEntry> {

    private val factory = SettingsEntryFactory()
    private var cachedMainStageEntries = listOf<SettingsEntry>()
    private var lastCached = Calendar.getInstance()

    companion object {
        val INSTANCE: SettingsEntryCache by lazy { Holder.INSTANCE }
    }

    override fun isNotUpToDate(): Boolean {
        return cachedMainStageEntries.isEmpty() || 30.minutesHavePassedSince(lastCached)
    }

    private fun Int.minutesHavePassedSince(givenTime : Calendar) : Boolean {
        val threshold = Calendar.getInstance()
        threshold.add(Calendar.MINUTE, -this)
        return givenTime.before(threshold)
    }

    override fun updateEntries(entries: List<SettingsEntry>) {
        cachedMainStageEntries = entries.toList()
        lastCached = Calendar.getInstance()
    }

    override fun retrieveEntries(): List<SettingsEntry> {
        return cachedMainStageEntries
    }

    override fun getUrl(): String {
        return ActiveSpreadsheetProperties.INSTANCE.getUrlFor(SETTINGS)
    }

    override fun getFactory(): SettingsEntryFactory {
        return factory
    }

    private object Holder {
        val INSTANCE = SettingsEntryCache()
    }



}


