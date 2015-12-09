package com.lc.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Camera.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.lc.LCConfig;
import com.lc.R;
import com.lc.common.IItemPostionVisitor;
import com.lc.common.LogUtils;
import com.lc.common.comparetor.GroupItemComparetor;
import com.lc.data.BaseItem.TYPE;
import com.lc.data.GroupItem;
import com.lc.setting.group.GroupSettingFragment;
import com.lc.view.LCGridView.DoubleClickCallBackInterface;
import com.lc.view.LCGridView.TouchDownCallBackInterface;
import com.lc.view.LCGridView.TouchForShineCallBackInterface;
import com.lc.view.LCGridView.TouchMoveCallBackInterface;
import com.lc.view.LCGridView.TouchUpCallBackInterface;
import com.lc.view.adapter.GroupAdapter;

/**
 * 组模块
 * 
 * @author LC work room
 * 
 */
public class LCGroupView {
	private Context mContext;
	private View rootView;
	private LCGridView mGridView;
	private GroupAdapter mGroupAdapter;
	private List<GroupItem> mGroupItems;
	private IItemPostionVisitor iItemPostionVisitor;
	private GroupItemComparetor itemComparetor;

	private OnGroupItemClickListen mGroupItemClickListen;
	private OnGroupItemLongClickListen mGroupItemLongClickListen;
	/**
	 * 是否多选，默认为单选
	 */
	private boolean isMutilClick = false;

	public void setOnGroupItemClickListen(
			OnGroupItemClickListen onGroupItemClickListen) {
		this.mGroupItemClickListen = onGroupItemClickListen;
	}

	public void setOnGroupItemLongClickListen(
			OnGroupItemLongClickListen onGroupItemLongClickListen) {
		this.mGroupItemLongClickListen = onGroupItemLongClickListen;
	}

	public interface OnGroupItemClickListen {
		void OnGroupItemClick(int position, GroupItem groupitem);
	}

	public interface OnGroupItemLongClickListen {
		void OnGroupItemLongClick(int position, GroupItem groupitem);
	}

	public LCGroupView(Context context, boolean ismutilClick) {
		this.mContext = context;
		rootView = LayoutInflater.from(mContext).inflate(
				R.layout.v1_base_gridview_layout, null);
		this.isMutilClick = ismutilClick;
		initView();
		initEvent();

	}

	private void initView() {
		mGridView = (LCGridView) rootView.findViewById(R.id.v1_base_gridview);

		mGroupItems = new ArrayList<GroupItem>();
		int itemsNum = LCConfig.getGroupNum();
		for (int i = 0; i < itemsNum; i++) {
			GroupItem item = new GroupItem(mContext);
			item.setItemType(TYPE.TYPE_GROUP);
			item.setItemId(i);
			mGroupItems.add(item);
		}
		mGroupAdapter = new GroupAdapter(mContext, mGroupItems,
				this.isMutilClick);
		mGridView.setAdapter(mGroupAdapter);

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

	public LCGridView getgir() {
		return mGridView;
	}

	@SuppressWarnings("rawtypes")
	public void changeData(List list) {
		if (itemComparetor == null) {
			itemComparetor = new GroupItemComparetor();
		}
		if (list != null) {
			Collections.sort(list, itemComparetor);
		}
		mGroupItems = list;
		mGroupAdapter.changeData(list);
	}

	public View getRootView() {
		return rootView;
	}

	public int getFocusPosition() {
		return mGroupAdapter.getCurFocusPosition();
	}

	public void setShadowBmSize(Point size) {
		mGridView.setShadowBmSize(size);
	}

	public boolean changeColor(int color) {
		if (mGroupAdapter.getCurFocusPosition() != -1) {
			GroupItem mItem = mGroupItems.get(mGroupAdapter
					.getCurFocusPosition());
			mItem.setItemColor(color);
			mGroupAdapter.notifyDataSetChanged();
			return true;
		}
		return false;
	}

	public boolean changeColor(int color, GroupItem item) {
		for (GroupItem sourceitem : mGroupItems)
			if (sourceitem.getItemId() == item.getItemId()) {
				sourceitem.setItemColor(color);
				sourceitem.setSelect(true);
				mGroupAdapter.notifyDataSetChanged();
				return true;
			}
		return false;
	}

	public void showTargetToFocus(List<GroupItem> data, boolean isFoces) {
		notifyAllItemToNoFocus();
		for (GroupItem item : data) {
			for (GroupItem sourceitem : mGroupItems) {
				if (sourceitem.getItemId() == item.getItemId()) {
					sourceitem.setItemFoceus(isFoces);
					mGroupAdapter.notifyDataSetChanged();
				}
			}
		}
	}

	public List<GroupItem> getData() {
		return mGroupItems;
	}

	private void initEvent() {
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (!isMutilClick) {
					int cur_postion = mGroupAdapter.getCurFocusPosition();
					if (cur_postion == position) {
						mGroupAdapter.setFocusPosition(-1);
						if (mGroupItemClickListen != null) {
							mGroupItemClickListen.OnGroupItemClick(position,
									mGroupItems.get(position));
						}
					} else {
						mGroupAdapter.setFocusPosition(position);
						if (mGroupItemClickListen != null) {
							mGroupItemClickListen.OnGroupItemClick(position,
									mGroupItems.get(position));
						}
					}
					mGroupAdapter.notifyDataSetChanged();
				}

			}
		});
		mGridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (mGroupItemLongClickListen != null) {
					mGroupItemLongClickListen.OnGroupItemLongClick(position,
							mGroupItems.get(position));
					return true;
				}
				return false;
			}
		});
	}

	public boolean isMutilClick() {
		return isMutilClick;
	}

	public void setMutilClick(boolean isMutilClick) {
		this.isMutilClick = isMutilClick;
	}

	/**
	 * 更新视图显示,实现目标item显示foces样式
	 */
	public void notifyItemToFocus(int position) {
		mGroupAdapter.setFocusPosition(position);
		mGroupAdapter.notifyDataSetChanged();
	}

	/**
	 * 设置所有item为NO focus
	 */
	public void notifyAllItemToNoFocus() {
		for (GroupItem item : mGroupItems) {
			item.setItemFoceus(false);
		}
		mGroupAdapter.notifyDataSetChanged();
	}

	/**
	 * 设置所有item为NO select
	 */
	public void notifyAllItemToNoSelect() {
		for (GroupItem item : mGroupItems) {
			item.setSelect(false);
		}
		mGroupAdapter.notifyDataSetChanged();
	}

	/**
	 * 设置所有item为白色
	 */
	public void notifyAllItemToWhite() {
		for (GroupItem item : mGroupItems) {
			item.setItemColor(Color.parseColor("#ffffff"));
		}
		mGroupAdapter.notifyDataSetChanged();
	}

	public IItemPostionVisitor getiItemPostionVisitor() {
		return iItemPostionVisitor;
	}

	public void setiItemPostionVisitor(IItemPostionVisitor iItemPostionVisitor) {
		this.iItemPostionVisitor = iItemPostionVisitor;
	}
}
