package me.bytebeats.skinchanger

import android.app.Application

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/1/12 19:59
 * @Version 1.0
 * @Description TO-DO
 */

class SkinChangerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SkinManager.init(this)
        SkinResources.init(this)
    }
}