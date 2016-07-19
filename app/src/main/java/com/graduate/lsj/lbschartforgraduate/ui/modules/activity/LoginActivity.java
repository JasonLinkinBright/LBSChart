package com.graduate.lsj.lbschartforgraduate.ui.modules.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.graduate.lsj.lbschartforgraduate.R;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseActivity;
import com.graduate.lsj.lbschartforgraduate.ui.modules.presenter.LoginPresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.LoginImpl;
import com.graduate.lsj.lbschartforgraduate.ui.view.LineEditText;
import com.graduate.lsj.lbschartforgraduate.utils.NetUtils;
import com.graduate.lsj.lbschartforgraduate.utils.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Linshijun on 2016/3/21.
 */
public class LoginActivity extends BaseActivity implements LoginImpl {


    @ViewInject(R.id.login_account_et)
    LineEditText loginAccountEt;
    @ViewInject(R.id.login_password_et)
    LineEditText loginPasswordEt;
    @ViewInject(R.id.login_btn)
    Button loginBtn;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.toolbar_right_text)
    TextView rightActionTv;
//    @ViewInject(R.id.create_data_btn)
//    Button creatDataBtn;



    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        x.view().inject(this);
        setTitle(getString(R.string.login));

        mPresenter = new LoginPresenter(this, this);
        mPresenter.init();

    }


    @Override
    public void initView(View view) {
        loginBtn.setEnabled(false);
//        rightActionTv= (TextView) toolbar.findViewById(R.id.toolbar_right_text);
        rightActionTv.setText(this.getString(R.string.register));
    }

    @Override
    public void initListener() {

        rightActionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginAccountEt.setTextFilledListener(new LineEditText.TextFilledListener() {
            @Override
            public void hasFilled() {
                loginBtn.setEnabled(true);
            }

            @Override
            public void notFilled() {
                loginBtn.setEnabled(false);

            }
        });

        loginPasswordEt.setTextFilledListener(new LineEditText.TextFilledListener() {
            @Override
            public void hasFilled() {

            }

            @Override
            public void notFilled() {

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login();
            }
        });

//        creatDataBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.creatData();
//            }
//        });

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
                            NetUtils.openSetting(LoginActivity.this);
                        }
                    });
            builder.create().show();
        }
    }

    @Override
    public String getAccount() {
        return loginAccountEt.getText().toString().trim();
    }

    @Override
    public String getPassWord() {
        return loginPasswordEt.getText().toString().trim();
    }

    @Override
    public void loginSuccess() {
        Intent intent=new Intent();
        intent.setClass(LoginActivity.this,CoreActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailed() {
        ToastUtil.get().showShortToast(this,"不能登录");

    }

}
