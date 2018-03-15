package byfo.evtconf.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import byfo.evtconf.R

/**
 * A simple [Fragment] subclass.
 */
class TwitchChatFragment : Fragment() {

    private val TWITCH_CHAT_URL = "https://www.twitch.tv/popout/riotgames/chat"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.webview, container, false)
    }

    override fun onResume() {
        super.onResume()

        activity.findViewById<WebView>(R.id.webview).also {
            it.settings.userAgentString = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0"
            it.settings.javaScriptEnabled = true
            it.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }

        }.apply {
                    loadUrl(TWITCH_CHAT_URL)
                }

    }
}
