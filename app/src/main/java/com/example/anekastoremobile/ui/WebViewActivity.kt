package com.example.anekastoremobile.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.anekastoremobile.databinding.ActivityWebviewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Pembayaran"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var snapToken = ""
        val i = intent
        if (i != null) snapToken = i.getStringExtra("url") ?: ""

        webView("https://app.sandbox.midtrans.com/snap/v4/redirection/$snapToken")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webView(url: String) {
        val webView = binding.webView
        val settings = webView.settings

        settings.domStorageEnabled = true
        settings.javaScriptEnabled = true
        settings.allowFileAccess = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.displayZoomControls = false
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.userAgentString = settings.userAgentString
        webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webView.isScrollbarFadingEnabled = false

        webView.loadUrl(url)
        webView.webViewClient = MyWebViewClient()
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            showLoading(true)
            super.onPageStarted(view, url, favicon)
        }

        @Deprecated(
            "Deprecated in Java", ReplaceWith(
                "super.shouldOverrideUrlLoading(view, url)",
                "android.webkit.WebViewClient"
            )
        )
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            showLoading(true)
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            showLoading(false)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // if your webView can go back it will go back
        @Suppress("DEPRECATION")
        if (binding.webView.canGoBack())
            binding.webView.goBack()
        // if your webView cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val i = Intent(this@WebViewActivity, HomeActivity::class.java)
            i.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}