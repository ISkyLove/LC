package com.lc;

import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.List;

import com.lc.common.LogUtils;
import com.lc.dialog.handLineDialog;
import com.lc.dialog.handLineDialog.OnHandlineDialogListen;
import com.lc.service.ILCNIOSocketService;
import com.lc.service.ILCSocketService;
import com.lc.service.LCNIOSocketService;
import com.lc.service.LCNIOSocketService.BinderServicePolice;
import com.lc.service.LCNIOSocketService.OnSocketServiceConnectListen;
import com.lc.socket.SocketHelper;
import com.lc.socket.SocketNioThread;
import com.lc.socket.SocketNioThread.OnNIOConnectListen;
import com.lc.socket.SocketNioThread.OnNIOReceiveDataListen;
import com.lc.socket.SocketThread;
import com.lc.view.BitmapHelp;
import com.lc.view.ColorView;
import com.lc.view.LCColorViewEx;
import com.lc.view.ColorView.OnTouchUpListen;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnDrawListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class mainFragment extends Fragment {

	private static String TAG = mainFragment.class.getSimpleName();

	private EditText etSend;
	private Button btnSend;
	private Button btnHand;
	private TextView tvReceive;
	private ColorView mColorView;
	private Context mContext;
	
	private SocketNioThread mSocketThread = null;

	private Handler mUIHandler;

	private Handler mSocketHandler;
	
	private boolean isFirstDraw = true;

	private ILCNIOSocketService mLcSocketService;
	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mLcSocketService = (ILCNIOSocketService) (((BinderServicePolice) service)
					.getService());
			mLcSocketService
					.addSocketServiceConnectListen(new OnSocketServiceConnectListen() {

						@Override
						public void OnConnectSuccess() {
							// TODO Auto-generated method stub

							Message msg = mUIHandler.obtainMessage();
							msg.what = SocketThread.SOCKET_CONNECT;
							msg.sendToTarget();
						}

						@Override
						public void OnConnectFaile() {
							// TODO Auto-generated method stub
							// btnHand.setEnabled(true);
							Message msg = mUIHandler.obtainMessage();
							msg.what = SocketThread.SOCKET_DESTORY;
							msg.sendToTarget();
						}

						@Override
						public void OnConnectBegin() {
							// TODO Auto-generated method stub
							Message msg = mUIHandler.obtainMessage();
							msg.what = SocketThread.SOCKET_BEGIN_CONNECT;
							msg.sendToTarget();
						}
					});
//			mLcSocketService.ConnectByAuto();
		}
	};

	public mainFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		etSend = (EditText) rootView.findViewById(R.id.main_send_text);
		btnSend = (Button) rootView.findViewById(R.id.main_send_btn);
		tvReceive = (TextView) rootView.findViewById(R.id.main_receive);
		mColorView = (ColorView) rootView.findViewById(R.id.color_pick);
		mColorView.setRectMode(true);
		btnHand = (Button) rootView.findViewById(R.id.main_hand_btn);
		mContext = getActivity();
		initView();
		initCreate();
//		initService();
		return rootView;
	}

	private void initService() {
		// TODO Auto-generated method stub

		Intent mserver = new Intent(LCNIOSocketService.SOCKET_SERVICE_ACTION);
		mContext.bindService(mserver, mConnection, Service.BIND_AUTO_CREATE);

	}

	private void initCreate() {
		// TODO Auto-generated method stub
		mContext = getActivity();
		mUIHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case SocketThread.SOCKET_BEGIN_CONNECT:
					btnSend.setText("正在连接服务器");
					btnSend.setEnabled(true);
					break;
				case SocketThread.SOCKET_CONNECT:
					btnSend.setText("已成功连接上服务器");
					btnSend.setEnabled(true);
					break;
				case SocketThread.SOCKET_DESTORY:
					btnSend.setText("连接已断开");
					btnSend.setEnabled(false);
					break;
				default:
					break;
				}
			}
		};
		// new findIPAsyn().execute();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

		super.onResume();
	}

	private void initSocket(String remoteIP, int remotePort) {
		if (remoteIP != null && mSocketThread == null) {
			mSocketThread = SocketNioThread.getInstance(mContext);
			mSocketThread.InitRemoteAddr(remoteIP, remotePort);
			mSocketHandler = mSocketThread.getHandler();
			mSocketThread.setOnNIOConnectListen(new OnNIOConnectListen() {

				@Override
				public void OnNIOContectBegined(SocketChannel socketchannel,
						String message) {
					// TODO Auto-generated method stub
					Message msg = mUIHandler.obtainMessage();
					msg.what = SocketThread.SOCKET_BEGIN_CONNECT;
					msg.sendToTarget();
				}

				@Override
				public void OnNIOConnectSuccess(SocketChannel socketchannel,
						String message) {
					// TODO Auto-generated method stub
					Message msg = mUIHandler.obtainMessage();
					msg.what = SocketThread.SOCKET_CONNECT;
					msg.sendToTarget();
				}

				@Override
				public void OnNIOConnectFaile(SocketChannel socketchannel,
						String message) {
					// TODO Auto-generated method stub
					Message msg = mUIHandler.obtainMessage();
					msg.what = SocketThread.SOCKET_DESTORY;
					msg.sendToTarget();
				}
			});

			mSocketThread
					.setOnNIOReceiveDataListen(new OnNIOReceiveDataListen() {

						@Override
						public void OnNIOReceiveData(byte[] data) {
							// TODO Auto-generated method stub
							String datastr = new String(data);
							Log.i("SocketThread", datastr);
							tvReceive.append(datastr);
							tvReceive.append("\n");
						}
					});
			Message msg = mSocketHandler.obtainMessage();
			msg.what = SocketNioThread.SOCKET_REQUEST_LINK;
			msg.sendToTarget();
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		btnHand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				final handLineDialog mHandLineDialog = new handLineDialog(
						mContext);
				mHandLineDialog
						.setOnHandlineDialogListen(new OnHandlineDialogListen() {

							@Override
							public void OnOkClick(String IP, int port) {
								// TODO Auto-generated method stub
								mHandLineDialog.destoryDialog();
								btnSend.setText("正在连接服务器");
								// btnHand.setEnabled(false);
//								mLcSocketService.ConnectByHand(IP, port);
								 initSocket(IP, port);
								// btnHand.setEnabled(true);
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

		mColorView.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// TODO Auto-generated method stub
						// mColorView.postDelayed(new Runnable() {
						//
						// @Override
						// public void run() {
						// // TODO Auto-generated method stub
						//
						// }
						// }, 1000);
						iniClorView();

					}
				});

		mColorView.setOnTouchUpListen(new OnTouchUpListen() {

			@Override
			public void OnTouchUp(int color) {
				// TODO Auto-generated method stub
				// if (mSocketHandler != null) {
				// mSocketHandler.removeMessages(SocketThread.SOCKET_SEND);
				// Message msg = mSocketHandler.obtainMessage();
				int red = Color.red(color);
				int green = Color.green(color);
				int blue = Color.blue(color);
				String redstr = String.valueOf(red);
				String greenstr = String.valueOf(green);
				String bluestr = String.valueOf(blue);
				byte[] redbyte = redstr.getBytes();
				byte[] greenbyte = greenstr.getBytes();
				byte[] bluebyte = bluestr.getBytes();
				byte[] result = new byte[3];
				result[0] = (byte) (red & 0xff);
				result[1] = (byte) (green & 0xff);
				result[2] = (byte) (blue & 0xff);
				Log.i(TAG, "setOnTouchUpListen:" + (red & 0xff) + ":" + red
						+ ":" + (green & 0xff) + ":" + green + ":"
						+ (blue & 0xff) + ":" + blue);
				// msg.obj = result;
				// msg.what = SocketThread.SOCKET_SEND;
				// msg.sendToTarget();
				if (mLcSocketService != null) {
					mLcSocketService.sendData(result, null);
				}
				// }
			}
		});
	}

	private void iniClorView() {
		if (isFirstDraw) {
			BitmapDrawable mBitmapDrawable = (BitmapDrawable) mContext
					.getResources().getDrawable(R.drawable.lc);
			int bitWidth = mColorView.getMeasuredWidth();
			int bitHeight = mColorView.getMeasuredHeight();
			if (bitWidth == 0) {

			}
			if (bitHeight == 0) {

			}
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
	public void onDestroy() {
		// TODO Auto-generated method stub
		// if (mSocketThread != null) {
		// mSocketThread.destoryThread();
		// }
		// Intent mIntent = new Intent(LCSocketService.SOCKET_SERVICE_ACTION);
		// mContext.stopService(mIntent);
		mContext.unbindService(mConnection);
		super.onDestroy();
	}

	private class findIPAsyn extends AsyncTask<Void, Integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			btnSend.setText("正在搜索服务IP...");
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String mIPs = SocketHelper.findTargetIP(mContext);
			return mIPs;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			// btnHand.setEnabled(true);
			if (result == null) {
				btnSend.setText("搜索服务IP失败");
				tvReceive.setText("请退出客户端并检查服务已经开启后再启动服务端");
			} else {
				btnSend.setText("已成功连接上服务器");
				initSocket(result, 0);
			}
		}

	}
}
