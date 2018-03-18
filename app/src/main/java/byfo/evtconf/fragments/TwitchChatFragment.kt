package byfo.evtconf.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Switch
import byfo.evtconf.R

/**
 * A simple [Fragment] subclass.
 */
class TwitchChatFragment : Fragment() {

    private val TWITCH_CHAT_URL = "https://www.twitch.tv/popout/hashinshin/chat"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_twitch_chat, container, false)
        initializeTwitchSwitch(view.findViewById(R.id.twitch_switch))
        return view
    }

    private fun initializeTwitchSwitch(switch: Switch) {
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked) {
                activity.findViewById<WebView>(R.id.twitch_webview).visibility = View.INVISIBLE
                pauseWebView()
            } else {
                initializeWebView()
                activity.findViewById<WebView>(R.id.twitch_webview).visibility = View.VISIBLE
            }
        }
    }

    private fun initializeWebView() {

        val webView = activity.findViewById<WebView>(R.id.twitch_webview)

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
                    val progressBar = activity.findViewById<ProgressBar>(R.id.twitch_progress_bar)

                    if (newProgress < 100 && View.GONE.equals(progressBar.visibility)) {
                        progressBar.visibility = View.VISIBLE
                    } else if (newProgress >= 100){
                        progressBar.visibility = View.GONE
                    }

                }
            }


        }.apply {
            loadUrl(TWITCH_CHAT_URL)
            onResume()
        }

    }

    private fun pauseWebView() {
        activity.findViewById<WebView>(R.id.twitch_webview).apply {
            onPause()
        }
    }


}
