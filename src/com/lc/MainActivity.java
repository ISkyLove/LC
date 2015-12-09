package com.lc;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.lc.common.DataUtils;
import com.lc.common.LogUtils;
import com.lc.common.QuickClickProtectUtils;
import com.lc.data.DataConbine;
import com.lc.data.DataConfig;
import com.lc.dialog.ExitDialog;
import com.lc.dialog.handLineDialog;
import com.lc.dialog.ExitDialog.IExitInterface;
import com.lc.dialog.handLineDialog.OnHandlineDialogListen;
import com.lc.dialog.handLineWifiDialog;
import com.lc.dialog.handLineWifiDialog.OnHandlineWifiDialogListen;
import com.lc.service.LCSocketService;
import com.lc.socket.SocketHelper;
import com.lc.view.BitmapHelp;
import com.lc.view.ColorView;
import com.lc.view.ColorView.OnTouchMoveListen;
import com.lc.view.LCAutoLayout;
import com.lc.view.LCColorViewEx;
import com.lc.view.ColorView.OnTouchUpListen;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.MenuItemCompat;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends LCActivity {

	private ActionBar mActionBar;

	private Context mContext;

	private boolean isLogPrint = false;

	private EditText etSend;
	private Button btnSend;
	private Button btnHand;
	private Button btnlog;
	private Button btn1, btn2;
	private Button btnControColor;
	private LinearLayout llContro;
	private TextView tvReceive;
	private ColorView mColorView;
	private SeekBar mSeekBar1;
	private SeekBar mSeekBar2;
	private boolean isOpen = false;

	private byte red = DataUtils.getByte(DataConfig.DATA_OFFSETS);
	private byte green = DataUtils.getByte(DataConfig.DATA_OFFSETS);
	private byte blue = DataUtils.getByte(DataConfig.DATA_OFFSETS);
	private byte right = DataUtils.getByte(DataConfig.DATA_OFFSETS);
	private byte right1 = DataUtils.getByte(DataConfig.DATA_OFFSETS);
	private MediaPlayer mediaPlayer;
	private Handler mHandler;
	private static final int WIFI_PASS = 101;
	
	
	
	private Button btn3;
	private Button btn4;
	private Button btn5;

	/**
	 * bug 添加addOnGlobalLayoutListener监听导致每次gridView数据刷新的时候ColorView都会重画滑塊,
	 * 添加是否第一次進入標誌，實現只在第一次進入的時候初始化數據 修改2015/2/19 author Lin
	 */
	private boolean isFirstDraw = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.fragment_main);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		// mActionBar = getSupportActionBar();
		// mActionBar.setBackgroundDrawable(getResources().getDrawable(
		// R.drawable.v1_layout_cus_bk_dr));
		// mActionBar.setTitle("haha");

		etSend = (EditText) findViewById(R.id.main_send_text);
		btnSend = (Button) findViewById(R.id.main_send_btn);
		btn1 = (Button) findViewById(R.id.main_btn_1);
		btn2 = (Button) findViewById(R.id.main_btn_2);
		btn3 = (Button) findViewById(R.id.main_btn_3);
		btn4 = (Button) findViewById(R.id.main_btn_4);
		btn5 = (Button) findViewById(R.id.main_btn_5);
		btnlog = (Button) findViewById(R.id.main_btn_log);
		tvReceive = (TextView) findViewById(R.id.main_receive);
		mColorView = (ColorView) findViewById(R.id.color_pick);
		btnControColor = (Button) findViewById(R.id.main_contro_color);
		llContro = (LinearLayout) findViewById(R.id.main_contro);
		mColorView.setRectMode(true);
		mColorView.setTimeDir(100);
		btnHand = (Button) findViewById(R.id.main_hand_btn);
		mSeekBar1 = (SeekBar) findViewById(R.id.main_seekbar_1);
		mSeekBar2 = (SeekBar) findViewById(R.id.main_seekbar_2);
		initEvent();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// toogleMediaplay();
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		// toogleMediaplay();
		super.onPause();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case WIFI_PASS:
					if (mSocketService != null) {
						byte[] mdata = (byte[]) msg.obj;
						mSocketService.sendData(mdata, null);
					}
					break;

				default:
					break;
				}
			}
		};
		mColorView.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// TODO Auto-generated method stub
						iniClorView();
					}
				});

		btnlog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isLogPrint) {
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							isLogPrint = true;
							LCLogPrint mLcLogPrint = new LCLogPrint(mContext);
							mLcLogPrint.makeLogFile();
							try {
								Thread.sleep(5000);
								mLcLogPrint.destoryProcess();
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							isLogPrint = false;
						}
					}).start();

				} else {
					Toast.makeText(mContext, "正在生成日志...", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		btnHand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				final handLineDialog mHandLineDialog = new handLineDialog(
						mContext);
				mHandLineDialog.setBK(mContext.getResources().getColor(
						R.color.v1_layout_cus_bk_dr_green));
				mHandLineDialog
						.setOnHandlineDialogListen(new OnHandlineDialogListen() {

							@Override
							public void OnOkClick(String IP, int port) {
								// TODO Auto-generated method stub
								mHandLineDialog.destoryDialog();
								mHandLineDialog.destoryDialog();
								mSocketService.ConnectByHand(IP, port);
							}

							@Override
							public void OnCancalClick() {
								// TODO Auto-generated method stub
								mHandLineDialog.destoryDialog();
							}
						});
				mHandLineDialog.showDialog();
			}

		});
		mColorView.setOnTouchMoveListen(new OnTouchMoveListen() {

			@Override
			public void OnTouchMove(int color) {
				// TODO Auto-generated method stub
				red = DataUtils.getByte(Color.red(color));
				green = DataUtils.getByte(Color.green(color));
				blue = DataUtils.getByte(Color.blue(color));

				byte[] result = new byte[4];
				result[0] = 0x02;
				result[1] = red;
				result[2] = green;
				result[3] = blue;
				// result[4] = right;
				if (mSocketService != null) {
					mSocketService.sendData(result, null);
				}

			}
		});
		mColorView.setOnTouchUpListen(new OnTouchUpListen() {

			@Override
			public void OnTouchUp(int color) {
				// TODO Auto-generated method stub

				red = DataUtils.getByte(Color.red(color));
				green = DataUtils.getByte(Color.green(color));
				blue = DataUtils.getByte(Color.blue(color));

				byte[] result = new byte[4];
				result[0] = 0x02;
				result[1] = red;
				result[2] = green;
				result[3] = blue;
				if (mSocketService != null) {
					mSocketService.sendData(result, null);
				}

			}
		});

		mSeekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (mSocketService != null) {
					right = DataUtils.getByte(progress);
					byte[] result = new byte[2];
					result[0] = 0x20;
					result[1] = right;
					if (mSocketService != null) {
						mSocketService.sendData(result, null);
					}
				}
			}
		});

		mSeekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (mSocketService != null) {
					right1 = DataUtils.getByte(progress);
					byte[] result = new byte[2];
					result[0] = 0x22;
					result[1] = right1;
					if (mSocketService != null) {
						mSocketService.sendData(result, null);
					}
				}
			}
		});
		btnControColor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final handLineWifiDialog mHandLineWifiDialog = new handLineWifiDialog(
						mContext);
				mHandLineWifiDialog.setBK(mContext.getResources().getColor(
						R.color.v1_layout_cus_bk_dr_green));
				mHandLineWifiDialog
						.setOnHandlineWifiDialogListen(new OnHandlineWifiDialogListen() {

							@Override
							public void OnOkClick(String ssid, String pw) {
								// TODO Auto-generated method stub
								byte[] ssid_byte = new byte[ssid.getBytes().length+2];
								ssid_byte[0]=0x05;
								int i=1;
								for(byte data:ssid.getBytes()){
									ssid_byte[i]=data;
									i++;
								}
								ssid_byte[ssid.getBytes().length+1]=0x0f;
								
								byte[] pw_byte =new byte[ pw.getBytes().length+2];
								pw_byte[0]=0x06;
								int j=1;
								for(byte data:pw.getBytes()){
									pw_byte[j]=data;
									j++;
								}
								pw_byte[pw.getBytes().length+1]=0x0f;
								
								
								if (mSocketService != null) {
									mSocketService.sendData(ssid_byte, null);
								}
								Message mpsa = mHandler.obtainMessage();
								mpsa.what = WIFI_PASS;
								mpsa.obj = pw_byte;
								mHandler.sendMessageDelayed(mpsa, 500);

							}

							@Override
							public void OnCancalClick() {
								// TODO Auto-generated method stub

							}
						});
				mHandLineWifiDialog.showDialog();
			}
		});
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				byte[] result = new byte[4];
				result[0] = 0x03;
				if (isOpen) {
					result[1] = 0x10;
					isOpen = false;
				} else {
					isOpen = true;
					result[1] = 0x01;
				}
				if (mSocketService != null) {
					mSocketService.sendData(result, null);
				}
			}
		});

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				byte[] result = new byte[4];
				result[0] = 0x03;
				result[1] = 0x02;
				if (mSocketService != null) {
					mSocketService.sendData(result, null);
				}
			}
		});

		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				byte[] result = new byte[4];
				result[0] = 0x03;
				result[1] = 0x20;
				if (mSocketService != null) {
					mSocketService.sendData(result, null);
				}
			}
		});
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				byte[] result = new byte[4];
				result[0] = 0x03;
				result[1] = 0x20;
				if (mSocketService != null) {
					mSocketService.sendData(result, null);
				}
			}
		});
		btn4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				byte[] result = new byte[4];
				result[0] = 0x03;
				result[1] = 0x20;
				if (mSocketService != null) {
					mSocketService.sendData(result, null);
				}
			}
		});
		btn5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				byte[] result = new byte[4];
				result[0] = 0x03;
				result[1] = 0x20;
				if (mSocketService != null) {
					mSocketService.sendData(result, null);
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showExitDialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showExitDialog() {
		ExitDialog exitDialog = new ExitDialog(mContext);
		exitDialog.setBK(mContext.getResources().getColor(
				R.color.v1_layout_cus_bk_dr_green));
		exitDialog.setExitCallback(new IExitInterface() {

			@Override
			public void exitCallback() {
				finish();
			}
		});
		exitDialog.show();
	}

	/**
	 * 初始化取色块
	 */
	private void iniClorView() {
		if (isFirstDraw) {
			BitmapDrawable mBitmapDrawable = (BitmapDrawable) mContext
					.getResources().getDrawable(R.drawable.bk);
			int width = mBitmapDrawable.getBitmap().getWidth();
			int height = mBitmapDrawable.getBitmap().getHeight();

			int bitWidth = mColorView.getMeasuredWidth();
			int bitHeight = mColorView.getMeasuredHeight();

			float biliwidth = width * 1.0f / bitWidth;
			float biliheight = height * 1.0f / bitHeight;
			float bili = 0.0f;
			if (biliwidth > biliheight) {
				bili = biliheight;
			} else {
				bili = biliwidth;
			}

			int resultwidth = (int) (bitWidth * bili);
			int resultheight = (int) (bitHeight * bili);

			Bitmap mbitmap = BitmapHelp.scaleBitmapDown(
					mBitmapDrawable.getBitmap(), bitWidth, bitHeight);
			mBitmapDrawable = new BitmapDrawable(mbitmap);
			mColorView.getLayoutParams().height = bitHeight;
			mColorView.getLayoutParams().width = bitWidth;
			mColorView
					.measure(MeasureSpec.makeMeasureSpec(bitWidth,
							MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
							bitHeight, MeasureSpec.EXACTLY));
			mColorView.setBackgroundDrawable(mBitmapDrawable);
			mColorView.initBK();
			isFirstDraw = false;

		}
	}

	@Override
	public void OnConnectBegin() {
		// TODO Auto-generated method stub
		// btnSend.setText("连接开始");
		Toast.makeText(mContext, "连接开始", Toast.LENGTH_SHORT).show();
		super.OnConnectBegin();
	}

	@Override
	public void OnConnectSuccess() {
		// TODO Auto-generated method stub
		// btnSend.setText("连接成功");
		Toast.makeText(mContext, "连接成功", Toast.LENGTH_SHORT).show();
		// WifiManager wifi = (WifiManager)
		// getSystemService(Context.WIFI_SERVICE);
		// WifiInfo info = wifi.getConnectionInfo();
		// if(info!=null){
		// info.get
		// }
		// DataUtils
		// mSocketService
		super.OnConnectSuccess();
	}

	@Override
	public void OnConnectFaile() {
		// TODO Auto-generated method stub
		// btnSend.setText("连接失败");
		Toast.makeText(mContext, "连接失败", Toast.LENGTH_SHORT).show();
		super.OnConnectFaile();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			if (!isLogPrint) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						isLogPrint = true;
						LCLogPrint mLcLogPrint = new LCLogPrint(mContext);
						mLcLogPrint.makeLogFile();
						try {
							Thread.sleep(5000);
							mLcLogPrint.destoryProcess();
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						isLogPrint = false;
					}
				}).start();

			} else {
				Toast.makeText(mContext, "正在生成日志...", Toast.LENGTH_SHORT)
						.show();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
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
			if (mediaPlayer != null) {
				mediaPlayer.stop();
				mediaPlayer.reset();
				mediaPlayer.release();
				mediaPlayer = null;
			}
			super.onDestroy();
		}

	}

	private void toogleMediaplay() {
		if (mediaPlayer == null) {
			mediaPlayer = new MediaPlayer();
			try {
				AssetFileDescriptor mfile = mContext.getAssets().openFd(
						"aa.mp3");
				mediaPlayer.setDataSource(mfile.getFileDescriptor(),
						mfile.getStartOffset(), mfile.getLength());
				mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						mediaPlayer.seekTo(0);
						mediaPlayer.start();
					}
				});
				mfile.close();
				mediaPlayer.prepare();
				mediaPlayer.start();

				// mediaPlayer.start();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		} else if (!mediaPlayer.isPlaying()) {
			mediaPlayer.start();
		}
	}

}
