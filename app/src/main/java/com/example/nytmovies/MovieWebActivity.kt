package com.example.nytmovies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.nytmovies.R

class MovieWebActivity : AppCompatActivity() {

    private lateinit var  movieWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_web)

        val extras = intent.extras ?: return
        val link = extras.getString("link")

        movieWebView = findViewById(R.id.webView)
        movieWebView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        movieWebView!!.loadUrl(link)

    }
}
