package com.app.libs;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.app.frame.http.NormalRequest.HttpUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DateTools {
    public static int year, month, day;

    public static void initYMD() {
        Date currentTime = new Date();
        year = currentTime.getYear();
        month = currentTime.getMonth();
        day = currentTime.getDay();
    }

    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    public static boolean isHeaderThanToday(String mInputDate) {
        Date nowDate = strToDate(getStringToday());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date d = sdf.parse(mInputDate);
            boolean flag = d.after(nowDate);
            if (flag)
                return true;
            else
                return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    /**
     * 判断是否润年
     *
     * @param ddate
     * @return
     */
    public static boolean isLeapYear(String ddate) {

        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        Date d = strToDate(ddate);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        if ((year % 400) == 0)
            return true;
        else if ((year % 4) == 0) {
            if ((year % 100) == 0)
                return false;
            else
                return true;
        } else
            return false;
    }

    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);

        return strtodate;
    }

    public static String getYMDStr(String datestr) {
        Date date = strToDate(datestr);
        Date currentTime = date;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String dateToString(Date date) {
        Date dateTmp = date;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateTmp);
        return dateString;
    }

    public static String getStringLoginDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getStringCurrentDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getStringHHSS() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static boolean isDateHigherThanOthers(Date dateFirst, Date dateNext) {
        int compareResult = dateFirst.compareTo(dateNext);
        if (compareResult > 0) {
            return true;
        } else if (compareResult == 0) {
            return false;
        } else {
            return false;
        }
    }

    public static String getStringLoginDate2() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = sdf.format(date);
        return str;

    }


    // 获取日期之间相差天数
    public static int subtractionBettenTheValue(String dateFirst,
                                                String dateSecond) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            long d1 = sdf.parse(dateFirst).getTime();
            long d2 = sdf.parse(dateSecond).getTime();
            int d = (int) (Math.abs(d2 - d1) / (1000 * 60 * 60 * 24));
            return d;
        } catch (Exception e) {
            return -1;
        }
    }

    // 获取加一天的时间数据
    public static String addDateFromKnownDate(String dateStr, int addDays) {
        Date date = DateTools.strToDate(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(calendar.DATE, addDays);
        date = calendar.getTime();
        return dateToString(date);
    }

    public static int getMonthForDate(String datestr) {
        Date date = strToDate(datestr);
        Date currentTime = date;
        int month = currentTime.getMonth() + 1;
        return month;
    }

    public static int getDayForDate(String dateStr) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            c.setTime(formatter.parse(dateStr));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int day = c.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public static int getYearForDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date dt = null;
        try {
            dt = sdf.parse(dateStr);
            cal.setTime(dt);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int year = cal.get(Calendar.YEAR);
        return year;
    }


    public static int getDayForWeek(String pTime) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;
        }
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    private static Calendar calendar;// 用来装日期的
    private static DatePickerDialog dialog;
    public static void setDateTimeValueForPopDialog(Context mContext, final HttpUtils.doPostCallBack callBack) {
        calendar = Calendar.getInstance();
        dialog = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear,int dayOfMonth) {
                       String setDate=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                       callBack.onRequestComplete(setDate);
                    }
                }, calendar.get(Calendar.YEAR), calendar
                .get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }


    public static void showDateDialog(View view, final Context ctx,
                                      final DateTranslateCallBack callBack)
    {
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialg = new DatePickerDialog(ctx, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        c.setTimeInMillis(System.currentTimeMillis());
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month+1);
                        c.set(Calendar.DAY_OF_MONTH, day);
                        c.set(Calendar.MILLISECOND, 0);
                        Map<String, Integer> dataMap = new HashMap<>();
                        dataMap.put("year", year);
                        dataMap.put("month", month+1);
                        dataMap.put("day", day);
                        callBack.loadData(dataMap);
                    }
                }, year, month, day);
                datePickerDialg.show();
            }
        });
    }


    //add simultaneous lock to be assure that you can not access more to this method.
    public static void showTimePickerDialog(View view, final Context ctx,
                                            final DateTranslateCallBack callBack) {

        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(ctx,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker arg0,
                                                  int hourOfDay, int timeOfHour) {
                                // TODO Auto-generated method stub
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, timeOfHour);
                                c.set(Calendar.SECOND, 0);
                                c.set(Calendar.MILLISECOND, 0);
                                Map<String, Integer> dataMap = new HashMap<String, Integer>();
                                dataMap.put("hour", hourOfDay);
                                dataMap.put("min", timeOfHour);
                                callBack.loadData(dataMap);
                            }
                        }, hour, minute, true);

                timePickerDialog.show();
            }
        });
    }

    public interface DateTranslateCallBack{
        void loadData(Object obj);
    }


}
