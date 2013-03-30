/* 
 * @(#)Validators.java    Created on 2011-9-26
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.xuan.app.yiba.utils;

import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * 对字符串按照常用规则进行验证的工具类.
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2011-9-26 上午09:15:11 $
 */
public abstract class Validators {

    /**
     * 普通年中每月的天数
     */
    private static final int[] DAY_OF_MONTH = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    /**
     * 字母数字的正则表达式。只包含数字和字母的串
     */
    private static final String REGEX_ALPHANUMERIC = "[a-zA-Z0-9]+";

    /**
     * 简体中文的正则表达式。
     */
    private static final String REGEX_SIMPLE_CHINESE = "^[\u4E00-\u9FA5]+$";

    /**
     * 移动手机号码的正则表达式。
     */
    private static final String REGEX_CHINA_MOBILE = "1(3[4-9]|4[7]|5[012789]|8[278])\\d{8}";

    /**
     * 联通手机号码的正则表达式。
     */
    private static final String REGEX_CHINA_UNICOM = "1(3[0-2]|5[56]|8[56])\\d{8}";

    /**
     * 电信手机号码的正则表达式。
     */
    private static final String REGEX_CHINA_TELECOM = "(?!00|015|013)(0\\d{9,11})|(1(33|53|80|89)\\d{8})";

    /**
     * 整数或浮点数的正则表达式。
     */
    private static final String REGEX_NUMERIC = "(\\+|-){0,1}(\\d+)([.]?)(\\d*)";

    /**
     * 身份证号码的正则表达式。
     */
    private static final String REGEX_ID_CARD = "(\\d{14}|\\d{17})(\\d|x|X)";

    /**
     * 电子邮箱的正则表达式。
     */
    private static final String REGEX_EMAIL = ".+@.+\\.[a-z]+";

    /**
     * 电话号码的正则表达式。
     */
    private static final String REGEX_PHONE_NUMBER = "(([\\(（]\\d+[\\)）])?|(\\d+[-－]?)*)\\d+";

    /**
     * 取某年某月的天数（仅供内部使用）
     * 
     * @param year
     *            年份
     * @param month
     *            月份，0表示1月，依此类推
     * @return 最多的天数
     */
    private static int getMaxDayOfMonth(int year, int month) {
        // 先判断是够是闰年
        Calendar calendar = Calendar.getInstance();
        boolean isLeapYear = ((GregorianCalendar) calendar).isLeapYear(year);

        if (month == 1 && isLeapYear) {
            return 29;
        }

        return DAY_OF_MONTH[month];
    }

    /**
     * 判断串是否是空串 注意：""和"   "和null这三种情况都返回true
     * 
     * @param str
     *            字符串
     * @return true/false
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {// ""和null这两种情况的验证
            return true;
        }

        // 验证"   "这种空串，Character.isWhitespace(str.charAt(i))是判断对应字符是否是空白符
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串是否为Empty，null和""和"   "都算是Empty，即会返回true
     * 
     * @param str
     *            字符串
     * @return true/false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 当数组为<code>null</code>, 或者长度为0, 或者长度为1且元素的值为<code>null</code>时返回 <code>true</code>.
     * 
     * @param args
     * @return true/false
     */
    public static boolean isEmpty(Object[] args) {
        return args == null || args.length == 0 || (args.length == 1 && args[0] == null);
    }

    /**
     * 判断集合是否为空。
     * 
     * @param <T>
     *            集合泛型
     * @param collection
     *            集合对象
     * @return 当集合对象为 <code>null</code> 或者长度为零时返回 <code>true</code>，否则返回 <code>false</code>。
     */
    public static <T> boolean isEmpty(Collection<T> collection) {
        if (collection == null) {
            return true;
        }

        return collection.isEmpty();
    }

    /**
     * 是否为数字的字符串。
     * 
     * @param str
     *            字符串
     * @return true/false
     */
    public static boolean isNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) > '9' || str.charAt(i) < '0') {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否是固定范围内的数字的字符串，精确的集合是[min,max]
     * 
     * @param str
     * @param min
     * @param max
     * @return true/false
     */
    public static boolean isNumber(String str, int min, int max) {

        // 首先判断是否是数字串
        if (!isNumber(str)) {
            return false;
        }

        int number = Integer.parseInt(str);
        return number >= min && number <= max;
    }

    /**
     * 是否是合法的日期字符串，即这种格式（1988-10-15） str.split("-")表示把串用"-"拆开放在数组items字符串数组中 年的范围是[1900,9999]
     * 
     * @param str
     *            日期字符串
     * @return 是true，否则false
     */
    public static boolean isDate(String str) {
        if (isEmpty(str) || str.length() > 10) {
            return false;
        }

        String[] items = str.split("-");

        if (items.length != 3) {
            return false;
        }

        if (!isNumber(items[0], 1900, 9999) || !isNumber(items[1], 1, 12)) {
            return false;
        }

        int year = Integer.parseInt(items[0]);
        int month = Integer.parseInt(items[1]);

        return isNumber(items[2], 1, getMaxDayOfMonth(year, month - 1));
    }

    /**
     * 判断是否是合法的时间字符串。即这种格式（23:48:31）或者（23:48）或（23:9）或（09:09:）
     * 
     * @param str
     *            字符串
     * @return true/false
     */
    public static boolean isTime(String str) {
        if (isEmpty(str) || str.length() > 8) {
            return false;
        }

        String[] items = str.split(":");

        if (items.length != 2 && items.length != 3) {
            return false;
        }

        for (int i = 0; i < items.length; i++) {
            if (items[i].length() != 2 && items[i].length() != 1) {
                return false;
            }
        }

        return !(!isNumber(items[0], 0, 23) || !isNumber(items[1], 0, 59) || (items.length == 3 && !isNumber(items[2],
                0, 59)));
    }

    /**
     * 是否是合法的日期时间字符串，就是把上面的日期和时间结合了一下
     * 
     * @param str
     *            日期时间字符串
     * @return 是true，否则false
     */
    public static boolean isDateTime(String str) {
        if (isEmpty(str) || str.length() > 20) {
            return false;
        }

        String[] items = str.split(" ");

        if (items.length != 2) {
            return false;
        }

        return isDate(items[0]) && isTime(items[1]);
    }

    /**
     * 判断是否是合法的邮编
     * 
     * @param str
     *            字符串
     * @return true/false
     */
    public static boolean isPostcode(String str) {
        if (isEmpty(str)) {
            return false;
        }

        if (str.length() != 6 || isNumber(str)) {
            return false;
        }

        return true;
    }

    /**
     * 判断是否是固定长度范围内的字符串
     * 
     * @param str
     * @param minLength
     * @param maxLength
     * @return true/false
     */
    public static boolean isString(String str, int minLength, int maxLength) {
        if (str == null) {
            return false;
        }

        if (minLength < 0) {
            return str.length() <= maxLength;
        }
        else if (maxLength < 0) {
            return str.length() >= minLength;
        }
        else {
            return str.length() >= minLength && str.length() <= maxLength;
        }
    }

    /**
     * 判断字符串是否匹配了正则表达式。<br>
     * 注意：输入的是null或者不匹配的都返回false
     * 
     * @param str
     *            字符串
     * @param regex
     *            正则表达式
     * @return true/false
     */
    public static boolean isRegexMatch(String str, String regex) {
        return str != null && str.matches(regex);
    }

    /**
     * 是否为中国移动手机号码。
     * 
     * @param str
     *            字符串
     * @return 如果是移动号码，返回 <code>true</code>，否则返回 <code>false</code>。
     */
    public static boolean isChinaMobile(String str) {
        return isRegexMatch(str, REGEX_CHINA_MOBILE);
    }

    /**
     * 是否为中国联通手机号码。
     * 
     * @param str
     *            字符串
     * @return 如果是联通号码，返回 <code>true</code>，否则返回 <code>false</code>。
     */
    public static boolean isChinaUnicom(String str) {
        return isRegexMatch(str, REGEX_CHINA_UNICOM);
    }

    /**
     * 判断是否为电信手机。
     * 
     * @param str
     *            字符串
     * @return 如果是电信号码，返回 <code>true</code>，否则返回 <code>false</code>。
     */
    public static boolean isChinaTelecom(String str) {
        return isRegexMatch(str, REGEX_CHINA_TELECOM);
    }

    /**
     * 判断字符串是否只包含字母和数字.<br>
     * 注意：空串返回的是false
     * 
     * @param str
     *            字符串
     * @return 如果字符串只包含字母和数字, 则返回 <code>true</code>, 否则返回 <code>false</code>.
     */
    public static boolean isAlphanumeric(String str) {
        return isRegexMatch(str, REGEX_ALPHANUMERIC);
    }

    /**
     * 判断字符串是否是合法的电子邮箱地址.
     * 
     * @param str
     *            字符串
     * @return 是true，否则false
     */
    public static boolean isEmail(String str) {
        return isRegexMatch(str, REGEX_EMAIL);
    }

    /**
     * 省份证的验证<br>
     * 这样的就对了：15位或18数字, 14数字加x(X)字符或17数字加x(X)字符才是合法的
     * 
     * @param str
     *            字符串
     * @return
     */
    public static boolean isIdCardNumber(String str) {
        return isRegexMatch(str, REGEX_ID_CARD);
    }

    /**
     * 判断字符是否为整数或浮点数.
     * 
     * @param str
     *            字符串
     * @return 若为整数或浮点数则返回 <code>true</code>, 否则返回 <code>false</code>
     */
    public static boolean isNumeric(String str) {
        return isRegexMatch(str, REGEX_NUMERIC);
    }

    /**
     * 判断是否是电话号码<br>
     * 
     * 这样的都对的：（78674585），（6872-4585），（(6872)4585）<br>
     * （0086-10-6872-4585），（0086-(10)-6872-4585），（0086(10)68724585）
     * 
     * @param str
     *            字符串
     * @return false/true
     */
    public static boolean isPhoneNumber(String str) {
        // Regex for checking phone number
        return isRegexMatch(str, REGEX_PHONE_NUMBER);
    }

    /**
     * 判断字符是否为符合精度要求的整数或浮点数。
     * 
     * @param str
     *            字符串
     * @param fractionNum
     *            小数部分的最多允许的位数
     * @return 若为整数或浮点数则返回 <code>true</code>, 否则返回 <code>false</code>
     */
    public static boolean isNumeric(String str, int fractionNum) {
        if (isEmpty(str)) {
            return false;
        }

        // 整数或浮点数
        String regex = "(\\+|-){0,1}(\\d+)([.]?)(\\d{0," + fractionNum + "})";
        return Pattern.matches(regex, str);
    }

    /**
     * 是否是简体中文字符串。（有空格和英文啊之类的都不行，必须得全中文的才true）
     * 
     * @param str
     *            字符串
     * @return true/false
     */
    public static boolean isSimpleChinese(String str) {
        return isRegexMatch(str, REGEX_SIMPLE_CHINESE);
    }

}
