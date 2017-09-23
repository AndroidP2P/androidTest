package com.app.libs;

import android.text.TextUtils;
import android.util.Log;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;

public class StringUtils {
    public static String getUnicodeString(String result) {
        return getUnicode(result);

    }

    public static String getYmdNormalString(String ymdStr) {
        return (String) ymdStr.subSequence(2, ymdStr.length());
    }

    public static String getYmdStyleFirst(String ymdStr) {
        try {
            String tempStr = getYmdNormalString(ymdStr.trim());
            return tempStr.replaceAll("-", "/");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    // definite whether this str is phonenumber
    public static boolean matchForUserPhoneNumber(String UserPhoneNumber) {
        if (UserPhoneNumber.matches("1[0-9]{10}")) {
            return true;
        }
        return false;
    }

    // start to convert into Unicode code
    public static String getUnicode(String str) {
        try {
            String strUTF8 = URLDecoder.decode(str, "UTF-8");
            return strUTF8;
        } catch (Exception e) {
            return "";
        }
    }

    public static String toURLEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            return "";
        }

        try
        {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        }
        catch (Exception localException)
        {
        }
        return "";
    }

    public static String toURLDecoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            return "";
        }

        try
        {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLDecoder.decode(str, "UTF-8");
            return str;
        }
        catch (Exception localException)
        {
        }

        return "";
    }


    public static String join(String join,String[] strAry){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<strAry.length;i++){
            if(i==(strAry.length-1)){
                sb.append(strAry[i]);
            }else{
                sb.append(strAry[i]).append(join);
            }
        }

        return new String(sb);
    }
    public static String join(String join,List<String> strAry){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<strAry.size();i++){
            if(i==(strAry.size()-1)){
                sb.append(strAry.get(i));
            }else{
                sb.append(strAry.get(i)).append(join);
            }
        }

        return new String(sb);
    }

    public static String getRandomString(int length) {
        String base = "abcdefghi^jkl&*!mnop+qrstu=$vwxyz012-345?|6789&%@#";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static boolean checkInputStr(String numStr){

        try {

            if(TextUtils.isEmpty(numStr)){
                return true;
            }
            char firstChar=numStr.charAt(0);
            char lastChar=numStr.charAt(numStr.length()-1);

            if(firstChar=='0'&&numStr.length()>1){
                return false;
            }else if(firstChar=='.'){
                return false;
            }else if(lastChar=='.'){
                return false;
            }
            String[] key=numStr.split("\\.");
            if(key.length>2){
                return false;
            }
        }catch (Exception e){
            Log.d("checkInputStr",e.getLocalizedMessage());
        }
        return true;
    }

}
