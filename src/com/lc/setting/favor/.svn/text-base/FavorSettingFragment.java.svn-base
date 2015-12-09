package com.lc.setting.favor;

import java.util.List;

import com.lc.LCConfig;
import com.lc.LCFragment;
import com.lc.R;
import com.lc.common.DataUtils;
import com.lc.common.bean.MemberBean;
import com.lc.data.ChangeDataUtils;
import com.lc.data.GroupItem;
import com.lc.data.LightItem;
import com.lc.data.SenceItem;
import com.lc.data.ShowItem;
import com.lc.dialog.EditNameAndRemarkDialog;
import com.lc.dialog.EditNameAndRemarkDialog.IChangeNameAndRemarkInterface;
import com.lc.group.bean.GroupBean;
import com.lc.sence.bean.SenceBean;
import com.lc.show.bean.ShowBean;
import com.lc.view.LCGroupView;
import com.lc.view.LCGroupView.OnGroupItemClickListen;
import com.lc.view.LCLightView;
import com.lc.view.LCLightView.ILightItemClickListener;
import com.lc.view.LCSenceView;
import com.lc.view.LCSenceView.OnSenceItemClickListen;
import com.lc.view.LCShowView;
import com.lc.view.LCShowView.OnShowItemClickListen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 偏好模块
 * 
 * @author LC work room
 * 
 */
public class FavorSettingFragment extends LCFragment implements OnClickListener {
	
	private Context mContext;
	private RelativeLayout rlContext;		//显示列表的位置
	private LCLightView mLightView;			//灯列表view
	private LCGroupView mGroupView;			//组列表view
	private LCSenceView mSenceView;			//场景列表view
	private LCShowView mShowView;			//show列表view
	
	//对话框唯一
	private EditNameAndRemarkDialog lightDialog;	//灯名字和备注编辑对话框
	private EditNameAndRemarkDialog groupDialog;	//组名字和备注编辑对话框
	private EditNameAndRemarkDialog senceDialog;	//场景名字和备注编辑对话框
	private EditNameAndRemarkDialog showDialog;		//show名字和备注编辑对话框

	//列表数据
	private List<LightItem> mLightData;
	private List<GroupItem> mGroupData;
	private List<SenceItem> mSenceData;
	private List<ShowItem> mShowData;

	private static final int TYPE_LIGHT = 1;
	private static final int TYPE_GROUP = 2;
	private static final int TYPE_SENCE = 3;
	private static final int TYPE_SHOW = 4;
	private int cur_type;		//当前显示的列表类型

	private void initEvent() {

		mLightView.setmLightItemClickListener(new ILightItemClickListener() {

			@Override
			public void OnLightItemClick(final int position, LightItem lightitem) {
				if (lightDialog == null) {
					lightDialog = new EditNameAndRemarkDialog(mContext);
					lightDialog
							.setChangeNameAndRemark(new IChangeNameAndRemarkInterface() {

								@Override
								public void changeNameAndRemark(String name,
										String remark) {
									MemberBean curMemberBean = LCConfig
											.getMembers().get(position);
									curMemberBean.setName(name);
									curMemberBean.setRemark(remark);
									ChangeDataUtils.alterOrAddMember(
											curMemberBean, name);
									mLightData.get(position).setItemName(name);
									mLightData.get(position).setItemRemark(
											remark);
									mLightView.changeData(mLightData);
									lightDialog.dismiss();
								}
							});
				}
				lightDialog.show();
			}
		});

		mGroupView.setOnGroupItemClickListen(new OnGroupItemClickListen() {

			@Override
			public void OnGroupItemClick(final int position, GroupItem groupitem) {
				if (groupDialog == null) {
					groupDialog = new EditNameAndRemarkDialog(mContext);
					groupDialog
							.setChangeNameAndRemark(new IChangeNameAndRemarkInterface() {

								@Override
								public void changeNameAndRemark(String name,
										String remark) {
									GroupBean curGroupBean = LCConfig
											.getGroups().get(position);
									curGroupBean.setName(name);
									curGroupBean.setRemark(remark);
									ChangeDataUtils.alterOrAddGroup(
											curGroupBean, name);
									mGroupData.get(position).setItemName(name);
									mGroupData.get(position).setItemRemark(
											remark);
									mGroupView.changeData(mGroupData);
									groupDialog.dismiss();
								}
							});
				}
				groupDialog.show();
			}
		});

		mSenceView.setOnSenceItemClickListen(new OnSenceItemClickListen() {

			@Override
			public void OnSenceItemClick(final int position, SenceItem senceitem) {
				if (senceDialog == null) {
					senceDialog = new EditNameAndRemarkDialog(mContext);
					senceDialog
							.setChangeNameAndRemark(new IChangeNameAndRemarkInterface() {

								@Override
								public void changeNameAndRemark(String name,
										String remark) {
									SenceBean curSenceBean = LCConfig
											.getSences().get(position);
									curSenceBean.setName(name);
									curSenceBean.setRemark(remark);
									ChangeDataUtils.alterOrAddSence(
											curSenceBean, name);
									mSenceData.get(position).setItemName(name);
									mSenceData.get(position).setItemRemark(
											remark);
									mSenceView.changeData(mSenceData);
									senceDialog.dismiss();
								}
							});
				}
				senceDialog.show();
			}
		});

		mShowView.setOnShowItemClickListen(new OnShowItemClickListen() {

			@Override
			public void OnShowItemClick(final int position, ShowItem showitem) {
				if (showDialog == null) {
					showDialog = new EditNameAndRemarkDialog(mContext);
					showDialog
							.setChangeNameAndRemark(new IChangeNameAndRemarkInterface() {

								@Override
								public void changeNameAndRemark(String name,
										String remark) {
									ShowBean curShowBean = LCConfig.getShows()
											.get(position);
									curShowBean.setName(name);
									curShowBean.setRemark(remark);
									ChangeDataUtils.alterOrAddShow(curShowBean,
											name);
									mShowData.get(position).setItemName(name);
									mShowData.get(position).setItemRemark(
											remark);
									mShowView.changeData(mShowData);
									showDialog.dismiss();
								}
							});
				}
				showDialog.show();
			}
		});
	}

	private void ChangeContentView(int type) {
		rlContext.removeAllViews();
		switch (type) {
		case TYPE_LIGHT:
			rlContext.addView(mLightView.getRootView());
			break;
		case TYPE_GROUP:
			rlContext.addView(mGroupView.getRootView());
			break;
		case TYPE_SENCE:
			rlContext.addView(mSenceView.getRootView());
			break;
		case TYPE_SHOW:
			rlContext.addView(mShowView.getRootView());
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.v1_lc_setting_favor_lightname:
			cur_type = TYPE_LIGHT;
			ChangeContentView(cur_type);
			break;
		case R.id.v1_lc_setting_favor_groupname:
			cur_type = TYPE_GROUP;
			ChangeContentView(cur_type);
			break;
		case R.id.v1_lc_setting_favor_sencename:
			cur_type = TYPE_SENCE;
			ChangeContentView(cur_type);
			break;
		case R.id.v1_lc_setting_favor_showname:
			cur_type = TYPE_SHOW;
			ChangeContentView(cur_type);
			break;

		default:
			break;
		}
	};

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
		cur_type = TYPE_LIGHT;
		View rootView = inflater.inflate(
				R.layout.v1_lc_setting_favor_fragment_layout, null);
		TextView tvLight = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_favor_lightname);
		TextView tvGroup = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_favor_groupname);
		TextView tvSence = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_favor_sencename);
		TextView tvShow = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_favor_showname);

		tvLight.setOnClickListener(this);
		tvGroup.setOnClickListener(this);
		tvSence.setOnClickListener(this);
		tvShow.setOnClickListener(this);

		rlContext = (RelativeLayout) rootView
				.findViewById(R.id.v1_lc_setting_favor_content);
		mLightView = new LCLightView(mContext);
		mLightView.setColumnsNum(5);
		mGroupView = new LCGroupView(mContext, false);
		mGroupView.setColumnsNum(3);
		mSenceView = new LCSenceView(mContext, false);
		mSenceView.setColumnsNum(4);
		mShowView = new LCShowView(mContext);
		mShowView.setColumnsNum(3);
		ChangeContentView(cur_type);
		initEvent();
		initData();
		return rootView;
	}

	/**
	 * 初始化数据，读取内存全部bean列表，对每一个列表都转化为对应的item列表数据，设置到对应的view中
	 */
	private void initData() {
		List<MemberBean> list1 = LCConfig.getMembers();
		List<GroupBean> list2 = LCConfig.getGroups();
		List<SenceBean> list3 = LCConfig.getSences();
		List<ShowBean> list4 = LCConfig.getShows();

		mLightData = DataUtils.coverMembersToLights(mContext, list1);
		mGroupData = DataUtils.coverGroupBeansToGroupItems(mContext, list2);
		mSenceData = DataUtils.coverSenceBeansToSenceItems(mContext, list3);
		mShowData = DataUtils.coverShowBeansToShowItems(mContext, list4);
		mLightView.setColumnsNum(3);
		mGroupView.setColumnsNum(3);
		mSenceView.setColumnsNum(3);
		mShowView.setColumnsNum(3);
		mLightView.changeData(mLightData);
		mGroupView.changeData(mGroupData);
		mSenceView.changeData(mSenceData);
		mShowView.changeData(mShowData);
	}

}
