/* 
 * @(#)SystemConfig.java    Created on 2011-12-20
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id: SystemConfigModel.java 32022 2012-10-31 07:11:38Z xuan $
 */
package com.xuan.app.yiba.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.xuan.app.yiba.MainActivity;

/**
 * 应用本身版本号的获取工具，主要用于比对本应用版本和服务器上的版本差异，最终决定是否升级
 * 
 * @author xuan
 * @version $Revision: 32022 $, $Date: 2012-10-31 15:11:38 +0800 (星期三, 31 十月 2012) $
 */
public class SystemConfigModel {

    private Context mContext;
    private static final SystemConfigModel systemConfig = new SystemConfigModel();

    private SystemConfigModel() {
    }

    public static SystemConfigModel instance(Context context) {
        systemConfig.mContext = context;
        return systemConfig;
    }

    /**
     * 得到版本代码versionCode（配在AndroidManifest.xml中），用于升级操作,每次发布都需要升级此版本号
     * 
     * @return
     */
    public int getVersionCode() {
        int versionCode = -1;
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(
                    MainActivity.class.getPackage().getName(), 0);
            versionCode = packageInfo.versionCode;
        }
        catch (NameNotFoundException e) {
            // Ignore
        }

        return versionCode;
    }

    /**
     * 获得应用程序图标
     * 
     * @return
     */
    public int getApplicationIcon() {
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(
                    MainActivity.class.getPackage().getName(), 0);
            return packageInfo.applicationInfo.icon;
        }
        catch (NameNotFoundException e) {
            // Igonre
        }

        return -1;
    }

    /**
     * 得到版本号，表示当前应用处于什么版本号
     * 
     * @return
     */
    public String getVersionName() {
        String versionName = "";
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(
                    MainActivity.class.getPackage().getName(), 0);
            versionName = packageInfo.versionName;
        }
        catch (NameNotFoundException e) {
            // Igonre
        }
        return versionName;
    }

}
