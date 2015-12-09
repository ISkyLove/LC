package com.lc.common;


import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * JSON鐟欙絾鐎界猾锟�
 * 
 * @author xiaoxh
 */
public class JsonUtils {

	/**
	 * 鐏忓攬son鐎涙顑佹稉鑼缎掗弸鎰嫙鏉╂柨娲栫�电懓绨查惃鍑剧猾鑽ょ波閺嬫粌顕挒锟�
	 * 
	 * @param content
	 *            json鐎涙顑佹稉锟�
	 * @param resultClass
	 *            鐟欙絾鐎絡son鐎电懓绨查惃鍕波閺嬫粎琚�
	 * @return
	 */
	public static <T> T fromJson(String content, Class<T> resultClass) {
		return JSON.parseObject(content, resultClass);
	}
	
	public static <T> List<T> fromArrayJson(String content, Class<T> resultClass){
		return JSON.parseArray(content, resultClass);
	}

	/**
	 * 鐏忓挳EAN鐟欙絾鐎介幋鎬濻ON鐎涙顑佹稉鎻掕嫙鏉╂柨娲�
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		return JSON.toJSONString(object);
	}

}