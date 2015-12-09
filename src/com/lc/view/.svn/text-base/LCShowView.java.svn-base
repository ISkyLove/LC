package com.lc.view;

import java.util.ArrayList;
import java.util.List;

import com.lc.LCConfig;
import com.lc.R;
import com.lc.data.BaseItem.TYPE;
import com.lc.data.ShowItem;
import com.lc.view.LCGridView.DoubleClickCallBackInterface;
import com.lc.view.LCGridView.TouchDownCallBackInterface;
import com.lc.view.LCGridView.TouchForShineCallBackInterface;
import com.lc.view.LCGridView.TouchMoveCallBackInterface;
import com.lc.view.LCGridView.TouchUpCallBackInterface;
import com.lc.view.adapter.ShowAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class LCShowView {
	private Context mContext;
	private View rootView;
	private LCGridView mGridView;
	private ShowAdapter mShowAdapter;
	private List<ShowItem> mShowItems;
	private OnShowItemClickListen mShowItemClickListen;

	public LCShowView(Context context) {
		this.mContext = context;
		rootView = LayoutInflater.from(mContext).inflate(
				R.layout.v1_base_gridview_layout, null);
		initView();
		initEvent();

	}

	public void setOnShowItemClickListen(
			OnShowItemClickListen onShowItemClickListen) {
		this.mShowItemClickListen = onShowItemClickListen;
	}

	public interface OnShowItemClickListen {
		void OnShowItemClick(int position, ShowItem showitem);
	}

	private void initView() {
		// TODO Auto-generated method stub
		mGridView = (LCGridView) rootView.findViewById(R.id.v1_base_gridview);
		mGridView.setNumColumns(4);
		mShowItems = new ArrayList<ShowItem>();
		int itemsNum = LCConfig.getShowNum();
		for (int i = 0; i < itemsNum; i++) {
			ShowItem item = new ShowItem(mContext);
			item.setItemType(TYPE.TYPE_SHOW);
			item.setItemName("item" + i);
			item.setItemId(i);
			mShowItems.add(item);
		}
		mShowAdapter = new ShowAdapter(mContext, mShowItems);
		mGridView.setAdapter(mShowAdapter);
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

	public void changeData(List list) {
		mShowItems = list;
		mShowAdapter.changeData(list);
	}

	public int isContains(int X, int Y) {
		return mGridView.Contains(X, Y);
	}

	public List<ShowItem> getData() {
		return this.mShowItems;
	}

	public void setShowFocusPosition(int position) {
		mShowAdapter.setFocusPosition(position);
	}

	public int getCurFocusPosition() {
		return mShowAdapter.getCurFocusPosition();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int cur_postion = mShowAdapter.getCurFocusPosition();
				if (cur_postion == position) {
					mShowAdapter.setFocusPosition(-1);
					if (mShowItemClickListen != null) {
						mShowItemClickListen.OnShowItemClick(position, mShowItems.get(position));
					}
				} else {
					mShowAdapter.setFocusPosition(position);
					if (mShowItemClickListen != null) {
						mShowItemClickListen.OnShowItemClick(position,
								mShowItems.get(position));
					}
				}
				mShowAdapter.notifyDataSetChanged();
			}

		});
	}
}
