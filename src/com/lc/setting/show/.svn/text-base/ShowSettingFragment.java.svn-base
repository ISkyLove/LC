package com.lc.setting.show;

import java.util.ArrayList;
import java.util.List;

import com.lc.LCConfig;
import com.lc.LCFragment;
import com.lc.R;
import com.lc.common.DataUtils;
import com.lc.data.DataConbine;
import com.lc.data.DataConfig;
import com.lc.data.GroupItem;
import com.lc.data.SenceItem;
import com.lc.data.ShowItem;
import com.lc.dialog.DeleteDialog;
import com.lc.dialog.SetTimeDialog;
import com.lc.dialog.DeleteDialog.IDeleteCallbackInterface;
import com.lc.dialog.SetTimeDialog.TimerGetterInterface;
import com.lc.sence.bean.SenceBean;
import com.lc.show.bean.ShowBean;
import com.lc.view.LCGridView;
import com.lc.view.LCGridView.DoubleClickCallBackInterface;
import com.lc.view.LCGridView.TouchMoveCallBackInterface;
import com.lc.view.LCGridView.TouchUpCallBackInterface;
import com.lc.view.LCSenceView;
import com.lc.view.LCShowView;
import com.lc.view.LCShowView.OnShowItemClickListen;
import com.lc.view.adapter.ShowPannerAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * show编组模块
 * 
 * @author LC work room
 * 
 */
public class ShowSettingFragment extends LCFragment {

	private Context mContext;
	private TextView tvName;
	private TextView tvPlay;
	private TextView tvStop;
	private RelativeLayout rlShowList;

	private RelativeLayout rlSenceList;
	private LCShowView mShowView;
	private LCSenceView mSenceView;

	private View rootView = null;

	private List<SenceItem> mSenceItems;
	private List<SenceBean> mSenceBeans;

	private ShowItem curShowItem = null;

	private LinearLayout llPane;

	private LCGridView mChildPane1;
	private LCGridView mChildPane2;
	private LCGridView mChildPane3;

	private TextView tvPane1;
	private TextView tvPane2;
	private TextView tvPane3;

	private ShowPannerAdapter mChildPaneAdaper1;
	private ShowPannerAdapter mChildPaneAdaper2;
	private ShowPannerAdapter mChildPaneAdaper3;

	private List<SenceItem> mChildPaneData1;
	private List<SenceItem> mChildPaneData2;
	private List<SenceItem> mChildPaneData3;

	private ImageView ivPaneAdd1;
	private ImageView ivPaneAdd2;

	private int curPannerFocus = -1;
	private int lastPannerFocus = -1;

	private SetTimeDialog setTimeDialog1;
	private SetTimeDialog setTimeDialog2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected View bindView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(
					R.layout.v1_lc_setting_show_fragment_layout, null);
		}
		tvName = (TextView) rootView.findViewById(R.id.v1_lc_setting_show_name);
		tvPlay = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_show_return);
		tvStop = (TextView) rootView.findViewById(R.id.v1_lc_setting_show_stop);
		rlShowList = (RelativeLayout) rootView
				.findViewById(R.id.v1_lc_setting_show_show_list);

		rlSenceList = (RelativeLayout) rootView
				.findViewById(R.id.v1_lc_setting_show_sence_list);

		llPane = (LinearLayout) rootView
				.findViewById(R.id.v1_lc_setting_show_pane);
		mChildPane1 = (LCGridView) rootView
				.findViewById(R.id.v1_lc_setting_show_pane_1);
		mChildPane2 = (LCGridView) rootView
				.findViewById(R.id.v1_lc_setting_show_pane_2);
		mChildPane3 = (LCGridView) rootView
				.findViewById(R.id.v1_lc_setting_show_pane_3);

		tvPane1 = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_show_pane_text1);
		tvPane2 = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_show_pane_text2);
		tvPane3 = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_show_pane_text3);

		ivPaneAdd1 = (ImageView) rootView
				.findViewById(R.id.v1_lc_setting_show_pane_image_1);
		ivPaneAdd2 = (ImageView) rootView
				.findViewById(R.id.v1_lc_setting_show_pane_image_2);

		mShowView = new LCShowView(mContext);
		mShowView.setColumnsNum(1);
		mSenceView = new LCSenceView(mContext, true);
		mSenceView.setColumnsNum(1);
		mSenceView.setCanMoveChild(true);
		rlShowList.addView(mShowView.getRootView());
		rlSenceList.addView(mSenceView.getRootView());

		iniPane();
		initData();
		initEvents();

		return rootView;
	}

	private void iniPane() {
		mChildPaneData1 = new ArrayList<SenceItem>();
		mChildPaneData2 = new ArrayList<SenceItem>();
		mChildPaneData3 = new ArrayList<SenceItem>();

		mChildPaneAdaper1 = new ShowPannerAdapter(mContext, mChildPaneData1);
		mChildPaneAdaper2 = new ShowPannerAdapter(mContext, mChildPaneData2);
		mChildPaneAdaper3 = new ShowPannerAdapter(mContext, mChildPaneData3);

		mChildPane1.setNumColumns(1);
		mChildPane2.setNumColumns(1);
		mChildPane3.setNumColumns(1);
		mChildPane1.setAdapter(mChildPaneAdaper1);
		mChildPane2.setAdapter(mChildPaneAdaper2);
		mChildPane3.setAdapter(mChildPaneAdaper3);
	}

	private void initData() {
		mSenceBeans = LCConfig.getSences();
		mSenceItems = DataUtils.coverSenceBeansToSenceItems(mContext,
				mSenceBeans);
		List<ShowBean> list = LCConfig.getShows();
		List<ShowItem> mShowData = DataUtils.coverShowBeansToShowItems(
				mContext, list);
		mShowView.changeData(mShowData);
		mSenceView.changeData(mSenceItems);
		curShowItem = mShowData.get(0);
		initPaneData(curShowItem);
	}

	/**
	 * 初始化pane容器的数据
	 * 
	 * @param item
	 */
	@SuppressWarnings("unchecked")
	private void initPaneData(ShowItem item) {
		tvName.setText(item.getItemName());
		List<SenceItem> msencedata = item.getChildData();
		mChildPaneData1.clear();
		mChildPaneData2.clear();
		mChildPaneData3.clear();
		mChildPaneAdaper1.notifyDataSetChanged();
		mChildPaneAdaper2.notifyDataSetChanged();
		mChildPaneAdaper3.notifyDataSetChanged();
		if (msencedata != null) {

			if (msencedata.size() > 0 && msencedata.get(0) != null) {
				mChildPaneData1.add(msencedata.get(0));
				mChildPaneAdaper1.notifyDataSetChanged();
			}
			if (msencedata.size() > 1 && msencedata.get(1) != null) {
				mChildPaneData2.add(msencedata.get(1));
				mChildPaneAdaper2.notifyDataSetChanged();
			}
			if (msencedata.size() > 2 && msencedata.get(2) != null) {
				mChildPaneData3.add(msencedata.get(2));
				mChildPaneAdaper3.notifyDataSetChanged();
			}
		}

	}

	private boolean isContainItem(List<SenceItem> list, SenceItem childItem) {
		for (SenceItem sourceItem : list) {
			if (sourceItem.getItemId() == childItem.getItemId()) {
				childItem.setSelect(true);
				return true;
			}
		}
		return false;
	}

	private void initEvents() {
		mSenceView.setMoveCallback(new TouchMoveCallBackInterface() {

			@Override
			public void moveCallback(int x, int y, int position) {
				if (DataUtils.isContains(x, y, mChildPane1)) {
					if (lastPannerFocus != 0) {
						setPannerToFoces(0);
						curPannerFocus = 0;
						lastPannerFocus = 0;
					}
				} else if (DataUtils.isContains(x, y, mChildPane2)) {
					if (lastPannerFocus != 1) {
						setPannerToFoces(1);
						curPannerFocus = 1;
						lastPannerFocus = 1;
					}
				} else if (DataUtils.isContains(x, y, mChildPane3)) {
					if (lastPannerFocus != 2) {
						setPannerToFoces(2);
						curPannerFocus = 2;
						lastPannerFocus = 2;
					}
				} else {
					if (lastPannerFocus != -1) {
						setPannerToFoces(-1);
						curPannerFocus = -1;
						lastPannerFocus = -1;
					}
				}
			}
		});

		mSenceView.setUpCallbck(new TouchUpCallBackInterface() {

			@Override
			public void upCallback(int x, int y, int position) {
				switch (curPannerFocus) {
				case 0:
					if (DataUtils.isContains(x, y, mChildPane1)) {
						SenceItem mSenceItem1 = mSenceView.getData().get(
								position);
						if (isContainItem(mChildPaneData1, mSenceItem1)) {
							return;
						} else {
							mSenceItem1.setSelect(true);
							addSenceItemToPanner(mSenceItem1, mChildPaneData1,
									mChildPaneAdaper1, 0);
						}
					}
					break;
				case 1:
					if (DataUtils.isContains(x, y, mChildPane2)) {
						if (mChildPaneData1.size() > 0) {
							SenceItem mSenceItem2 = mSenceView.getData().get(
									position);
							if (isContainItem(mChildPaneData2, mSenceItem2)) {
								return;
							} else {
								mSenceItem2.setSelect(true);
								addSenceItemToPanner(mSenceItem2,
										mChildPaneData2, mChildPaneAdaper2, 1);
							}
						} else {
							Toast.makeText(mContext, "容器1不能为空,请先向添加容器1添加场景。",
									Toast.LENGTH_SHORT).show();
						}
					}
					break;
				case 2:
					if (DataUtils.isContains(x, y, mChildPane3)) {
						if (mChildPaneData1.size() > 0
								&& mChildPaneData2.size() > 0) {
							SenceItem mSenceItem3 = mSenceView.getData().get(
									position);
							if (isContainItem(mChildPaneData3, mSenceItem3)) {
								return;
							} else {
								mSenceItem3.setSelect(true);
								addSenceItemToPanner(mSenceItem3,
										mChildPaneData3, mChildPaneAdaper3, 2);
							}
						} else {
							Toast.makeText(mContext,
									"容器1,2不能为空,请先向添加容器1,2添加场景。",
									Toast.LENGTH_SHORT).show();
						}
					}
					break;
				default:
					break;
				}
			}
		});

		mShowView.setOnShowItemClickListen(new OnShowItemClickListen() {

			@Override
			public void OnShowItemClick(int position, ShowItem showitem) {
				curShowItem = mShowView.getData().get(position);
				initPaneData(curShowItem);
				mSenceView.notifyAllItemToNoSelect();
				mSenceView.showTargetToFocus(curShowItem.getChildData(), true);
			}
		});
		mChildPane1.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (curPannerFocus != 0) {
						curPannerFocus = 0;
						setPannerToFoces(0);
					} else {
						curPannerFocus = -1;
						setPannerToFoces(-1);
					}
					break;
				default:
					break;
				}

				return false;
			}
		});

		mChildPane1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				curPannerFocus = 0;
				setPannerToFoces(0);

			}
		});
		mChildPane2.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (curPannerFocus != 1) {
						curPannerFocus = 1;
						setPannerToFoces(1);
					} else {
						curPannerFocus = -1;
						setPannerToFoces(-1);
					}
					break;
				default:
					break;
				}

				return false;
			}
		});

		mChildPane2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				curPannerFocus = 1;
				setPannerToFoces(1);

			}
		});
		mChildPane3.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (curPannerFocus != 2) {
						curPannerFocus = 2;
						setPannerToFoces(2);
					} else {
						curPannerFocus = -1;
						setPannerToFoces(-1);
					}
					break;
				default:
					break;
				}

				return false;
			}
		});

		mChildPane3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				curPannerFocus = 2;
				setPannerToFoces(2);

			}
		});
		
		mChildPane3.setDoubleClickCallBack(new DoubleClickCallBackInterface() {

			@Override
			public void doubleClickCallback(int x, int y, int position) {
					showDeleteDialog(2, position);
			}
		});

		mChildPane1.setDoubleClickCallBack(new DoubleClickCallBackInterface() {

			@Override
			public void doubleClickCallback(int x, int y, int position) {
				if (mChildPaneData2.size() == 0 && mChildPaneData3.size() == 0) {
					showDeleteDialog(0, position);
				} else {
					Toast.makeText(mContext, "请先删除容器2、3的场景！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		mChildPane2.setDoubleClickCallBack(new DoubleClickCallBackInterface() {

			@Override
			public void doubleClickCallback(int x, int y, int position) {
				if (mChildPaneData3.size() == 0) {
					showDeleteDialog(1, position);
				} else {
					Toast.makeText(mContext, "请先删除容器3的场景！", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		ivPaneAdd1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (setTimeDialog1 == null) {
					setTimeDialog1 = new SetTimeDialog(mContext);
					setTimeDialog1.setTimerGetter(new TimerGetterInterface() {

						@Override
						public void onGetTime(int time) {
							sendShowTime(time);
						}
					});
				}

				setTimeDialog1.showDialog();
			}
		});
		ivPaneAdd2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (setTimeDialog2 == null) {
					setTimeDialog2 = new SetTimeDialog(mContext);
					setTimeDialog2.setTimerGetter(new TimerGetterInterface() {

						@Override
						public void onGetTime(int time) {
							sendShowTime(time);
						}
					});
				}

				setTimeDialog2.showDialog();
			}
		});

		tvPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mSendDataListen != null) {
					if (mSendDataListen != null) {
						DataConbine mConbine = new DataConbine(
								DataConfig.SHOW_RUNNING);
						mConbine.addByte(DataUtils.getByte(curShowItem
								.getItemId()));
						mSendDataListen.sendData(mConbine.getTotalData(),
								curShowItem);
					}
				}
			}
		});

		tvStop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mSendDataListen != null) {
					if (mSendDataListen != null) {
						DataConbine mConbine = new DataConbine(
								DataConfig.SHOW_STOPPING);
						mConbine.addByte(DataUtils.getByte(curShowItem
								.getItemId()));
						mSendDataListen.sendData(mConbine.getTotalData(),
								curShowItem);
					}
				}
			}
		});

	}

	/**
	 * 添加场景的item进指定容器
	 * 
	 * @param item
	 * @param adapter
	 * @param pannerNum
	 */
	@SuppressWarnings("unchecked")
	private void addSenceItemToPanner(SenceItem item, List<SenceItem> data,
			ShowPannerAdapter adapter, int pannerNum) {
		if (data.size() > 0) {
			mSenceView.notifyItemToNoSelect(data.get(0));
			data.clear();
		}
		data.add(item);
		adapter.notifyDataSetChanged();
		setPannerToFoces(pannerNum);
		mSenceView.showTargetToFocus(getPanAllSenceItem(), true);
		curShowItem.changeChildData(getPanAllSenceItem());
		if (mSendDataListen != null) {
			if (mSendDataListen != null) {
				DataConbine mConbine = new DataConbine(
						DataConfig.SHOW_PANE_ADD_SENCE);
				mConbine.addByte(DataUtils.getByte(curShowItem.getItemId()));
				for (SenceItem mItem : (List<SenceItem>) curShowItem
						.getChildData()) {
					mConbine.addByte(DataUtils.getByte(mItem.getItemId()));
				}
				mSendDataListen.sendData(mConbine.getTotalData(), curShowItem);
			}
		}
	}

	/**
	 * 获取pane容器所有的场景
	 * 
	 * @return
	 */
	private List<SenceItem> getPanAllSenceItem() {
		List<SenceItem> paneitems = new ArrayList<SenceItem>();
		paneitems.addAll(mChildPaneData1);
		paneitems.addAll(mChildPaneData2);
		paneitems.addAll(mChildPaneData3);
		return paneitems;
	}

	/**
	 * 使panner显示foces样式
	 * 
	 * @param position
	 */
	private void setPannerToFoces(int position) {
		initPaneTextColor();
		switch (position) {
		case 0:
			tvPane1.setTextColor(getResources().getColor(R.color.yellow));
			mChildPane1
					.setBackgroundResource(R.drawable.v1_base_btn_yellow__unpress);
			break;
		case 1:
			tvPane2.setTextColor(getResources().getColor(R.color.yellow));
			mChildPane2
					.setBackgroundResource(R.drawable.v1_base_btn_yellow__unpress);
			break;
		case 2:
			tvPane3.setTextColor(getResources().getColor(R.color.yellow));
			mChildPane3
					.setBackgroundResource(R.drawable.v1_base_btn_yellow__unpress);
			break;
		default:
			initPaneTextColor();
			break;
		}
	}

	/** 初始化所有panner处于非focus样式 **/
	private void initPaneTextColor() {
		tvPane1.setTextColor(getResources().getColor(R.color.white));
		tvPane2.setTextColor(getResources().getColor(R.color.white));
		tvPane3.setTextColor(getResources().getColor(R.color.white));
		mChildPane1
				.setBackgroundResource(R.drawable.v1_base_btn_white__unpress);
		mChildPane2
				.setBackgroundResource(R.drawable.v1_base_btn_white__unpress);
		mChildPane3
				.setBackgroundResource(R.drawable.v1_base_btn_white__unpress);
	}

	private void showDeleteDialog(final int pannerPos, final int itemPos) {
		DeleteDialog deleteDialog = new DeleteDialog(mContext);
		deleteDialog.setDeleteCallback(new IDeleteCallbackInterface() {

			@Override
			public void deleteCallback() {
				deleteItem(pannerPos, itemPos);
			}
		});
		deleteDialog.show();
	}

	@SuppressWarnings("unchecked")
	private void deleteItem(int pannerPos, int itemPos) {
		switch (pannerPos) {
		case 0:
			mChildPaneData1.remove(itemPos);
			mChildPaneAdaper1.notifyDataSetChanged();
			curShowItem.getItemChildData().set(0, null);
			break;
		case 1:
			mChildPaneData2.remove(itemPos);
			mChildPaneAdaper2.notifyDataSetChanged();
			curShowItem.getItemChildData().set(1, null);
			break;
		case 2:
			mChildPaneData3.remove(itemPos);
			mChildPaneAdaper3.notifyDataSetChanged();
			curShowItem.getItemChildData().set(2, null);
			break;
		default:
			break;
		}

		if (mSendDataListen != null) {
			if (mSendDataListen != null) {
				DataConbine mConbine = new DataConbine(
						DataConfig.SHOW_PANE_ADD_SENCE);
				mConbine.addByte(DataUtils.getByte(curShowItem.getItemId()));
				for (int i = 0; i < pannerPos; i++) {
					if (i < curShowItem.getChildData().size()) {
						mConbine.addByte(DataUtils
								.getByte(((SenceItem) curShowItem
										.getChildData().get(i)).getItemId()));
					}
				}
				mSendDataListen.sendData(mConbine.getTotalData(), curShowItem);
			}
		}

	}

	/**
	 * 发送时间设置命令
	 * 
	 * @param time
	 */
	private void sendShowTime(int time) {
		if (mSendDataListen != null) {
			DataConbine mConbine = new DataConbine(DataConfig.SHOW_TIME_SETTING);
			mConbine.addByte(DataUtils.getByte(curShowItem.getItemId()));
			mConbine.addByte(DataUtils.getByte(time));
			mSendDataListen.sendData(mConbine.getTotalData(), curShowItem);
		}
	}

}
