package com.lc;

import java.nio.ByteBuffer;

import android.content.Intent;
import android.content.SyncAdapterType;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lc.LCFragment.OnSendDataListen;
import com.lc.common.DataUtils;
import com.lc.common.LogUtils;
import com.lc.common.bean.LCBaseBean;
import com.lc.data.DataConbine;
import com.lc.data.DataConfig;
import com.lc.data.SynDataTranslateHelper;
import com.lc.dialog.ExitDialog;
import com.lc.dialog.ExitDialog.IExitInterface;
import com.lc.dialog.LCProgressDialog;
import com.lc.dialog.ResetDialog;
import com.lc.dialog.ResetDialog.OnOKClickListen;
import com.lc.dialog.handLineDialog;
import com.lc.dialog.handLineDialog.OnHandlineDialogListen;
import com.lc.group.GroupsFragment;
import com.lc.sence.SencesFragment;
import com.lc.service.LCSocketService;
import com.lc.setting.favor.FavorSettingFragment;
import com.lc.setting.group.GroupSettingFragment;
import com.lc.setting.sence.SenceSettingFragment;
import com.lc.setting.show.ShowSettingFragment;
import com.lc.show.ShowsFragment;

/**
 * 主界面，仅包含宏观操作，包括子页面切换、数据保存，具体操作在子页面中
 * 
 * @author LC work room
 * 
 */
public class LCMainActivity extends LCActivity implements OnClickListener {

	private LinearLayout llGroupCtro; // 组控按钮
	private LinearLayout llSence; // 场景按钮
	private LinearLayout llSetting; // 设置按钮
	private LinearLayout llShow; // show按钮
	private LinearLayout llSettingBtns; // 设置中所有按钮的layout，用于显示和隐藏
	private LinearLayout llLink; // 手动连接按钮
	private LinearLayout llException; // 异常日志输出按钮
	private LinearLayout llReset; // 重置
	private LinearLayout llExit; // 退出

	private LinearLayout llSettingGroup; // 设置--组设置按钮
	private LinearLayout llSettingSence; // 设置--场景设置按钮
	private LinearLayout llSettingShow; // 设置--show设置按钮
	private LinearLayout llSettingFavor; // 设置--偏爱设置按钮

	private ImageView ivLogo;

	private boolean isLogPrint = false;

	/**
	 * 初始化数据进度框
	 */
	private LCProgressDialog mProgressDialog = null;

	// 以下为各个子页面
	private GroupsFragment groupsFragment;
	private SencesFragment sencesFragment;
	private ShowsFragment showsFragment;
	private GroupSettingFragment groupSettingFragment;
	private SenceSettingFragment senceSettingFragment;
	private ShowSettingFragment showSettingFragment;
	private FavorSettingFragment favorSettingFragment;

	private OnSendDataListen mgroupSendDataListen;
	private OnSendDataListen msenceSendDataListen;
	private OnSendDataListen mshowSendDataListen;
	private OnSendDataListen msettinggroupSendDataListen;
	private OnSendDataListen msettingsenceSendDataListen;
	private OnSendDataListen msettingshowSendDataListen;

	private LinearLayout llMenu;
	private FrameLayout flContent;

	private ByteBuffer mdata;

	private FragmentManager mFragmentManager; // 子页面管理

	// 以下为子页面标志
	private static final int GROUP_FRAGMENT_TAG = 1; // 组页面标志为1
	private static final int SENCE_FRAGMENT_TAG = 2; // 场景页面标志为2
	private static final int SHOW_FRAGMENT_TAG = 3; // show页面标志为3
	private static final int GROUP_SETTING_FRAGMENT_TAG = 4; // 设置--组页面标志为4
	private static final int SENCE_SETTING_FRAGMENT_TAG = 5; // 设置--场景页面标志为5
	private static final int SHOW_SETTING_FRAGMENT_TAG = 6; // 设置--show页面标志为6
	private static final int FAVOR_SETTING_FRAGMENT_TAG = 7; // 设置--偏爱页面标志为7
	private int curTAG = 0;

	public final static int PROGRESS_SHOW = 0;
	public final static int PROGRESS_HIDE = 1;
	public final static int DATA_RESET = 2;
	public final static int PROGRESS_HIDE_DELAYE = 3;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case PROGRESS_SHOW:
				if (mProgressDialog != null) {
					mProgressDialog.destoryDialog();
				}
				if (mProgressDialog == null) {
					mProgressDialog = new LCProgressDialog(mContext);
				}
				mProgressDialog.showDialog();
				mHandler.sendEmptyMessageDelayed(PROGRESS_HIDE_DELAYE, 2 * 60 * 1000);
				break;
			case PROGRESS_HIDE:
				if (mProgressDialog != null) {
					mProgressDialog.destoryDialog();
				}
				break;
			case DATA_RESET:
				DataConbine mConbine = new DataConbine((DataConfig.DATA_RESET));
				mSocketService.sendData(mConbine.getTotalData(), null);
				break;
			case PROGRESS_HIDE_DELAYE:
				if (mProgressDialog != null) {
					mProgressDialog.destoryDialog();
				}
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置窗口样式
		setContentView(R.layout.v1_lc_main_activity_layout);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		// 获取子页面管理实例
		mFragmentManager = getSupportFragmentManager();
		initViews();
		initEvent();
		showChildPage(curTAG);
		LCConfig.retainConfig(LCShare.getLocalConfig(mContext));
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		super.onResume();
	}

	/**
	 * 初始化页面，将左侧按钮实例化
	 */
	private void initViews() {
		// 外面的四个按钮
		llGroupCtro = (LinearLayout) findViewById(R.id.v1_main_group_contro_ll);
		llSence = (LinearLayout) findViewById(R.id.v1_main_sence_ll);
		llSetting = (LinearLayout) findViewById(R.id.v1_main_setting_ll);
		llShow = (LinearLayout) findViewById(R.id.v1_main_show_ll);
		llException = (LinearLayout) findViewById(R.id.v1_main_exception_ll);
		llLink = (LinearLayout) findViewById(R.id.v1_main_unauto_link_ll);
		llReset = (LinearLayout) findViewById(R.id.v1_main_reset_ll);
		llExit = (LinearLayout) findViewById(R.id.v1_main_exit_ll);
		// 设置中的按钮
		llSettingBtns = (LinearLayout) findViewById(R.id.setting_child_ll);
		llSettingGroup = (LinearLayout) findViewById(R.id.v1_main_edit_group_ll);
		llSettingSence = (LinearLayout) findViewById(R.id.v1_main_edit_sence_ll);
		llSettingShow = (LinearLayout) findViewById(R.id.v1_main_edit_show_ll);
		llSettingFavor = (LinearLayout) findViewById(R.id.v1_main_edit_favor_ll);

		flContent = (FrameLayout) findViewById(R.id.fragment_container_fl);
		llMenu = (LinearLayout) findViewById(R.id.v1_main_menu);

		ivLogo = (ImageView) findViewById(R.id.v1_main_layout_logo);

		// 设置点击事件监听
		llSence.setOnClickListener(this);
		llSetting.setOnClickListener(this);
		llShow.setOnClickListener(this);
		llGroupCtro.setOnClickListener(this);
		llSettingGroup.setOnClickListener(this);
		llSettingSence.setOnClickListener(this);
		llSettingShow.setOnClickListener(this);
		llSettingFavor.setOnClickListener(this);
		llException.setOnClickListener(this);
		llLink.setOnClickListener(this);
		llExit.setOnClickListener(this);
		llReset.setOnClickListener(this);
	}

	private void initEvent() {
		mgroupSendDataListen = new OnSendDataListen() {

			@Override
			public void sendData(byte[] data, LCBaseBean basebean) {
				if (mSocketService != null) {
					mSocketService.sendData(data, basebean);
				}
			}
		};
		msenceSendDataListen = new OnSendDataListen() {

			@Override
			public void sendData(byte[] data, LCBaseBean basebean) {
				if (mSocketService != null) {
					mSocketService.sendData(data, basebean);
				}
			}
		};

		mshowSendDataListen = new OnSendDataListen() {

			@Override
			public void sendData(byte[] data, LCBaseBean basebean) {
				if (mSocketService != null) {
					mSocketService.sendData(data, basebean);
				}
			}
		};

		msettinggroupSendDataListen = new OnSendDataListen() {

			@Override
			public void sendData(byte[] data, LCBaseBean basebean) {
				if (mSocketService != null) {
					mSocketService.sendData(data, basebean);
				}
			}
		};

		msettingsenceSendDataListen = new OnSendDataListen() {

			@Override
			public void sendData(byte[] data, LCBaseBean basebean) {
				if (mSocketService != null) {
					mSocketService.sendData(data, basebean);
				}
			}
		};

		msettingshowSendDataListen = new OnSendDataListen() {

			@Override
			public void sendData(byte[] data, LCBaseBean basebean) {
				if (mSocketService != null) {
					mSocketService.sendData(data, basebean);
				}
			}
		};
	}

	@Override
	public void OnConnectBegin() {
		// Toast.makeText(mContext, "OnConnectBegin",
		// Toast.LENGTH_SHORT).show();
		ivLogo.setBackgroundResource(R.drawable.v1_lc_main_begin_anim);
		AnimationDrawable mAniBegin = (AnimationDrawable) ivLogo
				.getBackground();
		if (mAniBegin != null) {
			if (!mAniBegin.isRunning()) {
				mAniBegin.start();
			}
		}
		if (mProgressDialog != null) {
			mProgressDialog.destoryDialog();
		}
		setMenuEnable(false);
		super.OnConnectBegin();
	}

	@Override
	public void OnConnectFaile() {
		// Toast.makeText(mContext, "OnConnectFaile",
		// Toast.LENGTH_SHORT).show();
		ivLogo.clearAnimation();
		ivLogo.setBackgroundResource(R.drawable.v1_logo_1);
		setMenuEnable(false);
		if (mProgressDialog != null) {
			mProgressDialog.destoryDialog();
		}
		super.OnConnectFaile();
	}

	@Override
	public void OnConnectSuccess() {
		// Toast.makeText(mContext, "OnConnectSuccess",
		// Toast.LENGTH_SHORT).show();
		ivLogo.setBackgroundResource(R.drawable.v1_logo_1);
		setMenuEnable(true);
		if (mProgressDialog != null) {
			mProgressDialog.destoryDialog();
		}
		// mHandler.sendEmptyMessage(1);
		mHandler.sendEmptyMessageDelayed(DATA_RESET, 1000);
	}

	@Override
	public void OnSocketSendDataSuccess(byte[] data, LCBaseBean baseBean) {
		int command = data[1];
		switch (command) {
		case DataConfig.GROUP_COLOR_CHANGE:
			// DataUtils.changeGroupColor(baseBean, data[3], data[4], data[5]);
			break;
		case DataConfig.GROUP_ADD_LIGHT:
			DataUtils.addLightToGroup(mContext, (int) data[2], baseBean);
			break;
		case DataConfig.GROUP_REMOVE_LIGHT:
			DataUtils.removeLightFromGroup(mContext, (int) data[2], baseBean);
			break;
		case DataConfig.SENCE_PANE_COLOR_CHANGE:
			DataUtils.changeSenceColor(baseBean);
			break;
		case DataConfig.SENCE_PANE_ADD_GROUP:
			DataUtils.addGroupToSence(mContext, (int) data[2], (int) data[3],
					baseBean);
			break;
		case DataConfig.SENCE_TIME_SETTING:
			DataUtils.changeSenceTime(baseBean, data[2], data[5]);
			break;
		case DataConfig.SHOW_TIME_SETTING:
			DataUtils.changeShowTime(baseBean, data[2]);
			break;
		case DataConfig.SHOW_PANE_ADD_SENCE:
			DataUtils.addSenceToShow(mContext, data[2], baseBean);
			break;

		default:
			break;
		}
		super.OnSocketSendDataSuccess(data, baseBean);
	}

	@Override
	public void OnReceiveData(byte[] data) {
		// TODO Auto-generated method stubo
		LogUtils.i(LCMainActivity.class, DataUtils.byte2HexStr(data));
		if (data[0] == DataConfig.DATA_RESET_GROUP_BEGIN1
				&& data[1] == DataConfig.DATA_RESET_GROUP_BEGIN2
				&& data[2] == DataConfig.DATA_RESET_GROUP_BEGIN3) {
			mdata = ByteBuffer.allocate(1024 * 2);
			mdata.clear();
			if (mProgressDialog == null) {
				mProgressDialog = new LCProgressDialog(mContext);
				mProgressDialog.showDialog();
			}
			LogUtils.i(LCMainActivity.class,
					"begin data:" + DataUtils.byte2HexStr(data));
		}
		if (mdata != null) {
			mdata.put(data);
		}
		int datalength = data.length;
		if (data[datalength - 3] == DataConfig.DATA_RESET_SHOW_END1
				&& data[datalength - 2] == DataConfig.DATA_RESET_SHOW_END2
				&& data[datalength - 1] == DataConfig.DATA_RESET_SHOW_END3
				&& mdata != null) {
			mdata.flip();
			byte[] totatldata = new byte[mdata.limit()];
			mdata.get(totatldata, 0, totatldata.length);
			LogUtils.i(
					LCMainActivity.class,
					"total data size:" + mdata.capacity() + " limit:"
							+ mdata.limit() + " data:"
							+ DataUtils.byte2HexStr(totatldata));
			if (mdata.limit() == 1302) {
				new ReceiveThread().start();
				return;
			} else {
				Toast.makeText(mContext, "初始化数据失败", Toast.LENGTH_SHORT).show();
				mHandler.sendEmptyMessage(PROGRESS_HIDE);
			}
		}
	}

	private class ReceiveThread extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (mdata != null) {
				mdata.flip();
				byte[] group = new byte[160];
				mdata.position(3);
				mdata.get(group, 0, 160);
				LogUtils.i(LCMainActivity.class,
						"group data:" + DataUtils.byte2HexStr(group));
				byte[] sence = new byte[1040];
				mdata.get(sence, 0, 1040);
				LogUtils.i(LCMainActivity.class,
						"sence data:" + DataUtils.byte2HexStr(sence));
				byte[] show = new byte[80];
				mdata.get(show, 0, 80);
				LogUtils.i(LCMainActivity.class,
						"show data:" + DataUtils.byte2HexStr(show));
				byte[] senceTime = new byte[16];
				mdata.get(senceTime, 0, 16);
				LogUtils.i(LCMainActivity.class,
						"time data:" + DataUtils.byte2HexStr(senceTime));
				SynDataTranslateHelper.SynGroupData(group);
				SynDataTranslateHelper.SynSenceData(sence, senceTime);
				SynDataTranslateHelper.SynShowData(show);

			}
			mHandler.sendEmptyMessageDelayed(PROGRESS_HIDE, 2000);

		}
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();

		switch (viewId) {
		// 组显示按钮
		case R.id.v1_main_group_contro_ll:
			changeToFocus(llGroupCtro);
			curTAG = GROUP_FRAGMENT_TAG;
			llSettingBtns.setVisibility(View.GONE);
			ivLogo.setVisibility(View.GONE);
			break;
		// 场景显示按钮
		case R.id.v1_main_sence_ll:
			changeToFocus(llSence);
			curTAG = SENCE_FRAGMENT_TAG;
			llSettingBtns.setVisibility(View.GONE);
			ivLogo.setVisibility(View.GONE);
			break;
		// 设置按钮
		case R.id.v1_main_setting_ll:
			changeToFocus(llSetting);
			resetChildBtnPerform();
			if (llSettingBtns.getVisibility() == View.VISIBLE) {
				llSettingBtns.setVisibility(View.GONE);
				if (mFragmentManager != null) {
					FragmentTransaction transaction = mFragmentManager
							.beginTransaction();
					hideChildPage(transaction);
				}
			} else {
				llSettingBtns.setVisibility(View.VISIBLE);
			}
			if (mFragmentManager != null) {
				FragmentTransaction transaction = mFragmentManager
						.beginTransaction();
				hideChildPage(transaction);
				transaction.commit();
				ivLogo.setVisibility(View.VISIBLE);
			}
			curTAG = -1;
			break;
		// show显示按钮
		case R.id.v1_main_show_ll:
			changeToFocus(llShow);
			curTAG = SHOW_FRAGMENT_TAG;
			llSettingBtns.setVisibility(View.GONE);
			ivLogo.setVisibility(View.GONE);
			break;
		// 设置--组设置按钮
		case R.id.v1_main_edit_group_ll:
			changeChildToFocus(llSettingGroup);
			curTAG = GROUP_SETTING_FRAGMENT_TAG;
			ivLogo.setVisibility(View.GONE);
			break;
		// 设置--场景设置按钮
		case R.id.v1_main_edit_sence_ll:
			changeChildToFocus(llSettingSence);
			ivLogo.setVisibility(View.GONE);
			curTAG = SENCE_SETTING_FRAGMENT_TAG;
			break;
		// 设置--show设置按钮
		case R.id.v1_main_edit_show_ll:
			changeChildToFocus(llSettingShow);
			curTAG = SHOW_SETTING_FRAGMENT_TAG;
			ivLogo.setVisibility(View.GONE);
			break;
		// 设置--偏爱设置按钮
		case R.id.v1_main_edit_favor_ll:
			changeChildToFocus(llSettingFavor);
			curTAG = FAVOR_SETTING_FRAGMENT_TAG;
			ivLogo.setVisibility(View.GONE);
			break;
		// 异常日志按钮
		case R.id.v1_main_exception_ll:
			printExceptionTrackToFile();
			break;
		// 手动连接按钮
		case R.id.v1_main_unauto_link_ll:
			showLinkDialog();
			break;
		case R.id.v1_main_reset_ll:
			showResetDialog();
			break;
		// 退出按钮
		case R.id.v1_main_exit_ll:
			showExitDialog();
			break;
		default:
			break;
		}
		showChildPage(curTAG);
		initEvent();
	}

	private void showResetDialog() {
		final ResetDialog resetDialog = new ResetDialog(mContext);
		resetDialog.setOnOKClickListen(new OnOKClickListen() {

			@Override
			public void OnOKClick() {
				// TODO Auto-generated method stub
				DataConbine mDataConbine = new DataConbine(
						DataConfig.DATA_NEWDATA);
				if (mSocketService != null) {
					mSocketService.sendData(mDataConbine.getTotalData(), null);
				}
				resetDialog.dismiss();
				finish();
			}
		});
		resetDialog.show();
	}

	private void showExitDialog() {
		ExitDialog exitDialog = new ExitDialog(mContext);
		exitDialog.setExitCallback(new IExitInterface() {

			@Override
			public void exitCallback() {
				finish();
			}
		});
		exitDialog.show();
	}

	private void showLinkDialog() {
		final handLineDialog mHandLineDialog = new handLineDialog(mContext);
		mHandLineDialog.setOnHandlineDialogListen(new OnHandlineDialogListen() {

			@Override
			public void OnOkClick(String IP, int port) {
				mHandLineDialog.destoryDialog();
				mSocketService.ConnectByHand(IP, port);
			}

			@Override
			public void OnCancalClick() {
				mHandLineDialog.destoryDialog();
			}
		});
		mHandLineDialog.showDialog();
	}

	private void printExceptionTrackToFile() {
		if (!isLogPrint) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					isLogPrint = true;
					LCLogPrint mLcLogPrint = new LCLogPrint(mContext);
					mLcLogPrint.makeLogFile();
					try {
						Thread.sleep(5000);
						mLcLogPrint.destoryProcess();
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					isLogPrint = false;
				}
			}).start();

		} else {
			Toast.makeText(mContext, "正在生成日志...", Toast.LENGTH_SHORT).show();
		}
	}

	private void changeToFocus(LinearLayout lllayout) {
		resetBtnPerform();

		lllayout.setBackgroundResource(R.drawable.v1_btn_press_bk);

	}

	private void changeChildToFocus(LinearLayout llChildlayout) {
		resetChildBtnPerform();
		llChildlayout.setBackgroundResource(R.drawable.v1_btn_press_bk);

	}

	/**
	 * 重置组、场景、show、设置按钮
	 */
	private void resetBtnPerform() {
		llGroupCtro.setBackgroundResource(R.drawable.v1_btn_unpress_bk);
		llSence.setBackgroundResource(R.drawable.v1_btn_unpress_bk);
		llShow.setBackgroundResource(R.drawable.v1_btn_unpress_bk);
		llSetting.setBackgroundResource(R.drawable.v1_btn_unpress_bk);
	}

	/**
	 * 重置设置--组、设置--场景、设置--show、设置--偏爱按钮
	 */
	private void resetChildBtnPerform() {
		llSettingGroup.setBackgroundResource(R.drawable.v1_btn_unpress_bk);
		llSettingSence.setBackgroundResource(R.drawable.v1_btn_unpress_bk);
		llSettingShow.setBackgroundResource(R.drawable.v1_btn_unpress_bk);
		llSettingFavor.setBackgroundResource(R.drawable.v1_btn_unpress_bk);
	}

	private void setMenuEnable(boolean isEnable) {
		if (isEnable) {
			if (mFragmentManager != null) {
				FragmentTransaction transaction = mFragmentManager
						.beginTransaction();
				hideChildPage(transaction);
				transaction.commit();
			}
			llMenu.setVisibility(View.VISIBLE);
			flContent.setVisibility(View.VISIBLE);
			ivLogo.setVisibility(View.GONE);
			ivLogo.setBackgroundResource(R.drawable.v1_logo_1);
		} else {
			if (mFragmentManager != null) {
				FragmentTransaction transaction = mFragmentManager
						.beginTransaction();
				hideChildPage(transaction);
				transaction.commit();
			}
			llMenu.setVisibility(View.GONE);
			flContent.setVisibility(View.GONE);
			ivLogo.setVisibility(View.VISIBLE);

		}
	}

	/**
	 * 显示子页面，传入子页面标识显示对应的子页面
	 * 
	 * @param tag
	 *            标识子页面的tag值：GROUP_FRAGMENT_TAG = 1 组页面标志为1 SENCE_FRAGMENT_TAG =
	 *            2 场景页面标志为2 SHOW_FRAGMENT_TAG = 3 show页面标志为3
	 *            GROUP_SETTING_FRAGMENT_TAG = 4 设置--组页面标志为4
	 *            SENCE_SETTING_FRAGMENT_TAG = 5 设置--场景页面标志为5
	 *            SHOW_SETTING_FRAGMENT_TAG = 6 设置--show页面标志为6
	 *            FAVOR_SETTING_FRAGMENT_TAG = 7 设置--偏爱页面标志为7
	 */
	private void showChildPage(int tag) {
		if (mFragmentManager != null) {
			FragmentTransaction transaction = mFragmentManager
					.beginTransaction();
			hideChildPage(transaction);
			switch (tag) {
			case GROUP_FRAGMENT_TAG:
				if (groupsFragment == null) {
					groupsFragment = new GroupsFragment();
					groupsFragment.setOnSendDataListen(mgroupSendDataListen);
					transaction.add(R.id.fragment_container_fl, groupsFragment);
				} else {
					transaction.show(groupsFragment);
				}

				break;
			case SENCE_FRAGMENT_TAG:
				if (sencesFragment == null) {
					sencesFragment = new SencesFragment();
					sencesFragment.setOnSendDataListen(msenceSendDataListen);
					transaction.add(R.id.fragment_container_fl, sencesFragment);
				} else {
					transaction.show(sencesFragment);
				}

				break;
			case SHOW_FRAGMENT_TAG:
				if (showsFragment == null) {
					showsFragment = new ShowsFragment();
					showsFragment.setOnSendDataListen(mshowSendDataListen);
					transaction.add(R.id.fragment_container_fl, showsFragment);
				} else {
					transaction.show(showsFragment);
				}

				break;
			case GROUP_SETTING_FRAGMENT_TAG:
				if (groupSettingFragment == null) {
					groupSettingFragment = new GroupSettingFragment();
					groupSettingFragment
							.setOnSendDataListen(msettinggroupSendDataListen);
					transaction.add(R.id.fragment_container_fl,
							groupSettingFragment);
				} else {
					transaction.show(groupSettingFragment);
				}

				break;
			case SENCE_SETTING_FRAGMENT_TAG:
				if (senceSettingFragment == null) {
					senceSettingFragment = new SenceSettingFragment();
					senceSettingFragment
							.setOnSendDataListen(msettingsenceSendDataListen);
					transaction.add(R.id.fragment_container_fl,
							senceSettingFragment);

				} else {
					transaction.show(senceSettingFragment);
				}

				break;
			case SHOW_SETTING_FRAGMENT_TAG:
				if (showSettingFragment == null) {
					showSettingFragment = new ShowSettingFragment();
					showSettingFragment
							.setOnSendDataListen(msettingshowSendDataListen);
					transaction.add(R.id.fragment_container_fl,
							showSettingFragment);
				} else {
					transaction.show(showSettingFragment);
				}

				break;
			case FAVOR_SETTING_FRAGMENT_TAG:
				if (favorSettingFragment == null) {
					favorSettingFragment = new FavorSettingFragment();
					transaction.add(R.id.fragment_container_fl,
							favorSettingFragment);
				} else {
					transaction.show(favorSettingFragment);
				}

				break;

			default:
				return;
			}

			transaction.commit();
		} else {
			LogUtils.e(this.getClass(), "FragmentManager为空");
			Toast.makeText(this, "初始化失败，请尝试重新启动", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 重置子页面，将所有子页面隐藏
	 * 
	 * @param transaction
	 *            处理子页面切换业务的对象
	 */
	private void hideChildPage(FragmentTransaction transaction) {
		if (groupsFragment != null) {
			if (!groupsFragment.isHidden()) {
				transaction.hide(groupsFragment);
			}
		}

		if (sencesFragment != null) {
			transaction.hide(sencesFragment);
		}

		if (showsFragment != null) {
			transaction.hide(showsFragment);
		}

		if (groupSettingFragment != null) {
			transaction.hide(groupSettingFragment);
		}

		if (senceSettingFragment != null) {
			transaction.hide(senceSettingFragment);
		}

		if (showSettingFragment != null) {
			transaction.hide(showSettingFragment);
		}

		if (favorSettingFragment != null) {
			transaction.hide(favorSettingFragment);
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		destroyService();
		try {
			Intent mService = new Intent(LCSocketService.SOCKET_SERVICE_ACTION);
			getApplicationContext().stopService(mService);
		} catch (Exception e) {

		} finally {
			super.onDestroy();
		}

	}

	/**
	 * 当应用暂停时进行保存
	 */
	@Override
	protected void onStop() {
		saveChange();
		super.onStop();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showExitDialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 保存设置到本地，供下一次使用
	 */
	private void saveChange() {
		LCShare.saveConfigInLocal(this);
	}
}
