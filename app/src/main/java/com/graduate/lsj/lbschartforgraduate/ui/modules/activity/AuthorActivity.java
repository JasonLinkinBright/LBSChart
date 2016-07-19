package com.graduate.lsj.lbschartforgraduate.ui.modules.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.graduate.lsj.lbschartforgraduate.R;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseActivity;
import com.graduate.lsj.lbschartforgraduate.ui.modules.presenter.AuthorPresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.AuthorImpl;
import com.graduate.lsj.lbschartforgraduate.utils.NetUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by lsj on 2016/4/11.
 */
public class AuthorActivity extends BaseActivity implements AuthorImpl {

    private AuthorPresenter mPresenter;
    @ViewInject(R.id.author_content)
    TextView authorContent;
    @ViewInject(R.id.author_mail)
    TextView authorMail;
    @ViewInject(R.id.author_csdn)
    TextView authorCsdn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auther);
        x.view().inject(this);

        mPresenter=new AuthorPresenter(this,this);
        mPresenter.init();

        setTitle("作者声明");


    }

    @Override
    public void initView(View view) {
        authorContent.setText(getResources().getString(R.string.author_content));

        authorMail.setText("联系邮箱： lsjxxsn_1234@163.com");

        String  str = "CSDN博客： <a href='http://blog.csdn.net/linshijun33'>blog.csdn.net/linshijun33</a>";
        authorCsdn.setText(Html.fromHtml(str));
        authorCsdn.setMovementMethod(LinkMovementMethod.getInstance());
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
                            NetUtils.openSetting(AuthorActivity.this);
                        }
                    });
            builder.create().show();
        }
    }
}
