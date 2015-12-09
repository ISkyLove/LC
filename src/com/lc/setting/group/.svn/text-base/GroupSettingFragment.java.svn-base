package com.lc.setting.group;

import java.util.List;

import com.lc.LCConfig;
import com.lc.LCFragment;
import com.lc.R;
import com.lc.common.DataUtils;
import com.lc.common.LogUtils;
import com.lc.common.bean.MemberBean;
import com.lc.data.DataConbine;
import com.lc.data.DataConfig;
import com.lc.data.LightItem;
import com.lc.data.GroupItem;
import com.lc.dialog.DeleteDialog;
import com.lc.dialog.DeleteDialog.IDeleteCallbackInterface;
import com.lc.group.bean.GroupBean;
import com.lc.view.LCGridView.DoubleClickCallBackInterface;
import com.lc.view.LCGridView.TouchForShineCallBackInterface;
import com.lc.view.LCGridView.TouchMoveCallBackInterface;
import com.lc.view.LCGridView.TouchUpCallBackInterface;
import com.lc.view.LCGroupView;
import com.lc.view.LCLightView;
import com.lc.view.LCGroupView.OnGroupItemClickListen;
import com.lc.view.LCLightView.ILightItemLongClickListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 编组模块
 * 
 * @author LC work room
 * 
 */
public class GroupSettingFragment extends LCFragment implements
		TouchMoveCallBackInterface, TouchUpCallBackInterface,
		DoubleClickCallBackInterface, IDeleteCallbackInterface {
	// touch开头的接口是lightview用的
	private Context mContext;
	private LCLightView mLightView;
	private LCGroupView mGroupView;

	private GroupItem curGroupItem; // 当前操作中的组
	private int curFocusGroup = -1; // 移动灯过程中当前聚焦的组位置
	private int lastFocusGroup = -1; // 上一次移动灯过程中聚焦的组位置，与curFocusGroup对比，不同则重画
	private int deletePosition = -1; // 双击操作删除时，双击的列表项位置
	private DeleteDialog mDeleteDialog; // 删除对话框，唯一
	private List<LightItem> mLightData;
	private boolean showingChildItems = false; // 标识可以删除，当点击某group项时设置为true，表示可以对其灯成员进行删除操作

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStop() {
		super.onStop();
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@SuppressLint("InflateParams")
	@Override
	protected View bindView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		View rootView = inflater.inflate(
				R.layout.v1_lc_setting_group_fragment_layout, null);
		LinearLayout llLight = (LinearLayout) rootView
				.findViewById(R.id.v1_setting_group_light);
		LinearLayout llGroup = (LinearLayout) rootView
				.findViewById(R.id.v1_setting_group_group);
		mLightView = new LCLightView(mContext);
		mLightView.setCanMoveChild(true);

		mGroupView = new LCGroupView(mContext, false);
		mGroupView.setCanMoveChild(false);
		llLight.addView(mLightView.getRootView());
		llGroup.addView(mGroupView.getRootView());
		initData();
		initEvent();
		return rootView;
	}

	private void initEvent() {
		mLightView.setUpCallbck(this);
		mLightView.setMoveCallback(this);
		mGroupView.setOnGroupItemClickListen(new OnGroupItemClickListen() {

			@Override
			public void OnGroupItemClick(int position, GroupItem groupitem) {
				GroupItem mgroupitem = mGroupView.getData().get(position);
				if (mGroupView.getFocusPosition() == position) {
					LogUtils.i(GroupSettingFragment.class, "child size:"
							+ mgroupitem.getItemChildData());
					mLightView.changeData(mgroupitem.getItemChildData());
					mLightView.setCanMoveChild(false);
					curGroupItem = mgroupitem;
					showingChildItems = true;
				} else {
					mLightView.changeData(mLightData);
					mLightView.setCanMoveChild(true);
					curGroupItem = null;
					showingChildItems = false;
				}
			}
		});
		mLightView.setDoubleClickCallback(new DoubleClickCallBackInterface() {

			@Override
			public void doubleClickCallback(int x, int y, int position) {
				// TODO Auto-generated method stub
				if (!showingChildItems) {
					if (mLightView.getData().size() == mLightData.size()) {
						LightItem mItem = (LightItem) mLightView.getData().get(
								position);
						if (mSendDataListen != null) {
							DataConbine mConbine = new DataConbine(
									DataConfig.LIGHT_LONG_CLICK);
							mConbine.addByte(DataUtils.getByte(mItem
									.getItemId()));
							mConbine.addByte(DataUtils
									.getByte(((LightItem) mLightView.getData()
											.get(position)).getItemId()));
							mSendDataListen.sendData(mConbine.getTotalData(),
									mItem);
						}
					}
				} else {
					if (showingChildItems) {
						if (mDeleteDialog == null) {
							mDeleteDialog = new DeleteDialog(mContext);
							mDeleteDialog
									.setDeleteCallback(GroupSettingFragment.this);
						}
						mDeleteDialog.show();
						deletePosition = position;
					}
				}
			}
		});

		// mLightView.setShineCallback(new TouchForShineCallBackInterface() {
		//
		// @Override
		// public void shineCallback(int x, int y, int position) {
		// Toast.makeText(mContext, "3s", Toast.LENGTH_SHORT).show();
		// }
		// });
	}

	/**
	 * 初始化数据，读取内存中灯和组的bean列表，转化为item列表后设置到对应的view中
	 */
	private void initData() {
		List<MemberBean> shadowMembers = LCConfig.getMembers();
		List<GroupBean> list = LCConfig.getGroups();
		List<GroupItem> mGroupData = DataUtils.coverGroupBeansToGroupItems(
				mContext, list);
		mLightData = DataUtils.coverMembersToLights(mContext, shadowMembers);
		mLightView.setColumnsNum(2);
		mLightView.changeData(mLightData);
		mGroupView.changeData(mGroupData);
	}

	// 删除的可以在双击接口中调用，双击接口是DoubleClickCallbackInterface，要就加入implements中
	private void deleteMemberFromGroup(int position) {
		if (showingChildItems && position >= 0 && curGroupItem != null) {
			int lightID = ((LightItem) curGroupItem.getItemChildData().get(
					position)).getItemId();
			LightItem lightItem = (LightItem) curGroupItem.getItemChildData()
					.get(position);
			lightItem.setId(lightItem.getItemId());
			curGroupItem.getItemChildData().remove(position);
			mLightView.changeData(curGroupItem.getChildData());
			if (mSendDataListen != null) {
				DataConbine mConbine = new DataConbine(
						DataConfig.GROUP_REMOVE_LIGHT);
				mConbine.addByte(DataUtils.getByte(curGroupItem.getItemId()));
				mConbine.addByte(DataUtils.getByte(lightID));
				mSendDataListen.sendData(mConbine.getTotalData(), lightItem);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void upCallback(int x, int y, int position) {
		int pos = mGroupView.isContains(x, y);
		if (pos >= 0) {
			curFocusGroup = pos;
			mGroupView.notifyItemToFocus(-1);
			GroupItem mitem = mGroupView.getData().get(curFocusGroup);
			if (mitem.isContainObject((LightItem) mLightView.getData().get(
					position))) {
				return;
			} else {
				if (mitem.getChildData().size() < 8) {
					mitem.getChildData().add(
							(LightItem) mLightView.getData().get(position));
					if (mSendDataListen != null) {
						DataConbine mConbine = new DataConbine(
								DataConfig.GROUP_ADD_LIGHT);
						mConbine.addByte(DataUtils.getByte(mitem.getItemId()));
						mConbine.addByte(DataUtils
								.getByte(((LightItem) mLightView.getData().get(
										position)).getItemId()));
						mSendDataListen.sendData(mConbine.getTotalData(),
								(LightItem) mLightView.getData().get(position));
					}
				}
			}
		} else {
			curFocusGroup = -1;
		}
	}

	@Override
	public void moveCallback(int x, int y, int position) {
		int pos = mGroupView.isContains(x, y);
		if (pos >= 0 && pos != lastFocusGroup) {
			curFocusGroup = pos;
			lastFocusGroup = curFocusGroup;
			mGroupView.notifyItemToFocus(pos);
		}
	}

	/**
	 * 双击组中的灯成员弹出删除对话框
	 */
	@Override
	public void doubleClickCallback(int x, int y, int position) {
		if (showingChildItems) {
			if (mDeleteDialog == null) {
				mDeleteDialog = new DeleteDialog(mContext);
				mDeleteDialog.setDeleteCallback(this);
			}
			mDeleteDialog.show();
			deletePosition = position;
		}
	}

	/**
	 * 删除对话框点击确定时回调
	 */
	@Override
	public void deleteCallback() {
		deleteMemberFromGroup(deletePosition);
		deletePosition = -1;
	}

}
