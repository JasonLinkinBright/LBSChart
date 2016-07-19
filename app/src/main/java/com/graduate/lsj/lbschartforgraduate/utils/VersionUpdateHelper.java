package com.graduate.lsj.lbschartforgraduate.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by lsj on 2016/3/22.
 */
public class VersionUpdateHelper {

    private static final String CONFIG_NAME = "update_config";

    private static final String APP_ACTIVE_TIME = "app_active_time";
    private static final String APP_LAST_VERSION = "app_last_version";
    private static final String APP_IS_UPDATE = "app_is_update";

    private static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 是否全新安装
     *
     * @param context
     * @return
     */
    public static boolean isNewInstall(Context context) {
        SharedPreferences sp = getSP(context);
        return !sp.contains(APP_ACTIVE_TIME);
    }

    /**
     * 是否进行了版本更新
     *
     * @param context
     * @return
     */
    public static boolean isVersionUpdated(Context context) {
        SharedPreferences sp = getSP(context);
        String lastVersion = sp.getString(APP_LAST_VERSION, "");

        String currentVersion;
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            currentVersion = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            currentVersion = "";
        }

        return !lastVersion.equals(currentVersion);
    }

    /**
     * 更新配置文件信息
     *
     * @param context
     */
    public static void updateConfig(Context context) {
        SharedPreferences sp = getSP(context);
        SharedPreferences.Editor editor = sp.edit();
        PackageManager pm = context.getPackageManager();

        if (!sp.contains(APP_ACTIVE_TIME)) {
            editor.putLong(APP_ACTIVE_TIME, System.currentTimeMillis());
            editor.putBoolean(APP_IS_UPDATE, false);
        } else {
            editor.putBoolean(APP_IS_UPDATE, true);
        }

        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            String versionName = info.versionName;
            editor.putString(APP_LAST_VERSION, versionName);
        } catch (PackageManager.NameNotFoundException e) {
        }

        editor.apply();
    }

    public static boolean isLogin(Context context){
        return true;
    }

}
