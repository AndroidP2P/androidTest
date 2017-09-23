package com.app.frame.http.NormalRequest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.app.kernel.AppEpplication.AppApplication;
import com.app.kernel.Exception.Log.AppExceptionLogUtils;
import com.yuyh.library.imgsel.ImageLoader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

//Http请求的工具类
public class HttpUtils {
    public static final int TIMEOUT_IN_MILLIONS = 8000;
    public static ImageLoader loader = null;
    private static Context con;

    public HttpUtils(Context context) {
        con = context;
    }

    /**
     * 异步的Get请求
     *
     * @param urlStr
     * @param callBack
     */
    public static void doGetAsyn(final String urlStr, final doPostCallBack callBack) {
        new Thread()
        {
            public void run()
            {
                try {
                    String result = doGet(urlStr);
                    AppExceptionLogUtils.LOG_FOR_STL(result);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            };
        }.start();
    }

    /**
     * 异步的Post请求
     *
     * @param urlStr
     * @param params
     * @param callBack
     * @throws Exception
     */
    public static void doPostAsyn(final String urlStr, final String params, final doPostCallBack callBack) throws Exception {
        AppExceptionLogUtils.LOG_FOR_STL("urlStr="+urlStr+",params="+params);
        new Thread()
        {
            public void run() {
                try {

                    String result = doPost(urlStr, params);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ;
        }.start();

    }

    public static void doGetHttpImageAsyn(final String urlStr, final doGetByteArray callBack) {
        new Thread() {
            public void run() {
                try {
                    byte[] result = doGetImage(urlStr);
                    if (callBack != null) {
                        callBack.loadImageData(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            ;
        }.start();
    }

    //获取网络图片资源
    public static byte[] doGetImage(String urlStr)
    {
        URL url = null;
        HttpsURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(urlStr);
            if(null==sslContext) {
                sslContext = initSSL();
                ssf=sslContext.getSocketFactory();
            }
            conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toByteArray();
            } else {

                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }

        return null;
    }
    /**
     * Get请求，获得返回数据
     *
     * @param urlStr
     * @return
     * @throws Exception
     */

    private static SSLContext sslContext = null;
    private static SSLSocketFactory ssf=null;
    public static String doGet(String urlStr) {
        URL url = null;
        HttpsURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            //PrintTimeStamp("开始时间");
            url = new URL(urlStr);
            if(null==sslContext) {
                sslContext = initSSL();
                ssf=sslContext.getSocketFactory();
            }
            conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            //PrintTimeStamp("准备发送时间");
            if (conn.getResponseCode() == 200)
            {
                //PrintTimeStamp("收到200 OK时间");
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[2048];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else {
                return "false";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //PrintTimeStamp("结束时间");
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }
        return "false";
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception#####################
     */
    public static String doPost(String url, String param)
    {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            if(null==sslContext) {
                sslContext = initSSL();
                ssf=sslContext.getSocketFactory();
            }
            HttpsURLConnection conn = (HttpsURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setSSLSocketFactory(ssf);
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(true);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "false";
        }
        // 使用finally块来关闭输出流、输入流
        finally
        {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    /*
     * 获取网络状态
     * kanxue
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    public interface doPostCallBack {
        void onRequestComplete(String result);
    }

    public interface doGetByteArray {
        void loadImageData(byte b[]);
    }


    /**
     * HttpUrlConnection 方式，支持指定load-der.crt证书验证，此种方式Android官方建议
     *
     * @throws CertificateException
     * @throws IOException
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext initSSL() throws CertificateException, IOException, KeyStoreException,
            NoSuchAlgorithmException, KeyManagementException
    {
//        CertificateFactory cf = CertificateFactory.getInstance("X.509");
//        InputStream in = AppApplication.getInstance().getApplicationContext().getAssets().open("xxx.crt");
//        Certificate ca = cf.generateCertificate(in);
//        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
//        keystore.load(null, null);
//        keystore.setCertificateEntry("ca", ca);
//        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
//        tmf.init(keystore);
        // Create an SSLContext that uses our TrustManager
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null,null, null);
        return context;
    }

}
