package byfo.evtconf

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.webview.*

/**
 * Created by Vic on 2/25/2018.
 */
class WebViewActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview)

        //TODO(vic) webView needs to be scrollable.
        val webView = findViewById<WebView>(R.id.webview)

        webview.settings.builtInZoomControls = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String) : Boolean {
                view?.loadUrl(url)
                return true
            }
        }

        webView.settings.javaScriptEnabled = true
        //TODO(vic) this needs to be dynamic, taken from a field in Entry
        webView.loadUrl("https://www.toornament.com/tournaments/1200127112880726016/information")



    }


}