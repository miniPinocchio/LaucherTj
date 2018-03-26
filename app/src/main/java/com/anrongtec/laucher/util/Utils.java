package com.anrongtec.laucher.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.text.DecimalFormat;

/**
 * 基础工具类
 * 
 * @ClassName: Utils
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author dongtianhao
 * @date 2016年7月8日 下午3:24:47
 *
 */
@SuppressLint("NewApi")
public class Utils {
	/**
	 * 
	 * @Title: isApkDebugable @Description:
	 *         Android应用程序中来判断当前应用是否处于debug状态 @param @param
	 *         context @param @return 设定文件 @return boolean 返回类型 @throws
	 */
	public static boolean isApkDebugable(Context context) {
		try {
			ApplicationInfo info = context.getApplicationInfo();
			return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
		} catch (Exception e) {

		}
		return false;
	}

	/**
	 * 获取版本编号 versionCode 例如1
	 *
	 * @return
	 */
	public static int getVersionCode(Context c) {
		int versionCode = 0;
		try {
			PackageManager pm = c.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(c.getPackageName(), 0);
			versionCode = pi.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * apk大小 由byte转为M
	 *
	 * @param l
	 * @return
	 */
	public static String byte2Mega(long l) {

		double d = l / 1024d / 1024d;

		return new DecimalFormat("0.00").format(d) + "M";

	}

}
