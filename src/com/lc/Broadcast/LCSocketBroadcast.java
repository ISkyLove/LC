package com.lc.Broadcast;

import com.lc.socket.SocketThread;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LCSocketBroadcast extends BroadcastReceiver {
	public static String TAG = "SocketBroadcast";
	public static String SOCKET_BROADCAST_ACTION = "com.lc.Broadcast.LCSocketBroadcast.action";
	public static String SOCKET_FLAGS = "socket_flag";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		final int flag = intent.getIntExtra(SOCKET_FLAGS, 0);
		switch (flag) {
		case SocketThread.SOCKET_BEGIN_CONNECT:

			break;
		case SocketThread.SOCKET_DESTORY:

			break;
		case SocketThread.SOCKET_CONNECTED:

			break;
		default:
			break;
		}
	}

}
