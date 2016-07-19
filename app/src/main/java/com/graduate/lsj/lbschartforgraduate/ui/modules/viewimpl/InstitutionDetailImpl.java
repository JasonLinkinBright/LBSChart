package com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl;

import com.graduate.lsj.lbschartforgraduate.dao.pojo.VisitData;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseViewImpl;

/**
 * Created by lsj on 2016/4/1.
 */
public interface InstitutionDetailImpl extends BaseViewImpl {

    void deploySuccess(VisitData visitData);

    void deployFailed();

    void changeLikeState();

    void changeLikeStateFailed();

    void changeSignInState();

    void changeSignInStateFailed();
}
