package com.graduate.lsj.lbschartforgraduate.framework.base;

import android.view.View;

/**
 * Created by lsj on 2016/3/10.
 */
public interface BaseViewImpl {
    /**
     * init {@link View} widget from {@link android.app.Activity} or {@link android.app.Fragment},maybe you should pass param {@link View} to {@link BasePresenter} from {@link android.app.Fragment}.
     *
     * @param view view from inflate
     */
    void initView(View view);

    /**
     * init listener
     */
    void initListener();

    /**
     * control progress widget's visibility
     *
     * @param shouldShow
     */
    void showLoading(boolean shouldShow);

    /**
     * 检查网络连接
     */
    void isNetConnected(boolean connected);
}
