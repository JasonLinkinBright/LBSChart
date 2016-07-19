package com.graduate.lsj.lbschartforgraduate.ui.modules.presenter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import com.graduate.lsj.lbschartforgraduate.dao.pojo.MyUser;
import com.graduate.lsj.lbschartforgraduate.dao.pojo.NearInstitution;
import com.graduate.lsj.lbschartforgraduate.framework.base.BasePresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.LoginImpl;
import com.graduate.lsj.lbschartforgraduate.utils.LogTool;
import com.graduate.lsj.lbschartforgraduate.utils.LoginDataUtil;
import com.graduate.lsj.lbschartforgraduate.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by lsj on 2016/3/25.
 */
public class LoginPresenter extends BasePresenter<LoginImpl> {
//    private Context context;

    /**
     * Constructor
     *
     * @param activity context
     * @param viewImpl view interface
     */
    public LoginPresenter(Activity activity, LoginImpl viewImpl) {
        super(activity, viewImpl);
//        this.context=activity;
    }

    public void login() {
        final MyUser user = new MyUser();
        user.setUsername(viewImpl.getAccount());
        user.setPassword(viewImpl.getPassWord());
        user.login(activity, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.e("asdfkj", "登录成功:");
                LogTool.e("登录", "成功");
                viewImpl.loginSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("登录失败", s);
                LogTool.e("登录失败", s);
                viewImpl.loginFailed();
            }
        });
    }

    public void creatData() {

        BmobQuery<NearInstitution> query = new BmobQuery<>();
        query.addQueryKeys("typeID,institutionType");
        query.findObjects(activity, new FindListener<NearInstitution>() {
            @Override
            public void onSuccess(List<NearInstitution> list) {

                Map<String, Integer> tempMap = new HashMap<String, Integer>();
                List<String> tempList = new ArrayList<String>();
                for (NearInstitution listItem : list) {
                    String StrignKeyType = listItem.getInstitutionType();
                    if (tempMap.containsKey(StrignKeyType)) {
                        int count = tempMap.get(StrignKeyType);
                        tempMap.put(StrignKeyType, count + 1);
                    } else {
                        tempMap.put(StrignKeyType, 1);
                        tempList.add(StrignKeyType);
                    }

                }
                ToastUtil.get().showShortToast(activity, tempMap.get("餐饮") + "：" + tempMap.get("娱乐"));

            }

            @Override
            public void onError(int i, String s) {
                ToastUtil.get().showShortToast(activity, "查询失败" + i + s);
            }
        });
    }
}
