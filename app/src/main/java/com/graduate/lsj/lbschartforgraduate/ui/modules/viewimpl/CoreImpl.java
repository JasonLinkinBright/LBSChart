package com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl;

import com.graduate.lsj.lbschartforgraduate.dao.pojo.NearInstitution;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseViewImpl;

import java.util.List;

/**
 * Created by lsj on 2016/3/28.
 */
public interface CoreImpl extends BaseViewImpl {
    void refreshAccountSuccess();

    void refreshAccountFailed();

    /**
     * 获取纬度
     */
    double getLatitude();

    /**
     * 获取经度
     */
    double getLongitude();

    void deployOverlay(List<NearInstitution> institutions);
}
