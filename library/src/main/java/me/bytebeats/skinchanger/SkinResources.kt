package me.bytebeats.skinchanger

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.text.TextUtils
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.content.res.Resources.NotFoundException
import android.os.Build
import kotlin.jvm.Volatile

/**
 * @Author bytebeats
 * @Email <happychinapc></happychinapc>@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/1/12 20:51
 * @Version 1.0
 * @Description TO-DO
 */
class SkinResources private constructor(private val context: Context) {
    private var mSkinResources: Resources? = null
    private var mSkinPackageName: String? = null
    private var isDefaultSkin = true
    private val mAppResources: Resources
    fun reset() {
        mSkinResources = null
        mSkinPackageName = ""
        isDefaultSkin = true
    }

    private fun requireAtLeastM(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

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

    @SuppressLint("UseCompatLoadingForColorStateLists")
    fun getColorStateList(resId: Int): ColorStateList {
        if (isDefaultSkin) {
            return if (requireAtLeastM()) mAppResources.getColorStateList(resId, context.theme)
            else mAppResources.getColorStateList(resId)
        }
        val skinId = getIdentifier(resId)
        return if (skinId == 0) {
            if (requireAtLeastM()) mAppResources.getColorStateList(resId, context.theme)
            else mAppResources.getColorStateList(resId)
        } else if (requireAtLeastM()) mSkinResources!!.getColorStateList(resId, context.theme)
        else mSkinResources!!.getColorStateList(resId)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getDrawable(resId: Int): Drawable {
        if (isDefaultSkin) {
            return if (requireAtLeastM()) mAppResources.getDrawable(resId, context.theme)
            else mAppResources.getDrawable(resId)
        }
        val skinId = getIdentifier(resId)
        return if (skinId == 0) {
            if (requireAtLeastM()) mAppResources.getDrawable(resId, context.theme)
            else mAppResources.getDrawable(resId)
        } else if (requireAtLeastM()) mSkinResources!!.getDrawable(resId, context.theme)
        else mSkinResources!!.getDrawable(resId)
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