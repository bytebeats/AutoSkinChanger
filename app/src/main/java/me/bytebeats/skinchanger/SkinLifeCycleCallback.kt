package me.bytebeats.skinchanger

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.LayoutInflaterCompat
import java.lang.RuntimeException

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/1/12 20:00
 * @Version 1.0
 * @Description TO-DO
 */

class SkinLifeCycleCallback : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        val inflater = LayoutInflater.from(activity)
        try {
            val field = LayoutInflater::class.java.getDeclaredField("mFactorySet")
            field.isAccessible = true
            field.setBoolean(inflater, false)
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
        val factory2 = SkinLayoutFactory2()
        LayoutInflaterCompat.setFactory2(inflater, factory2)
    }

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}

}