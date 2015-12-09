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
 * ����ģ��
 * 
 * @author LC work room
 * 
 */
public class GroupSettingFragment extends LCFragment implements
		TouchMoveCallBackInterface, TouchUpCallBackInterface,
		DoubleClickCallBackInterface, IDeleteCallbackInterface {
	// touch��ͷ�Ľӿ���lightview�õ�
	private Context mContext;
	private LCLightView mLightView;
	private LCGroupView mGroupView;

	private GroupItem curGroupItem; // ��ǰ�����е���
	private int curFocusGroup = -1; // �ƶ��ƹ����е�ǰ�۽�����λ��
	private int lastFocusGroup = -1; // ��һ���ƶ��ƹ����о۽�����λ�ã���curFocusGroup�Աȣ���ͬ���ػ�
	private int deletePosition = -1; // ˫������ɾ��ʱ��˫�����б���λ��
	private DeleteDialog mDeleteDialog; // ɾ���Ի���Ψһ
	private List<LightItem> mLightData;
	private boolean showingChildItems = false; // ��ʶ����ɾ���������ĳgroup��ʱ����Ϊtrue����ʾ���Զ���Ƴ�Ա����ɾ������

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
	 * ��ʼ�����ݣ���ȡ�ڴ��еƺ����bean�б�ת��Ϊitem�б�����õ���Ӧ��view��
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

	// ɾ���Ŀ�����˫���ӿ��е��ã�˫���ӿ���DoubleClickCallbackInterface��Ҫ�ͼ���implements��
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
	 * ˫�����еĵƳ�Ա����ɾ���Ի���
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
	 * ɾ���Ի�����ȷ��ʱ�ص�
	 */
	@Override
	public void deleteCallback() {
		deleteMemberFromGroup(deletePosition);
		deletePosition = -1;
	}

}
