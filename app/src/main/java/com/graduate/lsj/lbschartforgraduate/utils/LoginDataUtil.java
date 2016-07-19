package com.graduate.lsj.lbschartforgraduate.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lsj on 2016/3/29.
 */
public class LoginDataUtil {

    private static final String SP_NAME = "login_data";

    private static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    // 令牌，作为是否登录过的标志位
    public static String getToken(Context context) {
        SharedPreferences sp = getSP(context);
        return sp.getString("token", "");
    }

    public static void setToken(Context context, String token) {
        SharedPreferences sp = getSP(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.apply();
    }

    // 账号
    public static String getPhone(Context context){
        SharedPreferences sp = getSP(context);
        return sp.getString("phone", "");
    }

    public static void setPhone(Context context, String phone) {
        SharedPreferences sp = getSP(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("phone", phone);
        editor.apply();
    }

    // 账号编码
    public static Long getAccountId(Context context){
        SharedPreferences sp = getSP(context);
        return sp.getLong("accountid", 0);
    }

    public static void setAccountId(Context context, long accountid) {
        SharedPreferences sp = getSP(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("accountid", accountid);
        editor.apply();
    }

    // 所在地区编码
    public static long getRegionID(Context context){
        SharedPreferences sp = getSP(context);
        return sp.getLong("currentRegionID", 510100);
    }

    public static void setRegionID(Context context,long regionId){
        SharedPreferences sp = getSP(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("currentRegionID", regionId);
        editor.apply();
    }

    // 用户名称
    public static String getName(Context context){
        SharedPreferences sp = getSP(context);
        return sp.getString("name", "");
    }

    public static void setName(Context context,String name){
        SharedPreferences sp = getSP(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", name);
        editor.apply();
    }

    // 用户名称
    public static String getSex(Context context){
        SharedPreferences sp = getSP(context);
        return sp.getString("sex", "");
    }

    public static void setSex(Context context,String sex){
        SharedPreferences sp = getSP(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("sex", sex);
        editor.apply();
    }

    // 用户头像
    public static String getIcon(Context context){
        SharedPreferences sp = getSP(context);
        return sp.getString("icon","");
    }

    public static void setIcon(Context context,String headIcon){
        SharedPreferences sp = getSP(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("icon",headIcon);
        editor.apply();
    }

    // 用户昵称
    public static String getNickName(Context context){
        SharedPreferences sp = getSP(context);
        return sp.getString("nick","");
    }

    public static void setNickName(Context context,String nickName){
        SharedPreferences sp = getSP(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("nick",nickName);
        editor.apply();
    }

    public static void setLongitude(Context context,String longitude){
        SharedPreferences sp=getSP(context);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("longitude", longitude);
        editor.apply();
    }

    // 经度
    public static String getLongitude(Context context){
        SharedPreferences sp=getSP(context);
        return sp.getString("longitude", "0");
    }

    public static void setLatitude(Context context,String latitude){
        SharedPreferences sp=getSP(context);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("latitude",latitude);
        editor.apply();
    }

    // 纬度
    public static String getLatitude(Context context){
        SharedPreferences sp=getSP(context);
        return sp.getString("latitude", "0");
    }

    // 年龄
    public static int getAge(Context context){
        SharedPreferences sp = getSP(context);
        return sp.getInt("age", 0);
    }

    public static void setAge(Context context, int age) {
        SharedPreferences sp = getSP(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("age", age);
        editor.apply();
    }


    // 删除配置文件
    public static void delete(Context context){
        SharedPreferences sp = getSP(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
    }
}
