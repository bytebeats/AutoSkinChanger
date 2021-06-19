package me.bytebeats.skinchanger

import android.content.Context
import android.content.res.Resources
import android.text.TextUtils
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.content.res.Resources.NotFoundException
import kotlin.jvm.Volatile
import me.bytebeats.skinchanger.SkinResources

/**
 * @Author bytebeats
 * @Email <happychinapc></happychinapc>@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/1/12 20:51
 * @Version 1.0
 * @Description TO-DO
 */
class SkinResources private constructor(context: Context) {
    private var mSkinResources: Resources? = null
    private var mSkinPackageName: String? = null
    private var isDefaultSkin = true
    private val mAppResources: Resources
    fun reset() {
        mSkinResources = null
        mSkinPackageName = ""
        isDefaultSkin = true
    }

    fun applySkin(resources: Resources?, pkgName: String?) {
        mSkinResources = resources
        mSkinPackageName = pkgName
        isDefaultSkin = TextUtils.isEmpty(pkgName) || resources == null
    }

    fun getIdentifier(resId: Int): Int {
        if (isDefaultSkin) {
            return resId
        }
        val resName = mAppResources.getResourceEntryName(resId)
        val resType = mAppResources.getResourceTypeName(resId)
        return mSkinResources!!.getIdentifier(resName, resType, mSkinPackageName)
    }

    fun getColor(resId: Int): Int {
        if (isDefaultSkin) {
            return mAppResources.getColor(resId)
        }
        val skinId = getIdentifier(resId)
        return if (skinId == 0) {
            mAppResources.getColor(resId)
        } else mSkinResources!!.getColor(skinId)
    }

    fun getColorStateList(resId: Int): ColorStateList {
        if (isDefaultSkin) {
            return mAppResources.getColorStateList(resId)
        }
        val skinId = getIdentifier(resId)
        return if (skinId == 0) {
            mAppResources.getColorStateList(resId)
        } else mSkinResources!!.getColorStateList(skinId)
    }

    fun getDrawable(resId: Int): Drawable {
        if (isDefaultSkin) {
            return mAppResources.getDrawable(resId)
        }
        val skinId = getIdentifier(resId)
        return if (skinId == 0) {
            mAppResources.getDrawable(resId)
        } else mSkinResources!!.getDrawable(skinId)
    }

    fun getBackground(resId: Int): Any {
        val resTypeName = mAppResources.getResourceTypeName(resId)
        return if (resTypeName == "color") {
            getColor(resId)
        } else {
            getBackground(resId)
        }
    }

    fun getString(resId: Int): String? {
        try {
            if (isDefaultSkin) {
                return mAppResources.getString(resId)
            }
            val skinId = getIdentifier(resId)
            return if (skinId == 0) {
                mAppResources.getString(resId)
            } else mSkinResources!!.getString(skinId)
        } catch (e: NotFoundException) {
        }
        return null
    }

    companion object {
        @Volatile
        var instance: SkinResources? = null
            private set

        fun init(context: Context) {
            if (instance == null) {
                synchronized(SkinResources::class.java) {
                    if (instance == null) {
                        instance = SkinResources(context)
                    }
                }
            }
        }
    }

    init {
        mAppResources = context.resources
    }
}