package me.bytebeats.skinchanger.demo

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import me.bytebeats.skinchanger.SkinChangeable
import me.bytebeats.skinchanger.SkinResources

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created at 2022/1/13 12:18
 * @Version 1.0
 * @Description TO-DO
 */
class TestView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int) :
        View(context, attrs, defStyleAttr), SkinChangeable {
    private var child: TextView? = null
    override fun applySkin() {
        child?.text = SkinResources.instance?.getString(R.string.app_name)
    }
}