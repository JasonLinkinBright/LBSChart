package com.graduate.lsj.lbschartforgraduate.dao.task;

/**
 * Created by lsj on 2016/3/22.
 */
public abstract class TaskListener<T> {

    public abstract void onSuccess(T result);

    public abstract void onFail(int errorCode, String error);

}
