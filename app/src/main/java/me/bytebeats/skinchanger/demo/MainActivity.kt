package me.bytebeats.skinchanger.demo

import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import me.bytebeats.skinchanger.SkinLayoutFactory2


class MainActivity : AppCompatActivity() {
    private val text by lazy { findViewById<TextView>(R.id.text) }
    private val image by lazy { findViewById<ImageView>(R.id.image) }
    private val webView by lazy { findViewById<WebView>(R.id.web) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dynamicAddTitleView()
    }

    private fun dynamicAddTitleView() {
        val textView = TextView(this)
        textView.text = "Small Article (动态new)"
        val param: RelativeLayout.LayoutParams =
            RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
        param.addRule(RelativeLayout.CENTER_IN_PARENT)
        textView.layoutParams = param
        textView.setTextColor(resources.getColor(R.color.colorPrimaryDark))
        textView.textSize = 20f

        layoutInflater.factory2?.let {
            if (it is SkinLayoutFactory2) {
                it.loadProgrammaticView(textView, "textColor", R.color.colorPrimaryDark)
                it.loadProgrammaticView(textView, "background", R.color.colorPrimary)
            }
        }
    }

}