package com.graduate.lsj.lbschartforgraduate.framework.base;

import android.app.Activity;
import android.view.View;

import com.graduate.lsj.lbschartforgraduate.utils.NetUtils;
import com.graduate.lsj.lbschartforgraduate.utils.ToastUtil;

/**
 * Created by lsj on 2016/3/10.
 */
public class BasePresenter<T extends BaseViewImpl> {
    /**
     * 上下文相关
     */
    public Activity activity;
    /**
     * 对应的View Interface
     */
    public T viewImpl;

    /**
     * Constructor
     *
     * @param activity context
     * @param viewImpl view interface
     */
    public BasePresenter(Activity activity, T viewImpl) {
        this.activity = activity;
        this.viewImpl = viewImpl;
    }

    /**
     * init with no params,use for {@link Activity} generally
     */
    public void init() {
        this.init(null);
    }

    /**
     * init with {@link View},use for {@link android.app.Fragment} generally
     *
     * @param view
     */
    public void init(View view) {
        viewImpl.initView(view);
        viewImpl.initListener();
        if(!NetUtils.isConnected(activity)){
            ToastUtil.get().showShortToast(activity,"请检查您的网络连接");
            viewImpl.isNetConnected(false);
        }
    }
}