package com.graduate.lsj.lbschartforgraduate.ui.modules.presenter;

import android.app.Activity;

import com.graduate.lsj.lbschartforgraduate.dao.pojo.NearInstitution;
import com.graduate.lsj.lbschartforgraduate.framework.base.BasePresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.MarketShareImpl;
import com.graduate.lsj.lbschartforgraduate.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by lsj on 2016/4/7.
 */
public class MarketSharePresenter extends BasePresenter<MarketShareImpl> {
    /**
     * Constructor
     *
     * @param activity context
     * @param viewImpl view interface
     */
    public MarketSharePresenter(Activity activity, MarketShareImpl viewImpl) {
        super(activity, viewImpl);
    }

    public void loadData() {
        BmobQuery<NearInstitution> query = new BmobQuery<>();
        query.addQueryKeys("typeID,institutionType");
        query.findObjects(activity, new FindListener<NearInstitution>() {
            @Override
            public void onSuccess(List<NearInstitution> list) {

                Map<String, Integer> tempMap = new HashMap<String, Integer>();
                List<String> tempList = new ArrayList<String>();
                for (NearInstitution listItem : list) {
                    String stringKeyType = listItem.getInstitutionType();
                    if (tempMap.containsKey(stringKeyType)) {
                        int count = tempMap.get(stringKeyType);
                        tempMap.put(stringKeyType, count + 1);
                    } else {
                        tempMap.put(stringKeyType, 1);
                        tempList.add(stringKeyType);
                    }

                }
                ToastUtil.get().showShortToast(activity, tempMap.get("餐饮") + "：" + tempMap.get("娱乐"));
                viewImpl.deployData(tempList, tempMap);

            }

            @Override
            public void onError(int i, String s) {
                viewImpl.loadingFailed();
                ToastUtil.get().showShortToast(activity, "查询失败" + i + s);
            }
        });
    }
}
