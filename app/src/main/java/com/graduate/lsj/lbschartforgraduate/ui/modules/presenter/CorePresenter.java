package com.graduate.lsj.lbschartforgraduate.ui.modules.presenter;

import android.app.Activity;

import com.graduate.lsj.lbschartforgraduate.dao.pojo.MyUser;
import com.graduate.lsj.lbschartforgraduate.dao.pojo.NearInstitution;
import com.graduate.lsj.lbschartforgraduate.framework.base.BasePresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.CoreImpl;
import com.graduate.lsj.lbschartforgraduate.utils.LogTool;
import com.graduate.lsj.lbschartforgraduate.utils.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by lsj on 2016/3/28.
 */
public class CorePresenter extends BasePresenter<CoreImpl> {
    /**
     * Constructor
     *
     * @param activity context
     * @param viewImpl view interface
     */
    public CorePresenter(Activity activity, CoreImpl viewImpl) {
        super(activity, viewImpl);
    }

    public void getAccountInfo() {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        query.addWhereEqualTo("username", "林时均");
        query.setLimit(10);
        query.findObjects(activity, new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                for (MyUser user : list) {
                    ToastUtil.get().showShortToast(activity, user.getUsername());
                }
            }

            @Override
            public void onError(int i, String s) {
                ToastUtil.get().showShortToast(activity, s);
            }
        });
    }

    public void loadMarker() {
        BmobQuery<NearInstitution> query = new BmobQuery<>();
        query.setLimit(50);
        query.findObjects(activity, new FindListener<NearInstitution>() {
            @Override
            public void onSuccess(List<NearInstitution> list) {
//                LogTool.e("dalsfj",list.toString());
                LogTool.e("打撒了房间", list.size() + "数据");
                viewImpl.deployOverlay(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public void addData(final NearInstitution institution) {
        institution.save(activity, new SaveListener() {
            @Override
            public void onSuccess() {
                LogTool.e("查询成功", institution.getObjectId() + institution.getCreatedAt());
            }

            @Override
            public void onFailure(int i, String s) {
                LogTool.e("查询失败", "查询失败");
            }
        });
    }
}
