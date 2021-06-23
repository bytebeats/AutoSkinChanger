package me.bytebeats.skinchanger.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import me.bytebeats.skinchanger.demo.R

class MainActivity : AppCompatActivity() {
    private val text by lazy { findViewById<TextView>(R.id.text) }
    private val image by lazy { findViewById<ImageView>(R.id.image) }
    private val webView by lazy { findViewById<WebView>(R.id.web) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}