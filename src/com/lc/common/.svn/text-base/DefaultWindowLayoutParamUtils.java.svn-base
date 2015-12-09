package com.lc.common;

import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class DefaultWindowLayoutParamUtils {
	
	public static LayoutParams getDefault(){
		LayoutParams wmParams = new LayoutParams();
		wmParams = new WindowManager.LayoutParams();  
        wmParams.type = LayoutParams.TYPE_APPLICATION;
        wmParams.format = PixelFormat.RGBA_8888;  
        wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL;
        wmParams.width = LayoutParams.WRAP_CONTENT;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.height = LayoutParams.WRAP_CONTENT;
        return wmParams;
	}

}
