package com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl;

import com.graduate.lsj.lbschartforgraduate.framework.base.BaseViewImpl;

/**
 * Created by Linshijun on 2016/5/26.
 */
public interface PersonalInfoViewImpl extends BaseViewImpl {

    String getNickName();

    String getName();

    String getSex();

    int getAge();

    String getPhone();

    void updateSuccess();

}
