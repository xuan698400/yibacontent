/* 
 * @(#)ContextUtils.java    Created on 2012-5-7
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.xuan.app.yiba.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

/**
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2012-5-7 下午02:58:39 $
 */
public class ContextUtils {
    private ContextUtils() {
    }

    /**
     * 判断是否存在网络连接
     * 
     * @param context
     * @return
     */
    public static boolean hasNetwork(Context context) {
        ConnectivityManager connectManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();
        if (networkInfo == null || !connectManager.getActiveNetworkInfo().isAvailable()
                || !connectManager.getActiveNetworkInfo().isConnected()) {
            return false;
        }

        return true;
    }

    /**
     * 判断GPS是否打开
     * 
     * @param context
     * @return
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!alm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return false;
        }
        return true;
    }

    /**
     * 将单位dp转换为px
     * 
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int px = (int) (dpValue * scale + 0.5f);
        return px;
    }

    /**
     * SD卡是否可用
     * 
     * @return
     */
    public static boolean hasSdCard() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // sdcard 不可用
            return false;
        }
        return true;
    }
}
