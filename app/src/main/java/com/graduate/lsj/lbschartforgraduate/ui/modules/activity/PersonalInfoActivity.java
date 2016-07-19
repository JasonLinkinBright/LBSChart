package com.graduate.lsj.lbschartforgraduate.ui.modules.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.graduate.lsj.lbschartforgraduate.R;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseActivity;
import com.graduate.lsj.lbschartforgraduate.ui.modules.presenter.PersonalInfoPresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.PersonalInfoViewImpl;
import com.graduate.lsj.lbschartforgraduate.utils.LoginDataUtil;
import com.graduate.lsj.lbschartforgraduate.utils.NetUtils;
import com.graduate.lsj.lbschartforgraduate.utils.ToastUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Linshijun on 2016/5/26.
 */
public class PersonalInfoActivity extends BaseActivity implements PersonalInfoViewImpl {

    private static final int REQUEST_SET_AVATAR = 1002;


    @ViewInject(R.id.me_avatar)
    SimpleDraweeView mMeAvatar;
    @ViewInject(R.id.avatar_setting_layout)
    RelativeLayout mAvatarSettingLayout;
    @ViewInject(R.id.user_id)
    TextView mUserId;
    @ViewInject(R.id.user_nickname)
    EditText mUserNickname;
    @ViewInject(R.id.user_name)
    EditText mUserName;
    @ViewInject(R.id.user_sex)
    EditText mUserSex;
    @ViewInject(R.id.user_age)
    EditText mUserAge;
    @ViewInject(R.id.user_phone)
    TextView mUserPhone;
    @ViewInject(R.id.male)
    RadioButton mMale;
    @ViewInject(R.id.female)
    RadioButton mFemale;
    @ViewInject(R.id.sex_radio_group)
    RadioGroup mSexRadioGroup;

    PersonalInfoPresenter mPresenter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        x.view().inject(this);
        setTitle("我的信息");
        mPresenter = new PersonalInfoPresenter(this, this);
        mPresenter.init();

    }


    @Override
    public void initView(View view) {
        if (LoginDataUtil.getIcon(this) != null) {
            mMeAvatar.setImageURI(Uri.parse(LoginDataUtil.getIcon(this)));
        }
        mUserId.setText("123" + LoginDataUtil.getAccountId(this) + "68");
        mUserPhone.setText(LoginDataUtil.getPhone(this));
        mUserName.setText(LoginDataUtil.getName(this));
        mUserAge.setText("" + LoginDataUtil.getAge(this));
        mUserNickname.setText(LoginDataUtil.getNickName(this));

    }

    @Override
    public void initListener() {
        View.OnClickListener editListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserAge.setEnabled(true);
                mUserNickname.setEnabled(true);
                mUserNickname.setSelection(mUserNickname.getText().length());
                mUserName.setEnabled(true);
                mUserSex.setVisibility(View.GONE);
                mSexRadioGroup.setVisibility(View.VISIBLE);
                mMale.setChecked(true);
                showOneRightStrAction(getString(R.string.save), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.updateUserProfile();
                    }
                });
            }
        };

        showOneRightStrAction(getString(R.string.edit), editListener);

        mAvatarSettingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 16/4/25  avatar setting
                Intent intent = new Intent(PersonalInfoActivity.this, AvatarActivity.class);
                startActivityForResult(intent, REQUEST_SET_AVATAR);
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
                            NetUtils.openSetting(PersonalInfoActivity.this);
                        }
                    });
            builder.create().show();
        }
    }

    @Override
    public String getNickName() {
        return mUserNickname.getText().toString().trim();
    }

    @Override
    public String getName() {
        return mUserName.getText().toString().trim();

    }

    @Override
    public String getSex() {
        String sex;
        if (mMale.isChecked()) {
            sex = getString(R.string.male);
        } else {
            sex = getString(R.string.female);
        }
        return sex;

    }

    @Override
    public int getAge() {
        return Integer.parseInt(mUserAge.getText().toString().trim());

    }

    @Override
    public String getPhone() {
        return mUserPhone.getText().toString().trim();

    }

    @Override
    public void updateSuccess() {
        ToastUtil.get().showShortToast(this, "更新成功");
        setResult(RESULT_OK);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            mMeAvatar.setImageURI(Uri.parse(LoginDataUtil.getIcon(this)));
            setResult(RESULT_OK);
        }
    }

    /**
     * 头像设置
     */
//    @OnClick(R.id.avatar_setting_layout)
//    public void onClick() {
//        // TODO: 16/4/25  avatar setting
//        Intent intent = new Intent(PersonalInfoActivity.this, AvatarActivity.class);
//        startActivityForResult(intent,REQUEST_SET_AVATAR);
//    }


}
