package com.graduate.lsj.lbschartforgraduate.ui.modules.presenter;

import android.app.Activity;

import com.graduate.lsj.lbschartforgraduate.framework.base.BasePresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.PersonalInfoViewImpl;
import com.graduate.lsj.lbschartforgraduate.utils.LoginDataUtil;

/**
 * Created by Linshijun on 2016/5/26.
 */
public class PersonalInfoPresenter extends BasePresenter<PersonalInfoViewImpl> {
    public PersonalInfoPresenter(Activity activity, PersonalInfoViewImpl viewImpl) {
        super(activity, viewImpl);
    }

    public void updateUserProfile() {
        long accID = LoginDataUtil.getAccountId(activity);
        String name = viewImpl.getName();
        String nickName = viewImpl.getNickName();
        String sex = viewImpl.getSex();
        int age = viewImpl.getAge();
        String phone = viewImpl.getPhone();

        LoginDataUtil.setPhone(activity, phone);
        LoginDataUtil.setAge(activity, age);
        LoginDataUtil.setName(activity, name);
        LoginDataUtil.setSex(activity, sex);
        viewImpl.updateSuccess();
//        final UpdateUserProfileRequest request = new UpdateUserProfileRequest(accID,name,nickName,age,sex,phone);
//        action.updateUserProfile(request, new CallbackListener<UpdateUserProfileResponse>() {
//            @Override
//            public void onSuccess(UpdateUserProfileResponse result) {
//                UserInfo userInfo=result.getUserInfo();
//                LoginDataUtils.setPhone(activity,userInfo.getPhone());
//                LoginDataUtils.setNickName(activity,userInfo.getNickname());
//                LoginDataUtils.setName(activity,userInfo.getName());
//                LoginDataUtils.setAge(activity,userInfo.getAge());
//                LoginDataUtils.setSex(activity,userInfo.getSex());
//                viewImpl.updateSuccess();
//
//            }
//
//            @Override
//            public void onFailed(int errorCode, String errorMsg) {
//
//            }
//        });

    }

}
