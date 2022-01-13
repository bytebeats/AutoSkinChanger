package me.bytebeats.skinchanger.demo

import android.app.Application
import me.bytebeats.skinchanger.SkinManager
import me.bytebeats.skinchanger.SkinResources

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
    }
}