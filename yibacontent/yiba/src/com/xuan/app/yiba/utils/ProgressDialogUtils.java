/* 
 * @(#)ProgressDialogUtils.java    Created on 2012-11-16
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id: ProgressDialogUtils.java 32449 2012-11-16 02:21:48Z xuan $
 */
package com.xuan.app.yiba.utils;

import android.app.ProgressDialog;
import android.os.Handler;

/**
 * 简化一些加载提示框的操作
 * 
 * @author xuan
 * @version $Revision: 32449 $, $Date: 2012-11-16 10:21:48 +0800 (星期五, 16 十一月 2012) $
 */
public class ProgressDialogUtils {

    private ProgressDialogUtils() {
    }

    /**
     * 显示（在UI线程中使用）
     * 
     * @param title
     */
    public static void show(String title, ProgressDialog progressDialog) {
        progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * 隐藏（在线程中使用）
     * 
     * @param handler
     */
    public static void dismiss(Handler handler, final ProgressDialog progressDialog) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        });
    }

    /**
     * 隐藏（在UI线程中使用）
     */
    public static void dismiss(ProgressDialog progressDialog) {
        progressDialog.dismiss();
    }

}
