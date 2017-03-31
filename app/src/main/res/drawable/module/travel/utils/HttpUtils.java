package com.weiruanit.lifepro.module.travel.utils;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.Map;

/**
 * 将开源框架的网络请求再次封装，形成一个工具类，使这个框架更适合自己的项目使用
 */

public class HttpUtils {

    public static  void requestData(Context context , String url, Map<String,String> params,StringCallback StringCallback){
        OkGo.get(url)
                .tag(context)
                .headers("apikey","abcfe469f2ede2b495055162e97d8b82")
                .params(params)
                .execute(StringCallback);
    }
}
