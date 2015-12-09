package com.lc.setting.sence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lc.LCConfig;
import com.lc.LCFragment;
import com.lc.R;
import com.lc.common.DataUtils;
import com.lc.common.IItemPostionVisitor;
import com.lc.common.comparetor.GroupItemComparetor;
import com.lc.data.BaseItem.TYPE;
import com.lc.data.ChangeDataUtils;
import com.lc.data.DataConbine;
import com.lc.data.DataConfig;
import com.lc.data.GroupItem;
import com.lc.data.SenceItem;
import com.lc.dialog.DeleteDialog;
import com.lc.dialog.DeleteDialog.IDeleteCallbackInterface;
import com.lc.dialog.GroupListDialog;
import com.lc.dialog.SenceSelectorDialog;
import com.lc.dialog.SetTimeDialog;
import com.lc.dialog.SetTimeDialog.TimerGetterInterface;
import com.lc.group.bean.GroupBean;
import com.lc.view.LCColorViewEx;
import com.lc.view.LCColorViewEx.OnColorPickListen;
import com.lc.view.LCGridView;
import com.lc.view.LCGridView.DoubleClickCallBackInterface;
import com.lc.view.LCGridView.TouchMoveCallBackInterface;
import com.lc.view.LCGridView.TouchUpCallBackInterface;
import com.lc.view.LCGroupView;
import com.lc.view.LCPaneView;
import com.lc.view.adapter.GroupAdapter;
import com.lc.view.adapter.SencePannerAdapter;

/**
 * 场景编辑模块
 * 
 * @author LC work room
 * 
 */
public class SenceSettingFragment extends LCFragment implements
		TouchMoveCallBackInterface, TouchUpCallBackInterface,
		IItemPostionVisitor {

	private TextView tvName;
	private TextView tvPlay;
	private TextView tvStop;
	private TextView tvList;
	private LinearLayout llPane;
	private Context mContext;
	private LCPaneView mPaneView;
	private LCGroupView mGroupView;
	private LCColorViewEx mColorViewEx;
	private LinearLayout mPannerScrollView;
	private int light = 0xff;

	private LCGridView mChildPane1;
	private LCGridView mChildPane2;
	private LCGridView mChildPane3;
	private LCGridView mChildPane4;
	private LCGridView mChildPane5;

	private LCGridView mChildPane;

	private TextView tvPane1;
	private TextView tvPane2;
	private TextView tvPane3;
	private TextView tvPane4;
	private TextView tvPane5;

	FrameLayout flPane1;
	FrameLayout flPane2;
	FrameLayout flPane3;
	FrameLayout flPane4;
	FrameLayout flPane5;

	private SencePannerAdapter mChildPaneAdaper1;
	private SencePannerAdapter mChildPaneAdaper2;
	private SencePannerAdapter mChildPaneAdaper3;
	private SencePannerAdapter mChildPaneAdaper4;
	private SencePannerAdapter mChildPaneAdaper5;
	private SencePannerAdapter mChildPaneAdaper;

	private List<GroupItem> mChildPaneData1;
	private List<GroupItem> mChildPaneData2;
	private List<GroupItem> mChildPaneData3;
	private List<GroupItem> mChildPaneData4;
	private List<GroupItem> mChildPaneData5;

	private int[] pannColor;

	private ImageView ivPaneAdd1;
	private ImageView ivPaneAdd2;
	private ImageView ivPaneAdd3;
	private ImageView ivPaneAdd4;

	private int curPannerFocus = -1;
	private int lastPannerFocus = -1;

	private SenceSelectorDialog selectorDialog;

	private GroupListDialog groupListDialog;
	private HorizontalScrollView itemsShowingView;
	private GroupItemComparetor itemComparetor;

	private SenceItem curSenceItem;
	/**
	 * panner容器添加item后的界面更新问题，
	 * 估计setSelection以及notifyDataSetChanged都是对gripview进行重画
	 * ，为避免同步问题，setSelection在notifyDataSetChanged执行后一定时间才执行
	 */
	Handler mHandle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStop() {
		super.onStop();
	}

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
				R.layout.v1_lc_setting_sence_fragment_layout, null);
		tvName = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_sence_name);
		tvPlay = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_sence_return);
		tvStop = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_sence_stop);
		tvList = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_sence_list);
		llPane = (LinearLayout) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane);
		mChildPane1 = (LCGridView) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_1);
		mChildPane2 = (LCGridView) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_2);
		mChildPane3 = (LCGridView) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_3);
		mChildPane4 = (LCGridView) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_4);
		mChildPane5 = (LCGridView) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_5);

		flPane1 = (FrameLayout) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_fl_1);
		flPane2 = (FrameLayout) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_fl_2);
		flPane3 = (FrameLayout) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_fl_3);
		flPane4 = (FrameLayout) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_fl_4);
		flPane5 = (FrameLayout) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_fl_5);

		itemsShowingView = (HorizontalScrollView) rootView
				.findViewById(R.id.v1_setting_sence_items_hsv);

		mChildPane1.setVisibility(View.GONE);
		mChildPane2.setVisibility(View.GONE);
		mChildPane3.setVisibility(View.GONE);
		mChildPane4.setVisibility(View.GONE);
		mChildPane5.setVisibility(View.GONE);

		mChildPane = (LCGridView) rootView
				.findViewById(R.id.v1_setting_sence_panner_children_gv);

		tvPane1 = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_text1);
		tvPane2 = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_text2);
		tvPane3 = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_text3);
		tvPane4 = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_text4);
		tvPane5 = (TextView) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_text5);

		ivPaneAdd1 = (ImageView) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_image_1);
		ivPaneAdd2 = (ImageView) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_image_2);
		ivPaneAdd3 = (ImageView) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_image_3);
		ivPaneAdd4 = (ImageView) rootView
				.findViewById(R.id.v1_lc_setting_sence_pane_image_4);
		RelativeLayout rlGroups = (RelativeLayout) rootView
				.findViewById(R.id.v1_lc_setting_sence_groups);
		RelativeLayout rlColorPick = (RelativeLayout) rootView
				.findViewById(R.id.v1_lc_setting_sence_color_pick);
		mPannerScrollView = (LinearLayout) rootView
				.findViewById(R.id.v1_setting_sence_panner_scroll);
		mPaneView = new LCPaneView(mContext, -1);
		mGroupView = new LCGroupView(mContext, true);
		mGroupView.setShadowBmSize(new Point(180, 180));
		mColorViewEx = new LCColorViewEx(mContext, 0, 750);

		rlGroups.addView(mGroupView.getRootView());
		rlColorPick.addView(mColorViewEx.getRootView());

		pannColor = new int[] { Color.parseColor("#ffffff"),
				Color.parseColor("#ffffff"), Color.parseColor("#ffffff"),
				Color.parseColor("#ffffff"), Color.parseColor("#ffffff") };
		initData();
		iniPane();
		initEvent();

		/** panner容器添加数据后自动滑动到最新数据位置 **/
		mHandle = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					mChildPane1.setSelection(mChildPaneData1.size());
					break;
				case 1:
					mChildPane2.setSelection(mChildPaneData2.size());
					break;
				case 2:
					mChildPane3.setSelection(mChildPaneData3.size());
					break;
				case 3:
					mChildPane4.setSelection(mChildPaneData4.size());
					break;
				case 4:
					mChildPane5.setSelection(mChildPaneData5.size());
					break;

				default:
					break;
				}
				super.handleMessage(msg);
			}
		};
		return rootView;
	}

	private void iniPane() {
		mChildPaneData1 = new ArrayList<GroupItem>();
		mChildPaneData2 = new ArrayList<GroupItem>();
		mChildPaneData3 = new ArrayList<GroupItem>();
		mChildPaneData4 = new ArrayList<GroupItem>();
		mChildPaneData5 = new ArrayList<GroupItem>();
		mChildPaneAdaper1 = new SencePannerAdapter(mContext, mChildPaneData1);
		mChildPaneAdaper2 = new SencePannerAdapter(mContext, mChildPaneData2);
		mChildPaneAdaper3 = new SencePannerAdapter(mContext, mChildPaneData3);
		mChildPaneAdaper4 = new SencePannerAdapter(mContext, mChildPaneData4);
		mChildPaneAdaper5 = new SencePannerAdapter(mContext, mChildPaneData5);

		mChildPane1.setAdapter(mChildPaneAdaper1);
		mChildPane2.setAdapter(mChildPaneAdaper2);
		mChildPane3.setAdapter(mChildPaneAdaper3);
		mChildPane4.setAdapter(mChildPaneAdaper4);
		mChildPane5.setAdapter(mChildPaneAdaper5);

		mChildPaneAdaper = new SencePannerAdapter(mContext, null);
		mChildPane.setAdapter(mChildPaneAdaper);
		mChildPane.setDoubleClickCallBack(new DoubleClickCallBackInterface() {

			@Override
			public void doubleClickCallback(int x, int y, int position) {
				showDeleteDialog(curPannerFocus, position);
			}
		});

		iniPaneData(curSenceItem);
	}

	/**
	 * 根据给定的场景，初始化界面数据
	 * 
	 * @param senceItem
	 */
	private void iniPaneData(SenceItem senceItem) {
		curSenceItem = senceItem;
		if (curSenceItem != null) {
			mGroupView.notifyAllItemToNoSelect();
			mGroupView.notifyAllItemToWhite();
			tvName.setText(curSenceItem.getItemName());
			pannColor = curSenceItem.getPannColor();
			curPannerFocus = 0;
			mChildPaneData1 = curSenceItem.getChildPaneData1();
			mChildPane1.setNumColumns(1);
			mChildPaneAdaper1.changeData(mChildPaneData1);
			changePaneStrokeColor(pannColor[0]);

			curPannerFocus = 1;
			mChildPaneData2 = curSenceItem.getChildPaneData2();
			mChildPane2.setNumColumns(1);
			mChildPaneAdaper2.changeData(mChildPaneData2);
			changePaneStrokeColor(pannColor[1]);

			curPannerFocus = 2;
			mChildPaneData3 = curSenceItem.getChildPaneData3();
			mChildPane3.setNumColumns(1);
			mChildPaneAdaper3.changeData(mChildPaneData3);
			changePaneStrokeColor(pannColor[2]);

			curPannerFocus = 3;
			mChildPaneData4 = curSenceItem.getChildPaneData4();
			mChildPane4.setNumColumns(1);
			mChildPaneAdaper4.changeData(mChildPaneData4);
			changePaneStrokeColor(pannColor[3]);

			curPannerFocus = 4;
			mChildPaneData5 = curSenceItem.getChildPaneData5();
			mChildPane5.setNumColumns(1);
			mChildPaneAdaper5.changeData(mChildPaneData5);
			changePaneStrokeColor(pannColor[4]);

			curPannerFocus = -1;

		}
	}

	@SuppressLint("ClickableViewAccessibility")
	private void initEvent() {
		mGroupView.setCanMoveChild(true);
		mGroupView.setMoveCallback(this);
		mGroupView.setUpCallbck(this);
		mColorViewEx.setOnColorPickListen(new OnColorPickListen() {

			@Override
			public void OnSeekBarUpdate(int progress) {
				if (mSendDataListen != null) {
					if (curSenceItem != null && curPannerFocus != -1) {
						DataConbine mConbine = new DataConbine(
								DataConfig.SENCE_SETTING_RIGHT);
						mConbine.addByte(DataUtils.getByte(curSenceItem
								.getItemId()));
						mConbine.addByte(DataUtils.getByte(curPannerFocus));
						mConbine.addByte(DataUtils.getByte(progress));
						mSendDataListen.sendData(mConbine.getTotalData(), null);
					}
				}
			}

			@Override
			public void OnColorPickTouchUp(int color) {
				changePaneStrokeColor(color);
			}

			@Override
			public void OnColorPickTouchMove(int color) {
				changePaneStrokeColor(color);
			}

			@Override
			public void OnColorCardClick(int position, int color) {
				changePaneStrokeColor(color);
			}

			@Override
			public void OnSeekBarUpdate2(int progress) {
				// TODO Auto-generated method stub
				if (mSendDataListen != null) {
					if (curSenceItem != null && curPannerFocus != -1) {
						light = progress;
						DataConbine mConbine = new DataConbine(
								DataConfig.SENCE_PANE_COLOR_CHANGE);
						mConbine.addByte(DataUtils.getByte(curSenceItem
								.getItemId()));
						mConbine.addByte(DataUtils.getByte(curPannerFocus));
						int color = Color.parseColor("#ffffff");
						int[] pancolors = curSenceItem.getPannColor();
						if ((pancolors.length > curPannerFocus)
								&& (curPannerFocus > -1)) {
							color = pancolors[curPannerFocus];
						}
						int red = Color.red(color);
						int redl = red * light / 255;
						mConbine.addByte(DataUtils.getByte(redl));
						int green = Color.green(color);
						int greenl = green * light / 255;
						mConbine.addByte(DataUtils.getByte(greenl));
						int blue = Color.blue(color);
						int bluel = blue * light / 255;
						mConbine.addByte(DataUtils.getByte(bluel));
						mSendDataListen.sendData(mConbine.getTotalData(),
								curSenceItem);
					}
				}
			}
		});

		tvList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectorDialog = new SenceSelectorDialog(mContext);
				selectorDialog.setItemPostionVisitor(SenceSettingFragment.this);
				selectorDialog.show();
			}
		});
		ivPaneAdd1.setOnClickListener(mPannerAddClick);
		ivPaneAdd2.setOnClickListener(mPannerAddClick);
		ivPaneAdd3.setOnClickListener(mPannerAddClick);
		ivPaneAdd4.setOnClickListener(mPannerAddClick);

		flPane1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (curPannerFocus == 0) {
					itemsShowingView.setVisibility(View.GONE);
					curPannerFocus = -1;
					setPannerToFoces(-1);
				} else {
					curPannerFocus = 0;
					setPannerToFoces(0);
					itemsShowingView.setVisibility(View.VISIBLE);
					changeChildPane(mChildPaneData1);
				}
				// groupListDialog.chanData(mChildPaneData1);
				// groupListDialog.showDialog();
			}
		});

		flPane2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (curPannerFocus == 1) {
					itemsShowingView.setVisibility(View.GONE);
					curPannerFocus = -1;
					setPannerToFoces(-1);
				} else {
					curPannerFocus = 1;
					setPannerToFoces(1);
					changeChildPane(mChildPaneData2);
					itemsShowingView.setVisibility(View.VISIBLE);
				}
			}
		});

		flPane3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (curPannerFocus == 2) {
					itemsShowingView.setVisibility(View.GONE);
					curPannerFocus = -1;
					setPannerToFoces(-1);
				} else {
					curPannerFocus = 2;
					setPannerToFoces(2);
					changeChildPane(mChildPaneData3);
					itemsShowingView.setVisibility(View.VISIBLE);
				}
			}
		});

		flPane4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (curPannerFocus == 3) {
					itemsShowingView.setVisibility(View.GONE);
					curPannerFocus = -1;
					setPannerToFoces(-1);
				} else {
					curPannerFocus = 3;
					setPannerToFoces(3);
					changeChildPane(mChildPaneData4);
					itemsShowingView.setVisibility(View.VISIBLE);
				}
			}
		});

		flPane5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (curPannerFocus == 4) {
					itemsShowingView.setVisibility(View.GONE);
					curPannerFocus = -1;
					setPannerToFoces(-1);
				} else {
					curPannerFocus = 4;
					setPannerToFoces(4);
					changeChildPane(mChildPaneData5);
					itemsShowingView.setVisibility(View.VISIBLE);
				}
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
				mChildPaneAdaper.changeData(mChildPaneData1);
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
				mChildPaneAdaper.changeData(mChildPaneData2);
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
				mChildPaneAdaper.changeData(mChildPaneData3);
			}
		});
		mChildPane4.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (curPannerFocus != 3) {
						curPannerFocus = 3;
						setPannerToFoces(3);
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

		mChildPane4.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				curPannerFocus = 3;
				setPannerToFoces(3);
				mChildPaneAdaper.changeData(mChildPaneData4);
			}
		});
		mChildPane5.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (curPannerFocus != 4) {
						curPannerFocus = 4;
						setPannerToFoces(4);
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

		mChildPane5.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				curPannerFocus = 5;
				setPannerToFoces(5);
				mChildPaneAdaper.changeData(mChildPaneData5);
			}
		});

		mChildPane1.setDoubleClickCallBack(new DoubleClickCallBackInterface() {

			@Override
			public void doubleClickCallback(int x, int y, int position) {
				showDeleteDialog(0, position);
			}
		});

		mChildPane2.setDoubleClickCallBack(new DoubleClickCallBackInterface() {

			@Override
			public void doubleClickCallback(int x, int y, int position) {
				showDeleteDialog(1, position);
			}
		});

		mChildPane3.setDoubleClickCallBack(new DoubleClickCallBackInterface() {

			@Override
			public void doubleClickCallback(int x, int y, int position) {
				showDeleteDialog(2, position);
			}
		});

		mChildPane4.setDoubleClickCallBack(new DoubleClickCallBackInterface() {

			@Override
			public void doubleClickCallback(int x, int y, int position) {
				showDeleteDialog(3, position);
			}
		});

		mChildPane5.setDoubleClickCallBack(new DoubleClickCallBackInterface() {

			@Override
			public void doubleClickCallback(int x, int y, int position) {
				showDeleteDialog(4, position);
			}
		});

		mChildPane.setDoubleClickCallBack(new DoubleClickCallBackInterface() {

			@Override
			public void doubleClickCallback(int x, int y, int position) {
				showDeleteDialog(curPannerFocus, position);
			}
		});

		tvPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mSendDataListen != null) {
					DataConbine mConbine = new DataConbine(
							DataConfig.SENCE_RUNNING);
					mConbine.addByte(DataUtils.getByte(curSenceItem.getItemId()));
					mSendDataListen.sendData(mConbine.getTotalData(),
							curSenceItem);
				}
			}
		});

		tvStop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mSendDataListen != null) {
					DataConbine mConbine = new DataConbine(
							DataConfig.SENCE_STOPPING);
					mConbine.addByte(DataUtils.getByte(curSenceItem.getItemId()));
					mSendDataListen.sendData(mConbine.getTotalData(),
							curSenceItem);
				}
			}
		});

	}

	private void changeChildPane(List<GroupItem> childitems) {
		List<GroupItem> child = new ArrayList<GroupItem>();
		for (GroupItem childgroup : childitems) {
			GroupItem mItem = new GroupItem(mContext);
			mItem.setItemId(childgroup.getItemId());
			mItem.setItemColor(childgroup.getItemColor());
			mItem.setItemName(childgroup.getItemName());
			mItem.setItemType(TYPE.TYPE_GROUP);
			mItem.setItemRemark(childgroup.getItemRemark());
			child.add(mItem);
		}

		mChildPane.setNumColumns(childitems.size());
		int total = childitems.size() * 300 + (childitems.size() - 1) * 10;
		mChildPane.getLayoutParams().width = total;
		mChildPane.getLayoutParams().height = 120;
		if (itemComparetor == null) {
			itemComparetor = new GroupItemComparetor();
		}
		if (child != null) {
			Collections.sort(child, itemComparetor);
		}
		mChildPaneAdaper.changeData(child);
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

	@SuppressWarnings("deprecation")
	private void deleteItem(int pannerPos, int itemPos) {
		switch (pannerPos) {
		case 0:
			ChangeDataUtils.deleteSenceGroup(mContext, curSenceItem.getId(),
					pannerPos, mChildPaneData1.get(itemPos).getId());
			if (mSendDataListen != null) {
				DataConbine mConbine = new DataConbine(
						DataConfig.SENCE_PANE_REMOVE_GROUP);
				mConbine.addByte(DataUtils.getByte(curSenceItem.getItemId()));
				mConbine.addByte(DataUtils.getByte(pannerPos));
				mConbine.addByte(DataUtils.getByte(mChildPaneData1.get(itemPos)
						.getItemId()));
				mSendDataListen.sendData(mConbine.getTotalData(), curSenceItem);
			}
			mChildPaneData1.remove(itemPos);
			mChildPaneAdaper1.notifyDataSetChanged();
			changeChildPane(mChildPaneData1);
			break;
		case 1:
			ChangeDataUtils.deleteSenceGroup(mContext, curSenceItem.getId(),
					pannerPos, mChildPaneData2.get(itemPos).getId());
			if (mSendDataListen != null) {
				DataConbine mConbine = new DataConbine(
						DataConfig.SENCE_PANE_REMOVE_GROUP);
				mConbine.addByte(DataUtils.getByte(curSenceItem.getItemId()));
				mConbine.addByte(DataUtils.getByte(pannerPos));
				mConbine.addByte(DataUtils.getByte(mChildPaneData2.get(itemPos)
						.getItemId()));
				mSendDataListen.sendData(mConbine.getTotalData(), curSenceItem);
			}
			mChildPaneData2.remove(itemPos);
			mChildPaneAdaper2.notifyDataSetChanged();
			changeChildPane(mChildPaneData2);
			break;
		case 2:
			ChangeDataUtils.deleteSenceGroup(mContext, curSenceItem.getId(),
					pannerPos, mChildPaneData3.get(itemPos).getId());
			if (mSendDataListen != null) {
				DataConbine mConbine = new DataConbine(
						DataConfig.SENCE_PANE_REMOVE_GROUP);
				mConbine.addByte(DataUtils.getByte(curSenceItem.getItemId()));
				mConbine.addByte(DataUtils.getByte(pannerPos));
				mConbine.addByte(DataUtils.getByte(mChildPaneData3.get(itemPos)
						.getItemId()));
				mSendDataListen.sendData(mConbine.getTotalData(), curSenceItem);
			}
			mChildPaneData3.remove(itemPos);
			mChildPaneAdaper3.notifyDataSetChanged();
			changeChildPane(mChildPaneData3);
			break;
		case 3:
			ChangeDataUtils.deleteSenceGroup(mContext, curSenceItem.getId(),
					pannerPos, mChildPaneData4.get(itemPos).getId());
			if (mSendDataListen != null) {
				DataConbine mConbine = new DataConbine(
						DataConfig.SENCE_PANE_REMOVE_GROUP);
				mConbine.addByte(DataUtils.getByte(curSenceItem.getItemId()));
				mConbine.addByte(DataUtils.getByte(pannerPos));
				mConbine.addByte(DataUtils.getByte(mChildPaneData4.get(itemPos)
						.getItemId()));
				mSendDataListen.sendData(mConbine.getTotalData(), curSenceItem);
			}
			mChildPaneData4.remove(itemPos);
			mChildPaneAdaper4.notifyDataSetChanged();
			changeChildPane(mChildPaneData4);
			break;
		case 4:
			ChangeDataUtils.deleteSenceGroup(mContext, curSenceItem.getId(),
					pannerPos, mChildPaneData5.get(itemPos).getId());
			if (mSendDataListen != null) {
				DataConbine mConbine = new DataConbine(
						DataConfig.SENCE_PANE_REMOVE_GROUP);
				mConbine.addByte(DataUtils.getByte(curSenceItem.getItemId()));
				mConbine.addByte(DataUtils.getByte(pannerPos));
				mConbine.addByte(DataUtils.getByte(mChildPaneData5.get(itemPos)
						.getItemId()));
				mSendDataListen.sendData(mConbine.getTotalData(), curSenceItem);
			}
			mChildPaneData5.remove(itemPos);
			mChildPaneAdaper5.notifyDataSetChanged();
			changeChildPane(mChildPaneData5);
			break;
		default:
			break;
		}

	}

	private void initData() {
		List<GroupBean> list = LCConfig.getGroups();
		List<GroupItem> mGroupItems = DataUtils.coverGroupBeansToGroupItems(
				mContext, list);
		mGroupView.setColumnsNum(1);
		mGroupView.changeData(mGroupItems);

		selectorDialog = new SenceSelectorDialog(mContext);
		curSenceItem = selectorDialog.getSenceItems().get(0);
		// groupListDialog = new GroupListDialog(mContext);
		// groupListDialog.setDoublClick(new DoubleClickCallBackInterface() {
		//
		// @Override
		// public void doubleClickCallback(int x, int y, int position) {
		// showDeleteDialog(curPannerFocus, position);
		// }
		// });
	}

	/**
	 * 改变pane的stroke颜色值
	 * 
	 * @param color
	 */
	private void changePaneStrokeColor(int color) {
		switch (curPannerFocus) {
		case 0:
			setFocusPaneToColor(flPane1, color, 0, mChildPaneData1);
			break;
		case 1:
			setFocusPaneToColor(flPane2, color, 1, mChildPaneData2);
			break;
		case 2:
			setFocusPaneToColor(flPane3, color, 2, mChildPaneData3);
			break;
		case 3:
			setFocusPaneToColor(flPane4, color, 3, mChildPaneData4);
			break;
		case 4:
			setFocusPaneToColor(flPane5, color, 4, mChildPaneData5);
			break;

		default:
			break;
		}
	}

	private OnClickListener mPannerAddClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.v1_lc_setting_sence_pane_image_1:
				showSetTimeDialog(0);
				break;
			case R.id.v1_lc_setting_sence_pane_image_2:
				showSetTimeDialog(1);
				break;
			case R.id.v1_lc_setting_sence_pane_image_3:
				showSetTimeDialog(2);
				break;
			case R.id.v1_lc_setting_sence_pane_image_4:
				showSetTimeDialog(3);
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 添加组的item进指定容器
	 * 
	 * @param item
	 * @param adapter
	 * @param pannerNum
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addGroupItemToPanner(GroupItem item, List data,
			SencePannerAdapter adapter, int pannerNum) {
		if (data.size() < 8) {
			if (pannerNum >= 0 && pannerNum < pannColor.length) {
				item.setItemColor(pannColor[pannerNum]);
				mGroupView.changeColor(pannColor[pannerNum], item);
			}
			data.add(item);
			adapter.notifyDataSetChanged();
			changeChildPane(data);

			mHandle.sendEmptyMessageDelayed(pannerNum, 30);
			setPannerToFoces(pannerNum);

			/**
			 * 发送数据
			 */
			if (mSendDataListen != null) {
				DataConbine mConbine = new DataConbine(
						DataConfig.SENCE_PANE_ADD_GROUP);
				mConbine.addByte(DataUtils.getByte(curSenceItem.getItemId()));
				mConbine.addByte(DataUtils.getByte(pannerNum));
				mConbine.addByte(DataUtils.getByte(item.getItemId()));
				mSendDataListen.sendData(mConbine.getTotalData(), item);
			}
		}
	}

	private void showSetTimeDialog(final int position) {

		SetTimeDialog setTimeDialog = new SetTimeDialog(mContext);
		setTimeDialog.setTimerGetter(new TimerGetterInterface() {

			@Override
			public void onGetTime(int time) {
				sendSenceTime(position, time);
			}
		});

		setTimeDialog.showDialog();
	}

	private boolean isContainItem(List<GroupItem> list, GroupItem childItem) {
		for (GroupItem sourceItem : list) {
			if (sourceItem.getItemId() == childItem.getItemId()) {
				childItem.setSelect(true);
				return true;
			}
		}
		return false;
	}

	@Override
	public void upCallback(int x, int y, int position) {
		switch (curPannerFocus) {
		case 0:
			if (DataUtils.isContains(x, y, flPane1)) {
				GroupItem mGroupItem1 = mGroupView.getData().get(position);
				if (isContainItem(mChildPaneData1, mGroupItem1)) {
					return;
				} else {
					mGroupItem1.setSelect(true);
					addGroupItemToPanner(mGroupItem1, mChildPaneData1,
							mChildPaneAdaper1, 0);
				}
			}
			break;
		case 1:
			if (DataUtils.isContains(x, y, flPane2)) {
				GroupItem mGroupItem2 = mGroupView.getData().get(position);
				if (isContainItem(mChildPaneData2, mGroupItem2)) {
					return;
				} else {
					mGroupItem2.setSelect(true);
					addGroupItemToPanner(mGroupItem2, mChildPaneData2,
							mChildPaneAdaper2, 1);
				}
			}
			break;
		case 2:
			if (DataUtils.isContains(x, y, flPane3)) {
				GroupItem mGroupItem3 = mGroupView.getData().get(position);
				if (isContainItem(mChildPaneData3, mGroupItem3)) {
					return;
				} else {
					mGroupItem3.setSelect(true);
					addGroupItemToPanner(mGroupItem3, mChildPaneData3,
							mChildPaneAdaper3, 2);
				}
			}
			break;
		case 3:
			if (DataUtils.isContains(x, y, flPane4)) {
				GroupItem mGroupItem4 = mGroupView.getData().get(position);
				if (isContainItem(mChildPaneData4, mGroupItem4)) {
					return;
				} else {
					mGroupItem4.setSelect(true);
					addGroupItemToPanner(mGroupItem4, mChildPaneData4,
							mChildPaneAdaper4, 3);
				}
			}
			break;
		case 4:
			if (DataUtils.isContains(x, y, flPane5)) {
				GroupItem mGroupItem5 = mGroupView.getData().get(position);
				if (isContainItem(mChildPaneData5, mGroupItem5)) {
					return;
				} else {
					mGroupItem5.setSelect(true);
					addGroupItemToPanner(mGroupItem5, mChildPaneData5,
							mChildPaneAdaper5, 4);
				}
			}
			break;
		default:
			break;
		}
		// if (curGroupItem != null) {
		//
		// }
		// curGroupItem = null;
	}

	@Override
	public void moveCallback(int x, int y, int position) {
		if (DataUtils.isContains(x, y, flPane1)) {
			if (lastPannerFocus != 0) {
				setPannerToFoces(0);
				curPannerFocus = 0;
				lastPannerFocus = 0;
			}
		} else if (DataUtils.isContains(x, y, flPane2)) {
			if (lastPannerFocus != 1) {
				setPannerToFoces(1);
				curPannerFocus = 1;
				lastPannerFocus = 1;
			}
		} else if (DataUtils.isContains(x, y, flPane3)) {
			if (lastPannerFocus != 2) {
				setPannerToFoces(2);
				curPannerFocus = 2;
				lastPannerFocus = 2;
			}
		} else if (DataUtils.isContains(x, y, flPane4)) {
			if (lastPannerFocus != 3) {
				setPannerToFoces(3);
				curPannerFocus = 3;
				lastPannerFocus = 3;
			}
		} else if (DataUtils.isContains(x, y, flPane5)) {
			if (lastPannerFocus != 4) {
				setPannerToFoces(4);
				curPannerFocus = 4;
				lastPannerFocus = 4;
			}
		}

	}

	@Override
	public void visiteItemPos(int pos) {
		if (selectorDialog != null) {
			SenceItem item = selectorDialog.getSenceItems().get(pos);
			if (item != null) {
				tvName.setText(item.getName());
				iniPaneData(item);
				selectorDialog.dismiss();
			}
		}
	}

	/**
	 * 使panner显示foces样式
	 * 
	 * @param position
	 */
	private void setPannerToFoces(int position) {
		initPaneTextColor();
		List<GroupItem> totalItem = new ArrayList<GroupItem>();
		totalItem.addAll(mChildPaneData1);
		totalItem.addAll(mChildPaneData2);
		totalItem.addAll(mChildPaneData3);
		totalItem.addAll(mChildPaneData4);
		totalItem.addAll(mChildPaneData5);
		switch (position) {
		case 0:
			tvPane1.setTextColor(getResources().getColor(R.color.yellow));
			mGroupView.showTargetToFocus(totalItem, true);
			break;
		case 1:
			tvPane2.setTextColor(getResources().getColor(R.color.yellow));
			mGroupView.showTargetToFocus(totalItem, true);
			break;
		case 2:
			tvPane3.setTextColor(getResources().getColor(R.color.yellow));
			mGroupView.showTargetToFocus(totalItem, true);
			break;
		case 3:
			tvPane4.setTextColor(getResources().getColor(R.color.yellow));
			mGroupView.showTargetToFocus(totalItem, true);
			break;
		case 4:
			tvPane5.setTextColor(getResources().getColor(R.color.yellow));
			mGroupView.showTargetToFocus(totalItem, true);
			break;
		default:
			break;
		}
	}

	@SuppressWarnings("deprecation")
	private void setFocusPaneToColor(FrameLayout flLayout, int color,
			int position, List<GroupItem> data) {
		GradientDrawable mGradientDrawable = new GradientDrawable();
		mGradientDrawable.setCornerRadius(5);
		int defaultcolor = android.graphics.Color.parseColor("#ffffff");
		mGradientDrawable.setStroke(3, defaultcolor);
		mGradientDrawable.setStroke(3, color);
		flLayout.setBackgroundDrawable(mGradientDrawable);
		if (position >= 0 && position < pannColor.length) {
			pannColor[position] = color;
		}
		for (GroupItem item : data) {
			item.setItemColor(pannColor[position]);
			mGroupView.changeColor(color, item);
			if (mChildPaneAdaper.getList() != null) {
				for (GroupItem childitem : mChildPaneAdaper.getList()) {
					if (item.getItemId() == childitem.getItemId()) {
						childitem.setItemColor(color);
						mChildPaneAdaper.notifyDataSetChanged();
					}
				}
			}
		}

		/**
		 * 发送panner数据
		 */
		if (mSendDataListen != null) {
			DataConbine mConbine = new DataConbine(
					DataConfig.SENCE_PANE_COLOR_CHANGE);
			mConbine.addByte(DataUtils.getByte(curSenceItem.getItemId()));
			mConbine.addByte(DataUtils.getByte(position));
			int red = Color.red(color);
			int redl = red * light / 255;
			mConbine.addByte(DataUtils.getByte(redl));
			int green = Color.green(color);
			int greenl = green * light / 255;
			mConbine.addByte(DataUtils.getByte(greenl));
			int blue = Color.blue(color);
			int bluel = blue * light / 255;
			mConbine.addByte(DataUtils.getByte(bluel));
			mSendDataListen.sendData(mConbine.getTotalData(), curSenceItem);
		}

	}

	/** 初始化所有panner处于非focus样式 **/
	private void initPaneTextColor() {
		tvPane1.setTextColor(getResources().getColor(R.color.white));
		tvPane2.setTextColor(getResources().getColor(R.color.white));
		tvPane3.setTextColor(getResources().getColor(R.color.white));
		tvPane4.setTextColor(getResources().getColor(R.color.white));
		tvPane5.setTextColor(getResources().getColor(R.color.white));
	}

	/**
	 * 发送时间设置命令
	 * 
	 * @param time
	 */
	private void sendSenceTime(int position, int time) {
		if (mSendDataListen != null) {
			DataConbine mConbine = new DataConbine(
					DataConfig.SENCE_TIME_SETTING);
			mConbine.addByte(DataUtils.getByte(curSenceItem.getItemId()));
			mConbine.addByte(DataUtils.getByte(position));
			mConbine.addByte(DataUtils.getByte(time));
			mSendDataListen.sendData(mConbine.getTotalData(), curSenceItem);
		}
	}

}
