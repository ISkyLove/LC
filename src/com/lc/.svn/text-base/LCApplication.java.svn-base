package com.lc;

import android.app.Application;

/**
 * 
 * @author Lin
 * 
 */
public class LCApplication extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		LCUncatchExcaptionHandler mExcaptionHandler = LCUncatchExcaptionHandler
				.getInstance(getApplicationContext());
		mExcaptionHandler.init();
		super.onCreate();
	}
}
