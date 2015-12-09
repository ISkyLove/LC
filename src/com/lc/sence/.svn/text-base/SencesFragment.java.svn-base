package com.lc.sence;

import java.util.List;

import com.lc.LCConfig;
import com.lc.LCFragment;
import com.lc.R;
import com.lc.common.DataUtils;
import com.lc.data.DataConbine;
import com.lc.data.DataConfig;
import com.lc.data.SenceItem;
import com.lc.sence.bean.SenceBean;
import com.lc.view.LCSenceView;
import com.lc.view.LCSenceView.OnSenceItemClickListen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
/**
 * 场景页面
 * 
 * @author LC work room
 *
 */
public class SencesFragment extends LCFragment {

	private Context mContext;
	private LCSenceView mSenceView;

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
		View rootView = inflater.inflate(R.layout.v1_lc_sence_fragment_layout,
					null);
		LinearLayout llSence = (LinearLayout) rootView.findViewById(R.id.sences_gv);
		mSenceView = new LCSenceView(mContext, false);
		llSence.addView(mSenceView.getRootView());
		initEvent();
		initData();
		return rootView;
	}

	/**
	 * 初始化数据，读取内存数据bean，转为item列表，设置到view中
	 */
	private void initData() {
		List<SenceBean> list = LCConfig.getSences();
		List<SenceItem> senceItems = DataUtils.coverSenceBeansToSenceItems(mContext, list);
		mSenceView.changeData(senceItems);
		mSenceView.setColumnsNum(3);
	}

	private void initEvent() {
		mSenceView.setOnSenceItemClickListen(new OnSenceItemClickListen() {

			@Override
			public void OnSenceItemClick(int position, SenceItem senceitem) {
				if (mSenceView.getCurFocusPosition() == position) {
					if (mSendDataListen != null) {
						DataConbine mConbine = new DataConbine(
								DataConfig.SENCE_RUNNING);
						mConbine.addByte(DataUtils.getByte(senceitem
								.getItemId()));
						mSendDataListen.sendData(mConbine.getTotalData(),
								senceitem);
					}
				} else {
					if (mSendDataListen != null) {
						DataConbine mConbine = new DataConbine(
								DataConfig.SENCE_STOPPING);
						mConbine.addByte(DataUtils.getByte(senceitem
								.getItemId()));
						mSendDataListen.sendData(mConbine.getTotalData(),
								senceitem);
					}
				}
			}
		});
	}
}
