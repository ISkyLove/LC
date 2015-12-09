package com.lc.common;

import android.util.Log;

/**
 * 调试日志输出类 方便打开或关闭应用的所有调试日志
 * 
 * @author Lin
 * 
 */
public class LogUtils {
	/**
	 * 应用调试日志是否打开。默认打开
	 */
	public static boolean isAPPLogOpen = true;

	public static void i(String TAG, String info) {
		if (isAPPLogOpen) {
			Log.i(TAG, info);
		}
	}

	/**
	 * 
	 * @param clazz
	 * @param info
	 */
	@SuppressWarnings("rawtypes")
	public static void i(Class clazz, String info) {
		if (isAPPLogOpen) {
			Log.i(clazz.getSimpleName(), info);
		}
	}

	public static void d(String TAG, String info) {
		if (isAPPLogOpen) {
			Log.d(TAG, info);
		}
	}

	/**
	 * 
	 * @param clazz
	 * @param info
	 */
	@SuppressWarnings("rawtypes")
	public static void d(Class clazz, String info) {
		if (isAPPLogOpen) {
			Log.d(clazz.getSimpleName(), info);
		}
	}

	public static void w(String TAG, String info) {
		if (isAPPLogOpen) {
			Log.w(TAG, info);
		}
	}

	/**
	 * 
	 * @param clazz
	 * @param info
	 */
	@SuppressWarnings("rawtypes")
	public static void w(Class clazz, String info) {
		if (isAPPLogOpen) {
			Log.w(clazz.getSimpleName(), info);
		}
	}

	public static void v(String TAG, String info) {
		if (isAPPLogOpen) {
			Log.v(TAG, info);
		}
	}

	/**
	 * 
	 * @param clazz
	 * @param info
	 */
	@SuppressWarnings("rawtypes")
	public static void v(Class clazz, String info) {
		if (isAPPLogOpen) {
			Log.v(clazz.getSimpleName(), info);
		}
	}

	public static void e(String TAG, String info) {
		if (isAPPLogOpen) {
			Log.e(TAG, info);
		}
	}

	/**
	 * 
	 * @param clazz
	 * @param info
	 */
	@SuppressWarnings("rawtypes")
	public static void e(Class clazz, String info) {
		if (isAPPLogOpen) {
			Log.e(clazz.getSimpleName(), info);
		}
	}

}
