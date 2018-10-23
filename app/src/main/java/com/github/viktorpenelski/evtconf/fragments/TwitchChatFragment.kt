package com.github.viktorpenelski.evtconf.fragments


import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Switch
import com.github.viktorpenelski.evtconf.R
import com.github.viktorpenelski.evtconf.spreadsheet.settings.RemoteSettings
import kotlinx.android.synthetic.main.fragment_twitch_chat.*
import kotlinx.android.synthetic.main.fragment_twitch_chat.view.*


/**
 * Represents a view of twitch chat in-app.
 */
class TwitchChatFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_twitch_chat, container, false)
        initializeTwitchSwitch(view.twitch_switch)
        initializeOpenTwitchButton(view.open_external_twitch_button)
        return view
    }

    private fun initializeTwitchSwitch(switch: Switch) {
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked) {
                twitch_webview_container.visibility = View.INVISIBLE
                twitch_progress_bar.visibility = View.GONE
                destroyWebView()
            } else {
                initializeWebView()
                twitch_webview_container.visibility = View.VISIBLE
            }
        }
    }

    private fun initializeOpenTwitchButton(button: Button) {

        button.setOnClickListener {
            val url = getTwitchUrl()
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
                startActivity(this)
            }

        }
    }

    private fun destroyWebView() {
        val view = activity.findViewById<WebView>(R.id.twitch_webview) ?: return

        twitch_webview_container.removeAllViews()
        view.clearHistory()
        view.clearCache(false)
        view.loadUrl("about:blank")
        view.onPause()
        view.removeAllViews()
        view.destroyDrawingCache()
        view.destroy()
    }

    private fun initializeWebView() {

        val layout = twitch_webview_container

        val webView = WebView(context).apply {
            id = R.id.twitch_webview
            isScrollbarFadingEnabled = false
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        webView.also {
            it.settings.userAgentString = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0"
            it.settings.javaScriptEnabled = true
            it.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    view?.loadUrl(url)
                    return true
                }

            }
            it.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {

                    twitch_progress_bar?.apply {
                        if (newProgress < 100 && View.GONE.equals(visibility)) {
                            visibility = View.VISIBLE
                        } else if (newProgress >= 100) {
                            visibility = View.GONE
                        }
                    }

                }
            }

        }.apply {
            loadUrl("https://www.twitch.tv/popout/${RemoteSettings.INSTANCE.getTwitchChannel()}/chat")
            layout.addView(this)
        }


    }

    private fun getTwitchUrl(): String {
        return try {
            context.packageManager.getPackageInfo("tv.twitch.android.app", PackageManager.GET_ACTIVITIES)
            "twitch://stream/${RemoteSettings.INSTANCE.getTwitchChannel()}"
        } catch (e: PackageManager.NameNotFoundException) {
            "https://m.twitch.tv/${RemoteSettings.INSTANCE.getTwitchChannel()}"
        }

    }


}
