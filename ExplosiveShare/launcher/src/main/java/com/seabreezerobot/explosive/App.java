package com.seabreezerobot.explosive;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.robot.seabreeze.log.Logger;
import com.squareup.leakcanary.LeakCanary;

/**
 * UserConfig: milan
 * Time: 2020/2/13 18:18
 * Des:
 */
public class App extends MultiDexApplication {
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initLogger(this);

        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }
    }


    private void initLogger(@NonNull Context context) {
            Logger.getLogConfig().configAllowLog(true).configShowBorders(true);
    }
}
