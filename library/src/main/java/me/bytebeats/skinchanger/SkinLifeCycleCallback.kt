package me.bytebeats.skinchanger

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.core.view.LayoutInflaterCompat
import java.lang.RuntimeException
import java.util.*

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
        val factory2 = SkinLayoutFactory2()
        /**
         * only worked before Android 10.
         * On Android 10+, NoSuchFieldException: No field mFactorySet in class Landroid/view/LayoutInflater
         */
//        try {
//            val field = LayoutInflater::class.java.getDeclaredField("mFactorySet")
//            field.isAccessible = true
//            field.setBoolean(inflater, false)
//        } catch (e: RuntimeException) {
//            e.printStackTrace()
//        }
//        LayoutInflaterCompat.setFactory2(inflater, factory2)
        forceSetFactory2(inflater, factory2)
        SkinManager.addObserver(factory2)
        if (activity is ComponentActivity) {
            activity.lifecycle.addObserver(factory2)
        }
    }


    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        if (activity.layoutInflater.factory2 is Observer) {
            SkinManager.deleteObserver(activity.layoutInflater.factory2 as Observer)
        }
    }

    companion object {
        private fun forceSetFactory2(inflater: LayoutInflater, factory2: SkinLayoutFactory2) {
            val compatInflaterClass = LayoutInflaterCompat::class.java
            val inflaterClass = LayoutInflater::class.java
            try {
                val sCheckedField = compatInflaterClass.getDeclaredField("sCheckedField")
                sCheckedField.isAccessible = true
                sCheckedField.set(inflater, false)//???
                val mFactory = inflaterClass.getDeclaredField("mFactory")
                mFactory.isAccessible = true
                val mFactory2 = inflaterClass.getDeclaredField("mFactory2")
                mFactory2.isAccessible = true
                factory2.mSrcFactory2 = inflater.factory2
                factory2.mSrcFactory = inflater.factory
                mFactory2.set(inflater, factory2)
                mFactory.set(inflater, factory2)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            }
        }
    }

}