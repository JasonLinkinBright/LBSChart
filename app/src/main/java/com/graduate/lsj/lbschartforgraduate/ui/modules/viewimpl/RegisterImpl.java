package com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl;

import com.graduate.lsj.lbschartforgraduate.framework.base.BaseViewImpl;

/**
 * Created by lsj on 2016/3/22.
 */
public interface RegisterImpl extends BaseViewImpl {
    String getUserName();

    String getPassWord();

    String getCheckPassWord();

    void registerSuccess();

    void registerFailed();
}
