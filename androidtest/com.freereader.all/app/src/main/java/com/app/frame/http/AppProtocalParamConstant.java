package com.app.frame.http;

import com.app.kernel.AppEpplication.AppApplication;
import com.app.libs.SPUtils;

/**
 * Created by kanxue on 2016/12/2.
 */
public class AppProtocalParamConstant {

    protected static final String HTTPS_PROTOCAL_HEADER="https://";
    protected static final String HTTP_PROTOCAL_HEADER="http://";
    protected static final String TEST_SERVER_API="HelloFreeReader.reader.com:8082";
    protected static final String API_SERVER_API="api.reader.com:8082";
    public static String BASE_SERVER_API=HTTPS_PROTOCAL_HEADER+TEST_SERVER_API;

    //change base server api
    public static void changeBaseServerAPI(String currentServerApi){
        BASE_SERVER_API=HTTPS_PROTOCAL_HEADER+currentServerApi;
    }

    // change state between HelloFreeReader and normal server,true is api server false is HelloFreeReader server
    public static void changeBaseServerAPI(boolean isNormal){
        if(isNormal){
            changeBaseServerAPI(API_SERVER_API);
            return;
        }
        changeBaseServerAPI(TEST_SERVER_API);
    }

    //balance url
    public static void setBalanceUrlToLocal(String balanceUrlToLocal){
        SPUtils.put(AppApplication.getInstance().getApplicationContext(),"AppbalanceURL",balanceUrlToLocal);
    }

    public static void getBalanceUrl(){
        SPUtils.get(AppApplication.getInstance().getApplicationContext(),"AppbalanceURL","");
    }

    //获取基类地址，这个是因为由于软件可能会请求不同服务器获取数据。
    public static String getBaseUrl(int type){
        return "";
    }

}
