package com.graduate.lsj.lbschartforgraduate.ui.modules.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import com.graduate.lsj.lbschartforgraduate.R;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseActivity;
import com.graduate.lsj.lbschartforgraduate.ui.modules.presenter.AboutPresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.AboutImpl;
import com.graduate.lsj.lbschartforgraduate.utils.LogTool;
import com.graduate.lsj.lbschartforgraduate.utils.NetUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by lsj on 2016/4/8.
 */
public class AboutActivity extends BaseActivity implements AboutImpl {

    private AboutPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        x.view().inject(this);
        setTitle("关于一统地图");
        mPresenter = new AboutPresenter(this, this);
        mPresenter.init();
    }



    @Override
    public void initView(View view) {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void showLoading(boolean shouldShow) {

    }

    @Override
    public void isNetConnected(boolean connected) {
        if(!connected){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this).setMessage(R.string.netSetting)
                    .setNegativeButton(R.string.settingAccess, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NetUtils.openSetting(AboutActivity.this);
                        }
                    });
            builder.create().show();
        }
    }
}
