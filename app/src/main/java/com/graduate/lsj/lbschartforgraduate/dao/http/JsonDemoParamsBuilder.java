package com.graduate.lsj.lbschartforgraduate.dao.http;

import android.text.TextUtils;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.ParamsBuilder;
import org.xutils.x;

import java.util.HashMap;

import javax.net.ssl.SSLSocketFactory;

/**
 * Created by lsj on 2016/03/14.
 */
public class JsonDemoParamsBuilder implements ParamsBuilder {

    public static final String SEEVER_A = "a";
    public static final String SEEVER_B = "b";

    private static final HashMap<String, String> SERVER_MAP = new HashMap<String, String>();

    private static final HashMap<String, String> DEBUG_SERVER_MAP = new HashMap<String, String>();

    static {
        SERVER_MAP.put(SEEVER_A, "http://a.xxx.xxx");
        SERVER_MAP.put(SEEVER_B, "http://b.xxx.xxx");
        DEBUG_SERVER_MAP.put(SEEVER_A, "http://debug.a.xxx.xxx");
        DEBUG_SERVER_MAP.put(SEEVER_B, "http://debug.b.xxx.xxx");
    }

    @Override
    public String buildUri(RequestParams params, HttpRequest httpRequest) {
        String url = getHost(httpRequest.host());
        url += "/" + httpRequest.path();
        return url;
    }

    @Override
    public String buildCacheKey(RequestParams params, String[] cacheKeys) {
        return null;
    }

    @Override
    public SSLSocketFactory getSSLSocketFactory() {
        return null;
    }

    @Override
    public void buildParams(RequestParams params) {
        //
        params.addParameter("common_a", "xxxx");
        params.addParameter("common_b", "xxxx");



        params.setAsJsonContent(true);

        /*String json = params.toJSONString();
        params.clearParams();
        if (params.getMethod() == HttpMethod.GET) {
            params.addQueryStringParameter("xxx", json);
        } else {
            params.setBodyContent(json);
        }*/
    }

    @Override
    public void buildSign(RequestParams params, String[] signs) {
        params.addParameter("sign", "xxxx");
    }


    private String getHost(String host) {
        String result = null;
        if (x.isDebug()) {
            result = DEBUG_SERVER_MAP.get(host);
        } else {
            result = SERVER_MAP.get(host);
        }
        return TextUtils.isEmpty(result) ? host : result;
    }
}
