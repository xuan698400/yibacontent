package com.xuan.app.yiba.utils;

import java.net.URLEncoder;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 翻译工具
 * 
 * @author xuan
 */
public class TranslateUtils {

    public static String getTranslateWord(String fromWord, List<String> explainsList) {
        String result = "";
        String resultTemp = "";

        if (Validators.isEmpty(fromWord)) {
            return result;
        }

        try {
            String fromWordEncoder = URLEncoder.encode(fromWord);
            String url = "http://fanyi.youdao.com/openapi.do?keyfrom=xuanner&key=1726129807&type=data&doctype=json&version=1.1&q="
                    + fromWordEncoder;
            resultTemp = HttpUtils.requestURL(url);
        }
        catch (Exception e) {
            // Ignore
        }

        // 从Json串中解析出译文
        try {
            JSONObject obj = new JSONObject(resultTemp);
            JSONArray array = obj.getJSONArray("translation");
            result = array.get(0).toString();

            // 解析出解析的翻译
            if (obj.has("basic")) {
                JSONObject basic = obj.getJSONObject("basic");
                JSONArray explains = basic.getJSONArray("explains");
                for (int i = 0; i < explains.length(); i++) {
                    explainsList.add(explains.get(i).toString());
                }
            }
        }
        catch (Exception e) {
            result = "不好意思出错了，请联系我的主人：15858178400";
        }

        return result;
    }

}
