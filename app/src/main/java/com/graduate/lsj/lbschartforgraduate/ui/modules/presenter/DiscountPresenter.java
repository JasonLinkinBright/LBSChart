package com.graduate.lsj.lbschartforgraduate.ui.modules.presenter;

import android.app.Activity;

import com.graduate.lsj.lbschartforgraduate.dao.pojo.VisitData;
import com.graduate.lsj.lbschartforgraduate.framework.base.BasePresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.DiscountImpl;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by lsj on 2016/4/14.
 */
public class DiscountPresenter extends BasePresenter<DiscountImpl> {
    /**
     * Constructor
     *
     * @param activity context
     * @param viewImpl view interface
     */
    public DiscountPresenter(Activity activity, DiscountImpl viewImpl) {
        super(activity, viewImpl);
    }

    public void getData(){
        BmobQuery<VisitData> query=new BmobQuery<>();
        query.include("institution");
        query.findObjects(activity, new FindListener<VisitData>() {
            @Override
            public void onSuccess(List<VisitData> list) {

                viewImpl.deployData(list);
            }

            @Override
            public void onError(int i, String s) {
                viewImpl.deployFailed();
            }
        });
    }
}
