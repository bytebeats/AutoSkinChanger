package me.bytebeats.skinchanger

import android.app.Application
import me.bytebeats.skinchanger.SkinManager
import android.text.TextUtils
import me.bytebeats.skinchanger.SkinResources
import android.content.res.AssetManager
import java.lang.reflect.Method
import android.content.res.Resources
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import java.lang.Exception
import kotlin.jvm.Volatile
import me.bytebeats.skinchanger.SkinLifeCycleCallback
import java.util.*

/**
 * @Author bytebeats
 * @Email <happychinapc></happychinapc>@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/1/12 19:55
 * @Version 1.0
 * @Description TO-DO
 */
class SkinManager private constructor(private val mApp: Application) : Observable() {
    val instance: SkinManager?
        get() = Companion.instance

    fun load(apk: String?) {
        if (TextUtils.isEmpty(apk)) {
            SkinResources.instance?.reset()
        } else {
            try {
                val assetManager = AssetManager::class.java.newInstance()
                val method = assetManager.javaClass.getDeclaredMethod("addAssetPath", String::class.java)
                method.isAccessible = true
                method.invoke(assetManager, apk)

                //app默认资源
                val resources = mApp.resources
                val skinResources = Resources(assetManager, resources.displayMetrics, resources.configuration)
                val pm = mApp.packageManager
                val pi = pm.getPackageInfo(apk!!, PackageManager.GET_ACTIVITIES)
                val packageName = pi.packageName
                SkinResources.instance?.applySkin(skinResources, packageName)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        setChanged()
        notifyObservers()
    }

    companion object {
        @Volatile
        private var instance: SkinManager? = null
        fun init(application: Application) {
            if (instance == null) {
                synchronized(SkinManager::class.java) {
                    if (instance == null) {
                        instance = SkinManager(application)
                    }
                }
            }
        }
    }

    init {
        mApp.registerActivityLifecycleCallbacks(SkinLifeCycleCallback())
    }
}