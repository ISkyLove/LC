package com.lc.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lc.R;
import com.lc.common.comparetor.LightItemComparetor;
import com.lc.data.GroupItem;
import com.lc.data.LightItem;
import com.lc.view.LCGridView.DoubleClickCallBackInterface;
import com.lc.view.LCGridView.TouchDownCallBackInterface;
import com.lc.view.LCGridView.TouchForShineCallBackInterface;
import com.lc.view.LCGridView.TouchMoveCallBackInterface;
import com.lc.view.LCGridView.TouchUpCallBackInterface;
import com.lc.view.adapter.LightAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class LCLightView {
	private Context mContext;
	private View rootView;
	private LCGridView mGridView;
	private LightAdapter mLightAdapter;
	private List<LightItem> mLightItems;
	private LightItemComparetor itemComparetor;

	private ILightItemClickListener mLightItemClickListener;

	private ILightItemLongClickListener mLightItemLongClickListener;

	public LCLightView(Context context) {
		this.mContext = context;
		rootView = LayoutInflater.from(mContext).inflate(
				R.layout.v1_base_gridview_layout, null);
		initView();
		initEvent();

	}

	private void initView() {
		// TODO Auto-generated method stub
		mGridView = (LCGridView) rootView.findViewById(R.id.v1_base_gridview);
		mGridView.setNumColumns(3);
		mLightItems = new ArrayList<LightItem>();
		for (int i = 0; i < 50; i++) {
			LightItem item = new LightItem(mContext);
			item.setItemRectMode();
			item.setItemId(i);
			item.setItemName("item" + i);
			mLightItems.add(item);
		}
		mLightAdapter = new LightAdapter(mContext, mLightItems);
		mGridView.setAdapter(mLightAdapter);
	}

	public List getData() {
		return this.mLightItems;
	}

	public void showLightByGroup(GroupItem groupitem) {
		if (groupitem != null) {
			List<LightItem> mItems = (List<LightItem>) groupitem.getChildData();
			if (mItems.size() > 0) {
				for (LightItem childitem : mItems) {
					int id = childitem.getId();
					LightItem mtaritem = mLightItems.get(id);
					mtaritem.setItemFoceus(true);
				}
			}
		} else {
			for (LightItem childitem : mLightItems) {
				childitem.setItemFoceus(false);
			}
		}
		mLightAdapter.notifyDataSetChanged();
	}

	public void changeData(List list) {
		if (itemComparetor == null) {
			itemComparetor = new LightItemComparetor();
		}
		if (list != null) {
			Collections.sort(list, itemComparetor);
		}
		mLightItems = list;
		mLightAdapter.changeData(list);
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

	private void initEvent() {
		// TODO Auto-generated method stub
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int cur_postion = mLightAdapter.getCurFocusPosition();
				if (cur_postion == position) {
					mLightAdapter.setFocusPosition(-1);
					if (mLightItemClickListener != null) {
						mLightItemClickListener.OnLightItemClick(position,
								mLightItems.get(position));
					}
				} else {
					mLightAdapter.setFocusPosition(position);
					if (mLightItemClickListener != null) {
						mLightItemClickListener.OnLightItemClick(position,
								mLightItems.get(position));
					}
				}
				mLightAdapter.notifyDataSetChanged();

			}
		});

		mGridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (mLightItemLongClickListener != null) {
					mLightItemLongClickListener.OnLightItemLongClick(position,
							mLightItems.get(position));
				}
				return true;
			}
		});
	}

	public ILightItemClickListener getmLightItemClickListener() {
		return mLightItemClickListener;
	}

	public ILightItemLongClickListener getmLightItemLongClickListener() {
		return mLightItemLongClickListener;
	}

	public void setmLightItemClickListener(
			ILightItemClickListener mLightItemClickListener) {
		this.mLightItemClickListener = mLightItemClickListener;
	}

	public void setmLightItemLongClickListener(
			ILightItemLongClickListener mLightItemLongClickListener) {
		this.mLightItemLongClickListener = mLightItemLongClickListener;
	}

	public interface ILightItemClickListener {
		void OnLightItemClick(int position, LightItem lightitem);
	}

	public interface ILightItemLongClickListener {
		void OnLightItemLongClick(int position, LightItem lightitem);
	}
}
