package com.graduate.lsj.lbschartforgraduate.ui.modules.presenter;

import android.app.Activity;

import com.graduate.lsj.lbschartforgraduate.framework.base.BasePresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.AboutImpl;

/**
 * Created by Administrator on 2016/4/8.
 */
public class AboutPresenter extends BasePresenter<AboutImpl> {
    /**
     * Constructor
     *
     * @param activity context
     * @param viewImpl view interface
     */
    public AboutPresenter(Activity activity, AboutImpl viewImpl) {
        super(activity, viewImpl);
    }
}
