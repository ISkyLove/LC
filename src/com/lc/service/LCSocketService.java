package com.lc.service;

import java.net.Socket;
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
 * socket 网路连接服务类
 * 
 * @author LC work room
 * 
 */
public class LCSocketService extends Service implements ILCSocketService {

	private Context mContext;
	private SocketThread mSocketThread = null;
	private Handler mSocketHandler;
	private Handler mUIHandler;
	private List<OnSocketSendDataSuccessListen> mSendDataSuccessListens;
	private List<OnSocketServiceConnectListen> mConnectListens;
	private List<OnReceiveDataListen> mReceiveDataListens;
	public static String TAG = "LCSocketService";
	private BinderServicePolice mBinderServicePolice = null;
	private findIPAsyn mAsyn = null;
	private static LCBaseBean curBaseBean = null;

	public static final String SOCKET_SERVICE_ACTION = "com.lc.service.LCSocketService.action";

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
		if (mSocketHandler != null) {
			mSocketHandler.removeMessages(SocketThread.SOCKET_SEND);
			Message msg = mSocketHandler.obtainMessage();
			msg.obj = data;
			msg.what = SocketThread.SOCKET_SEND;
			msg.sendToTarget();
			curBaseBean = basebean;
		}
	}

	public class BinderServicePolice extends Binder {
		public LCSocketService getService() {
			return LCSocketService.this;
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
		mConnectListens = new ArrayList<LCSocketService.OnSocketServiceConnectListen>();
		mReceiveDataListens = new ArrayList<LCSocketService.OnReceiveDataListen>();
		mSendDataSuccessListens = new ArrayList<LCSocketService.OnSocketSendDataSuccessListen>();
		mUIHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case SocketThread.SOCKET_BEGIN_CONNECT:
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
				case SocketThread.SOCKET_CONNECTED:
					LogUtils.i(TAG, "搜索远程服务IP成功,连接完成");
					for (OnSocketServiceConnectListen mConnectListen : mConnectListens) {
						mConnectListen.OnConnectSuccess();
					}

					if (isOpenBroadcast) {
						Intent mintent = new Intent(
								LCSocketBroadcast.SOCKET_BROADCAST_ACTION);
						mintent.putExtra(LCSocketBroadcast.SOCKET_FLAGS,
								SocketThread.SOCKET_CONNECTED);
						sendBroadcast(mintent);
					}
					break;
				case SocketThread.SOCKET_DESTORY:
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
				case SocketThread.SOCKET_FIND_ID_BEGIN:
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
				case SocketThread.SOCKET_FIND_ID_FAILED:
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
				case SocketThread.SOCKET_FIND_ID_SUCCESS:
					LogUtils.i(TAG, "搜索远程服务IP成功,开始建立连接。。。");
					break;
				case SocketThread.SOCKET_RECEIVE:
					byte[] data = (byte[]) msg.obj;
					for (OnReceiveDataListen mReceiveDataListen : mReceiveDataListens) {
						mReceiveDataListen.OnReceiveData(data);
					}
					break;
				case SocketThread.SOCKET_SEND_SUCCESS:
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
		// if (mSocketThread != null && mSocketThread.getSocket() != null
		// && mSocketThread.getSocket().isConnected()) {
		// if (SocketHelper.getTargetIP(mContext).equals(remoteIP)
		// && SocketHelper.getTargetPort(mContext) == remotePort) {
		// Toast.makeText(mContext, "服务器已成功连接", Toast.LENGTH_SHORT).show();
		// return;
		// }
		// }

		if (mSocketThread != null) {
			mSocketThread.destoryThread();
			mSocketThread = null;
		}
		initSocket(remoteIP, remotePort);

	}

	private void initSocket(final String remoteIP, int remotePort) {
		if (remoteIP != null && mSocketThread == null) {
			mSocketThread = SocketThread.getInstance(mContext);
			mSocketThread.InitRemoteAddr(remoteIP, remotePort);
			mSocketHandler = mSocketThread.getHandler();
			mSocketThread.setOnConnectListen(new ConnectListen() {
				@Override
				public void OnContectBegined(Socket socket, String message) {
					// TODO Auto-generated method stub
					LogUtils.i(TAG, "Socket Contect Begined:" + message);
					Message msg = mUIHandler.obtainMessage();
					msg.what = SocketThread.SOCKET_BEGIN_CONNECT;
					msg.sendToTarget();
				}

				@Override
				public void OnConnectSuccess(Socket socket, String message) {
					// TODO Auto-generated method stub
					LogUtils.i(TAG, "Socket Contect Success:" + message);
					SocketHelper.saveTargetIP(remoteIP, mContext);
					Message msg = mUIHandler.obtainMessage();
					msg.what = SocketThread.SOCKET_CONNECTED;
					msg.sendToTarget();
				}

				@Override
				public void OnConnectFaile(Socket socket, String message) {
					// TODO Auto-generated method stub
					LogUtils.i(TAG, "Socket Contect Faile:" + message);
					Message msg = mUIHandler.obtainMessage();
					msg.what = SocketThread.SOCKET_DESTORY;
					msg.sendToTarget();
				}

			});
			mSocketThread
					.setOnSendDataSuccessListen(new OnSendDataSuccessListen() {

						@Override
						public void OnSendDataSuccess(byte[] data) {
							// TODO Auto-generated method stub
							String datastr = DataUtils.byte2HexStr(data);
							LogUtils.i(TAG, "socket send Data success:"
									+ datastr);
							Message msg = mUIHandler.obtainMessage();
							msg.what = SocketThread.SOCKET_SEND_SUCCESS;
							msg.obj = data;
							msg.sendToTarget();
						}
					});
			mSocketThread.setOnReceiveDataListen(new ReceiveDataListen() {

				@Override
				public void OnReceiveData(byte[] data) {
					// TODO Auto-generated method stub
					String datastr = new String(data);
					LogUtils.i(TAG, "socket Receive Data:" + datastr);
					Message msg = mUIHandler.obtainMessage();
					msg.what = SocketThread.SOCKET_RECEIVE;
					msg.obj = data;
					msg.sendToTarget();
				}
			});
			/**
			 * 请求进行连接
			 */
			Message msg = mSocketHandler.obtainMessage();
			msg.what = SocketThread.SOCKET_CONNECT;
			msg.sendToTarget();
		}
	}

	private void DestoryAllResource() {
		if (mSocketThread != null) {
			mSocketThread.destoryThread();
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
			msg.what = SocketThread.SOCKET_FIND_ID_BEGIN;
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
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	@Override
	public boolean isConnnect() {
		// TODO Auto-generated method stub
		if (mSocketThread != null && mSocketThread.getSocket() != null) {
			Socket msocket = mSocketThread.getSocket();
			if (msocket.isConnected()) {
				return true;
			}
		}
		return false;
	}

}
