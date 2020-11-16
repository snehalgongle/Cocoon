package com.snake.cocoon

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class BrowserActivity : AppCompatActivity() {
    var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val type = intent.getStringExtra("ClickedText")
        webView = findViewById<View>(R.id.webview) as WebView
        val webSettings = webView!!.settings
        webSettings.javaScriptEnabled = true
        webSettings.loadsImagesAutomatically = true
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webSettings.setAppCacheEnabled(true)
        webSettings.builtInZoomControls = false
        webSettings.databaseEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.setSupportZoom(true)
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webView!!.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webView!!.isScrollbarFadingEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) webView!!.setLayerType(
            View.LAYER_TYPE_HARDWARE,
            null
        ) else webView!!.setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        val main_url = intent.getSerializableExtra(this.resources.getString(R.string.extra_main_url)) as? String
        main_url?.let { webView!!.loadUrl(it) }
    }

    override fun onBackPressed() {
        finish()
    }


}