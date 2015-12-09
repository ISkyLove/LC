package com.lc.service;

import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import com.lc.Broadcast.LCSocketBroadcast;
import com.lc.common.DataUtils;
import com.lc.common.LogUtils;
import com.lc.common.bean.LCBaseBean;
import com.lc.data.BaseItem;
import com.lc.dialog.handLineDialog;
import com.lc.dialog.handLineDialog.OnHandlineDialogListen;
import com.lc.socket.SocketHelper;
import com.lc.socket.SocketNioThread;
import com.lc.socket.SocketNioThread.OnNIOConnectListen;
import com.lc.socket.SocketNioThread.OnNIOReceiveDataListen;
import com.lc.socket.SocketNioThread.OnNIOSendDataSuccessListen;
import com.lc.socket.SocketThread;
import com.lc.socket.SocketThread.ConnectListen;
import com.lc.socket.SocketThread.OnSendDataSuccessListen;
import com.lc.socket.SocketThread.ReceiveDataListen;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * socket 网路连接服务类v2.0
 * 
 * @author LC work room
 * 
 */
public class LCNIOSocketService extends Service implements ILCNIOSocketService {

	private Context mContext;
	private SocketNioThread mNIOSocketThread = null;
	private Handler mNIOSocketHandler;
	private Handler mUIHandler;
	private List<OnSocketSendDataSuccessListen> mSendDataSuccessListens;
	private List<OnSocketServiceConnectListen> mConnectListens;
	private List<OnReceiveDataListen> mReceiveDataListens;
	public static String TAG = "LCNIOSocketService";
	private BinderServicePolice mBinderServicePolice = null;
	private findIPAsyn mAsyn = null;
	private static LCBaseBean curBaseBean = null;

	public static final String SOCKET_SERVICE_ACTION = "com.lc.service.LCNIOSocketService.action";

	/**
	 * 是否开启广播
	 */
	public boolean isOpenBroadcast = false;

	@Override
	public void addSocketServiceConnectListen(
			OnSocketServiceConnectListen onsocketserviceConnectListen) {
		if (mConnectListens != null) {
			mConnectListens.add(onsocketserviceConnectListen);
		}
	}

	@Override
	public void addReceiveDataListen(OnReceiveDataListen onReceiveDataListen) {
		if (mReceiveDataListens != null) {
			mReceiveDataListens.add(onReceiveDataListen);
		}
	}

	@Override
	public void removeSocketServiceConnectListen(
			OnSocketServiceConnectListen onSocketServiceConnectListen) {
		if (mConnectListens != null) {
			mConnectListens.remove(onSocketServiceConnectListen);
		}
	}

	@Override
	public void removeReceiveDataListen(OnReceiveDataListen onReceiveDataListen) {
		if (mReceiveDataListens != null) {
			mReceiveDataListens.remove(onReceiveDataListen);
		}
	}

	@Override
	public void addOnSocketSendDataSuccessListen(
			OnSocketSendDataSuccessListen onSocketSendDataSuccessListen) {
		// TODO Auto-generated method stub
		if (mSendDataSuccessListens != null) {
			mSendDataSuccessListens.add(onSocketSendDataSuccessListen);
		}
	}

	@Override
	public void removeOnSocketSendDataSuccessListen(
			OnSocketSendDataSuccessListen onSocketSendDataSuccessListen) {
		// TODO Auto-generated method stub
		if (mSendDataSuccessListens != null) {
			mSendDataSuccessListens.remove(onSocketSendDataSuccessListen);
		}
	}

	private interface OnSocketServiceIPListen {
		void OnFindIPBegin();

		void OnFindIPFailed();

		void OnFindIPSuccess();
	}

	public interface OnSocketServiceConnectListen {
		void OnConnectBegin();

		void OnConnectFaile();

		void OnConnectSuccess();

	}

	public interface OnSocketSendDataSuccessListen {
		void OnSocketSendDataSuccess(byte[] data, LCBaseBean baseBean);
	}

	public interface OnReceiveDataListen {
		void OnReceiveData(byte[] data);
	}

	@Override
	public void sendData(byte[] data, LCBaseBean basebean) {
		if (mNIOSocketHandler != null) {
			mNIOSocketHandler.removeMessages(SocketNioThread.SOCKET_SEND_DATA);
			Message msg = mNIOSocketHandler.obtainMessage();
			msg.obj = data;
			msg.what = SocketNioThread.SOCKET_SEND_DATA;
			msg.sendToTarget();
			curBaseBean = basebean;
		}
	}

	public class BinderServicePolice extends Binder {
		public LCNIOSocketService getService() {
			return LCNIOSocketService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinderServicePolice;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mAsyn = null;
		mContext = this;
		mBinderServicePolice = new BinderServicePolice();
		mConnectListens = new ArrayList<LCNIOSocketService.OnSocketServiceConnectListen>();
		mReceiveDataListens = new ArrayList<LCNIOSocketService.OnReceiveDataListen>();
		mSendDataSuccessListens = new ArrayList<LCNIOSocketService.OnSocketSendDataSuccessListen>();
		mUIHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case SocketNioThread.SOCKET_BEGIN_CONNECT:
					LogUtils.i(TAG, "搜索远程服务IP成功,正在连接。。。");
					for (OnSocketServiceConnectListen mConnectListen : mConnectListens) {
						mConnectListen.OnConnectBegin();
					}
					if (isOpenBroadcast) {
						Intent mintent = new Intent(
								LCSocketBroadcast.SOCKET_BROADCAST_ACTION);
						mintent.putExtra(LCSocketBroadcast.SOCKET_FLAGS,
								SocketThread.SOCKET_BEGIN_CONNECT);
						sendBroadcast(mintent);
					}
					break;
				case SocketNioThread.SOCKET_CONNECT_FINISH:
					LogUtils.i(TAG, "搜索远程服务IP成功,连接完成");
					for (OnSocketServiceConnectListen mConnectListen : mConnectListens) {
						mConnectListen.OnConnectSuccess();
					}
					/**
					 * 添加缓存IP功能
					 */
					String target = msg.getData().getString(
							SocketHelper.TARGETIP,
							SocketHelper.getTargetIP(mContext));
					SocketHelper.saveTargetIP(target, mContext);

					if (isOpenBroadcast) {
						Intent mintent = new Intent(
								LCSocketBroadcast.SOCKET_BROADCAST_ACTION);
						mintent.putExtra(LCSocketBroadcast.SOCKET_FLAGS,
								SocketThread.SOCKET_CONNECTED);
						sendBroadcast(mintent);
					}
					break;
				case SocketNioThread.SOCKET_CLOSE:
					LogUtils.i(TAG, "搜索远程服务IP成功,连接失败");
					for (OnSocketServiceConnectListen mConnectListen : mConnectListens) {
						mConnectListen.OnConnectFaile();
					}
					if (isOpenBroadcast) {
						Intent mintent = new Intent(
								LCSocketBroadcast.SOCKET_BROADCAST_ACTION);
						mintent.putExtra(LCSocketBroadcast.SOCKET_FLAGS,
								SocketThread.SOCKET_DESTORY);
						sendBroadcast(mintent);
					}
					break;
				case SocketNioThread.SOCKET_FIND_ID_BEGIN:
					for (OnSocketServiceConnectListen mConnectListen : mConnectListens) {
						mConnectListen.OnConnectBegin();
					}
					if (isOpenBroadcast) {
						Intent mintent = new Intent(
								LCSocketBroadcast.SOCKET_BROADCAST_ACTION);
						mintent.putExtra(LCSocketBroadcast.SOCKET_FLAGS,
								SocketThread.SOCKET_BEGIN_CONNECT);
						sendBroadcast(mintent);
					}
					LogUtils.i(TAG, "开始搜索远程服务IP");
					break;
				case SocketNioThread.SOCKET_FIND_ID_FAILED:
					LogUtils.i(TAG, "搜索远程服务IP失败");
					LogUtils.i(TAG, "搜索远程服务IP成功,连接失败");
					for (OnSocketServiceConnectListen mConnectListen : mConnectListens) {
						mConnectListen.OnConnectFaile();
					}
					if (isOpenBroadcast) {
						Intent mintent = new Intent(
								LCSocketBroadcast.SOCKET_BROADCAST_ACTION);
						mintent.putExtra(LCSocketBroadcast.SOCKET_FLAGS,
								SocketThread.SOCKET_DESTORY);
						sendBroadcast(mintent);
					}
					break;
				case SocketNioThread.SOCKET_FIND_ID_SUCCESS:
					LogUtils.i(TAG, "搜索远程服务IP成功,开始建立连接。。。");
					break;
				case SocketNioThread.SOCKET_RECEIVE_DATA:
					byte[] data = (byte[]) msg.obj;
					for (OnReceiveDataListen mReceiveDataListen : mReceiveDataListens) {
						mReceiveDataListen.OnReceiveData(data);
					}
					break;
				case SocketNioThread.SOCKET_SEND_FINISH:
					byte[] senddata = (byte[]) msg.obj;
					for (OnSocketSendDataSuccessListen mSocketSendDataSuccessListen : mSendDataSuccessListens) {
						mSocketSendDataSuccessListen.OnSocketSendDataSuccess(
								senddata, curBaseBean);
					}
					break;
				default:
					break;
				}
			}
		};

	}

	/**
	 * 自动连接
	 */
	@Override
	public void ConnectByAuto() {
		if (mAsyn == null) {
			mAsyn = new findIPAsyn();
			mAsyn.execute();
		}
	}

	/**
	 * 手动连接
	 * 
	 * @param remoteIP
	 * @param remotePort
	 */
	@Override
	public void ConnectByHand(String remoteIP, int remotePort) {

		// TODO Auto-generated method stub
		if (mNIOSocketThread != null
				&& mNIOSocketThread.getSocketChannel() != null
				&& mNIOSocketThread.getSocketChannel().isConnected()) {
			Toast.makeText(mContext, "服务器已成功连接", Toast.LENGTH_SHORT).show();
		} else {
			if (mNIOSocketThread != null) {
				mNIOSocketThread.destoryThread();
				mNIOSocketThread = null;
			}
			initSocket(remoteIP, remotePort);
		}

	}

	private void initSocket(String remoteIP, int remotePort) {
		if (remoteIP != null && mNIOSocketThread == null) {
			mNIOSocketThread = SocketNioThread.getInstance(mContext);
			mNIOSocketThread.InitRemoteAddr(remoteIP, remotePort);
			mNIOSocketHandler = mNIOSocketThread.getHandler();
			mNIOSocketThread.setOnNIOConnectListen(new OnNIOConnectListen() {

				@Override
				public void OnNIOContectBegined(SocketChannel socketchannel,
						String message) {
					// TODO Auto-generated method stub
					LogUtils.i(TAG, "Socket Contect Begined:" + message);
					Message msg = mUIHandler.obtainMessage();
					msg.what = SocketNioThread.SOCKET_BEGIN_CONNECT;
					msg.sendToTarget();
				}

				@Override
				public void OnNIOConnectSuccess(SocketChannel socketchannel,
						String message) {
					// TODO Auto-generated method stub
					LogUtils.i(TAG, "Socket Contect Success:" + message);
					Message msg = mUIHandler.obtainMessage();
					msg.what = SocketNioThread.SOCKET_CONNECT_FINISH;
					msg.getData().putString(
							SocketHelper.TARGETIP,
							socketchannel.socket().getInetAddress()
									.getHostName());
					msg.sendToTarget();
				}

				@Override
				public void OnNIOConnectFaile(SocketChannel socketchannel,
						String message) {
					// TODO Auto-generated method stub
					LogUtils.i(TAG, "Socket Contect Faile:" + message);
					Message msg = mUIHandler.obtainMessage();
					msg.what = SocketNioThread.SOCKET_CLOSE;
					msg.sendToTarget();
				}
			});

			mNIOSocketThread
					.setOnNIOSendDataSuccessListen(new OnNIOSendDataSuccessListen() {

						@Override
						public void OnNIOSendDataSuccess(byte[] data) {
							// TODO Auto-generated method stub
							String datastr = DataUtils.byte2HexStr(data);
							LogUtils.i(TAG, "socket send Data success:"
									+ datastr);
							Message msg = mUIHandler.obtainMessage();
							msg.what = SocketNioThread.SOCKET_SEND_FINISH;
							msg.obj = data;
							msg.sendToTarget();
						}
					});

			mNIOSocketThread
					.setOnNIOReceiveDataListen(new OnNIOReceiveDataListen() {

						@Override
						public void OnNIOReceiveData(byte[] data) {
							// TODO Auto-generated method stub
							String datastr = new String(data);
							LogUtils.i(TAG, "socket Receive Data:" + datastr);
							Message msg = mUIHandler.obtainMessage();
							msg.what = SocketNioThread.SOCKET_RECEIVE_DATA;
							msg.obj = data;
							msg.sendToTarget();
						}
					});

			/**
			 * 请求进行连接
			 */
			Message msg = mNIOSocketHandler.obtainMessage();
			msg.what = SocketNioThread.SOCKET_REQUEST_LINK;
			msg.sendToTarget();
		}
	}

	private void DestoryAllResource() {
		if (mNIOSocketThread != null) {
			mNIOSocketThread.destoryThread();
		}
		if (mAsyn != null) {
			mAsyn.cancel(true);
			mAsyn = null;
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		DestoryAllResource();
		super.onDestroy();
	}

	private class findIPAsyn extends AsyncTask<Void, Integer, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			Message msg = mUIHandler.obtainMessage();
			msg.what = SocketNioThread.SOCKET_FIND_ID_BEGIN;
			msg.sendToTarget();
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
			Message msg = mUIHandler.obtainMessage();
			msg.sendToTarget();
			if (result == null) {
				Message msgfailed = mUIHandler.obtainMessage();
				msgfailed.what = SocketThread.SOCKET_FIND_ID_FAILED;
				msgfailed.sendToTarget();
			} else {
				Message msgsuccess = mUIHandler.obtainMessage();
				msgsuccess.what = SocketThread.SOCKET_FIND_ID_SUCCESS;
				msgsuccess.sendToTarget();
				initSocket(result, 0);
			}
		}

	}

}
