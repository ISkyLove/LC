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
 * �б�ͳһ��
 * 
 * @author LC work room
 * 
 */
@SuppressLint("ClickableViewAccessibility")
public class LCGridView extends GridView {

	private Context mContext; // ������
	private TimeCalThread timeCalThread; // ����ʱ���߳�
	private boolean longTouchFlag = false; // ������־
	private boolean touchForShineFlag = false; // ����ָ���־
	private boolean touchUpFlag = false; // ��ָ�뿪�������־
	private boolean shineOnceFlag = false; // �ѷ���ָ���־
	private boolean canMoveChild = false; // �����϶��б����־
	private boolean movingChild = false; // �Ѿ���ʼ�϶��б����־
	private boolean vibrateOnceFlag = false; // �Ѿ��𶯱�־
	private boolean longTouchMoveFlag = false; // �����ƶ���־
	private boolean doubleClickFlag = false; // ˫����־
	private long startCalTime = 0; // ��ʼ����ʱ��ʱ���
	private WindowManager mWindowManager; // �϶��б���ڹ���
	private WindowManager.LayoutParams mWindowParams; // ���ڹ������
	private ImageView dragImageView; // �϶��б������ɵ�ͼview
	private int originPosition = INVALID_POSITION; // �϶����б������б��е�λ��
	private Rect r = new Rect(); // �϶����б�������Ļ�ϵ�ԭʼ����
	private int touchX; // ���ʱ��x���꣨rawX��
	private int touchY; // ���ʱ��y���꣨rawY��
	private int moveRangeLeft = -1; // �ƶ�����������ֵ��������ʾ�����ƣ�
	private int moveRangeTop = -1; // �ƶ�����������ֵ��������ʾ�����ƣ�
	private int moveRangeRight = -1; // �ƶ�����������ֵ��������ʾ�����ƣ�
	private int moveRangeBottom = -1; // �ƶ�����������ֵ��������ʾ�����ƣ�
	private Point shadowBmSize = null;

	private TouchDownCallBackInterface downCallback; // ACTION_DOWN�ص�
	private TouchMoveCallBackInterface moveCallBack; // ACTION_MOVE�ص�
	private TouchUpCallBackInterface upCallback; // ACTION_UP�ص�
	private TouchForShineCallBackInterface touchForShineCallBack; // �������ָ��ص�
	private DoubleClickCallBackInterface doubleClickCallBack; // ˫���ص�

	private static final int LONG_TOUCH_TIME = 500; // ����Ϊ������ʱ�䣨ms��
	private static final int SHINE_TOUCH_TIME = 3000; // ����Ϊ����ָ���ʱ�䣨ms��
	private static final int VIBRATE_TIME = 100; // �𶯳���ʱ�䣨ms��

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
	 * �жϲ������ڵ������Ƿ����б���
	 * 
	 * @param X
	 *            ������
	 * @param Y
	 *            ������
	 * @return ����������ڵ��������б����򷵻ظ��б����positionֵ�������򷵻�INVALID_POSITION
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
			// ͳ��˫���¼�����100ms�ڵ��ͬ����λ������Ϊ��˫��
			if (!doubleClickFlag) {
				int curClickItemPostion = pointToPosition((int) ev.getX(),
						(int) ev.getY());
				if (QuickClickProtectUtils.isFastDoubleClick(400) && originPosition >= 0 && curClickItemPostion == originPosition
						) {
					doubleClickFlag = true;
					return true;
				}
			}

			startCalTime = System.currentTimeMillis(); // ��¼�����ʼʱ���
			resetFlags();
			originPosition = pointToPosition((int) ev.getX(), (int) ev.getY()); // ����ԭʼλ��
			// ����ԭʼ�������
			touchX = (int) ev.getRawX();
			touchY = (int) ev.getRawY();
			// ��ȡ��ǰ������б�����б���λ��-��Ļ��ʾ�ĵ�һ�����б��е�λ�ü����������б�������Ļ��ǰ��λ��
			ViewGroup drapView = (ViewGroup) getChildAt(originPosition
					- this.getFirstVisiblePosition());
			if (drapView != null) { // ��������λ�ô����б���
				drapView.getGlobalVisibleRect(r); // ��ȡ���б����ڴ����е�����
				if (downCallback != null) {
					downCallback.downCallback(r.left, r.top, originPosition);
				}
				// ��ʼ����ʱ��
				timeCalThread = new TimeCalThread();
				timeCalThread.start();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			// ����ָ��ص���������1.���ʱ����ڻ����SHINE_TOUCH_TIME; 2.û�з��͹�ָ���ֻ�ܷ�һ�Σ�Ҫ��ε��޸ı�־��
			// 3.������Action_Move���жϣ�������ָ�ƶ��ľ���Ҫ�Ƚ�С��Լ��Ϊû���ƶ�
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
			// �����ص����������ʱ����ڻ����LONG_TOUCH_TIME
			// �����ƶ�����Ƚ�С
			if (longTouchFlag
					&& (Math.abs((int) ev.getRawX() - touchX) < 20 && Math
							.abs((int) ev.getRawY() - touchY) < 20)) {
				// ֻ��һ��
				if (!vibrateOnceFlag && canMoveChild) {
					Vibrator vibrator = (Vibrator) mContext
							.getSystemService(Context.VIBRATOR_SERVICE);
					vibrator.vibrate(VIBRATE_TIME);
					vibrateOnceFlag = true;
				}

				longTouchMoveFlag = true;
			}

			// ֻ�����ÿ��ƶ�ʱ�����϶��б���
			if (canMoveChild && longTouchMoveFlag) {
				// û�п�ʼ�϶����ȿ�ʼ�϶�
				if (!movingChild) {
					LogUtils.i(LCGridView.class, "��ʼ�϶�");
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
				LogUtils.i(LCGridView.class, "�����϶�");
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
	 * ���ñ�־
	 */
	private void resetFlags() {
		touchUpFlag = false;
		shineOnceFlag = false;
		movingChild = false;
		vibrateOnceFlag = false;
		longTouchMoveFlag = false;
	}

	/**
	 * ��ʼ�϶�
	 */
	private void starDrag() {
		// ���Ȱѿ��ܴ��ڵ�Ӱ��ȥ��
		stopDrap();

		ViewGroup drapView = (ViewGroup) getChildAt(originPosition
				- this.getFirstVisiblePosition());
		if (drapView != null) {
			// ���Ӱ����ʽ
			View shadow = drapView.findViewById(R.id.v1_base_item_layout);
			shadow.setDrawingCacheEnabled(true);
			Bitmap bm = Bitmap.createBitmap(shadow.getDrawingCache());
			if(shadowBmSize != null){
				bm = BitmapHelp.scaleBitmapDown(bm, bm.getWidth() / 2, bm.getHeight() / 2);
			}
			// ���ô��ڲ���
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
	 * �϶���������Ӱ���ڴ��ڵ�λ��
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
	 * ֹͣ�϶����Ƴ�Ӱ��
	 */
	private void stopDrap() {
		if (dragImageView != null) {
			mWindowManager.removeView(dragImageView);
			dragImageView = null;
		}
	}

	/**
	 * �����ƶ���Χ����r > lʱ�������ҷ�Χ����b > tʱ�������·�Χ������������һ���߽緶Χ�������øñ߽�Ϊ����
	 * 
	 * @param l
	 *            ����
	 * @param t
	 *            ����
	 * @param r
	 *            ����
	 * @param b
	 *            ����
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
	 * ������ʱ�䲢���ñ�־�߳���
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
	 * ACTION_DOWN�ص��ӿڣ�����չ��
	 * 
	 * @author LC work room
	 * 
	 */
	public interface TouchDownCallBackInterface {
		public void downCallback(int x, int y, int position);
	}

	/**
	 * ACTION_MOVE�ص��ӿڣ�����չ��
	 * 
	 * @author LC work room
	 * 
	 */
	public interface TouchMoveCallBackInterface {
		public void moveCallback(int x, int y, int position);
	}

	/**
	 * ACTION_UP�ص��ӿڣ�����չ��
	 * 
	 * @author LC work room
	 * 
	 */
	public interface TouchUpCallBackInterface {
		public void upCallback(int x, int y, int position);
	}

	/**
	 * ����ָ��ص��ӿڣ�����չ��
	 * 
	 * @author LC work room
	 * 
	 */
	public interface TouchForShineCallBackInterface {
		public void shineCallback(int x, int y, int position);
	}

	/**
	 * ˫���ص��ӿڣ�����չ��
	 * 
	 * @author LC work room
	 * 
	 */
	public interface DoubleClickCallBackInterface {
		public void doubleClickCallback(int x, int y, int position);
	}
}
