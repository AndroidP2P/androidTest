package com.app.libs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kanxue on 2016/3/31.
 */
public class MessageDigestUtils
{

    public static String getMd5(String content)
    {
        MessageDigest messageDigest=getMessageDigest("MD5");
        messageDigest.update(content.getBytes());
        return bytes2Hex(messageDigest.digest());
    }



    public static String getSHA1(String content)
    {
        MessageDigest messageDigest=getMessageDigest("SHA-1");
        messageDigest.update(content.getBytes());
        return bytes2Hex(messageDigest.digest());

    }

    public static String bytes2Hex(byte[] src)
    {
        char[] res = new char[src.length*2];
        final char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        for(int i=0,j=0; i<src.length; i++){
            res[j++] = hexDigits[src[i] >>>4 & 0x0f];
            res[j++] = hexDigits[src[i] & 0x0f];
        }

        return new String(res).toLowerCase();
    }



    public static MessageDigest getMessageDigest(String alg)
    {
        MessageDigest messageDigest=null;
         if(alg.equals("MD5"))
         {
             try {
                 messageDigest=MessageDigest.getInstance("MD5");
                 return messageDigest;
             } catch (NoSuchAlgorithmException e) {
                 e.printStackTrace();
             }
             return messageDigest;
         }else if(alg.equals("SHA-1"))
         {
             try {
                 messageDigest=MessageDigest.getInstance("SHA-1");
                 return messageDigest;
             } catch (NoSuchAlgorithmException e) {
                 e.printStackTrace();
             }
             return messageDigest;
         }
        return null;
    }

    public static String textToMD5L32(String plainText)
    {
        String result = null;
        //首先判断是否为空
        if(plainText.equals(""))
        {
            return null;
        }
        try{
            //首先进行实例化和初始化
            MessageDigest md = MessageDigest.getInstance("MD5");
            //得到一个操作系统默认的字节编码格式的字节数组
            byte[] btInput = plainText.getBytes();
            //对得到的字节数组进行处理
            md.update(btInput);
            //进行哈希计算并返回结果
            byte[] btResult = md.digest();
            //进行哈希计算后得到的数据的长度
            StringBuffer sb = new StringBuffer();
            for(byte b : btResult)
            {
                int bt = b&0xff;
                if(bt<16){
                    sb.append(0);
                }
                sb.append(Integer.toHexString(bt));
            }
            result = sb.toString();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 2.对文本进行32位MD5大写加密
     * @param plainText 要进行加密的文本
     * @return 加密后的内容
     */
    public static String textToMD5U32(String plainText){
        if(plainText.equals("")){
            return null;
        }
        String result = textToMD5L32(plainText);
        return result.toUpperCase();
    }


}
