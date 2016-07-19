package com.graduate.lsj.lbschartforgraduate.dao.http;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/03/14.
 */
public class JsonResponseParser implements ResponseParser {// InputStreamResponseParser,

    @Override
    public void checkResponse(UriRequest request) throws Throwable {
        // custom check ?
        // get headers ?
    }


    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        // TODO: json to java bean
        if (resultClass == List.class) {
            //
            List<BaiduResponse> list = new ArrayList<BaiduResponse>();
            BaiduResponse baiduResponse = new BaiduResponse();
            baiduResponse.setTest(result);
            list.add(baiduResponse);
            return list;
            // fastJson
            // return JSON.parseArray(result, (Class<?>) ParameterizedTypeUtil.getParameterizedType(resultType, List.class, 0));
        } else {
            BaiduResponse baiduResponse = new BaiduResponse();
            baiduResponse.setTest(result);
            return baiduResponse;
            // fastjson
            // return JSON.parseObject(result, resultClass);
        }

    }
}

