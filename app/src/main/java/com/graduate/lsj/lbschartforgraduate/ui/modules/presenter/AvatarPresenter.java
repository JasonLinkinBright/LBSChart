package com.graduate.lsj.lbschartforgraduate.ui.modules.presenter;

import android.app.Activity;

import com.graduate.lsj.lbschartforgraduate.dao.pojo.MyUser;
import com.graduate.lsj.lbschartforgraduate.framework.base.BasePresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.AvatarViewImpl;
import com.graduate.lsj.lbschartforgraduate.utils.LogTool;
import com.graduate.lsj.lbschartforgraduate.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by lsj on 2016/6/1.
 */
public class AvatarPresenter extends BasePresenter<AvatarViewImpl> {
    public AvatarPresenter(Activity activity, AvatarViewImpl viewImpl) {
        super(activity, viewImpl);
    }

    public void upload(File file) {
        List<File> list = new ArrayList<>();
        list.add(file);
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadblock(activity, new UploadFileListener() {
            @Override
            public void onSuccess() {
                setAvatar(bmobFile.getFileUrl(activity));
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtil.get().showShortToast(activity, "上传图片失败");
            }
        });
        viewImpl.showLoading(true);


    }

    private void setAvatar(final String path) {

        MyUser myUser = BmobUser.getCurrentUser(activity, MyUser.class);
        MyUser newUser = new MyUser();
        newUser.setUserIcon(path);
        newUser.update(activity, myUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                viewImpl.showLoading(false);
                viewImpl.SetAvatarSuccess(path);
            }

            @Override
            public void onFailure(int i, String s) {
                viewImpl.showLoading(false);
                viewImpl.SetAvatarFailed("上传失败");
            }
        });

    }

}

