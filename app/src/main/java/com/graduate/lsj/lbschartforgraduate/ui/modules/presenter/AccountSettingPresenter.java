package com.graduate.lsj.lbschartforgraduate.ui.modules.presenter;

import android.app.Activity;
import android.content.Intent;

import com.graduate.lsj.lbschartforgraduate.framework.base.BasePresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.activity.LoginActivity;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.AccountSettingImpl;

import cn.bmob.v3.BmobUser;

/**
 * Created by lsj on 2016/4/8.
 */
public class AccountSettingPresenter extends BasePresenter<AccountSettingImpl> {
    /**
     * Constructor
     *
     * @param activity context
     * @param viewImpl view interface
     */
    public AccountSettingPresenter(Activity activity, AccountSettingImpl viewImpl) {
        super(activity, viewImpl);
    }

    public void uploadImage(){

    }

    public void exit(){


    }
}
