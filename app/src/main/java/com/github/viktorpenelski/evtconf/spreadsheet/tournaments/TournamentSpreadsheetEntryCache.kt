package com.github.viktorpenelski.evtconf.spreadsheet.tournaments

import com.github.viktorpenelski.evtconf.spreadsheet.ActiveSpreadsheetProperties
import com.github.viktorpenelski.evtconf.spreadsheet.ActiveSpreadsheetProperties.Companion.PropertyTag.TOURNAMENTS
import com.github.viktorpenelski.evtconf.spreadsheet.SpreadsheetEntryCache
import java.util.*

/**
 * Singleton class used to cache a list of SpreadsheetEntries.
 *
 * The cached list is stored in a local variable and is considered "outdated" if either
 * the list is empty or more than 30 minutes have elapsed since it was last updated.
 */
class TournamentSpreadsheetEntryCache private constructor() : SpreadsheetEntryCache<TournamentSpreadsheetEntry> {

    private val factory = TournamentSpreadsheetEntryFactory()
    private var cachedMainStageEntries = listOf<TournamentSpreadsheetEntry>()
    private var lastCached = Calendar.getInstance()

    companion object {
        val INSTANCE: TournamentSpreadsheetEntryCache by lazy { Holder.INSTANCE }
    }

    override fun isNotUpToDate(): Boolean {
        return cachedMainStageEntries.isEmpty() || 30.minutesHavePassedSince(lastCached)
    }

    private fun Int.minutesHavePassedSince(givenTime : Calendar) : Boolean {
        val threshold = Calendar.getInstance()
        threshold.add(Calendar.MINUTE, -this)
        return givenTime.before(threshold)
    }

    override fun updateEntries(entries: List<TournamentSpreadsheetEntry>) {
        cachedMainStageEntries = entries.toList()
        lastCached = Calendar.getInstance()
    }

    override fun retrieveEntries(): List<TournamentSpreadsheetEntry> {
        return cachedMainStageEntries
    }

    override fun getUrl(): String {
        return ActiveSpreadsheetProperties.INSTANCE.getUrlFor(TOURNAMENTS)
    }

    override fun getFactory() : TournamentSpreadsheetEntryFactory {
        return factory
    }

    private object Holder {
        val INSTANCE = TournamentSpreadsheetEntryCache()
    }



}


