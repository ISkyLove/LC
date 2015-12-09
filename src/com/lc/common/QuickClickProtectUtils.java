package com.lc.common;

/**
 * 快速操作判断工具类
 * 
 * @author LC work room
 *
 */
public class QuickClickProtectUtils {

	public static long lastClickTime;
	
	/**
	 * 判断是否在divideTime时间内再操作
	 * @param divideTime 设定的时间间隔
	 * @return	判断结果
	 */
	public static boolean isFastDoubleClick(long divideTime) {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < divideTime) {   
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
