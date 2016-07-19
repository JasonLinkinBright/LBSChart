package com.graduate.lsj.lbschartforgraduate.dao.http;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by lsj on 2016/03/14.
 */
@HttpResponse(parser = JsonResponseParser.class)
public class BaiduResponse {
    // some properties

    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return test;
    }
}
