package com.app.frame.webview;

import org.json.JSONObject;

/**
 * Created by kanxue on 2016/12/8.
 */
public class WebviewModel {

    public static String getDataFromJson(String result) {
        try {
            JSONObject jsonResult = new JSONObject(result);
            String jsonData = jsonResult.getString("value");
            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
