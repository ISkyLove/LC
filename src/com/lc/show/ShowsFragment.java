package com.lc.show;

import java.util.List;

import com.lc.LCConfig;
import com.lc.LCFragment;
import com.lc.R;
import com.lc.common.DataUtils;
import com.lc.data.DataConbine;
import com.lc.data.DataConfig;
import com.lc.data.ShowItem;
import com.lc.show.bean.ShowBean;
import com.lc.view.LCShowView;
import com.lc.view.LCShowView.OnShowItemClickListen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * show模块
 * 
 * @author LC work room
 * 
 */
public class ShowsFragment extends LCFragment {

	private Context mContext;
	private LCShowView mShowView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressLint("InflateParams") 
	@Override
	protected View bindView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		View rootView = inflater.inflate(R.layout.v1_lc_show_fragment_layout,
				null);
		LinearLayout llShow = (LinearLayout) rootView.findViewById(R.id.show_gv);
		mShowView = new LCShowView(mContext);
		llShow.addView(mShowView.getRootView());
		initEvent();
		initData();
		return rootView;
	}

	/**
	 * 初始化数据，读取内存show的bean列表，转为item列表，设置到view中
	 */
	private void initData() {
		List<ShowBean> list = LCConfig.getShows();
		List<ShowItem> showItem = DataUtils.coverShowBeansToShowItems(mContext, list);
		mShowView.changeData(showItem);
		mShowView.setColumnsNum(3);
	}

	private void initEvent() {
		mShowView.setOnShowItemClickListen(new OnShowItemClickListen() {

			@Override
			public void OnShowItemClick(int position, ShowItem showitem) {
				if (mShowView.getCurFocusPosition() == position) {
					if (mSendDataListen != null) {
						DataConbine mConbine = new DataConbine(
								DataConfig.SHOW_RUNNING);
						mConbine.addByte(DataUtils.getByte(showitem.getItemId()));
						mSendDataListen.sendData(mConbine.getTotalData(),
								showitem);
					}
				} else {
					if (mSendDataListen != null) {
						DataConbine mConbine = new DataConbine(
								DataConfig.SHOW_STOPPING);
						mConbine.addByte(DataUtils.getByte(showitem.getItemId()));
						mSendDataListen.sendData(mConbine.getTotalData(),
								showitem);
					}
				}
			}
		});
	}

}
