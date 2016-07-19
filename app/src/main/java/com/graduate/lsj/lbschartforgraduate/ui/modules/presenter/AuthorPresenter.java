package com.graduate.lsj.lbschartforgraduate.ui.modules.presenter;

import android.app.Activity;

import com.graduate.lsj.lbschartforgraduate.framework.base.BasePresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.AuthorImpl;

/**
 * Created by lsj on 2016/4/11.
 */
public class AuthorPresenter extends BasePresenter<AuthorImpl>{
    /**
     * Constructor
     *
     * @param activity context
     * @param viewImpl view interface
     */
    public AuthorPresenter(Activity activity, AuthorImpl viewImpl) {
        super(activity, viewImpl);
    }
}
