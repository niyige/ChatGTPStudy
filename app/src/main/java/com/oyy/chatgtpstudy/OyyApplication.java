package com.oyy.chatgtpstudy;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import dagger.hilt.android.HiltAndroidApp;
import kotlinx.coroutines.GlobalScope;

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
            getSlotFromICCid(this);
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

    public static void getSlotFromICCid(Context context) {
        int slot = -1;
        try {
            SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            if (subscriptionManager != null) {
                @SuppressLint("MissingPermission")
                List<SubscriptionInfo> activeSubscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
                if (activeSubscriptionInfoList != null && activeSubscriptionInfoList.size() > 0) {
                    for (SubscriptionInfo activeSubscriptionInfo : activeSubscriptionInfoList) {
//                        if (activeSubscriptionInfo.getIccId().equals(iccId)) {
//                            slot = activeSubscriptionInfo.getSimSlotIndex();
//                            break;
//                        }
                        Log.i("OyyApplication", "getSlotFromICCid: " + activeSubscriptionInfo.getIccId());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return slot;
    }


    public void setDataRoamingEnabled(Context context, boolean enabled) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            SubscriptionManager subscriptionManager = SubscriptionManager.from(context);

            // 获取所有的 SIM 卡
            List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();

            for (SubscriptionInfo subscriptionInfo : subscriptionInfoList) {
                int subId = subscriptionInfo.getSubscriptionId();
                // 设置每张 SIM 卡的数据漫游状态
                try {
                    setRoamingStateForSubscription(context, subId, enabled);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            // 对于 Android 5.1 以下的版本，无法单独设置每张 SIM 卡的数据漫游状态
            // 使用之前的方法设置数据漫游状态
            Settings.Secure.putInt(context.getContentResolver(), Settings.Secure.DATA_ROAMING, enabled ? 1 : 0);
        }
    }

    private void setRoamingStateForSubscription(Context context, int subId, boolean enabled) throws Exception {
        // 通过反射调用隐藏的 API
        Class<?> telephonyManagerClass = Class.forName("android.telephony.TelephonyManager");

        Method setDataRoamingEnabledMethod = telephonyManagerClass.getDeclaredMethod("setDataRoamingEnabled", int.class, boolean.class);
        setDataRoamingEnabledMethod.setAccessible(true);

        // 调用方法设置数据漫游状态
        setDataRoamingEnabledMethod.invoke(context.getSystemService(Context.TELEPHONY_SERVICE), subId, enabled);
    }

}
