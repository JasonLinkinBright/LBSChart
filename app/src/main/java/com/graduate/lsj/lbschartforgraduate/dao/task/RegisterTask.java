package com.graduate.lsj.lbschartforgraduate.dao.task;

import android.content.Context;

import com.graduate.lsj.lbschartforgraduate.dao.pojo.MyUser;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by lsj on 2016/3/22.
 */
public class RegisterTask extends BaseTask {

    private String mUserName;
    private String mPassWord;
    private Double mLongitude;
    private Double mlatitude;
    private Context mContext;
    private TaskListener<String> mListener;

    public RegisterTask(Context context, String userName, String passWord, Double longitude, Double latitude, TaskListener<String> listener) {
        this.mContext = context;
        this.mUserName = userName;
        this.mPassWord = passWord;
        this.mLongitude = longitude;
        this.mlatitude = latitude;
        this.mListener = listener;
    }

    @Override
    public void run() {
        MyUser bu = new MyUser();
        bu.setUsername(mUserName);
        bu.setPassword(mPassWord);
        bu.setLongitude(mLongitude);
        bu.setLatitude(mlatitude);
        bu.signUp(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                mListener.onSuccess(null);
            }

            @Override
            public void onFailure(int i, String s) {
                mListener.onFail(i, s);
            }
        });
    }
}
