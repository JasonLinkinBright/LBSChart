package com.graduate.lsj.lbschartforgraduate.ui.modules.presenter;

import android.app.Activity;

import com.graduate.lsj.lbschartforgraduate.dao.pojo.NearInstitution;
import com.graduate.lsj.lbschartforgraduate.framework.base.BasePresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.RankImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindStatisticsListener;

/**
 * Created by lsj on 2016/4/8.
 */
public class RankPresenter extends BasePresenter<RankImpl> {
    /**
     * Constructor
     *
     * @param activity context
     * @param viewImpl view interface
     */
    public RankPresenter(Activity activity, RankImpl viewImpl) {
        super(activity, viewImpl);
    }

    public void getRank() {
        BmobQuery<NearInstitution> query = new BmobQuery<>();
        query.addQueryKeys("institutionType,numberStar,institutionName");
        query.order("-numberStar");
        query.findStatistics(activity, NearInstitution.class, new FindStatisticsListener() {
            @Override
            public void onSuccess(Object object) {
                // TODO Auto-generated method stub
                JSONArray ary = (JSONArray) object;
                Map<String, Integer> tempMap = new HashMap<String, Integer>();
                List<String> tempList = new ArrayList<String>();
                List<NearInstitution> institutionList = new ArrayList<>();
                if (ary != null) {
                    int length = ary.length();
                    try {
                        for (int i = 0; i < length; i++) {
                            NearInstitution ni = new NearInstitution();
                            JSONObject obj = ary.getJSONObject(i);
                            String stringKeyType = obj.getString("institutionType");
                            ni.setInstitutionName(obj.getString("institutionName"));
                            ni.setInstitutionType(stringKeyType);
                            ni.setNumberStar(obj.getDouble("numberStar"));
                            institutionList.add(ni);
                            if (tempMap.containsKey(stringKeyType)) {
                                int count = tempMap.get(stringKeyType);
                                tempMap.put(stringKeyType, count + 1);
                            } else {
                                tempMap.put(stringKeyType, 1);
                                tempList.add(stringKeyType);
                            }
                        }
                        viewImpl.deploySuccess(tempList,institutionList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//                    showToast("查询成功，无数据");
                    viewImpl.deployFailed();
                }
            }

            @Override
            public void onFailure(int i, String s) {
                viewImpl.deployFailed();
            }
        });
    }
}
