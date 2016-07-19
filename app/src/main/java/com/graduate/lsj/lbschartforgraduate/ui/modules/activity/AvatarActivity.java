package com.graduate.lsj.lbschartforgraduate.ui.modules.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.graduate.lsj.lbschartforgraduate.R;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseActivity;
import com.graduate.lsj.lbschartforgraduate.ui.modules.presenter.AvatarPresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.AvatarViewImpl;
import com.graduate.lsj.lbschartforgraduate.ui.view.ChooseImageDialog;
import com.graduate.lsj.lbschartforgraduate.utils.FileUtil;
import com.graduate.lsj.lbschartforgraduate.utils.GetFilePath;
import com.graduate.lsj.lbschartforgraduate.utils.LoginDataUtil;
import com.graduate.lsj.lbschartforgraduate.utils.NetUtils;
import com.graduate.lsj.lbschartforgraduate.utils.ToastUtil;


import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

/**
 * Created by lsj on 2016/6/1.
 */
public class AvatarActivity extends BaseActivity implements AvatarViewImpl{

    private static final int REQUEST_CUT = 1002;

    @ViewInject(R.id.avatar_layout)
    LinearLayout mAvatarLayout;
    @ViewInject(R.id.user_avatar)
    SimpleDraweeView mUserAvatar;

    private String fileDir;
    private String tempFilePath;
    private File avatarFile;
    private File tempFile;

    private AvatarPresenter mPresenter;

    private ChooseImageDialog chooseImageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        x.view().inject(this);
        showBackActionBtn();
        setTitle("个人头像");
        mPresenter = new AvatarPresenter(this, this);
        mPresenter.init();

        showRightAction(R.mipmap.sangedian, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempFilePath = FileUtil.newImageFileDir(fileDir, "temp");
                chooseImageDialog.setCameraPhotoPath(tempFilePath);
                tempFile = new File(tempFilePath);
                chooseImageDialog.show();

            }
        });
        setLeftActionRes();
    }

    @Override
    public void initView(View view) {
        String icon = LoginDataUtil.getIcon(this);
        if (TextUtils.isEmpty(icon)) {
            icon = "res://mipmap/" + R.mipmap.user_icon_default;
        }
        mUserAvatar.setAspectRatio(1f);
        mUserAvatar.setImageURI(Uri.parse(icon));

        chooseImageDialog = new ChooseImageDialog(this);
        chooseImageDialog.setActivity(this);

        fileDir = FileUtil.newFileFolder(this, null, "cache");
        String filePath = FileUtil.newImageFileDir(fileDir, "avatar");
        avatarFile = new File(filePath);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void showLoading(boolean shouldShow) {
        showLoadingView(shouldShow);

    }

    @Override
    public void isNetConnected(boolean connected) {
        if(!connected){
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this).setMessage(R.string.netSetting)
                    .setNegativeButton(R.string.settingAccess, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NetUtils.openSetting(AvatarActivity.this);
                        }
                    });
            builder.create().show();
        }

    }

    @Override
    public void SetAvatarSuccess(String path) {
        ToastUtil.get().showShortToast(AvatarActivity.this, "设置成功");
        LoginDataUtil.setIcon(this, path);
        setResult(RESULT_OK);
        finish();

    }

    @Override
    public void SetAvatarFailed(String msg) {
        ToastUtil.get().showShortToast(AvatarActivity.this, msg);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ChooseImageDialog.REQUEST_CAMERA:
                    cropPhoto(Uri.fromFile(tempFile));
                    break;
                case ChooseImageDialog.REQUEST_ALBUM:
                    cropPhoto(data.getData());
                    break;
                case REQUEST_CUT:
                    mPresenter.upload(avatarFile);
                    break;

            }
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tempFile != null) {
            FileUtil.deleteFile(tempFilePath);
        }
    }


    private void cropPhoto(Uri uri) {
        String path = GetFilePath.getPath(AvatarActivity.this, uri);
        File file = null;
        if (path != null) {
            file = new File(path);
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(file), "image/*");
        intent.putExtra("output", Uri.fromFile(avatarFile));
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        int crop = 300;
        intent.putExtra("outputX", crop);
        intent.putExtra("outputY", crop);
        startActivityForResult(intent, REQUEST_CUT);

    }
}
