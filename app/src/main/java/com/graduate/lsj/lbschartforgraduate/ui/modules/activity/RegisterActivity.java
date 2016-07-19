package com.graduate.lsj.lbschartforgraduate.ui.modules.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.graduate.lsj.lbschartforgraduate.R;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseActivity;
import com.graduate.lsj.lbschartforgraduate.ui.modules.presenter.RegisterPresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.RegisterImpl;
import com.graduate.lsj.lbschartforgraduate.ui.view.LineEditText;
import com.graduate.lsj.lbschartforgraduate.utils.NetUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by lsj on 2016/3/30.
 */
public class RegisterActivity extends BaseActivity implements RegisterImpl {

    @ViewInject(R.id.register_account_et)
    LineEditText registerAccount;
    @ViewInject(R.id.register_password_et)
    LineEditText registerPassword;
    @ViewInject(R.id.register_password_et_again)
    LineEditText registerPasswordCheck;
    @ViewInject(R.id.register_btn)
    Button registerBtn;
    @ViewInject(R.id.toolbar_right_text)
    TextView rightActionTv;

    private RegisterPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        x.view().inject(this);
        setTitle("新用户注册");

        mPresenter=new RegisterPresenter(this,this);
        mPresenter.init();

    }

    @Override
    public String getUserName() {
        return registerAccount.getText().toString().trim();
    }

    @Override
    public String getPassWord() {
        return registerPassword.getText().toString().trim();
    }

    @Override
    public String getCheckPassWord() {
        return registerPasswordCheck.getText().toString().trim();
    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void registerFailed() {

    }

    @Override
    public void initView(View view) {
        rightActionTv.setText(this.getString(R.string.login));
        registerBtn.setEnabled(false);

    }

    @Override
    public void initListener() {

        registerAccount.setTextFilledListener(new LineEditText.TextFilledListener() {
            @Override
            public void hasFilled() {
                registerBtn.setEnabled(true);
            }

            @Override
            public void notFilled() {
                registerBtn.setEnabled(false);

            }
        });

        registerPasswordCheck.setTextFilledListener(new LineEditText.TextFilledListener() {
            @Override
            public void hasFilled() {

            }

            @Override
            public void notFilled() {

            }
        });
        registerPassword.setTextFilledListener(new LineEditText.TextFilledListener() {
            @Override
            public void hasFilled() {

            }

            @Override
            public void notFilled() {

            }
        });



        rightActionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.register();
            }
        });
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
                            NetUtils.openSetting(RegisterActivity.this);
                        }
                    });
            builder.create().show();
        }
    }
}
