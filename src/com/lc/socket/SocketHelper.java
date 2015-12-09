package com.lc.socket;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.lc.common.DataUtils;
import com.lc.common.LogUtils;
import com.lc.data.DataConfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

/**
 * Socket ������
 * 
 * @author Lin
 * 
 */
public class SocketHelper {

	private static String TAG = SocketHelper.class.getSimpleName();

	public static int CHECK_TIME_OUT = 50;

	private static OnFindIPProgressListen mProgressListen = null;

	public static String TARGETIP = "targetIP";
	public static String TARGETPORT = "targetPort";
	public static String TARGETSSID = "targetSSID";
	public static String TARGETPW = "targetPW";

	private SocketHelper() {
	}

	public static interface OnFindIPProgressListen {
		void OnFindIPProgress(int percent);
	}

	public static void setOnFindIPProgressListen(
			OnFindIPProgressListen onFindIPProgressListen) {
		mProgressListen = onFindIPProgressListen;
	}

	/**
	 * ��ѰĿ�ķ�����IP
	 * 
	 * @return ���ط���Ҫ���Ŀ�ķ�����IP
	 */
	public static String findTargetIP(Context context) {
		String TargetIP = null;
		try {
			String adrs = getLocalIP(context);
			String adr = adrs.substring(0, adrs.lastIndexOf(".") + 1);
			for (int i = 0; i < 255; i++) {
				// int i = 104;
				String tarferadr = adr;
				Socket mSocket = new Socket();
				String targetIP = null;
				try {
					int percent = (int) (i / 255.0f * 100);
					if (mProgressListen != null) {
						mProgressListen.OnFindIPProgress(percent);
					}
					targetIP = tarferadr.concat(String.valueOf(i));
					InetSocketAddress mAddress = new InetSocketAddress(
							targetIP, SocketThread.SOCKET_PORT);
					mSocket.connect(mAddress, CHECK_TIME_OUT);
					if (mSocket != null && mSocket.isConnected()) {
						Log.i(TAG, "findTargetIP:" + targetIP);
						TargetIP = targetIP;
						saveTargetIP(TargetIP, context);
						mSocket.close();
						break;
					}
				} catch (SocketTimeoutException time) {
					LogUtils.i(TAG,
							"findTargetIP error time out:" + time.getMessage());
				} catch (Exception e) {

					LogUtils.i(TAG, "findTargetIP error:" + e.getMessage());

				} finally {
					if (!mSocket.isClosed()) {
						mSocket.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(TAG, "findTargetIP Exception:" + e.getMessage());
		}
		return TargetIP;
	}

	public static void saveTargetIP(String IP, Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				TARGETIP, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString(TARGETIP, IP);
		editor.commit();
	}

	public static String getTargetIP(Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				TARGETIP, Context.MODE_PRIVATE);
		return mySharedPreferences.getString(TARGETIP, getLocalIP(context));
	}
	
	

	public static void saveTargetPort(int port, Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				TARGETPORT, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putInt(TARGETPORT, port);
		editor.commit();
	}

	public static int getTargetPort(Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				TARGETPORT, Context.MODE_PRIVATE);
		return mySharedPreferences.getInt(TARGETPORT, SocketThread.SOCKET_PORT);
	}
	
	
	public static void saveTargetSSID(String ssid, Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				TARGETSSID, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString(TARGETSSID, ssid);
		editor.commit();
	}

	public static String getTargetSSID(Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				TARGETSSID, Context.MODE_PRIVATE);
		return mySharedPreferences.getString(TARGETSSID,"wicam");
	}
	
	public static void saveTargetPW(String pw, Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				TARGETPW, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString(TARGETPW, pw);
		editor.commit();
	}

	public static String getTargetPW(Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences(
				TARGETPW, Context.MODE_PRIVATE);
		return mySharedPreferences.getString(TARGETPW,"12345678");
	}

	public static String getLocalIP(Context context) {
		String ip = null;
		try {
			// ��ȡwifi����
			WifiManager wifiManager = (WifiManager) context
					.getSystemService(context.WIFI_SERVICE);
			// �ж�wifi�Ƿ���
			if (!wifiManager.isWifiEnabled()) {
				wifiManager.setWifiEnabled(true);
			}
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			ip = Formatter.formatIpAddress(wifiInfo.getIpAddress());
		} catch (Exception ex) {
			Log.i(TAG, ex.getMessage());
		}

		return ip;
	}

	/**
	 * ���WiFi�Ƿ��
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWiFiOpen(Context context) {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(context.WIFI_SERVICE);
		if (wifiManager.isWifiEnabled()) {
			return true;
		}
		return false;
	}

	/**
	 * �Ƿ�����Ŀ��wifi
	 * 
	 * @return
	 */
	public static boolean isTargetWifi(Context context) {
		if (isWiFiOpen(context)) {
			WifiManager wifi = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			LogUtils.d(TAG, "current WIFI info:" + "SSID " + info.getSSID()
					+ " IP " + getLocalIP(context));
			if (info.getSSID().equals(DataConfig.SSID)) {
				return true;
			}

		}

		return false;
	}
}
