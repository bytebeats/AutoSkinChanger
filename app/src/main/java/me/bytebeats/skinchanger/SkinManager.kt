package me.bytebeats.skinchanger;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.Observable;

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2021/1/12 19:55
 * @Version 1.0
 * @Description TO-DO
 */

public class SkinManager extends Observable {
    private Application mApp;
    private static volatile SkinManager instance;

    private SkinManager(Application application) {
        mApp = application;
        mApp.registerActivityLifecycleCallbacks(new SkinLifeCycleCallback());
    }

    public static void init(Application application) {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null) {
                    instance = new SkinManager(application);
                }
            }
        }
    }

    public SkinManager getInstance() {
        return instance;
    }

    public void load(String apk) {
        if (TextUtils.isEmpty(apk)) {
            SkinResources.getInstance().reset();
        } else {
            try {
                AssetManager assetManager = AssetManager.class.newInstance();
                Method method = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
                method.setAccessible(true);
                method.invoke(assetManager, apk);

                //app默认资源
                Resources resources = mApp.getResources();
                Resources skinResources = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
                PackageManager pm = mApp.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(apk, PackageManager.GET_ACTIVITIES);
                String packageName = pi.packageName;
                SkinResources.getInstance().applySkin(skinResources, packageName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setChanged();
        notifyObservers();
    }
}
