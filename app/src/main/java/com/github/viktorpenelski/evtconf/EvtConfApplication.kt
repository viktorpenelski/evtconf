package com.github.viktorpenelski.evtconf

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by Vic on 2/28/2018.
 */
class EvtConfApplication : Application() {

    companion object {
        private lateinit var mContext : Context

        fun getContext() : Context {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        mContext = this
    }
}