package com.anrongtec.laucher.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * 字符串处理类
 *
 * @author huiliu
 * @date 2014年6月25日
 **/
public class StringUtil {
    public static final String TAG = StringUtil.class.getSimpleName();
    private static Pattern NUMBER_PATTERN = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$");
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return boolean true空，false 非空
     */
    public static boolean isBlank(String str) {
        if (str != null) {
            return "".equals(str.trim());
        }
        return true;
    }

    /**
     * 获取字符串半角长度（半角算1、全角算2）
     *
     * @param str 字符串 字符串
     * @return int 长度
     */
    public static int getSemiangleLength(String str) {
        if (isBlank(str)) {
            return 0;
        }
        int len = str.length();
        for (int i = 0; i < str.length(); i++) {
            if (isFullwidthCharacter(str.charAt(i))) {
                len = len + 1;
            }
        }
        return len;
    }

    /**
     * 截取指定半角长度的字符串
     *
     * @param str       待处理的字符串
     * @param maxLength 截取的长度
     * @return String 截取的字符串
     */
    public static String getSemiangleString(String str, int maxLength) {
        if (isBlank(str)) {
            return "";
        }
        int length = 0;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isFullwidthCharacter(str.charAt(i))) {
                if (length + 2 <= maxLength) {
                    length = length + 2;
                } else {
                    return str.substring(0, i - 1);
                }
            } else {
                if (length + 1 <= maxLength) {
                    length = length + 1;
                } else {
                    return str.substring(0, i);
                }
            }
        }
        return str;
    }

    /**
     * 判断字符是否为全角字符
     *
     * @param ch 待判断的字符
     * @return boolean true：全角； false：半角
     */
    private static boolean isFullwidthCharacter(char ch) {
        if (ch >= 32 && ch <= 127) {
            // 基本拉丁字母（即键盘上可见的，空格、数字、字母、符号）
            return false;
        } else if (ch >= 65377 && ch <= 65439) {
            // 日文半角片假名和符号
            return false;
        } else {
            return true;
        }
    }

    /**
     * 对字符串进行md5加密
     *
     * @param string 需要加密的字符串
     * @return 加密后的字符串
     */
    public static String md5(String string) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = string.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 将对象序列化成字符串
     *
     * @param object
     * @return
     */
    public static String Serialize(Object object) {
        if (object == null) {
            return "";
        }

        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String serStr = byteArrayOutputStream.toString("ISO-8859-1");
            return serStr;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 化序列化对象
     *
     * @param desStr
     * @return
     */
    public static Object Deserialize(String desStr) {
        if (StringUtil.isBlank(desStr))
            return null;
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        byte[] bt = null;

        Object object;
        try {
            bt = desStr.getBytes("ISO-8859-1");
            byteArrayInputStream = new ByteArrayInputStream(bt);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            object = objectInputStream.readObject();
            return object;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                bt = null;
                if (objectInputStream != null)
                    objectInputStream.close();
                if (byteArrayInputStream != null)
                    byteArrayInputStream.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 将时间转换为时间戳
     *
     * @param s
     * @return
     */
    public static String dateToStamp(String s) {
        String res;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 将时间戳转换为时间
     *
     * @param s
     * @return
     */
    public static String stampToTime(String s) {
        String res;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间
     *
     * @param s
     * @return
     */
    public static String stampToTime(long s) {
        String res;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为年月日
     *
     * @param s
     * @return
     */
    public static String stampToDate(String s) {
        String res;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = Long.valueOf(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为年月日
     *
     * @param s
     * @return
     */
    public static String stampToDate(long s) {
        String res;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 去掉数字0
     * @param number
     * @return
     */
    public static String getPrettyNumber(String number) {
        return BigDecimal.valueOf(Double.parseDouble(number))
                .stripTrailingZeros().toPlainString();
    }

    /**
     * 将时间戳转换为年月日
     *
     * @param s
     * @return
     */
    public static String stampToMonth(long s) {
        String res;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为xx年 xx月 xx日
     *
     * @param s
     * @return
     */
    public static String stampToDateLetter(String s) {
        String res;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH时mm分ss秒");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 18位身份证校验,粗略的校验
     *
     * @param idCard
     * @return
     * @author lyl
     */
    public static boolean is18ByteIdCard(String idCard) {
        String reg = "^\\d{15}$|^\\d{17}[0-9Xx]$";
        if (!idCard.matches(reg)) {
            System.out.println("Format Error!");
            return false;
        }
        return true;
    }

    /**
     * 车牌号格式：汉字 + A-Z + 5位A-Z或0-weather9
     * （只包括了普通车牌号，教练车和部分部队车等车牌号不包括在内）
     */
    public static boolean isCarNumberNO(String carnumber) {
        String carNumRegex = "[京,津,渝,沪,冀,晋,辽,吉,黑,苏,浙,皖,闽,赣,鲁,豫,鄂,湘,粤,琼,川,贵,云,陕,秦,甘,陇,青,台,内蒙古,桂,宁,新,藏,澳,军,海,航,警][a-zA-Z][0-9,a-zA-Z]{5}$";
        if (TextUtils.isEmpty(carnumber)) {
            return false;
        } else {
            return carnumber.matches(carNumRegex);
        }
    }


    /**
     * Generate a value suitable for use in {setId(int)}.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) {
                newValue = 1;
            }
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}
