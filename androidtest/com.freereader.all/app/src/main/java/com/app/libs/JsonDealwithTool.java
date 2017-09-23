package com.app.libs;

import android.os.Bundle;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonDealwithTool {
    /**
     * 从json HASH表达式中获取一个map，改map支持嵌套功能
     *
     * @param jsonString
     * @return
     */
    public static Map getMap4Json(String jsonString) {
        Map valueMap = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Iterator keyIter = jsonObject.keys();
            String key;
            Object value;
            valueMap = new HashMap();

            while (keyIter.hasNext()) {
                key = (String) keyIter.next();
                value = jsonObject.get(key).toString();
                valueMap.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return valueMap;
    }


    public static String getJsonDataValueByKey(String datakey, String jsonData) {

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.has(datakey)) {
                return jsonObject.getString(datakey);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static JSONArray getJsonArrayByKey(String datakey, String jsonData) {

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject.has(datakey)) {
                return jsonObject.getJSONArray(datakey);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static JSONObject getJsonObjectValueByKey(String jsonKey, JSONObject jsonData) {

        try {

            if (jsonData == null)
                return null;
            if (jsonData.has(jsonKey)) {
                return jsonData.getJSONObject(jsonKey);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getJsonDataValueByKey(String datakey, JSONObject jsonData) {

        try {
            if (jsonData == null)
                return null;
            JSONObject jsonObject = jsonData;
            if (jsonObject.has(datakey)) {
                return jsonObject.getString(datakey);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static List<String> getJsonList(String key, JSONArray jsonArray, String defaultValue) {
        List<String> list = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String result = getJsonDataValueByKey(key, jsonObject, defaultValue);
                list.add(result);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJsonDataValueByKey(String datakey, String jsonData, String defaultValue) {
        try {
            return getJsonDataValueByKey(datakey, new JSONObject(jsonData), defaultValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }


    public static String getJsonDataValueByKey(String datakey, JSONObject jsonData, String defaultValue) {

        try {
            JSONObject jsonObject = jsonData;
            if (jsonObject.has(datakey)) {
                Object valueObj = jsonObject.get(datakey);
                if (valueObj instanceof String) {
                    return jsonObject.optString(datakey, defaultValue);
                } else if (valueObj instanceof Integer) {
                    return jsonObject.optInt(datakey, -1) + "";
                } else if (valueObj instanceof Double) {
                    return jsonObject.optDouble(datakey, -1) + "";
                } else if (valueObj instanceof Float) {
                    return jsonObject.optDouble(datakey, -1) + "";
                } else if (valueObj instanceof Boolean) {
                    return jsonObject.optBoolean(datakey, false) + "";
                } else
                    return defaultValue;
            }
            return defaultValue;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defaultValue;

    }


    public static String[] getJsonArrayFromJsonObject(String[] keyArray, String jsonData) {

        String jsonArray[] = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            jsonArray = new String[keyArray.length];
            for (int i = 0; i < keyArray.length; i++) {
                String key = keyArray[i];
                if (jsonObject.has(key)) {
                    String value = jsonObject.getString(key);
                    jsonArray[i] = value;
                } else
                    jsonArray[i] = null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return jsonArray;
    }

    public static int getJsonDealWithResult(String result) {
        if (result == null) {
            return -1;
        } else if (result.equals("false")) {
            return -1;
        }
        return 1;
    }

    public static Bundle getJsonBundleByJsonObject(String jsonData) {
        Bundle jsonResultBundle = null;
        try {
            jsonResultBundle = new Bundle();
            JSONObject jsonObject = new JSONObject(jsonData);
            Iterator<String> keyIterator = jsonObject.keys();
            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                if (jsonObject.has(key))
                    jsonResultBundle.putString(key, jsonObject.getString(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return jsonResultBundle;
    }
}
