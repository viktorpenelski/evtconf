package com.github.viktorpenelski.evtconf.spreadsheet

import org.json.JSONObject

/**
 * Created by victo on 3/16/2018.
 */
interface SpreadsheetEntryFactory<out T : SpreadsheetEntry> {

    /**
     * Serialize a SpreadsheetEntry from given JSONObject.
     * This JSONObject has to follow google sheet's API.
     *
     * For reference check out the following GET request, looking for the array element "feed.entry":
     * https://spreadsheets.google.com/feeds/list/1_Ol_0bP-S3GqEXEGPL3ODKmHAdWBBXcOdE3_M4phVe0/1/public/values?alt=json
     */
    fun createFromJsonObject(obj: JSONObject): T
}