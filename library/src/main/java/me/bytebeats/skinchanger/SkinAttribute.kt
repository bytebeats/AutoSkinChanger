package me.bytebeats.skinchanger

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.AbsListView
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/1/12 20:22
 * @Version 1.0
 * @Description TO-DO
 */

class SkinAttribute {
    private val skinViews = mutableListOf<SkinView>()

    fun load(view: View?, attrs: AttributeSet) {
        if (view == null) return
        val skinPairs = mutableListOf<SkinPair>()
        for (i in 0 until attrs.attributeCount) {
            val attrName = attrs.getAttributeName(i)
            if (mAttributes.contains(attrName)) {
                val attrValue = attrs.getAttributeValue(i)
                if (attrValue.startsWith("#")) {
                    continue
                }
                var resId = 0
                if (attrValue.startsWith("?")) {
                    val attrId = attrValue.substring(1).toInt()
                    resId = SkinUtil.getResId(view.context, intArrayOf(attrId)).first()
                } else {
                    resId = attrValue.substring(1).toInt()
                }
                if (resId != 0) {
                    skinPairs.add(SkinPair(attrName, resId))
                }
            }
        }
        if (skinPairs.isNotEmpty() || view is TextView || view is SkinChangeable) {
            skinViews.add(SkinView(view, skinPairs))
        }
    }

    fun load(view: View?, attrs: List<SkinPair>?) {
        if (view != null && !attrs.isNullOrEmpty()) {
            skinViews.add(SkinView(view, attrs))
        }
    }

    fun applySkin() {
        for (skinView in skinViews) {
            skinView.applySkin()
        }
    }

    fun clear() {
        skinViews.clear()
    }

    private companion object {
        private val mAttributes = listOf(
            "background",
            "src",
            "textColor",
            "drawableLeft",
            "drawableRight",
            "drawableTop",
            "drawableBottom",
            "text",
            "divider",
            "listSelector"
        )
    }

    data class SkinView(val view: View, val skinPairs: List<SkinPair>) {
        fun applySkin() {
            var left: Drawable? = null
            var top: Drawable? = null
            var right: Drawable? = null
            var bottom: Drawable? = null
            var start: Drawable? = null
            var end: Drawable? = null
            for (pair in skinPairs) {
                when (pair.attrName) {
                    "background" -> {
                        val background = SkinResources.instance!!.getBackground(pair.resId)
                        if (background is Int) {
                            view.setBackgroundColor(background)
                        } else {
                            view.background = background as Drawable
                        }
                    }
                    "src" -> {
                        val src = SkinResources.instance!!.getBackground(pair.resId)
                        if (src is Int) {
                            (view as ImageView).setImageDrawable(ColorDrawable(src))
                        } else {
                            (view as ImageView).setImageDrawable(src as Drawable)
                        }
                    }
                    "textColor" -> {
                        (view as TextView).setTextColor(SkinResources.instance!!.getColorStateList(pair.resId))
                    }
                    "drawableLeft" -> {
                        left = SkinResources.instance!!.getDrawable(pair.resId)
                    }
                    "drawableTop" -> {
                        top = SkinResources.instance!!.getDrawable(pair.resId)
                    }
                    "drawableRight" -> {
                        right = SkinResources.instance!!.getDrawable(pair.resId)
                    }
                    "drawableBottom" -> {
                        bottom = SkinResources.instance!!.getDrawable(pair.resId)
                    }
                    "drawableStart" -> {
                        start = SkinResources.instance!!.getDrawable(pair.resId)
                    }
                    "drawableEnd" -> {
                        end = SkinResources.instance!!.getDrawable(pair.resId)
                    }
                    "text" -> {
                        val text = SkinResources.instance!!.getString(pair.resId)
                        (view as TextView).text = text
                    }
                    "divider" -> {
                        val divider = SkinResources.instance!!.getBackground(pair.resId)
                        if (divider is Int) {
                            (view as ListView).selector = ColorDrawable(divider)
                        } else {
                            (view as ListView).selector = divider as Drawable
                        }
                    }
                    "listSelector" -> {
                        val selector = SkinResources.instance!!.getBackground(pair.resId)
                        if (selector is Int) {
                            (view as AbsListView).selector = ColorDrawable(selector)
                        } else {
                            (view as AbsListView).selector = selector as Drawable
                        }
                    }
                }
            }
            if (left != null || top != null || right != null || bottom != null) {
                (view as TextView).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
            }
            if (start != null || end != null || top != null || bottom != null) {
                (view as TextView).setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
            }
            if (view is SkinChangeable) {//customized View
                view.applySkin()
            }
        }
    }
}