package com.app.frame.http.NormalRequest;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.app.libs.StringUtils;


import java.io.File;
import java.util.Iterator;
import java.util.Set;

public class AppNetRequestFactory {

    public static void getDataNormalByMethod(String UrlStr, final Handler handler, final int sendType) {
        HttpUtils.doGetAsyn(UrlStr, new HttpUtils.doPostCallBack() {
            @Override
            public void onRequestComplete(String result) {
                // TODO Auto-generated method stub
                if (handler == null) {
                    System.out.println(result);
                    return;
                }
                Message msg = handler.obtainMessage();
                msg.what = sendType;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        });
    }

    public static void getDataNormalByMethod(String UrlStr, final HttpUtils.doPostCallBack callBack) {
        HttpUtils.doGetAsyn(UrlStr, new HttpUtils.doPostCallBack() {
            @Override
            public void onRequestComplete(String result) {
                // TODO Auto-generated method stub
                if(callBack==null){
                    return;
                }
                callBack.onRequestComplete(result);
            }
        });
    }

    // if param is null ,you can use dealWithRequestMethod("",url);
    public static String dealWithRequestMethod(String Params, String Url) {
       return null;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void doPostFileMethod(File filePhoto, String HttpUrl, ContentValues contentValues,
                                        final HttpUtils.doPostCallBack callBack) throws Exception {

    }

    public static void postDataNomalMethod(String UrlStr, String params, final Handler handler, final int sendType) {

        UrlStr = dealWithRequestMethod(null, UrlStr);
        try {
            HttpUtils.doPostAsyn(UrlStr, params, new HttpUtils.doPostCallBack() {
                @Override
                public void onRequestComplete(String result) {
                    // TODO Auto-generated method stub
                    Message msg = handler.obtainMessage();
                    msg.what = sendType;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void getManbuDataFromCallBack(String UrlStr, final HttpUtils.doPostCallBack callBack) {
        UrlStr += dealWithRequestMethod("", UrlStr);
        try {
            HttpUtils.doGetAsyn(UrlStr, new HttpUtils.doPostCallBack() {
                @Override
                public void onRequestComplete(String result) {
                    // TODO Auto-generated method stub
                    if (callBack == null) {
                        return;
                    }
                    callBack.onRequestComplete(result);
                }
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void postDataFromCallBack(String UrlStr, String params, final HttpUtils.doPostCallBack callBack) {

        UrlStr = dealWithRequestMethod(null, UrlStr);
        try {
            HttpUtils.doPostAsyn(UrlStr, params, new HttpUtils.doPostCallBack() {
                @Override
                public void onRequestComplete(String result) {
                    // TODO Auto-generated method stub
                    if (callBack != null)
                        callBack.onRequestComplete(result);
                }
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void postDataNomalMethod(String UrlStr, ContentValues params, final HttpUtils.doPostCallBack callBack) {

        String paramValue=paramsCombind(params);
        try {
            HttpUtils.doPostAsyn(UrlStr, paramValue, new HttpUtils.doPostCallBack() {
                @Override
                public void onRequestComplete(String result) {
                    // TODO Auto-generated method stub
                    callBack.onRequestComplete(result);
                }
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void postDataMethodByContentValue(String UrlStr, ContentValues contentValues, final HttpUtils.doPostCallBack callBack) {
        //数组参数值如下
        String contentParams = "";
        Set<String> keySets = contentValues.keySet();
        Iterator<String> iterator = keySets.iterator();
        while (iterator.hasNext()) {
            String tempKey = iterator.next();
            contentParams += tempKey + "=" + StringUtils.getUnicode(contentValues.getAsString(tempKey)) + "&";
        }
        contentParams = contentParams.substring(0, contentParams.length() - 1);
        //add one colume
        UrlStr = dealWithRequestMethod(null, UrlStr);
        try {
            HttpUtils.doPostAsyn(UrlStr, contentParams, new HttpUtils.doPostCallBack() {
                @Override
                public void onRequestComplete(String result) {
                    // TODO Auto-generated method stub
                    if (callBack != null)
                        callBack.onRequestComplete(result);
                }
            });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String paramsCombind(ContentValues mContentValue){
        if(mContentValue==null){
            return null;
        }
        String contentParams = "";
        Set<String> keySets = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            keySets = mContentValue.keySet();
        }
        Iterator<String> iterator = keySets.iterator();
        while (iterator.hasNext()) {
            String tempKey = iterator.next();
            contentParams += tempKey + "=" + StringUtils.getUnicode(mContentValue.getAsString(tempKey)) + "&";
        }
        contentParams = contentParams.substring(0, contentParams.length() - 1);
        return contentParams;
    }


}