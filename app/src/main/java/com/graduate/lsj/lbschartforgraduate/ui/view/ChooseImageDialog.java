package com.graduate.lsj.lbschartforgraduate.ui.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.graduate.lsj.lbschartforgraduate.R;

import org.xutils.x;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lsj on 2016/6/1.
 */
public class ChooseImageDialog extends Dialog implements View.OnClickListener {
    @Bind(R.id.dialog_choose_image_album)
    TextView dialogChooseImageAlbum;
    @Bind(R.id.dialog_choose_image_camera)
    TextView dialogChooseImageCamera;
    @Bind(R.id.dialog_choose_image_cancel)
    TextView dialogChooseImageCancel;

    /**
     * request code：从相册选择照片
     */
    public static final int REQUEST_ALBUM = 1000;
    /**
     * request code：拍照
     */
    public static final int REQUEST_CAMERA = 1001;

    private Context context;
    private Activity activity;
    private String cameraPhotoPath;

    public ChooseImageDialog(Context context) {
        super(context, R.style.ChooseImageDialogStyle);
        this.context = context;
    }

    public ChooseImageDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected ChooseImageDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_image);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.ChooseImageDialogAnimation);

        dialogChooseImageAlbum.setOnClickListener(this);
        dialogChooseImageCamera.setOnClickListener(this);
        dialogChooseImageCancel.setOnClickListener(this);

    }

    public void setCameraPhotoPath(String cameraPhotoPath) {
        this.cameraPhotoPath = cameraPhotoPath;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_choose_image_album: {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("image/*");
//                activity.startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_ALBUM);
                activity.startActivityForResult(intent, REQUEST_ALBUM);
            }
            break;
            case R.id.dialog_choose_image_camera: {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (!TextUtils.isEmpty(cameraPhotoPath))
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(cameraPhotoPath)));
                activity.startActivityForResult(intent, REQUEST_CAMERA);
            }
            break;
            case R.id.dialog_choose_image_cancel:

                break;
        }
        this.dismiss();
    }


}
