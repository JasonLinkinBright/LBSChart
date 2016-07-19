package com.graduate.lsj.lbschartforgraduate.ui.modules.presenter;

import android.app.Activity;

import com.graduate.lsj.lbschartforgraduate.dao.pojo.VisitData;
import com.graduate.lsj.lbschartforgraduate.framework.base.BasePresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.InstitutionDetailImpl;
import com.graduate.lsj.lbschartforgraduate.utils.ToastUtil;

import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by lsj on 2016/4/1.
 */
public class InstitutionDetailPresenter extends BasePresenter<InstitutionDetailImpl> {
    /**
     * Constructor
     *
     * @param activity context
     * @param viewImpl view interface
     */
    public InstitutionDetailPresenter(Activity activity, InstitutionDetailImpl viewImpl) {
        super(activity, viewImpl);
    }

    public void loadData(String objectID) {
        BmobQuery<VisitData> query = new BmobQuery<>();
        query.addWhereEqualTo("institution", objectID);
        query.include("institution");
        query.findObjects(activity, new FindListener<VisitData>() {
            @Override
            public void onSuccess(List<VisitData> list) {
                if (list.size() >= 1) {
                    viewImpl.deploySuccess(list.get(0));
                    ToastUtil.get().showShortToast(activity, "查询成功");
                } else {
                    viewImpl.deployFailed();
                }
            }

            @Override
            public void onError(int i, String s) {
                viewImpl.deployFailed();
                ToastUtil.get().showShortToast(activity, "查询失败");
            }
        });

    }

    public void addLike(String objectID, boolean addLike) {
        int addOne = 1;
        if (!addLike) {
            addOne = -1;
        }
        VisitData visitData = new VisitData();
        visitData.increment("like", addOne);
        visitData.update(activity, objectID, new UpdateListener() {
            @Override
            public void onSuccess() {
                viewImpl.changeLikeState();
            }

            @Override
            public void onFailure(int i, String s) {
                viewImpl.changeLikeStateFailed();
            }
        });
    }

    public void addSignIn(String objectID, boolean addSignIn) {
        Random r = new Random();
        int i = r.nextInt(5) + 1;
        String key = "visit" + i;
        int addOne = 1;
        if (!addSignIn) {
            addOne = -1;
        }
        VisitData visitData=new VisitData();
        visitData.increment(key,addOne);
        visitData.update(activity, objectID, new UpdateListener() {
            @Override
            public void onSuccess() {
                viewImpl.changeSignInState();
            }

            @Override
            public void onFailure(int i, String s) {
                viewImpl.changeSignInStateFailed();
            }
        });
    }
}
