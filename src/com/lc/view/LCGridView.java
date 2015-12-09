package com.lc.view;

import com.lc.R;
import com.lc.common.LogUtils;
import com.lc.common.QuickClickProtectUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * 列表统一类
 * 
 * @author LC work room
 * 
 */
@SuppressLint("ClickableViewAccessibility")
public class LCGridView extends GridView {

	private Context mContext; // 上下文
	private TimeCalThread timeCalThread; // 计算时间线程
	private boolean longTouchFlag = false; // 长按标志
	private boolean touchForShineFlag = false; // 发送指令标志
	private boolean touchUpFlag = false; // 手指离开触控面标志
	private boolean shineOnceFlag = false; // 已发送指令标志
	private boolean canMoveChild = false; // 允许拖动列表项标志
	private boolean movingChild = false; // 已经开始拖动列表项标志
	private boolean vibrateOnceFlag = false; // 已经震动标志
	private boolean longTouchMoveFlag = false; // 长按移动标志
	private boolean doubleClickFlag = false; // 双击标志
	private long startCalTime = 0; // 开始计算时间时间戳
	private WindowManager mWindowManager; // 拖动列表项窗口管理
	private WindowManager.LayoutParams mWindowParams; // 窗口管理参数
	private ImageView dragImageView; // 拖动列表项生成的图view
	private int originPosition = INVALID_POSITION; // 拖动的列表项在列表中的位置
	private Rect r = new Rect(); // 拖动的列表项在屏幕上的原始坐标
	private int touchX; // 点击时的x坐标（rawX）
	private int touchY; // 点击时的y坐标（rawY）
	private int moveRangeLeft = -1; // 移动过程中左限值（负数表示不限制）
	private int moveRangeTop = -1; // 移动过程中上限值（负数表示不限制）
	private int moveRangeRight = -1; // 移动过程中右限值（负数表示不限制）
	private int moveRangeBottom = -1; // 移动过程中下限值（负数表示不限制）
	private Point shadowBmSize = null;

	private TouchDownCallBackInterface downCallback; // ACTION_DOWN回调
	private TouchMoveCallBackInterface moveCallBack; // ACTION_MOVE回调
	private TouchUpCallBackInterface upCallback; // ACTION_UP回调
	private TouchForShineCallBackInterface touchForShineCallBack; // 点击发送指令回调
	private DoubleClickCallBackInterface doubleClickCallBack; // 双击回调

	private static final int LONG_TOUCH_TIME = 500; // 鉴定为长按的时间（ms）
	private static final int SHINE_TOUCH_TIME = 3000; // 鉴定为发送指令的时间（ms）
	private static final int VIBRATE_TIME = 100; // 震动持续时间（ms）

	public LCGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
	}

	public LCGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	public LCGridView(Context context) {
		super(context);
		mContext = context;
	}

	/**
	 * 判断参数所在的坐标是否在列表中
	 * 
	 * @param X
	 *            横坐标
	 * @param Y
	 *            纵坐标
	 * @return 如果参数所在的坐标在列表中则返回该列表项的position值，不在则返回INVALID_POSITION
	 */
	public int Contains(int X, int Y) {
		int length = this.getLastVisiblePosition()
				- this.getFirstVisiblePosition();
		int[] viewPosition = new int[2];
		this.getLocationInWindow(viewPosition);
		for (int i = 0; i < length + 1; i++) {
			View view = this.getChildAt(i);
			if (view != null) {
				if (X >= view.getLeft() + viewPosition[0]
						&& X <= view.getRight() + viewPosition[0]
						&& Y >= view.getTop() + viewPosition[1]
						&& Y <= view.getBottom() + viewPosition[1]) {
					return this.getFirstVisiblePosition() + i;
				}
			}
		}
		return INVALID_POSITION;
	}

	public boolean isViewContains(int x, int y) {
		int[] location=new int[2];
		getLocationInWindow(location);
		int width=getMeasuredWidth();
		int height=getMeasuredHeight();
		Rect mRect = new Rect(location[0], location[1], location[0]+width, location[1]+height);
		return mRect.contains(x, y);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 统计双击事件：在100ms内点击同样的位置则认为是双击
			if (!doubleClickFlag) {
				int curClickItemPostion = pointToPosition((int) ev.getX(),
						(int) ev.getY());
				if (QuickClickProtectUtils.isFastDoubleClick(400) && originPosition >= 0 && curClickItemPostion == originPosition
						) {
					doubleClickFlag = true;
					return true;
				}
			}

			startCalTime = System.currentTimeMillis(); // 记录点击开始时间戳
			resetFlags();
			originPosition = pointToPosition((int) ev.getX(), (int) ev.getY()); // 保存原始位置
			// 保存原始点击坐标
			touchX = (int) ev.getRawX();
			touchY = (int) ev.getRawY();
			// 获取当前点击的列表项，用列表项位置-屏幕显示的第一项在列表中的位置计算出点击的列表项在屏幕当前的位置
			ViewGroup drapView = (ViewGroup) getChildAt(originPosition
					- this.getFirstVisiblePosition());
			if (drapView != null) { // 如果点击的位置存在列表项
				drapView.getGlobalVisibleRect(r); // 获取该列表项在窗口中的坐标
				if (downCallback != null) {
					downCallback.downCallback(r.left, r.top, originPosition);
				}
				// 开始计算时间
				timeCalThread = new TimeCalThread();
				timeCalThread.start();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			// 发送指令回调的条件：1.点击时间大于或等于SHINE_TOUCH_TIME; 2.没有发送过指令（即只能发一次，要多次的修改标志）
			// 3.由于在Action_Move中判断，所以手指移动的距离要比较小，约认为没有移动
			if (touchForShineFlag
					&& !shineOnceFlag
					&& (Math.abs((int) ev.getRawX() - touchX) < 40 && Math
							.abs((int) ev.getRawY() - touchY) < 40)) {
				if (touchForShineCallBack != null) {
					touchForShineCallBack.shineCallback(mWindowParams.x,
							mWindowParams.y, originPosition);
				}
				shineOnceFlag = true;
			}
			// 长按回调条件：点击时间大于或等于LONG_TOUCH_TIME
			// 并且移动距离比较小
			if (longTouchFlag
					&& (Math.abs((int) ev.getRawX() - touchX) < 20 && Math
							.abs((int) ev.getRawY() - touchY) < 20)) {
				// 只震动一次
				if (!vibrateOnceFlag && canMoveChild) {
					Vibrator vibrator = (Vibrator) mContext
							.getSystemService(Context.VIBRATOR_SERVICE);
					vibrator.vibrate(VIBRATE_TIME);
					vibrateOnceFlag = true;
				}

				longTouchMoveFlag = true;
			}

			// 只有设置可移动时才能拖动列表项
			if (canMoveChild && longTouchMoveFlag) {
				// 没有开始拖动的先开始拖动
				if (!movingChild) {
					LogUtils.i(LCGridView.class, "开始拖动");
					starDrag();
					movingChild = true;
				} else {
					onDrag((int) ev.getRawX(), (int) ev.getRawY());
				}
				return true;
			}

			break;
		case MotionEvent.ACTION_UP:
			if (doubleClickFlag) {
				if (doubleClickCallBack != null) {
					doubleClickCallBack.doubleClickCallback((int) ev.getRawX(),
							(int) ev.getRawY(), originPosition);
				}
				doubleClickFlag = false;
				longTouchFlag = false;
				touchForShineFlag = false;
				touchUpFlag = true;
				return true;
			}

			if (canMoveChild && longTouchFlag) {
				LogUtils.i(LCGridView.class, "结束拖动");
				if (upCallback != null) {
					upCallback.upCallback((int) ev.getRawX(),
							(int) ev.getRawY(), originPosition);
				}
				stopDrap();
			}

			longTouchFlag = false;
			touchForShineFlag = false;
			touchUpFlag = true;
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 重置标志
	 */
	private void resetFlags() {
		touchUpFlag = false;
		shineOnceFlag = false;
		movingChild = false;
		vibrateOnceFlag = false;
		longTouchMoveFlag = false;
	}

	/**
	 * 开始拖动
	 */
	private void starDrag() {
		// 首先把可能存在的影子去除
		stopDrap();

		ViewGroup drapView = (ViewGroup) getChildAt(originPosition
				- this.getFirstVisiblePosition());
		if (drapView != null) {
			// 获得影子样式
			View shadow = drapView.findViewById(R.id.v1_base_item_layout);
			shadow.setDrawingCacheEnabled(true);
			Bitmap bm = Bitmap.createBitmap(shadow.getDrawingCache());
			if(shadowBmSize != null){
				bm = BitmapHelp.scaleBitmapDown(bm, bm.getWidth() / 2, bm.getHeight() / 2);
			}
			// 设置窗口参数
			mWindowParams = new WindowManager.LayoutParams();
			mWindowParams.gravity = Gravity.TOP | Gravity.LEFT;
			mWindowParams.x = touchX;
			mWindowParams.y = r.top;

			mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
			mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
			mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
					| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
					| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
					| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
			mWindowParams.format = PixelFormat.TRANSLUCENT;
			mWindowParams.windowAnimations = 0;

			ImageView imageView = new ImageView(getContext());
			imageView.setImageBitmap(bm);
			mWindowManager = (WindowManager) getContext().getSystemService(
					"window");
			mWindowManager.addView(imageView, mWindowParams);
			dragImageView = imageView;
		}
	}

	/**
	 * 拖动，即更新影子在窗口的位置
	 */
	private void onDrag(int moveX, int moveY) {
		if (dragImageView != null) {
			mWindowParams.alpha = 0.8f;
			int windowX = moveX;
			int windowY = r.top + moveY - touchY;
			if (moveRangeLeft >= 0 && windowX < moveRangeLeft) {
				windowX = moveRangeLeft;
			}
			if (moveRangeRight >= 0 && windowX > moveRangeRight) {
				windowX = moveRangeRight;
			}
			if (moveRangeTop >= 0 && windowY < moveRangeTop) {
				windowY = moveRangeTop;
			}
			if (moveRangeBottom >= 0 && windowY > moveRangeBottom) {
				windowY = moveRangeBottom;
			}
			mWindowParams.y = windowY;
			mWindowParams.x = windowX;
			mWindowManager.updateViewLayout(dragImageView, mWindowParams);
			if (moveCallBack != null) {
				moveCallBack.moveCallback(mWindowParams.x + mWindowParams.width / 2, mWindowParams.y + mWindowParams.height / 2,
						originPosition);
			}
		}

	}

	/**
	 * 停止拖动，移除影子
	 */
	private void stopDrap() {
		if (dragImageView != null) {
			mWindowManager.removeView(dragImageView);
			dragImageView = null;
		}
	}

	/**
	 * 设置移动范围，当r > l时设置左右范围，当b > t时设置上下范围，不设置任意一个边界范围可以设置该边界为负数
	 * 
	 * @param l
	 *            左限
	 * @param t
	 *            上限
	 * @param r
	 *            右限
	 * @param b
	 *            下限
	 */
	public void setMovingRange(int l, int t, int r, int b) {
		if (r * l < 0) {
			moveRangeLeft = l;
			moveRangeRight = r;
		} else {
			if (r < 0 && l < 0) {
				moveRangeLeft = l;
				moveRangeRight = r;
			} else {
				if (r - l > 0) {
					moveRangeLeft = l;
					moveRangeRight = r;
				}
			}
		}

		if (b * t < 0) {
			moveRangeTop = t;
			moveRangeBottom = b;
		} else {
			if (b < 0 && t < 0) {
				moveRangeTop = t;
				moveRangeBottom = b;
			} else {
				if (b - t > 0) {
					moveRangeTop = t;
					moveRangeBottom = b;
				}
			}
		}
	}

	public TouchDownCallBackInterface getDownCallback() {
		return downCallback;
	}

	public void setDownCallback(TouchDownCallBackInterface downCallback) {
		this.downCallback = downCallback;
	}

	public TouchMoveCallBackInterface getMoveCallBack() {
		return moveCallBack;
	}

	public void setMoveCallBack(TouchMoveCallBackInterface moveCallBack) {
		this.moveCallBack = moveCallBack;
	}

	public TouchUpCallBackInterface getUpCallback() {
		return upCallback;
	}

	public void setUpCallback(TouchUpCallBackInterface upCallback) {
		this.upCallback = upCallback;
	}

	public TouchForShineCallBackInterface getTouchForShineCallBack() {
		return touchForShineCallBack;
	}

	public void setTouch3SecCallBack(
			TouchForShineCallBackInterface touchForShineCallBack) {
		this.touchForShineCallBack = touchForShineCallBack;
	}

	public boolean isCanMoveChild() {
		return canMoveChild;
	}

	public void setCanMoveChild(boolean canMoveChild) {
		this.canMoveChild = canMoveChild;
	}

	public DoubleClickCallBackInterface getDoubleClickCallBack() {
		return doubleClickCallBack;
	}

	public void setDoubleClickCallBack(
			DoubleClickCallBackInterface doubleClickCallBack) {
		this.doubleClickCallBack = doubleClickCallBack;
	}

	public Point getShadowBmSize() {
		return shadowBmSize;
	}

	public void setShadowBmSize(Point shadowBmSize) {
		this.shadowBmSize = shadowBmSize;
	}

	/**
	 * 计算点击时间并设置标志线程类
	 * 
	 * @author LC work room
	 * 
	 */
	private class TimeCalThread extends Thread {

		@Override
		public void run() {
			while (!touchUpFlag) {
				if (System.currentTimeMillis() - startCalTime >= LONG_TOUCH_TIME) {
					longTouchFlag = true;
				}

				if (System.currentTimeMillis() - startCalTime >= SHINE_TOUCH_TIME) {
					touchForShineFlag = true;
				}

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * ACTION_DOWN回调接口（备拓展）
	 * 
	 * @author LC work room
	 * 
	 */
	public interface TouchDownCallBackInterface {
		public void downCallback(int x, int y, int position);
	}

	/**
	 * ACTION_MOVE回调接口（备拓展）
	 * 
	 * @author LC work room
	 * 
	 */
	public interface TouchMoveCallBackInterface {
		public void moveCallback(int x, int y, int position);
	}

	/**
	 * ACTION_UP回调接口（备拓展）
	 * 
	 * @author LC work room
	 * 
	 */
	public interface TouchUpCallBackInterface {
		public void upCallback(int x, int y, int position);
	}

	/**
	 * 发送指令回调接口（备拓展）
	 * 
	 * @author LC work room
	 * 
	 */
	public interface TouchForShineCallBackInterface {
		public void shineCallback(int x, int y, int position);
	}

	/**
	 * 双击回调接口（备拓展）
	 * 
	 * @author LC work room
	 * 
	 */
	public interface DoubleClickCallBackInterface {
		public void doubleClickCallback(int x, int y, int position);
	}
}
