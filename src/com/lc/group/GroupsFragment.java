package com.lc.group;

import java.util.List;

import com.lc.LCConfig;
import com.lc.LCFragment;
import com.lc.R;
import com.lc.common.DataUtils;
import com.lc.common.bean.LCBaseBean;
import com.lc.data.DataConbine;
import com.lc.data.DataConfig;
import com.lc.data.GroupItem;
import com.lc.group.bean.GroupBean;
import com.lc.view.LCColorViewEx;
import com.lc.view.LCColorViewEx.OnColorPickListen;
import com.lc.view.LCGridView.DoubleClickCallBackInterface;
import com.lc.view.LCGroupView;
import com.lc.view.LCGroupView.OnGroupItemLongClickListen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;

/**
 * 组模块
 * 
 * @author LC work room
 * 
 */
public class GroupsFragment extends LCFragment implements OnColorPickListen {

	private Context mContext;
	private LCGroupView mGroupView;
	private int Light = 0xff;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@SuppressLint("InflateParams")
	@Override
	protected View bindView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		View rootView = inflater.inflate(R.layout.v1_lc_group_fragment_layout,
				null);
		LinearLayout llGroup = (LinearLayout) rootView
				.findViewById(R.id.v1_groups_layout);
		RelativeLayout rlColorPick = (RelativeLayout) rootView
				.findViewById(R.id.v1_group_color_pick);
		mGroupView = new LCGroupView(mContext, false);
		LCColorViewEx mColorViewEx = new LCColorViewEx(mContext);
		mColorViewEx.setOnColorPickListen(this);
		llGroup.addView(mGroupView.getRootView());
		rlColorPick.addView(mColorViewEx.getRootView());
		initEvent();
		initData();
		return rootView;
	}

	/**
	 * 初始化数据，读取内存bean列表，转化为item列表，设置到view中
	 */
	private void initData() {
		List<GroupBean> shadowList = LCConfig.getGroups();
		List<GroupItem> groupItems = DataUtils.coverGroupBeansToGroupItems(
				mContext, shadowList);
		mGroupView.changeData(groupItems);
	}

	private void initEvent() {
		mGroupView.setDoubleClickCallback(new DoubleClickCallBackInterface() {

			@Override
			public void doubleClickCallback(int x, int y, int position) {
				if (mSendDataListen != null) {
					DataConbine mConbine = new DataConbine(
							DataConfig.GROUP_LONG_CLICK);
					mConbine.addByte(DataUtils.getByte(position));
					int red = Color.red(mGroupView.getData().get(position)
							.getItemColor());
					mConbine.addByte(DataUtils.getByte(red));
					int green = Color.green(mGroupView.getData().get(position)
							.getItemColor());
					mConbine.addByte(DataUtils.getByte(green));
					int blue = Color.blue(mGroupView.getData().get(position)
							.getItemColor());
					mConbine.addByte(DataUtils.getByte(blue));
					LCBaseBean mBaseBean = mGroupView.getData().get(position);
					mSendDataListen.sendData(mConbine.getTotalData(), mBaseBean);
				}
			}
		});
	}

	@Override
	public void OnColorPickTouchMove(int color) {
		if (mGroupView.changeColor(color)) {
			if (mGroupView.getFocusPosition() != -1) {
				if (mSendDataListen != null) {
					DataConbine mConbine = new DataConbine(
							DataConfig.GROUP_COLOR_CHANGE);
					mConbine.addByte(DataUtils.getByte(mGroupView
							.getFocusPosition()));
					int red = Color.red(color);
					int redl = red * Light / 255;
					mConbine.addByte(DataUtils.getByte(redl));
					int green = Color.green(color);
					int greenl = green * Light / 255;
					mConbine.addByte(DataUtils.getByte(greenl));
					int blue = Color.blue(color);
					int bluel = blue * Light / 255;
					mConbine.addByte(DataUtils.getByte(bluel));
					LCBaseBean mBaseBean = mGroupView.getData().get(
							mGroupView.getFocusPosition());
					mSendDataListen
							.sendData(mConbine.getTotalData(), mBaseBean);
				}
			}
		}
	}

	@Override
	public void OnColorPickTouchUp(int color) {
		if (mGroupView.changeColor(color)) {
			if (mGroupView.getFocusPosition() != -1) {
				if (mSendDataListen != null) {
					DataConbine mConbine = new DataConbine(
							DataConfig.GROUP_COLOR_CHANGE);
					mConbine.addByte(DataUtils.getByte(mGroupView
							.getFocusPosition()));
					int red = Color.red(color);
					int redl = red * Light / 255;
					mConbine.addByte(DataUtils.getByte(redl));
					int green = Color.green(color);
					int greenl = green * Light / 255;
					mConbine.addByte(DataUtils.getByte(greenl));
					int blue = Color.blue(color);
					int bluel = blue * Light / 255;
					mConbine.addByte(DataUtils.getByte(bluel));
					LCBaseBean mBaseBean = mGroupView.getData().get(
							mGroupView.getFocusPosition());
					mSendDataListen
							.sendData(mConbine.getTotalData(), mBaseBean);
				}
			}
		}
	}

	@Override
	public void OnSeekBarUpdate(int progress) {
		if (mGroupView != null && mGroupView.getFocusPosition() != -1) {
			if (mSendDataListen != null) {
				DataConbine mConbine = new DataConbine(DataConfig.LIGHT_RIGHT);
				mConbine.addByte(DataUtils.getByte(mGroupView
						.getFocusPosition()));
				mConbine.addByte(DataUtils.getByte(progress));
				mSendDataListen.sendData(mConbine.getTotalData(), null);
			}
		}
	}

	@Override
	public void OnSeekBarUpdate2(int progress) {
		// TODO Auto-generated method stub
		if (mGroupView != null && mGroupView.getFocusPosition() != -1) {
			Light = progress;
			if (mSendDataListen != null) {
				DataConbine mConbine = new DataConbine(
						DataConfig.GROUP_COLOR_CHANGE);
				mConbine.addByte(DataUtils.getByte(mGroupView
						.getFocusPosition()));
				GroupItem mfocusItem = mGroupView.getData().get(
						mGroupView.getFocusPosition());
				int color = Color.parseColor("#ffffff");
				if (mfocusItem != null) {
					color = mfocusItem.getItemColor();
				}
				int red = Color.red(color);
				int redl = red * progress / 255;
				mConbine.addByte(DataUtils.getByte(redl));
				int green = Color.green(color);
				int greenl = green * progress / 255;
				mConbine.addByte(DataUtils.getByte(greenl));
				int blue = Color.blue(color);
				int bluel = blue * progress / 255;
				mConbine.addByte(DataUtils.getByte(bluel));
				LCBaseBean mBaseBean = mGroupView.getData().get(
						mGroupView.getFocusPosition());
				mSendDataListen.sendData(mConbine.getTotalData(), mBaseBean);
			}
		}
	}

	@Override
	public void OnColorCardClick(int position, int color) {
		if (mGroupView.changeColor(color)) {
			if (mGroupView.getFocusPosition() != -1) {
				if (mSendDataListen != null) {
					DataConbine mConbine = new DataConbine(
							DataConfig.GROUP_COLOR_CHANGE);
					mConbine.addByte(DataUtils.getByte(mGroupView
							.getFocusPosition()));
					int red = Color.red(color);
					mConbine.addByte(DataUtils.getByte(red));
					int green = Color.green(color);
					mConbine.addByte(DataUtils.getByte(green));
					int blue = Color.blue(color);
					mConbine.addByte(DataUtils.getByte(blue));
					LCBaseBean mBaseBean = mGroupView.getData().get(
							mGroupView.getFocusPosition());
					mSendDataListen
							.sendData(mConbine.getTotalData(), mBaseBean);
				}
			}
		}
	}

}
