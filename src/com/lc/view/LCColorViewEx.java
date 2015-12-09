package com.lc.view;

import com.lc.R;
import com.lc.common.LogUtils;
import com.lc.view.ColorView.OnTouchDownListen;
import com.lc.view.ColorView.OnTouchMoveListen;
import com.lc.view.ColorView.OnTouchUpListen;
import com.lc.view.ColorViewBottom.OnColorCardClickListen;
import com.lc.view.ColorViewBottom.OnProgressUpdate2Listen;
import com.lc.view.ColorViewBottom.OnProgressUpdateListen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 颜色模块
 * 
 * @author LC work room
 * 
 */
public class LCColorViewEx {

	private Context mContext;
	private View rootView;
	private ColorView mColorView;
	private ColorViewBottom mColorViewBottom;
	private LinearLayout llBottom;
	private int cirwidth;
	private int cirheight;

	private OnColorPickListen mColorPickListen;
	/**
	 * bug 添加addOnGlobalLayoutListener监听导致每次gridView数据刷新的时候ColorView都会重画滑塊,
	 * 添加是否第一次進入標誌，實現只在第一次進入的時候初始化數據 修改2015/2/19 author Lin
	 */
	private boolean isFirstDraw = true;

	public LCColorViewEx(Context context) {
		// TODO Auto-generated constructor stub
		this(context, 0, 0);

	}

	public LCColorViewEx(Context context, int width, int height) {
		this.mContext = context;
		mColorViewBottom = new ColorViewBottom(mContext);
		rootView = LayoutInflater.from(mContext).inflate(
				R.layout.v1_color_pick_layout, null);
		this.cirwidth = width;
		this.cirheight = height;

		initView();
		initEvent();
	}

	public void setOnColorPickListen(OnColorPickListen onColorPickListen) {
		this.mColorPickListen = onColorPickListen;
	}

	public interface OnColorPickListen {
		void OnColorPickTouchMove(int color);

		void OnColorPickTouchUp(int color);

		void OnSeekBarUpdate(int progress);

		void OnSeekBarUpdate2(int progress);

		void OnColorCardClick(int position, int color);
	}

	private void initView() {
		// TODO Auto-generated method stub
		mColorView = (ColorView) rootView
				.findViewById(R.id.v1_color_pick_color);

		llBottom = (LinearLayout) rootView
				.findViewById(R.id.v1_color_pick_bottom);
		llBottom.addView(mColorViewBottom.getRootView());
	}

	/**
	 * 初始化取色块
	 */
	private void iniClorView() {
		if (isFirstDraw) {
			BitmapDrawable mBitmapDrawable = (BitmapDrawable) mContext
					.getResources().getDrawable(R.drawable.v1_color_pick_bk);
			LogUtils.i(
					LCColorViewEx.class,
					"size:" + rootView.getMeasuredWidth() + ":"
							+ rootView.getMeasuredHeight() + ":"
							+ llBottom.getMeasuredWidth() + ":"
							+ llBottom.getMeasuredHeight() + ":"
							+ mColorView.getMeasuredWidth() + ":"
							+ mColorView.getMeasuredHeight());
			int bitWidth = mColorView.getMeasuredWidth();
			int bitHeight = mColorView.getMeasuredHeight();
			if (bitWidth == 0) {

			}
			if (bitHeight == 0) {

			}

			cirwidth = bitWidth;
			if (cirheight > 0) {
				bitHeight = cirheight;
				setMargins(mColorView, 100, 0, 100, 200);
			}

			int size = 0;
			if (bitWidth >= bitHeight) {
				size = bitHeight;
			} else {
				size = bitWidth;
			}

			Bitmap mbitmap = BitmapHelp.scaleBitmapDown(
					mBitmapDrawable.getBitmap(), size, size);
			mBitmapDrawable = new BitmapDrawable(mbitmap);
			mColorView.getLayoutParams().height = size;
			mColorView.getLayoutParams().width = size;
			mColorView.measure(
					MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY),
					MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY));
			mColorView.setBackgroundDrawable(mBitmapDrawable);
			mColorView.initBK();
			isFirstDraw = false;

		}
	}

	public static void setMargins(View v, int l, int t, int r, int b) {
		if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v
					.getLayoutParams();
			p.setMargins(l, t, r, b);
			v.requestLayout();
		}
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		mColorView.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// TODO Auto-generated method stub
						iniClorView();
					}
				});

		mColorView.setOnTouchUpListen(new OnTouchUpListen() {

			@Override
			public void OnTouchUp(int color) {
				// TODO Auto-generated method stub

				if (mColorPickListen != null) {
					mColorPickListen.OnColorPickTouchUp(color);
				}
			}
		});

		mColorView.setOnTouchMoveListen(new OnTouchMoveListen() {

			@Override
			public void OnTouchMove(int color) {
				// TODO Auto-generated method stub
				if (mColorPickListen != null) {
					mColorPickListen.OnColorPickTouchMove(color);
				}
			}
		});

		mColorView.setOnTouchDownListen(new OnTouchDownListen() {

			@Override
			public void OnTouchDown(int color) {
				// TODO Auto-generated method stub

			}
		});

		mColorViewBottom
				.setOnProgressUpdateListen(new OnProgressUpdateListen() {

					@Override
					public void OnProgressUpdate(int progress) {
						// TODO Auto-generated method stub
						if (mColorPickListen != null) {
							mColorPickListen.OnSeekBarUpdate(progress);
						}
					}
				});
		mColorViewBottom
				.setOnProgressUpdate2Listen(new OnProgressUpdate2Listen() {

					@Override
					public void OnProgressUpdate2(int progress) {
						// TODO Auto-generated method stub
						if (mColorPickListen != null) {
							mColorPickListen.OnSeekBarUpdate2(progress);
						}
					}
				});
		mColorViewBottom
				.setOnColorCardClickListen(new OnColorCardClickListen() {

					@Override
					public void OnColorCardClick(int position, int color) {
						// TODO Auto-generated method stub
						if (mColorPickListen != null) {
							mColorPickListen.OnColorCardClick(position, color);
						}
					}
				});
	}

	public static byte[] getByteByRGB(int red, int green, int blue) {
		byte[] result = new byte[3];
		result[0] = (byte) (red & 0xff);
		result[1] = (byte) (green & 0xff);
		result[2] = (byte) (blue & 0xff);
		return result;
	}

	public View getRootView() {
		return rootView;
	}

	public void setSeekBar2Gone() {
		mColorViewBottom.setSeeekBarVisiable(View.VISIBLE, View.GONE);
	}
}
