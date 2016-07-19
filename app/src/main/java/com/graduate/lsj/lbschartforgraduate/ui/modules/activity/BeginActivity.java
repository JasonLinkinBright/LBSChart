package com.graduate.lsj.lbschartforgraduate.ui.modules.activity;

import android.os.Bundle;

import com.graduate.lsj.lbschartforgraduate.R;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseActivity;

/**
 * Created by Administrator on 2016/3/10.
 */
public class BeginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("百度地图");
        showBackActionBtn();
    }
}
