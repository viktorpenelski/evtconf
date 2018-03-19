package com.github.viktorpenelski.evtconf.spreadsheet

/**
 * Created by victo on 3/16/2018.
 */
interface OnEntriesFetched<in T : SpreadsheetEntry> {
    fun onEntriesFetched(entries: List<T>)
}