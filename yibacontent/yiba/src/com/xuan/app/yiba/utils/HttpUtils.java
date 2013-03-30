/* 
 * @(#)HttpUtils.java    Created on 2011-2-18
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id: HttpUtils.java 28840 2012-07-06 06:30:12Z huangwq $
 */
package com.xuan.app.yiba.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * 提供访问HTTP服务的工具类.
 * 
 * @author xuan
 * @version $Revision: 28840 $, $Date: 2012-07-06 14:30:12 +0800 (星期五, 06 七月 2012) $
 */
public abstract class HttpUtils {

    public static String requestURL(String url) throws IOException {
        String result = null;
        BufferedReader reader = null;
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setConnectTimeout(1000 * 12);
            connection.setReadTimeout(1000 * 12);

            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"), 8 * 1024);
            String line = null;
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            result = stringBuilder.toString();
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e1) {
                    // Ignore
                }
            }
        }

        return result;
    }

}
