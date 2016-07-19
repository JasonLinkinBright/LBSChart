package com.graduate.lsj.lbschartforgraduate.dao.http;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.DefaultParamsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/03/14.
 */
@HttpRequest(
        host = "https://www.baidu.com",
        path = "s",
        builder = DefaultParamsBuilder.class)
public class BaiduParams extends RequestParams {
    public String wd;


    public int[] aa = new int[]{1, 2, 4};
    public List<String> bb = new ArrayList<String>();

    public BaiduParams() {
        bb.add("a");
        bb.add("c");
        // this.setMultipart(true);
        // this.setAsJsonContent(true);
    }

    //public long timestamp = System.currentTimeMillis();
    //public File uploadFile;
    //public List<File> files;
}
