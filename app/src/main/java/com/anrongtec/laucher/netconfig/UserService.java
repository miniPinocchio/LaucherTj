package com.anrongtec.laucher.netconfig;

import android.content.Context;
import android.text.TextUtils;

import com.anrongtec.laucher.TjApp;
import com.anrongtec.laucher.bean.sos.SosBean;
import com.anrongtec.laucher.bean.sos.SosNewBean;
import com.anrongtec.laucher.util.GsonUtil;
import com.anrongtec.laucher.util.SharedPreferencesUtil;
import com.google.gson.Gson;


/**
 * @author huiliu
 * @date 2017/10/10
 * @email liu594545591@126.com
 * @introduce
 */
public class UserService {
    private static final String USER_INFO = "userInfo";
    private static final String STATE_INFO = "stateInfo";

    private static final String INIT_INFO = "initConfig";

    private static final String AUTO_LOGIN = "autoLogin";

    private static final String TASK_CURRENT = "taskCurrent";

    private static final String SAVE_LNG = "saveLng";

    private static final String ORDER_NO = "autoLogin";
    private static String ACTIVE = "active";

    /**
     * 存储用户信息
     *
     * @param user
     */
    public static void saveUserInfo(SosBean user) {
        if (user == null) {
            return;
        }
        String userJson = GsonUtil.parseBeanWithJson(user);
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(TjApp.getContext(), Constant.SP_NAME);
        sharedPreferencesUtil.setData(USER_INFO, userJson);
    }

    /**
     * 存储用户信息
     *
     * @param user
     */
    public static void saveNewUserInfo(SosNewBean user) {
        if (user == null) {
            return;
        }
        String userJson = GsonUtil.parseBeanWithJson(user);
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(TjApp.getContext(), Constant.SP_NAME);
        sharedPreferencesUtil.setData(USER_INFO, userJson);
    }

    /**
     * 保存弹出信息
     *
     * @param user
     */
    public static void saveStateInfo(String user) {
        if (user == null) {
            return;
        }
        String userJson = GsonUtil.parseBeanWithJson(user);
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(TjApp.getContext(), Constant.SP_NAME);
        sharedPreferencesUtil.setData(STATE_INFO, userJson);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static String getStateInfo(Context context) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(TjApp.getContext(), Constant.SP_NAME);
        String userJson = (String) sharedPreferencesUtil.getData(STATE_INFO);
        if (TextUtils.isEmpty(userJson)) {
            return null;
        } else {

            return userJson;
        }
    }


    public static void clearUserInfo() {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(TjApp.getContext(), Constant.SP_NAME);
        sharedPreferencesUtil.clear();
    }


    /**
     * 获取用户信息
     *
     * @return
     */
    public static SosBean getUserInfo(Context context) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(TjApp.getContext(), Constant.SP_NAME);
        String userJson = (String) sharedPreferencesUtil.getData(USER_INFO);
        if (TextUtils.isEmpty(userJson)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(userJson, SosBean.class);
        }
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static SosNewBean getNewUserInfo(Context context) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(TjApp.getContext(), Constant.SP_NAME);
        String userJson = (String) sharedPreferencesUtil.getData(USER_INFO);
        if (TextUtils.isEmpty(userJson)) {
            return null;
        } else {
            Gson gson = new Gson();
            return gson.fromJson(userJson, SosNewBean.class);
        }
    }

    /**
     * 获取激活状态
     *
     * @return
     */
    public static String getIsActive(Context context) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(TjApp.getContext(), Constant.CHECK_IS_ACTIVE);
        return (String) sharedPreferencesUtil.getData(ACTIVE);
    }

    /***
     * 保存激活状态  0 激活 1未激活
     */
    public static void saveActive(String autoLogin) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(TjApp.getContext(), Constant.CHECK_IS_ACTIVE);
        sharedPreferencesUtil.setData(ACTIVE, autoLogin);

    }


    /***
     * 设置自动登录
     */
    public static void setAutoLogin(String autoLogin) {

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(TjApp.getContext(), Constant.SP_NAME);
        sharedPreferencesUtil.setData(AUTO_LOGIN, autoLogin);

    }

    /**
     * 判断是否要自动登录
     *
     * @return
     */
    public static boolean isAutoLogin() {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(TjApp.getContext(), Constant.SP_NAME);
        String autoLoginJson = (String) sharedPreferencesUtil.getData(AUTO_LOGIN);

        return TextUtils.equals("1", autoLoginJson);
    }

}