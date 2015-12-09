package com.lc.view;

import android.graphics.Bitmap;
import android.util.Log;

public class BitmapHelp {

	public static void checkBitmapParam(Bitmap bitmap) {
		if (bitmap == null) {
			throw new IllegalArgumentException("bitmap can not be null");
		}
		if (bitmap.isRecycled()) {
			throw new IllegalArgumentException("bitmap can not be recycled");
		}
	}

	public static Bitmap scaleBitmapDown(Bitmap bitmap, int width, int height) {
		return Bitmap.createScaledBitmap(bitmap, width, height, true);
	}

}
