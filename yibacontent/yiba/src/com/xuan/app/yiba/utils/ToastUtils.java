/* 
 * @(#)ToastUtils.java    Created on 2011-5-31
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id: ToastUtils.java 16471 2011-05-31 14:02:22Z redmine $
 */
package com.xuan.app.yiba.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 吐司信息工具类
 * 
 * @author xuan
 * 
 * @version $Revision: 16471 $, $Date: 2011-05-31 22:02:22 +0800 (星期二, 31 五月 2011) $
 */
public class ToastUtils {
    /**
     * 显示吐司信息（较长时间）
     * 
     * @param context
     * @param text
     */
    public static void displayTextLong(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示吐司信息（较短时间）
     * 
     * @param context
     * @param text
     */
    public static void displayTextShort(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

}
