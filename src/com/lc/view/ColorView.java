package com.lc.view;

import java.util.ArrayList;
import java.util.List;

import com.lc.common.LogUtils;

import android.R.bool;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

/**
 * 取色器
 * 
 * @author LC work room
 * 
 */
public class ColorView extends View implements OnTouchListener {

	private String TAG = ColorView.class.getSimpleName();

	private OnTouchListener mListener = null;

	// private ColorPickPoint mColorPickPoint;

	private List<ColorPickPoint> mColorPickPoints;

	private ColorPickPoint mCurrentColorPickPoint;

	private int Down_X = -1;

	private int Down_Y = -1;

	private int Location_X = -1;
	private int Location_Y = -1;

	private VelocityTracker mVelocityTracker = null;
	private int XVelocity = 0;
	private int YVelocity = 0;
	private OnTouchUpListen mOnTouchUpListen;
	private OnTouchMoveListen mOnTouchMoveListen;
	private OnTouchDownListen mOnTouchDownListen;

	/**
	 * 设置取色器取色时间间隔
	 */
	public static long TIME_DIRECTION = 200;
	/**
	 * 开辟静态区域，在Move过程中不断更新颜色
	 */
	private static int move_color;
	/**
	 * 设置消息事件，每隔一定时间TIME_DIRECTION去获取最新的颜色数据，实现每隔TIME_DIRECTION发送一个颜色值功能
	 */
	public static final int MOVE_BEGIN = 1;
	public static final int MOVE_ING = 2;
	public static final int MOVE_END = 3;

	private boolean isRectMode = false;
	private Handler mMoveHandler;

	public void setOnTouchUpListen(OnTouchUpListen onTouchUpListen) {
		this.mOnTouchUpListen = onTouchUpListen;
	}

	public void setOnTouchMoveListen(OnTouchMoveListen onTouchMoveListen) {
		this.mOnTouchMoveListen = onTouchMoveListen;
	}

	public void setOnTouchDownListen(OnTouchDownListen onTouchDownListen) {
		this.mOnTouchDownListen = onTouchDownListen;
	}

	public interface OnTouchUpListen {
		void OnTouchUp(int color);
	}

	public interface OnTouchMoveListen {
		void OnTouchMove(int color);
	}

	public interface OnTouchDownListen {
		void OnTouchDown(int color);
	}

	public void setRectMode(boolean isrectmode) {
		this.isRectMode = isrectmode;
	}

	public void setTimeDir(long time) {
		TIME_DIRECTION = time;
	}

	public ColorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	public ColorView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	public void initBK() {

		int centerx = getMeasuredWidth() / 2;
		int centery = getMeasuredHeight() / 2;
		// mColorPickPoint.setCenter_x(centerx);
		// mColorPickPoint.setCenter_y(centery);
		BitmapDrawable mdra = (BitmapDrawable) getBackground();
		Bitmap bitmap = mdra.getBitmap();
		mColorPickPoints = new ArrayList<ColorPickPoint>();
		for (int i = 0; i < 1; i++) {
			ColorPickPoint colorPickPoint = new ColorPickPoint();

			colorPickPoint.setCenter_x(centerx);
			int newy = centery;// + (i + 1) * 70;
			colorPickPoint.setCenter_y(newy);
			int pixel = bitmap.getPixel(centerx, newy);
			colorPickPoint.setId(i);
			colorPickPoint.setMain_Color(pixel);
			mColorPickPoints.add(colorPickPoint);
		}

	}

	private void initView() {
		setOnTouchListener(this);
		// mColorPickPoint = new ColorPickPoint();s
		mMoveHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case MOVE_BEGIN:
					mMoveHandler.sendEmptyMessage(MOVE_ING);
					break;
				case MOVE_ING:
					if (mOnTouchMoveListen != null) {
						mOnTouchMoveListen.OnTouchMove(move_color);
						Message msendmsg = mMoveHandler.obtainMessage();
						msendmsg.what = MOVE_ING;
						mMoveHandler.sendMessageDelayed(msendmsg,
								TIME_DIRECTION);
					}
					break;
				case MOVE_END:
					mMoveHandler.removeMessages(MOVE_ING);
					break;

				default:
					break;
				}
			}
		};

	}

	@Override
	public void setOnTouchListener(OnTouchListener l) {
		// TODO Auto-generated method stub
		if (mListener == null) {
			mListener = l;
		} else {
			return;
		}
		super.setOnTouchListener(mListener);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		getParent().requestDisallowInterceptTouchEvent(true);
		BitmapDrawable mdra = (BitmapDrawable) getBackground();
		Bitmap bitmap = mdra.getBitmap();
		int bitwidth = bitmap.getWidth();
		int bitheight = bitmap.getHeight();
		int range = 0;
		if (bitwidth >= bitheight) {
			range = bitheight / 2;
		} else {
			range = bitwidth / 2;
		}
		int centerwidth = bitwidth / 2;
		int centerheight = bitheight / 2;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Down_X = ((int) event.getX()) - Location_X;
			Down_Y = ((int) event.getY()) - Location_Y;
			// mColorPickPoint.setTouch(isOnTargetPoint(Down_X, Down_Y,
			// mColorPickPoint));
			mCurrentColorPickPoint = getOnTargetPoint(Down_X, Down_Y);
			if (mCurrentColorPickPoint != null) {
				mCurrentColorPickPoint.setTouch(true);
			}
			if (mVelocityTracker == null) {
				mVelocityTracker = VelocityTracker.obtain();
				mVelocityTracker.addMovement(event);
			} else {
				mVelocityTracker.clear();
			}
			if (mOnTouchDownListen != null) {
				mOnTouchDownListen.OnTouchDown(-1);
			}

			mMoveHandler.sendEmptyMessage(MOVE_BEGIN);
			break;
		case MotionEvent.ACTION_MOVE:
			if (mVelocityTracker != null) {
				mVelocityTracker.addMovement(event);
				mVelocityTracker.computeCurrentVelocity(1000);
				XVelocity = (int) mVelocityTracker.getXVelocity();
				YVelocity = (int) mVelocityTracker.getYVelocity();
				// Log.i(TAG, "XVelocity:" + XVelocity + ",YVelocity:" +
				// YVelocity);
			}
			int Move_rawx = (int) event.getRawX();
			int Move_rawy = (int) event.getRawY();

			int Move_X = ((int) event.getX());
			int Move_Y = ((int) event.getY());
			if (isRectMode) {
				if (Move_X < 0) {
					Move_X = 0;
				}
				if (Move_X > bitwidth) {
					Move_X = bitwidth;
				}

				if (Move_Y > bitheight) {
					Move_Y = bitheight;
				}

				if (Move_Y < 0) {
					Move_Y = 0;
				}
			} else {
				int rangx = range - Move_X;
				int rangy = range - Move_Y;
				double direction = Math.sqrt((range - Move_X)
						* (range - Move_X) + (range - Move_Y)
						* (range - Move_Y));
				if (direction > (range - ColorPickPoint.SIZE - 10)) {
					return false;
				}
				// LogUtils.i("messagegg", "x:" + Move_X + ",y" +
				// Move_Y+",direction"+direction+",range"+range);
			}
			// LogUtils.i(TAG, "Move_X:" + Move_X + "," + Move_Y);

			if (mCurrentColorPickPoint != null) {
				if (mCurrentColorPickPoint.isTouch()) {
					mCurrentColorPickPoint.setCenter_x(Move_X);
					mCurrentColorPickPoint.setCenter_y(Move_Y);
					if (Move_X > 0 && Move_X < bitwidth && Move_Y > 0
							&& Move_Y < bitheight) {
						int pixel = bitmap.getPixel(Move_X, Move_Y);
						mCurrentColorPickPoint.setMain_Color(pixel);
						move_color = pixel;
						// if (mOnTouchMoveListen != null) {
						// mOnTouchMoveListen.OnTouchMove(pixel);
						// }
					} else {
						return false;
					}
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			int UP_X = ((int) event.getX());
			int UP_Y = ((int) event.getY());
			// if (mVelocityTracker != null) {
			// mVelocityTracker.recycle();
			// }
			if (mCurrentColorPickPoint != null) {
				if (mCurrentColorPickPoint.isTouch()) {
					if (UP_X > 0 && UP_X < bitwidth && UP_Y > 0
							&& UP_Y < bitheight) {
						int pixel = bitmap.getPixel(UP_X, UP_Y);
						int red = Color.red(pixel);
						int green = Color.green(pixel);
						int blue = Color.blue(pixel);
						if (mOnTouchUpListen != null) {
							mOnTouchUpListen.OnTouchUp(pixel);
						}
					}
				}
				mCurrentColorPickPoint = null;
			}
			mMoveHandler.sendEmptyMessage(MOVE_END);
			// mColorPickPoint.setTouch(false);
			break;
		default:
			break;
		}
		invalidate();
		getParent().requestDisallowInterceptTouchEvent(false);
		return true;
	}

	private boolean isOnTargetPoint(int x, int y, ColorPickPoint colorPickPoint) {
		Rect mrect = colorPickPoint.getMain_rect();
		return mrect.contains(x, y);
	}

	private ColorPickPoint getOnTargetPoint(int x, int y) {
		for (ColorPickPoint colorPickPoint : mColorPickPoints) {
			if (isOnTargetPoint(x, y, colorPickPoint)) {
				return colorPickPoint;
			}
		}
		return null;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		// mColorPickPoint.DrawPoint(canvas);
		if (mColorPickPoints != null) {
			for (ColorPickPoint colorPickPoint : mColorPickPoints) {
				colorPickPoint.DrawPoint(canvas);
			}
		}
	}

}
