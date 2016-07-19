package com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl;

import com.graduate.lsj.lbschartforgraduate.dao.pojo.NearInstitution;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseViewImpl;

import java.util.List;

/**
 * Created by lsj on 2016/4/8.
 */
public interface RankImpl extends BaseViewImpl {
    void deploySuccess(List<String> typeList, List<NearInstitution> institutionList);

    void deployFailed();
}
