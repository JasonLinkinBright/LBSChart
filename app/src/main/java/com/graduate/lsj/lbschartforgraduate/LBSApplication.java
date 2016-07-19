package com.graduate.lsj.lbschartforgraduate;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.xutils.x;

import cn.bmob.v3.Bmob;

/**
 * Created by Linshijun on 2016/3/14.
 */
public class LBSApplication extends Application {

    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        x.Ext.init(this);
        x.Ext.setDebug(true);
        SDKInitializer.initialize(getApplicationContext());
        //初始化图片
        Fresco.initialize(getApplicationContext());
        // 初始化Bomb
        Bmob.initialize(this, "3e107710358ef21fe9442792dba31b07");
//        SDKInitializer.initialize(getApplicationContext());
//        Fresco.initialize(getApplicationContext());
//        SystemUtils.initLocation(getApplicationContext());
    }
}
