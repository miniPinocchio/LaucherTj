package com.anrongtec.laucher.util;

import com.google.gson.Gson;

import java.util.List;

/**
 *
 * @author liuhui
 * @date 2017/8/12
 * @email liu594545591@126.com
 * @introduce
 */

public class GsonUtil {

    /** 将Json数据解析成相应的映射对象 */
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(jsonData, type);
    }

    /** 将Json数据解析成相应的映射对象 */
    public static String parseJsonWithBean(List<Object> type) {
        Gson gson = new Gson();
        return gson.toJson(type);
    }
    /** 将对象数据解析成json */
    public static String parseBeanWithJson(Object type) {
        Gson gson = new Gson();
        return gson.toJson(type);
    }


}
