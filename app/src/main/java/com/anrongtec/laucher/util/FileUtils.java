package com.anrongtec.laucher.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.Formatter;

import java.io.File;

/**
 * 文件工具类
 *
 * @author dongtianhao 2016年8月19日17:04:11
 */
public class FileUtils {

	/**
	 * 格式转换文件大小
	 *
	 * @param context
	 * @param fileSize
	 * @return
	 */
	@SuppressLint("NewApi")
	public static String formatFileSize(Context context, long fileSize) {
		return Formatter.formatFileSize(context, fileSize);
	}

	/**
	 * 判断该文件是否存在
	 *
	 * @return
	 */
	public static boolean isExist(String path) {
		try {
			File f = new File(path);
			if (f.exists()) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;

	}

}
