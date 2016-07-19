package com.graduate.lsj.lbschartforgraduate.dao.http;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/03/14.
 */
@HttpRequest(
        host = JsonDemoParamsBuilder.SEEVER_A,
        path = "query/test",
        builder = JsonDemoParamsBuilder.class
)
public class JsonDemoParams extends RequestParams {

    public String paramStr;

    public int paramInt;

    public List<String> paramList;



    public static Callback.Cancelable send(Callback.CommonCallback<BaiduResponse> callback) {
        JsonDemoParams params = new JsonDemoParams();
        params.paramStr = "test";
        params.paramInt = 10;
        params.paramList = new ArrayList<String>();
        params.paramList.add("test");
        return x.http().post(params, callback);
    }
}