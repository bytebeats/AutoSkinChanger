package me.bytebeats.skinchanger

import android.annotation.SuppressLint
import android.content.Context

object SkinUtil {

    @SuppressLint("Recycle")
    fun getResId(context: Context, attrs: IntArray): IntArray {
        val resIds = IntArray(attrs.size)
        val typedArray = context.obtainStyledAttributes(attrs)
        for (i in 0 until typedArray.indexCount) {
            resIds[i] = typedArray.getResourceId(i, 0)
        }
        typedArray.recycle()
        return resIds
    }
}