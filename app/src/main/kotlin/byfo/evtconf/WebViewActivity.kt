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
        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webview1)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String) : Boolean {
                view.loadUrl(url)
                return true
            }
        }

        webView.settings.javaScriptEnabled = true
        //TODO(vic) this needs to be dynamic, taken from a field in Entry
        webView.loadUrl("https://battlefy.com/naow-tourneys/naow-premier/5a78e6dea772d8038f474a65/info?infoTab=rules")



    }


}