package com.github.viktorpenelski.evtconf

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.webview.*


class WebViewActivity : AppCompatActivity() {

    lateinit var mFirebaseAnalytics: FirebaseAnalytics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val intentUrl = intent.extras.getString(EXTRAS_URL)

        //log firebase analytics event
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, intentUrl)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "external_url")
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle)

        initializeWebView().apply {
            loadUrl(intentUrl)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return false
    }

    /**
     * Initialize the web view, taking extras into account for configuration purposes.
     */
    private fun initializeWebView(): WebView {

        return webview.apply {
            enableZoom()
            configureUserAgent()
            configureJavaScriptEnabled()
            configureWebClient()
        }
    }

    /**
     * Enable pinch zoom and disable on-screen zoom controls.
     */
    private fun WebView.enableZoom() {
        this.settings.loadWithOverviewMode = true
        this.settings.useWideViewPort = true
        this.settings.builtInZoomControls = true
        this.settings.displayZoomControls = false
    }

    /**
     * Based on [EXTRAS_REQUEST_DESKTOP], have the WebView request either mobile or desktop websites.
     *
     * If not provided, mobile websites will be requested by default.
     */
    private fun WebView.configureUserAgent() {
        if (intent.extras.getBoolean(EXTRAS_REQUEST_DESKTOP, false)) {
            this.settings.userAgentString = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0"
        }
    }

    /**
     * Based on [EXTRAS_ENABLE_JS], enable or disable JS for this WebView.
     *
     * If not provided, JS will be disabled by default.
     */
    private fun WebView.configureJavaScriptEnabled() {
        this.settings.javaScriptEnabled = intent.extras.getBoolean(EXTRAS_ENABLE_JS, false)
    }

    /**
     * Makes the host application handle url instead of webview.
     *
     * Deprecated [WebViewClient.shouldOverrideUrlLoading] is used here in order to support
     * devices prior to API 24.
     */
    private fun WebView.configureWebClient() {
        this.webViewClient = object : WebViewClient() {
            @Suppress("OverridingDeprecatedMember")
            // consider the new method as well (for devices above android N)
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
    }

    companion object {
        const val EXTRAS_URL = "url"
        const val EXTRAS_REQUEST_DESKTOP = "request_desktop"
        const val EXTRAS_ENABLE_JS = "enable_js"
    }

}