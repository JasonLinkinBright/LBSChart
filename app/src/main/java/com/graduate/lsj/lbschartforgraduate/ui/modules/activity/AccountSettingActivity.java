package com.graduate.lsj.lbschartforgraduate.ui.modules.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.graduate.lsj.lbschartforgraduate.R;
import com.graduate.lsj.lbschartforgraduate.dao.pojo.MyUser;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseActivity;
import com.graduate.lsj.lbschartforgraduate.listener.OnItemSelectedListener;
import com.graduate.lsj.lbschartforgraduate.ui.modules.presenter.AccountSettingPresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.AccountSettingImpl;
import com.graduate.lsj.lbschartforgraduate.ui.view.LoopView;
import com.graduate.lsj.lbschartforgraduate.utils.LogTool;
import com.graduate.lsj.lbschartforgraduate.utils.LoginDataUtil;
import com.graduate.lsj.lbschartforgraduate.utils.NetUtils;
import com.graduate.lsj.lbschartforgraduate.utils.RoundImageUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import org.xutils.*;


import java.math.RoundingMode;
import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;

import static com.graduate.lsj.lbschartforgraduate.R.layout.popup_sex_layout;

/**
 * Created by lsj on 2016/4/8.
 */
public class AccountSettingActivity extends BaseActivity implements AccountSettingImpl {

    @ViewInject(R.id.account_info_head_icon_img)
    ImageView accountHeadIconImg;
    @ViewInject(R.id.account_info_head_icon_setting)
    RelativeLayout accountHeadRy;
    @ViewInject(R.id.account_info_age_setting)
    RelativeLayout accountAgeRy;
    @ViewInject(R.id.account_info_nick_name_setting)
    RelativeLayout accountNameRy;
    @ViewInject(R.id.account_info_sex_setting)
    RelativeLayout accountSexRy;
    @ViewInject(R.id.account_info_phone_setting)
    RelativeLayout accountPhoneRy;
    @ViewInject(R.id.exit_ly)
    LinearLayout ExitLy;
    private LayoutInflater layoutInflaterForSex;
    private AccountSettingPresenter mPresenter;
    private RelativeLayout.LayoutParams layoutParams;
    private RelativeLayout rootviewForSex;
    //xUtil相关
    private ImageOptions imageOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        x.view().inject(this);
        setTitle("个人信息");

        mPresenter = new AccountSettingPresenter(this, this);
        mPresenter.init();


    }

    @Override
    public void initView(View view) {

        MyUser user = BmobUser.getCurrentUser(this, MyUser.class);

        imageOptions = new ImageOptions.Builder()
//                // 加载中或错误图片的ScaleType
//                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
//                // 默认自动适应大小
//                // .setSize(...)

//                .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
//                .setRadius(DensityUtil.dip2px(5))
                .setCircular(true)  //设置圆角
//                .setIgnoreGif(false)
//                        // 如果使用本地文件url, 添加这个设置可以在本地文件更新后刷新立即生效.
//                        //.setUseMemCache(false)
//                .setImageScaleType(ImageView.ScaleType.CENTER)
                .build();

        if (user != null) {
            BmobQuery<MyUser> query = new BmobQuery<MyUser>();
            query.getObject(this, user.getObjectId(), new GetListener<MyUser>() {
                @Override
                public void onSuccess(MyUser myUser) {
                    LogTool.e("成功", myUser.getUserIcon());

                    x.image().bind(accountHeadIconImg, myUser.getUserIcon(), imageOptions);

                }

                @Override
                public void onFailure(int i, String s) {

                    LogTool.e("失败", "什么鬼");
                }
            });


//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.head);
//        Bitmap output = RoundImageUtil.getRoundedCornerBitmap(bitmap, 2);
//        accountHeadIconImg.setImageBitmap(output);

            setUserAge();
            setUserName();
            setUserPhone();
            setUserSex();

            layoutInflaterForSex = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
            View layoutForSex = layoutInflaterForSex.inflate(R.layout.popup_sex_layout, null);

            rootviewForSex = (RelativeLayout) layoutForSex.findViewById(R.id.popup_sex_rootview);
            LoopView loopViewForSex = new LoopView(this);
            ArrayList<String> listForSex = new ArrayList<>();
            listForSex.add("男");
            //设置是否循环播放
            //loopView.setNotLoop();
            //滚动监听
            loopViewForSex.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    Log.d("debug", "Item " + index);
                }
            });
            //设置原始数据
            loopViewForSex.setItems(listForSex);
            //设置初始位置
            loopViewForSex.setInitPosition(5);
            //设置字体大小
            loopViewForSex.setTextSize(30);


            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

            rootviewForSex.addView(loopViewForSex, layoutParams);

        }
    }


    private void setUserName() {
        View view = findViewById(R.id.account_info_nick_name_setting);
        ((TextView) view.findViewById(R.id.item_info_setting_name)).setText("昵称");
        ((TextView) view.findViewById(R.id.item_info_setting_value)).setText(""+LoginDataUtil.getNickName(this));
    }

    private void setUserSex() {
        View view = findViewById(R.id.account_info_sex_setting);
        ((TextView) view.findViewById(R.id.item_info_setting_name)).setText("性别");
        ((TextView) view.findViewById(R.id.item_info_setting_value)).setText("" + LoginDataUtil.getSex(this));
    }

    private void setUserAge() {
        View view = findViewById(R.id.account_info_age_setting);
        ((TextView) view.findViewById(R.id.item_info_setting_name)).setText("年龄");
        if (LoginDataUtil.getAge(this) != 0) {
            ((TextView) view.findViewById(R.id.item_info_setting_value)).setText("" + LoginDataUtil.getAge(this));
        }
        ((TextView) view.findViewById(R.id.item_info_setting_value)).setText("");
    }

    private void setUserPhone() {
        View view = findViewById(R.id.account_info_phone_setting);
        ((TextView) view.findViewById(R.id.item_info_setting_name)).setText("手机号");
        ((TextView) view.findViewById(R.id.item_info_setting_value)).setText(""+LoginDataUtil.getPhone(this));
    }


    @Override
    public void initListener() {

        final Intent intent = new Intent();
        intent.setClass(AccountSettingActivity.this, PersonalInfoActivity.class);
        accountHeadRy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent, 2001);
            }
        });

        accountAgeRy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent, 2001);
            }
        });


        accountNameRy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent, 2001);
            }
        });

        accountPhoneRy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent, 2001);
            }
        });

        accountSexRy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent, 2001);
            }
        });

        ExitLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut(AccountSettingActivity.this);   //清除缓存用户对象
                Intent intent = new Intent();
                intent.setClass(AccountSettingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void showLoading(boolean shouldShow) {

    }

    @Override
    public void isNetConnected(boolean connected) {
        if(!connected){
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this).setMessage(R.string.netSetting)
                    .setNegativeButton(R.string.settingAccess, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NetUtils.openSetting(AccountSettingActivity.this);
                        }
                    });
            builder.create().show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            initView(null);
        }
    }
}
