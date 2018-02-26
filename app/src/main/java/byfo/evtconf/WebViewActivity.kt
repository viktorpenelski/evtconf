package byfo.evtconf

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * Created by Vic on 2/25/2018.
 */
class WebViewActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview)

        val webView = initializeWebView()
        webView.loadUrl(intent.extras.getString(EXTRAS_URL))
    }

    /**
     * Initialize the web view, taking extras into account for configuration purposes.
     */
    private fun initializeWebView() : WebView{
        val webView = findViewById<WebView>(R.id.webview)

        if (intent.extras.getBoolean(EXTRAS_REQUEST_DESKTOP, false)) {
            webView.settings.userAgentString =  "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0"
        }
        webView.settings.javaScriptEnabled = intent.extras.getBoolean(EXTRAS_ENABLE_JS, false)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String) : Boolean {
                view?.loadUrl(url)
                return true
            }
        }

        return webView
    }

    companion object {
        const val EXTRAS_URL = "url"
        const val EXTRAS_REQUEST_DESKTOP = "request_desktop"
        const val EXTRAS_ENABLE_JS = "enable_js"
    }

}