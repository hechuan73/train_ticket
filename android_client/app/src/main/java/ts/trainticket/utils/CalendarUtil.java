package ts.trainticket.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarUtil {

    public static Calendar getToday() {
        return Calendar.getInstance();
    }


    public static String formatMMddyyyy(String mmddyyyy) {
        SimpleDateFormat fromat1 = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        Date date2 = null;
        try {
            date2 = fromat1.parse(mmddyyyy);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat fromat2 = new SimpleDateFormat("yyyy-MM-dd");
        return fromat2.format(date2);
    }

    public static Calendar getLastDay() {
        Calendar lastDay = getToday();
        lastDay.add(Calendar.DAY_OF_MONTH, 30 - 1);
        return lastDay;
    }


    public static boolean compareTimeDate(String beginTime) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        try {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String dateNowStr = sdf.format(d);
            Date dt1 = df.parse(dateNowStr);
            Date dt2 = df.parse(beginTime);
            if (dt1.getTime() > dt2.getTime())
                return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean compare_date(String DATE2) {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String DATE1 = sdf.format(d);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            } else if (dt1.getTime() < dt2.getTime()) {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }



    public static int compareDate(Calendar minuend, Calendar subtrahend) {
        int fh = minuend.get(Calendar.HOUR_OF_DAY);
        int fm = minuend.get(Calendar.MINUTE);
        int sh = subtrahend.get(Calendar.HOUR_OF_DAY);
        int sm = subtrahend.get(Calendar.MINUTE);

        if (fh > sh || fh == sh && fm > sm) {
            return 1;
        } else if (fh == sh && fm == sm) {
            return 0;
        } else {
            return -1;
        }
    }


    public static boolean isDateInRange(Calendar date, Calendar min, Calendar max) {
        return compareDate(date, min) * compareDate(max, date) >= 0;
    }



    public static String getDayFormatStr(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return format.format(calendar.getTime());
    }


    public static Calendar getCalendarByStr(String time) {
        String[] split = time.trim().split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(split[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(split[1]));
        return calendar;
    }



    public static String getHMS(String timeStamp) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(timeStamp);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res.substring(11, 19);
    }

    public static String getHM(String timeStamp) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(timeStamp);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res.substring(11, 16);
    }


    public static String getHMSMin(Long t1, Long t2) {
        long number = t1 - t2;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(number);
        return formatter.format(calendar.getTime());
    }

    public static String getDate(String timeStamp) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(timeStamp);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String getYMD(String timeStamp) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(timeStamp);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (day != 0)
            return day + "" + hour + "h" + min + "m";
        else if (hour != 0)
            return hour + "h" + min + "m";
        else if (min != 0)
            return min + "m";
        return "0s";
    }

    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Calendar cal = Calendar.getInstance();
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String tempTime = datetime.substring(5, 10);
        if ('0' == tempTime.charAt(0))
            tempTime = tempTime.substring(1, 4);
        tempTime = tempTime.replaceAll("-", "-");

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return tempTime + " " + weekDays[w];
    }


}
