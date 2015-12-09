package com.lc.view;

import java.util.ArrayList;
import java.util.List;

import com.lc.LCConfig;
import com.lc.R;
import com.lc.data.BaseItem.TYPE;
import com.lc.data.GroupItem;
import com.lc.data.SenceItem;
import com.lc.view.LCGridView.DoubleClickCallBackInterface;
import com.lc.view.LCGridView.TouchDownCallBackInterface;
import com.lc.view.LCGridView.TouchForShineCallBackInterface;
import com.lc.view.LCGridView.TouchMoveCallBackInterface;
import com.lc.view.LCGridView.TouchUpCallBackInterface;
import com.lc.view.adapter.SenceAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class LCSenceView {
	private Context mContext;
	private View rootView;
	private LCGridView mGridView;
	private SenceAdapter mSenceAdapter;
	private List<SenceItem> mSenceItems;

	/**
	 * 是否多选，默认为单选
	 */
	private boolean isMutilClick = false;

	private OnSenceItemClickListen mSenceItemClickListen;

	public void setOnSenceItemClickListen(
			OnSenceItemClickListen onSenceItemClickListen) {
		this.mSenceItemClickListen = onSenceItemClickListen;
	}

	public interface OnSenceItemClickListen {
		void OnSenceItemClick(int position, SenceItem senceitem);
	}

	public LCSenceView(Context context, boolean ismutilClick) {
		this.mContext = context;
		rootView = LayoutInflater.from(mContext).inflate(
				R.layout.v1_base_gridview_layout, null);
		this.isMutilClick = ismutilClick;
		initView();
		initEvent();

	}

	private void initView() {
		// TODO Auto-generated method stub
		mGridView = (LCGridView) rootView.findViewById(R.id.v1_base_gridview);
		mGridView.setNumColumns(5);
		mSenceItems = new ArrayList<SenceItem>();
		int itemsNum = LCConfig.getSenceNum();
		for (int i = 0; i < itemsNum; i++) {
			SenceItem item = new SenceItem(mContext);
			item.setItemType(TYPE.TYPE_SENCE);
			item.setItemId(i);
			mSenceItems.add(item);
		}
		mSenceAdapter = new SenceAdapter(mContext, mSenceItems,
				this.isMutilClick);
		mGridView.setAdapter(mSenceAdapter);
	}

	public View getRootView() {
		return rootView;
	}

	public void setColumnsNum(int count) {
		mGridView.setNumColumns(count);
	}

	public void setCanMoveChild(boolean moveFlag) {
		mGridView.setCanMoveChild(moveFlag);
	}

	public void setDownCallback(TouchDownCallBackInterface downCallBack) {
		mGridView.setDownCallback(downCallBack);
	}

	public void setUpCallbck(TouchUpCallBackInterface upCallBack) {
		mGridView.setUpCallback(upCallBack);
	}

	public void setMoveCallback(TouchMoveCallBackInterface moveCallBack) {
		mGridView.setMoveCallBack(moveCallBack);
	}

	public void setShineCallback(TouchForShineCallBackInterface shineCallBack) {
		mGridView.setTouch3SecCallBack(shineCallBack);
	}

	public void setDoubleClickCallback(
			DoubleClickCallBackInterface doubleClickCallBack) {
		mGridView.setDoubleClickCallBack(doubleClickCallBack);
	}

	public int isContains(int X, int Y) {
		return mGridView.Contains(X, Y);
	}

	public void changeData(List list) {
		mSenceItems = list;
		mSenceAdapter.changeData(list);
	}

	public List<SenceItem> getData() {
		return this.mSenceItems;
	}

	public void showTargetToFocus(List<SenceItem> data, boolean isFoces) {
		notifyAllItemToNoFocus();
		for (SenceItem item : data) {
			if (item != null) {
				for (SenceItem sourceItem : mSenceItems) {
					if (sourceItem.getItemId() == item.getItemId())
						sourceItem.setItemFoceus(isFoces);
					mSenceAdapter.notifyDataSetChanged();
				}
			}
		}
	}

	/**
	 * 设置所有item为NO focus
	 */
	public void notifyAllItemToNoFocus() {
		for (SenceItem item : mSenceItems) {
			item.setItemFoceus(false);
		}
		mSenceAdapter.notifyDataSetChanged();
	}

	public int getCurFocusPosition() {
		return mSenceAdapter.getCurFocusPosition();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (!isMutilClick) {
					int cur_postion = mSenceAdapter.getCurFocusPosition();
					if (cur_postion == position) {
						mSenceAdapter.setFocusPosition(-1);
						if (mSenceItemClickListen != null) {
							mSenceItemClickListen.OnSenceItemClick(position,
									mSenceItems.get(position));
						}
					} else {
						mSenceAdapter.setFocusPosition(position);
						if (mSenceItemClickListen != null) {
							mSenceItemClickListen.OnSenceItemClick(position,
									mSenceItems.get(position));
						}
					}
					mSenceAdapter.notifyDataSetChanged();
				}

			}
		});
	}

	/**
	 * 设置所有item为NO Select
	 */
	public void notifyItemToNoSelect(SenceItem item) {
		if (mSenceItems.contains(item)) {
			SenceItem senceitem = mSenceItems.get(mSenceItems.indexOf(item));
			senceitem.setSelect(false);
		}
		mSenceAdapter.notifyDataSetChanged();
	}

	/**
	 * 设置所有item为NO select
	 */
	public void notifyAllItemToNoSelect() {
		for (SenceItem item : mSenceItems) {
			item.setSelect(false);
		}
		mSenceAdapter.notifyDataSetChanged();
	}

	public boolean isMutilClick() {
		return isMutilClick;
	}

	public void setMutilClick(boolean isMutilClick) {
		this.isMutilClick = isMutilClick;
	}

	public void setFocusItem(int position) {
		mSenceAdapter.setFocusPosition(position);
		mSenceAdapter.notifyDataSetChanged();
	}

}
