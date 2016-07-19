package com.graduate.lsj.lbschartforgraduate.ui.modules.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.graduate.lsj.lbschartforgraduate.R;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseActivity;
import com.graduate.lsj.lbschartforgraduate.utils.VersionUpdateHelper;

/**
 * Created by lsj on 2016/3/22.
 */
public class SplashActivity extends BaseActivity {
    private static final int DELAY_MILLIS = 1000;

    private final Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        dispatchIntent();

    }

    private void dispatchIntent() {
        if (!VersionUpdateHelper.isLogin(this)) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }, DELAY_MILLIS);
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, CoreActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, DELAY_MILLIS);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Intent intent = new Intent(SplashActivity.this, CoreActivity.class);
        startActivity(intent);
        finish();
    }
}
