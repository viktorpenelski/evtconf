package com.github.viktorpenelski.evtconf.spreadsheet

/**
 * Created by victo on 3/16/2018.
 */
interface SpreadsheetEntryCache<T : SpreadsheetEntry> {

    /**
     * Return weather the local cached collection is up to date.
     *
     * Return false if either the collection is empty or outdated based on predefined time.
     */
    fun isNotUpToDate(): Boolean

    /**
     * Provide a list of entries to update the local cache with.
     */
    fun updateEntries(entries: List<T>)

    /**
     * Retrieve the local cache.
     */
    fun retrieveEntries(): List<T>

    fun getUrl(): String

    fun getFactory(): SpreadsheetEntryFactory<T>
}