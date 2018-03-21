package com.github.viktorpenelski.evtconf.spreadsheet

import com.github.viktorpenelski.evtconf.EvtConfApplication
import java.io.IOException
import java.util.*

/**
 * This singleton class loads the app properties once and then acts as a cache.
 *
 * NOTE that this implementation only works for static app.properties.
 * In case app properties is changed at runtime, modifications will be required.
 */
class ActiveSpreadsheetProperties private constructor() {

    private val template = "__SHEET_NUM__"
    private val properties = loadProperties()

    companion object {
        val INSTANCE: ActiveSpreadsheetProperties by lazy { Holder.INSTANCE }
        enum class PropertyTag(val path: String) {
            MAIN_STAGE("main_stage"), TOURNAMENTS("tournaments"), SETTINGS("settings")
        }
    }

    fun getUrlFor(tag: PropertyTag): String {
        val baseUrl = properties.getProperty("spreadsheet.url")
        val sheetNumber = properties.getProperty("spreadsheet.sheet_num." + tag.path)

        return baseUrl.replace(template, sheetNumber)

    }

    //TODO(vic) this should be tested to make sure app.properties exists.
    private fun loadProperties() : Properties {
        val properties = Properties()

        try {
            //load app properties file
            val inputStream = EvtConfApplication.getContext().assets.open("app.properties")
            properties.load(inputStream)

        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        return properties
    }

    private object Holder {
        val INSTANCE = ActiveSpreadsheetProperties()
    }
}