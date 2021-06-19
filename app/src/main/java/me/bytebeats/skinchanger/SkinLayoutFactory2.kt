package me.bytebeats.skinchanger

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import java.lang.RuntimeException
import java.lang.reflect.Constructor
import java.util.*

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/1/12 20:06
 * @Version 1.0
 * @Description TO-DO
 */

class SkinLayoutFactory2 : LayoutInflater.Factory2, Observer {
    private val skinAttributes by lazy { SkinAttribute() }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
//        return createViewFromTag(name, context, attrs)
        return null
    }

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        val view = createViewFromTag(name, context, attrs)
        skinAttributes.load(view, attrs)
        return view
    }

    private fun createViewFromTag(name: String, context: Context, attrs: AttributeSet): View? {
        if (name.contains('.')) {//完整的 View 名
            return createView(name, context, attrs)
        }
        for (path in mClassPrefixList) {
            val view = createView("$path$name", context, attrs)
            if (view != null) {
                return view
            }
        }
        return null
    }


    private fun createView(name: String, context: Context, attrs: AttributeSet): View? {
        var constructor = mConstructorMap[name]
        if (constructor == null) {
            try {
                val clazz = context.classLoader.loadClass(name).asSubclass(View::class.java)
                constructor = clazz.getConstructor(Context::class.java, AttributeSet::class.java)
                mConstructorMap[name] = constructor
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }
        }
        if (constructor != null) {
            try {
                return constructor.newInstance(constructor, attrs)
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }
        }
        return null
    }

    override fun update(o: Observable?, arg: Any?) {
        skinAttributes.applySkin()
    }

    private companion object {
        private val mConstructorMap = mutableMapOf<String, Constructor<out View>>()
        private val mClassPrefixList = listOf("android.view.", "android.widget.", "android.webkit.")
    }
}