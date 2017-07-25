package com.bayue.ciic.utils;



import android.util.Log;

import java.io.File;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BaYue on 2017/6/8.
 * email : 2651742485@qq.com
 */

public class HTTPUtils {
    public static void getNetDATA(String url, Map<String, Object> map, Callback callback){
        Tracer.e("AddAddressActivity", url);
        map.put("safecode", "BaYue.ZhongZhi");
        map.put("apiversion", "v.1.0");
        RequestBody body ;
        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : map.entrySet()){
            formBody.add(entry.getKey(), String.valueOf(entry.getValue()));
//            Tracer.e("AddAddressActivity", entry.getKey() +":"+String.valueOf(entry.getValue()));
        }
        body = formBody.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        OKHttpUtils.enqueue(request,callback);
    }
    public static void getFileDATA(String url, Map<String, Object> map, Map<String,File> fileMap,Callback callback){
        Tracer.e("AddAddressActivity", url);
        map.put("safecode", "BaYue.ZhongZhi");
        map.put("apiversion", "v.1.0");
        RequestBody body  ;
       /* FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : map.entrySet()){
            formBody.add(entry.getKey(), String.valueOf(entry.getValue()));
//            Tracer.e("AddAddressActivity", entry.getKey() +":"+String.valueOf(entry.getValue()));
        }
        body = formBody.build();*/

        MultipartBody.Builder part=new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Map.Entry<String, Object> entry : map.entrySet()){
            part.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
//            Tracer.e("AddAddressActivity", entry.getKey() +":"+String.valueOf(entry.getValue()));
        }

        int i=0;
        for (Map.Entry<String, File> entry : fileMap.entrySet()){
            Log.e("图片file=2222222===",entry.getValue()+"");
            Log.e("图片大小=====",entry.getValue().length()+"");
            Log.e(entry.getKey()+"图片名字=====",entry.getValue().getName());
            part.addFormDataPart(entry.getKey(),entry.getValue().getName(), RequestBody.create( MediaType.parse("application/octet-stream"), entry.getValue()));

//            Tracer.e("AddAddressActivity", entry.getKey() +":"+String.valueOf(entry.getValue()));
        }

        body=part.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        OKHttpUtils.enqueue(request,callback);
    }
}
