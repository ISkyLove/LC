package com.lc.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lc.common.QuickClickProtectUtils;

/**
 * ÒÆ¶¯´°¿Ú
 * 
 * @author LC work room
 * 
 */
public class MoveWindow {
	private ImageView dragImageView;

	private WindowManager mWindowManager;
	private WindowManager.LayoutParams mWindowParams;

	private int offsetX;
	private int offsetY;
	private int curPosition;

	private int[] childLocation = new int[2];
	private ChildMovingCallbackInterface cmci;
	private ChildDrapDownCallbackInterface cddci;

	private ViewGroup drapView;
	private int childViewLayoutId = -1;

	private boolean isMoveItem = false;
	private boolean canMoveItem = false;
	private Context mContext;

	public MoveWindow(Context context) {
		this.mContext = context;
	}

	// @Override
	// public boolean onInterceptTouchEvent(MotionEvent ev) {
	// TODO Auto-generated method stub
	// if (ev.getAction() == MotionEvent.ACTION_DOWN) {
	// int x = (int) ev.getX();
	// int y = (int) ev.getY();
	//
	// if (pointToPosition(x, y) == AdapterView.INVALID_POSITION) {
	// return super.onInterceptTouchEvent(ev);
	// }
	//
	// drapView = (ViewGroup) getChildAt(pointToPosition(x, y));
	//
	// curPosition = y - drapView.getTop();
	// drapView.getLocationInWindow(childLocation);
	// offsetX = (int) ev.getRawX();
	// offsetY = (int) ev.getRawY();
	//
	// View shadow = drapView.findViewById(childViewLayoutId);
	//
	// if (shadow != null && x > shadow.getLeft() - 10) {
	// shadow.setDrawingCacheEnabled(true);
	// Bitmap bm = Bitmap.createBitmap(shadow.getDrawingCache());
	// starDrag(bm, y);
	// }
	// return false;
	// }
	// return super.onInterceptTouchEvent(ev);
	// }

//	@Override
//	public boolean onTouchEvent(MotionEvent ev) {
//		// TODO Auto-generated method stub
//		if (dragImageView != null) {
//			switch (ev.getAction()) {
//			case MotionEvent.ACTION_MOVE:
//				if (!QuickClickProtectUtils.isFastDoubleClick(500)
//						&& canMoveItem) {
//					int moveX = (int) ev.getRawX();
//					int moveY = (int) ev.getRawY();
//					onDrag(moveX, moveY);
//					isMoveItem = true;
//					return true;
//				}
//				break;
//			case MotionEvent.ACTION_UP:
//				if (isMoveItem) {
//					int lastX = (int) ev.getRawX();
//					int lastY = (int) ev.getRawY();
//					if (cddci != null) {
//						cddci.callback(lastX, lastY);
//					}
//					stopDrap();
//					return true;
//				}
//				break;
//
//			default:
//				break;
//			}
//			return false;
//		}
//		return super.onTouchEvent(ev);
//	}

	private void onDrag(int moveX, int moveY) {
		if (dragImageView != null) {
			mWindowParams.alpha = 0.8f;
			mWindowParams.y = childLocation[1] + moveY - offsetY;
			mWindowParams.x = childLocation[0] + moveX - offsetX;
			mWindowManager.updateViewLayout(dragImageView, mWindowParams);
			if (cmci != null) {
				cmci.callback(childLocation[0] + moveX - offsetX + 50,
						childLocation[1] + moveY - offsetY + 10);
			}
		}
	}

	private void stopDrap() {
		if (dragImageView != null) {
			mWindowManager.removeView(dragImageView);
			dragImageView = null;
		}
	}

	private void starDrag(Bitmap bm, int y) {
		// TODO Auto-generated method stub
		stopDrap();

		mWindowParams = new WindowManager.LayoutParams();
		mWindowParams.gravity = Gravity.TOP | Gravity.LEFT;
		// mWindowParams.x = this.getLeft();
		mWindowParams.y = y - curPosition + offsetY;

		mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
		mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
		mWindowParams.format = PixelFormat.TRANSLUCENT;
		mWindowParams.windowAnimations = 0;

		ImageView imageView = new ImageView(mContext);
		imageView.setImageBitmap(bm);
		mWindowManager = (WindowManager) mContext.getSystemService("window");
		mWindowManager.addView(imageView, mWindowParams);
		dragImageView = imageView;
	}

	public ChildMovingCallbackInterface getMovingInterface() {
		return cmci;
	}

	public void setMovingInterface(ChildMovingCallbackInterface cmci) {
		this.cmci = cmci;
	}

	public int getChildViewLayoutId() {
		return childViewLayoutId;
	}

	public void setChildViewLayoutId(int childViewLayoutId) {
		this.childViewLayoutId = childViewLayoutId;
	}

	public ChildDrapDownCallbackInterface getActionUpInterface() {
		return cddci;
	}

	public void setActionUpInterface(ChildDrapDownCallbackInterface cddci) {
		this.cddci = cddci;
	}

	public boolean isCanMoveItem() {
		return canMoveItem;
	}

	public void setCanMoveItem(boolean canMoveItem) {
		this.canMoveItem = canMoveItem;
	}

	public interface ChildMovingCallbackInterface {
		public void callback(int x, int y);
	}

	public interface ChildDrapDownCallbackInterface {
		public void callback(int x, int y);
	}

}
