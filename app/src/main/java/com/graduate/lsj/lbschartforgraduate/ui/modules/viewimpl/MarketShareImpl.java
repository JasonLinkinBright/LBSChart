package com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl;

import com.graduate.lsj.lbschartforgraduate.framework.base.BaseViewImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by lsj on 2016/4/7.
 */
public interface MarketShareImpl extends BaseViewImpl {

    void deployData(List<String> stringList, Map<String, Integer> map);

    void loadingFailed();
}
