package com.lc.animation;

import com.lc.common.LogUtils;
/**
 * 动画枚举类
 * @author LC work room
 *
 */
public enum Effectstype {

	Flipv(FlipV.class), Flipy(FlipY.class);

	public static String TAG = "Effectstype";
	private Class<? extends BaseEffects> effectsClazz;

	private Effectstype(Class<? extends BaseEffects> mclass) {
		effectsClazz = mclass;
	}

	public BaseEffects getAnimator() {
		BaseEffects bEffects = null;
		try {
			bEffects = effectsClazz.newInstance();
		} catch (Exception e) {
			LogUtils.i(TAG, "初始化动画类失败");
		}
		return bEffects;
	}
}
