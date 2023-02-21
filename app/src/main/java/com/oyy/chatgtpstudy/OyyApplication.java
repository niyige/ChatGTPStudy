package com.oyy.chatgtpstudy;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class OyyApplication extends Application {
    private String processName;
    private String packageName;

    private static Context context;
    private static String sCurProcessName = null;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        processName = getCurProcessName(base);
        packageName = getPackageName();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        boolean isMainProcess = !TextUtils.isEmpty(packageName) && TextUtils.equals(packageName, processName);
        if(isMainProcess) {
            //todo

        }
    }

    private static String getCurProcessName(Context context) {
        if (!TextUtils.isEmpty(sCurProcessName)) {
            return sCurProcessName;
        }
        sCurProcessName = getProcessName(android.os.Process.myPid());
        if (!TextUtils.isEmpty(sCurProcessName)) {
            return sCurProcessName;
        }
        try {
            int pid = android.os.Process.myPid();

            sCurProcessName = getProcessName(pid);
            if (!TextUtils.isEmpty(sCurProcessName)) {
                return sCurProcessName;
            }
            //获取系统的ActivityManager服务
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (am == null) {
                return sCurProcessName;
            }
            for (ActivityManager.RunningAppProcessInfo appProcess : am.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    sCurProcessName = appProcess.processName;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sCurProcessName;
    }

    private static String getProcessName(int pid) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }

            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
