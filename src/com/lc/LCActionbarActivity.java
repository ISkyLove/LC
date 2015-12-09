package com.lc;

import com.lc.common.bean.LCBaseBean;
import com.lc.service.ILCSocketService;
import com.lc.service.LCSocketService;
import com.lc.service.LCSocketService.BinderServicePolice;
import com.lc.service.LCSocketService.OnReceiveDataListen;
import com.lc.service.LCSocketService.OnSocketSendDataSuccessListen;
import com.lc.service.LCSocketService.OnSocketServiceConnectListen;
import com.lc.socket.SocketThread.OnSendDataSuccessListen;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * 基本activity 默认包含socket连接状态的监听，数据获取接口
 * 
 * @author LC work room
 * 
 */
public class LCActionbarActivity extends ActionBarActivity implements
		OnReceiveDataListen, OnSocketServiceConnectListen,
		OnSocketSendDataSuccessListen {
	protected ILCSocketService mSocketService = null;
	protected boolean isSocketOpen = false;
	protected Context mContext = null;
	protected boolean isBind = false;
	protected ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			mSocketService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mSocketService = (ILCSocketService) (((BinderServicePolice) service)
					.getService());
			mSocketService
					.addSocketServiceConnectListen(LCActionbarActivity.this);
			mSocketService.addReceiveDataListen(LCActionbarActivity.this);
			mSocketService
					.addOnSocketSendDataSuccessListen(LCActionbarActivity.this);
			mSocketService.ConnectByAuto();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mContext = this;
		Intent mService = new Intent(LCSocketService.SOCKET_SERVICE_ACTION);
		isBind = getApplicationContext().bindService(mService,
				mServiceConnection, Service.BIND_AUTO_CREATE);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		super.setContentView(layoutResID);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	protected void destroyService() {
		if (mSocketService != null) {
			mSocketService.removeSocketServiceConnectListen(this);
			mSocketService.removeReceiveDataListen(this);
		}
		if (isBind) {
			getApplicationContext().unbindService(mServiceConnection);
			isBind = false;
		}
	}

	@Override
	public void OnConnectBegin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnConnectFaile() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnConnectSuccess() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnReceiveData(byte[] data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnSocketSendDataSuccess(byte[] data, LCBaseBean baseBean) {
		// TODO Auto-generated method stub

	}

}
