/* 
 * @(#)AlertDialogUtils.java    Created on 2011-6-2
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id: AlertDialogUtils.java 16549 2011-06-02 12:44:27Z redmine $
 */
package com.xuan.app.yiba.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

/**
 * AlertDialog工具类
 * 
 * @author xuan
 * @version $Revision: 16549 $, $Date: 2011-06-02 20:44:27 +0800 (星期四, 02 六月 2011) $
 */
public class AlertDialogUtils {
    /**
     * 展现简单的一个按钮的alert框，类似网页alert
     * 
     * @param title
     * @param message
     * @param buttonText
     */
    public static void displayAlert(Context context, String title, String message, String buttonText) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setTitle(title).setMessage(message).create();

        alertDialog.show();
    }

    /**
     * 可在线程中调用
     * 
     * @param context
     * @param title
     * @param message
     * @param buttonText
     * @param handler
     */
    public static void displayAlert(final Context context, final String title, final String message,
            final String buttonText, Handler handler) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setPositiveButton(buttonText, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setTitle(title).setMessage(message).create();

                alertDialog.show();
            }
        });
    }
}
