package com.graduate.lsj.lbschartforgraduate.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lsj on 2016/3/25.
 */
public class EditTextVerifyUtils {
    /**
     * 验证输入框的输入情况，提示“‘hint’不能为空”。
     *
     * @param context   {@link Context}
     * @param editTexts {@link EditText}
     * @return 全部输入则返回true，否则返回false
     */
    public static boolean verifyEditTexts(Context context, EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (TextUtils.isEmpty(editText.getText().toString())) {
                ToastUtil.get().showShortToast(context, String.format("%s不能为空", editText.getHint()));
                return false;
            }
        }
        return true;
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
//        p = Pattern.compile("^[1]((3[0-9])|(5[^4,\\D])|(7[0,6-8])|(8[0,0-9]))\\d{8}$"); // 验证手机号
        Pattern p = Pattern.compile("^1\\d{10}");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 邮箱验证
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
