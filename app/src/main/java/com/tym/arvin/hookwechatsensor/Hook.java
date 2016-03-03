package com.tym.arvin.hookwechatsensor;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import static de.robv.android.xposed.XposedHelpers.*;

/**
 * Created by arvin on 2016/3/3.
 */
public class Hook implements IXposedHookLoadPackage{

    final String WECHAT_PACKAGENAME = "com.tencent.mm";
    final SettingsHelper settingsHelper = new SettingsHelper("com.tym.arvin.hookwechatsensor");

    static int checkCount = 1;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        if (!loadPackageParam.packageName.equals(WECHAT_PACKAGENAME))
            return;

        XposedBridge.hookAllMethods(findClass("android.hardware.SystemSensorManager$SensorEventQueue", loadPackageParam.classLoader), "dispatchSensorEvent", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                Log.e("Hook", String.valueOf(((float[]) (param.args[1]))[0]));
//                Log.e("Hook", String.valueOf(settingsHelper.getBoolean("check", true)));
                if (settingsHelper.getBoolean("check", true)) {
                    Log.e("Hook", String.valueOf((float) (settingsHelper.getInt("count", 1) * 100 * checkCount)));
                    ((float[]) (param.args[1]))[0] = ((float[]) (param.args[1]))[0] + (float) (settingsHelper.getInt("count", 1) * 100 * checkCount);
                    checkCount++;
                }
                settingsHelper.reload();
            }
        });
    }
}
