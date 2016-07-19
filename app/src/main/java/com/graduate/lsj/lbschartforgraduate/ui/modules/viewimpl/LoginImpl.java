package com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl;

import com.graduate.lsj.lbschartforgraduate.framework.base.BaseViewImpl;

/**
 * Created by lsj on 2016/3/25.
 */
public interface LoginImpl extends BaseViewImpl {

    String getAccount();

    String getPassWord();

    void loginSuccess();

    void loginFailed();
}
