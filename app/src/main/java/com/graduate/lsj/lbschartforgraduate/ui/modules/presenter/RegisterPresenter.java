package com.graduate.lsj.lbschartforgraduate.ui.modules.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.graduate.lsj.lbschartforgraduate.R;
import com.graduate.lsj.lbschartforgraduate.dao.pojo.MyUser;
import com.graduate.lsj.lbschartforgraduate.framework.base.BasePresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.RegisterImpl;
import com.graduate.lsj.lbschartforgraduate.utils.EditTextVerifyUtils;
import com.graduate.lsj.lbschartforgraduate.utils.LoginDataUtil;
import com.graduate.lsj.lbschartforgraduate.utils.ToastUtil;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by lsj on 2016/3/22.
 */
public class RegisterPresenter extends BasePresenter<RegisterImpl> {
    /**
     * Constructor
     *
     * @param activity context
     * @param viewImpl view interface
     */
    public RegisterPresenter(Activity activity, RegisterImpl viewImpl) {
        super(activity, viewImpl);
    }

    public void register() {
        String account = viewImpl.getUserName();
        final String password = viewImpl.getPassWord();
        final String passwordCheck = viewImpl.getCheckPassWord();
        if (TextUtils.isEmpty(account)) {
            ToastUtil.get().showShortToast(activity, String.format(activity.getString(R.string.not_null), activity.getString(R.string.phone_number)));
            return;
        } else if (!EditTextVerifyUtils.isMobile(account)) {
            ToastUtil.get().showShortToast(activity, activity.getString(R.string.phone_illegal));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.get().showShortToast(activity, String.format(activity.getString(R.string.not_null), activity.getString(R.string.user_password_login_hint)));
            return;
        }
        if (TextUtils.isEmpty(passwordCheck)) {
            ToastUtil.get().showShortToast(activity, String.format(activity.getString(R.string.not_null), activity.getString(R.string.user_password_login_hint)));
            return;
        } else if (!password.equals(passwordCheck)) {
            ToastUtil.get().showShortToast(activity, String.format(activity.getString(R.string.confirm_pwd_failed)));
        }


        MyUser myUser = new MyUser();
        myUser.setUsername(account);
        myUser.setPassword(password);
        myUser.setLatitude(Double.parseDouble(LoginDataUtil.getLatitude(activity)));
        myUser.setLongitude(Double.parseDouble(LoginDataUtil.getLongitude(activity)));
        myUser.signUp(activity, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtil.get().showShortToast(activity, "注册成功");
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtil.get().showShortToast(activity, s);
            }
        });

    }


}
