package com.app.libs;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

public class FileUtils {
    // 创建 目录
    public static boolean createDirectory(String dirName) {
        File dir = new File(dirName);
        if (dir.exists()) {
            return true;
        }
        if (!dirName.endsWith(File.separator)) {
            dirName = dirName + File.separator;
        }
        if (dir.mkdirs()) {
            return true;
        }
        return false;
    }

    // 创建文件
    public static boolean createNewFile(String fileName, String dirName, boolean isReplace) {
        File tempFile = new File(dirName + fileName);
        try {
            if (createDirectory(dirName)) {
                if (tempFile.exists()) {
                    if (isReplace) {
                        deleteFile(dirName + fileName);
                        tempFile.createNewFile();
                    }
                    return false;
                }
                tempFile.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return false;
        File tempFile = new File(filePath);
        if (tempFile.exists()) {
            return true;
        }
        return false;
    }

    public static boolean deleteFile(String filePath) {
        File tempFile = new File(filePath);
        if (tempFile.exists()) {
            if (tempFile.delete()) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static String getDataFromAssets(Context context, String fileName) {
        try {
            InputStream IS = context.getAssets().open(fileName);
            ByteArrayOutputStream BIOS = new ByteArrayOutputStream();
            int a = 0;
            byte b[] = new byte[1024];
            while ((a = IS.read(b)) != -1) {
                BIOS.write(b, 0, a);
                BIOS.flush();
            }
            BIOS.close();
            IS.close();
            return BIOS.toString();
        } catch (Exception e) {
            return null;
        }
    }

    //保存文件
    public static void saveFile(String filePath, byte[] fileData) {
        FileOutputStream FOS = null;
        try {
            FOS = new FileOutputStream(new File(filePath));
            FOS.write(fileData);
            FOS.write(fileData, 0, fileData.length);
            FOS.flush();
            FOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(FOS!=null)
                FOS.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static void saveFile(String filePath, String fileData){
        if(fileData==null){
            return;
        }
        byte data[]=fileData.getBytes(Charset.defaultCharset());
        saveFile(filePath,data);
    }


    public static void saveExceptionFileToLocal(byte[] fileData) {
        FileOutputStream FOS = null;
        try {
            fileData=(new String(fileData)+"\n").getBytes();
            String filePath= Environment.getExternalStorageDirectory().getAbsolutePath() + "/Yuntongxun";
            String fileName=DateTools.getStringCurrentDate();
            FOS = new FileOutputStream(new File(filePath,fileName));
            FOS.write(fileData);
            FOS.write(fileData, 0, fileData.length);
            FOS.flush();
            FOS.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                FOS.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static String getFileDataFromInputStream(InputStream inputStream) {
        try {


            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String tempInfo = null, tempContent = null;
            while ((tempInfo = bufferedReader.readLine()) != null) {
                tempContent += tempInfo;
            }
            return tempContent.trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //读取文件
    public static byte[] readFlieToByte(String filePath, int seek, int length) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        if (length == -1) {
            length = (int) file.length();
        }

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            byte[] bs = new byte[length];
            randomAccessFile.seek(seek);
            randomAccessFile.readFully(bs);
            randomAccessFile.close();
            return bs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getReplacedStrByNewString(String strSource, String from, String to, String newString) {
        int start = strSource.indexOf(from);
        int end = strSource.indexOf(to) + to.length();
        String head = (String) strSource.subSequence(0, start);
        String back = strSource.substring(end, strSource.length());
        String newStringData = head + newString + back;
        return newStringData;
    }

    public static String readFlieToString(String filePath) {
        FileInputStream FIS = null;
        ByteArrayOutputStream BIOS = null;
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        try {
            FIS = new FileInputStream(file);
            BIOS = new ByteArrayOutputStream();
            int a = 0;
            byte[] catchData = new byte[1024 * 10];
            while ((a = FIS.read(catchData)) != -1) {
                BIOS.write(catchData, 0, a);
                BIOS.flush();
            }
            BIOS.close();
            FIS.close();
            return BIOS.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (BIOS != null || FIS != null) {
                try {
                    BIOS.close();
                    BIOS = null;
                    FIS.close();
                    FIS = null;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


}
