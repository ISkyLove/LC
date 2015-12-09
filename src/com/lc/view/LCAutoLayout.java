package com.lc.view;

import java.util.ArrayList;
import java.util.List;

import com.lc.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class LCAutoLayout extends ViewGroup implements OnClickListener,
		OnLongClickListener {
	private final static String TAG = "LCAutoLayout";

	private List<View> views = new ArrayList<View>();

	private static int VIEW_MARGIN = 2;

	private int height = 0;

	private int viewHeight = 0;

	public interface OnItemClickListener {
		public void onClick(int position, View view);
	}

	public interface OnItemLongClickListener {
		public void onLongClick(int position, View view);
	}

	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}

	public void setOnItemLongClickListener(
			OnItemLongClickListener onItemLongClickListener) {
		this.mOnItemLongClickListener = onItemLongClickListener;
	}

	public LCAutoLayout(Context context) {
		super(context);
		initView();
	}

	public LCAutoLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public LCAutoLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		VIEW_MARGIN = 10;
		setBackgroundResource(R.drawable.v1_base_btn_white__unpress);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int w = MeasureSpec.getSize(widthMeasureSpec);
		int h = MeasureSpec.getSize(heightMeasureSpec);
		int tmpHeight = 0;
		if (height == 0) {
			tmpHeight = h;
		} else {
			tmpHeight = height;
		}
		setMeasuredDimension(w, tmpHeight);
		View childView = getChildAt(0);
		measureChildren(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		// for (int i = 0; i < getChildCount(); i++) {
		// getChildAt(i).measure(MeasureSpec.UNSPECIFIED,
		// MeasureSpec.UNSPECIFIED);
		// }

	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {

		final int count = getChildCount();
		int row = 0;
		int lengthX = arg1;
		int lengthY = arg2;
		for (int i = 0; i < count; i++) {
			final View child = this.getChildAt(i);
			int width = child.getMeasuredWidth();
			int height = child.getMeasuredHeight();
			lengthX += width + VIEW_MARGIN;
			lengthY = row * (height + VIEW_MARGIN) + VIEW_MARGIN + height;
			if (lengthX > arg3) {
				lengthX = width + VIEW_MARGIN + arg1;
				row++;
				lengthY = row * (height + VIEW_MARGIN) + VIEW_MARGIN + height;

			}
			child.layout(lengthX - width, lengthY - height, lengthX, lengthY);
		}
		viewHeight = lengthY;
		if (viewHeight > height) {
			height = viewHeight;
			postInvalidate();
		}
	}

	/**
	 * 
	 * 
	 * @param view
	 */
	public void add(View view) {
		views.add(view);
		addView(view);
		view.setOnClickListener(this);
		view.setOnLongClickListener(this);
	}

	public void clearAllViews() {
		views.clear();
	}

	@Override
	public void onClick(View v) {
		int position = views.indexOf(v);
		if (position >= 0) {
			if (mOnItemClickListener != null)
				mOnItemClickListener.onClick(position, views.get(position));
		}
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		int position = views.indexOf(v);
		if (position >= 0) {
			if (mOnItemLongClickListener != null) {
				mOnItemLongClickListener.onLongClick(position,
						views.get(position));
				return true;
			}
		}
		return false;
	}

	public int getViewHeight() {
		return viewHeight;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}