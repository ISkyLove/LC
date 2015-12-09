package com.lc.view;

import java.util.ArrayList;
import java.util.List;

import com.lc.R;
import com.lc.common.LogUtils;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * ¾Ž½MÈÝÆ÷
 * 
 * @author LC work room
 * 
 */
public class LCPaneView {
	public static String TAG = "";
	private Context mContext;
	private View rootView;
	private List<View> mItemPanes;

	private LinearLayout llTotalPane;
	private int PANE_COUNT = 5;
	private List<LCAutoLayout> mAutoLayouts;

	public LCPaneView(Context context, int count) {
		this.mContext = context;
		if (count > 0) {
			PANE_COUNT = count;
		}
		mItemPanes = new ArrayList<View>();
		mAutoLayouts = new ArrayList<LCAutoLayout>();
		initView();
		initEvent();
	}

	private void initView() {
		// TODO Auto-generated method stub
		rootView = LayoutInflater.from(mContext).inflate(
				R.layout.v1_base_pane_horizontal_layoutr, null);
		llTotalPane = (LinearLayout) rootView
				.findViewById(R.id.v1_base_pane_horizontal_pane);
		for (int i = 0; i < PANE_COUNT; i++) {
			addPane();
		}

	}

	public View getRootView() {
		return rootView;
	}

	public boolean isContains(int X, int Y) {
		for (View view : mItemPanes) {
			Rect mViewRect = new Rect(view.getLeft(), view.getTop(),
					view.getRight(), view.getBottom());
			if (mViewRect.contains(X, Y)) {
				return true;
			}
		}
		return false;
	}

	public int addPane() {
		int oldpostion = llTotalPane.getChildCount();
		View mView = getItemPane();
		mItemPanes.add(mView);
		LCAutoLayout mAutoLayout = (LCAutoLayout) mView
				.findViewById(R.id.v1_base_pane_item);
		mAutoLayouts.add(mAutoLayout);
		llTotalPane.addView(mView);
		int newposition = llTotalPane.getChildCount();
		if ((oldpostion + 1) == newposition) {
			return newposition;
		} else {
			LogUtils.i(TAG, "Ìí¼ÓÈÝÆ÷Ê§°Ü");
			return -1;
		}
	}

	private View getItemPane() {
		View mItemPane = LayoutInflater.from(mContext).inflate(
				R.layout.v1_base_pane_item_layout, null);
		return mItemPane;
	}

	private void initEvent() {
		// TODO Auto-generated method stub

	}
}
