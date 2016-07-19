package com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl;

import com.graduate.lsj.lbschartforgraduate.dao.pojo.VisitData;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseViewImpl;

import java.util.List;

/**
 * Created by lsj on 2016/4/14.
 */
public interface DiscountImpl extends BaseViewImpl {

    void deployData(List<VisitData> list);

    void deployFailed();
}
