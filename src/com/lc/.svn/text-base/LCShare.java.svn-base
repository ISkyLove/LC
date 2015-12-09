package com.lc;

import com.lc.common.JsonUtils;
import com.lc.common.bean.ConfigBean;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LCShare {

	private static final String LOCAL_SHARE_NAME = "localConfig";
	private static final String CONFIG_FIELD_NAME = "config";
	
	public static void saveIntField(Context context, String key, int value){
		SharedPreferences sp = context.getSharedPreferences(LCShare.LOCAL_SHARE_NAME, Context.MODE_PRIVATE);
		try {
			Editor editor = sp.edit();
			editor.putInt(key, value);
			editor.commit();
		} catch (Exception e) {
		}
	}
	
	public static void saveLongField(Context context, String key, long value){
		SharedPreferences sp = context.getSharedPreferences(LCShare.LOCAL_SHARE_NAME, Context.MODE_PRIVATE);
		try {
			Editor editor = sp.edit();
			editor.putLong(key, value);
			editor.commit();
		} catch (Exception e) {
		}
	}
	
	public static void saveBooleanField(Context context, String key, boolean value){
		SharedPreferences sp = context.getSharedPreferences(LCShare.LOCAL_SHARE_NAME, Context.MODE_PRIVATE);
		try {
			Editor editor = sp.edit();
			editor.putBoolean(key, value);
			editor.commit();
		} catch (Exception e) {
		}
	}
	
	public static void saveStringField(Context context, String key, String value){
		SharedPreferences sp = context.getSharedPreferences(LCShare.LOCAL_SHARE_NAME, Context.MODE_PRIVATE);
		try {
			Editor editor = sp.edit();
			editor.putString(key, value);
			editor.commit();
		} catch (Exception e) {
		}
	}
	
	public static void saveFloatField(Context context, String key, float value){
		SharedPreferences sp = context.getSharedPreferences(LCShare.LOCAL_SHARE_NAME, Context.MODE_PRIVATE);
		try {
			Editor editor = sp.edit();
			editor.putFloat(key, value);
			editor.commit();
		} catch (Exception e) {
		}
	}
	
	public static int getIntegerField(Context context, String key){
		SharedPreferences sp = context.getSharedPreferences(LCShare.LOCAL_SHARE_NAME, Context.MODE_PRIVATE);
		return sp.getInt(key, -1);
	}
	
	public static void saveConfigInLocal(Context context){
		String config = JsonUtils.toJson(LCConfig.getConfig());
		SharedPreferences sp = context.getSharedPreferences(LCShare.LOCAL_SHARE_NAME, Context.MODE_PRIVATE);
		try {
			Editor editor = sp.edit();
			editor.putString(CONFIG_FIELD_NAME, config);
			editor.commit();
		} catch (Exception e) {
		}
	}
	
	public static ConfigBean getLocalConfig(Context context){
		SharedPreferences sp = context.getSharedPreferences(LCShare.LOCAL_SHARE_NAME, Context.MODE_PRIVATE);
		try {
			String config = sp.getString(CONFIG_FIELD_NAME, null);
			if(config == null){
				return null;
			}
			
			ConfigBean configBean = JsonUtils.fromJson(config, ConfigBean.class);
			return configBean;
		} catch (Exception e) {
			return null;
		}
	}
	
}
