package com.graduate.lsj.lbschartforgraduate.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lsj on 2016/3/22.
 */
public class ToastUtil {

    private static ToastUtil INSTANCE = new ToastUtil();

    public static ToastUtil get() {
        return INSTANCE;
    }

    private Toast toast;

    /**
     * Show {@link Toast #LENGTH_LONG} duration toast
     *
     * @param c   {@link Context}
     * @param msg {@link String} msg
     */
    public void showLongToast(Context c, String msg) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(c, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Show {@link Toast #LENGTH_SHORT} duration toast
     *
     * @param c   {@link Context}
     * @param msg {@link String} msg
     */
    public void showShortToast(Context c, String msg) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(c, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Show self-define duration toast
     *
     * @param c        {@link Context}
     * @param msg      {@link String} msg
     * @param duration {@link Integer} duration
     */

    public void showDefineToast(Context c, String msg, int duration) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(c, msg, duration);
        toast.show();
    }
}

