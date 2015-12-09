package com.lc.socket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * socket通讯类V2.0 使用NIO非阻塞模式设计Socket，添加了容错机制，在稳定性提高不少，但布局较麻烦
 * 
 * @author Lin
 * 
 */
public class SocketNioThread extends Thread {

	private Context mContext;

	public static String TAG = SocketNioThread.class.getSimpleName();

	public static int SOCKET_PORT = 1024;

	public static int CONNECT_TIME_OUT = 1000 * 30;

	public static int RECEIVE_TIME_OUT = 1000 * 60 * 10;

	public static int RECEIVE_SIZE = 1024 * 2;
	/**
	 * 开始连接
	 */
	public static final int SOCKET_BEGIN_CONNECT = 1;
	/**
	 * 连接成功
	 */
	public static final int SOCKET_CONNECT_FINISH = 2;
	/**
	 * 连接失败
	 */
	public static final int SOCKET_CONNECT_FLAILED = 3;
	/**
	 * 发送数据
	 */
	public static final int SOCKET_SEND_DATA = 4;
	/**
	 * 发送数据失败
	 */
	public static final int SOCKET_SEND_FINISH = 5;
	/**
	 * 接受数据
	 */
	public static final int SOCKET_RECEIVE_DATA = 6;
	/**
	 * 关闭socket通道
	 */
	public static final int SOCKET_CLOSE = 7;
	/**
	 * 请求连接命令
	 */
	public static final int SOCKET_REQUEST_LINK = 8;

	public static final int SOCKET_FIND_ID_BEGIN = 8;

	public static final int SOCKET_FIND_ID_FAILED = 9;

	public static final int SOCKET_FIND_ID_SUCCESS = 10;

	OnNIOReceiveDataListen mReceiveDataListen;

	OnNIOSendDataSuccessListen mSendDataSuccessListen;

	OnNIOConnectListen mConnectListen;

	static SocketNioThread mThread = null;

	Handler mHandler;

	SocketChannel mSocketChannel = null;

	Selector mSelector = null;

	InetSocketAddress mSocketRemoteAddr;

	InetSocketAddress mSocketLocalAddr;

	ConnectAndReadableThread mConnectAndReadableThread = null;

	ByteBuffer receivebuffer = ByteBuffer.allocateDirect(RECEIVE_SIZE);

	private SocketNioThread(Context context) {
		this.mContext = context;
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case SOCKET_BEGIN_CONNECT:
					if (mConnectListen != null) {
						mConnectListen.OnNIOContectBegined(mSocketChannel,
								mSocketRemoteAddr.toString());
					}
					break;
				case SOCKET_CONNECT_FINISH:
					if (mConnectListen != null) {
						mConnectListen.OnNIOConnectSuccess(mSocketChannel,
								"Sokect channel connect success");
					}
					break;
				case SOCKET_CONNECT_FLAILED:
					if (mConnectListen != null) {
						mConnectListen.OnNIOConnectFaile(mSocketChannel,
								"Sokect channel connect failed");
					}
					break;
				case SOCKET_SEND_DATA:
					byte[] msenddata = (byte[]) msg.obj;
					if (sendData(msenddata) >= 0) {
						Message msgfailed = mHandler.obtainMessage();
						msgfailed.obj = msenddata;
						msgfailed.what = SocketNioThread.SOCKET_SEND_FINISH;
						msgfailed.sendToTarget();
					} else {
						Message msgfailed = mHandler.obtainMessage();
						msgfailed.what = SocketNioThread.SOCKET_CONNECT_FLAILED;
						msgfailed.sendToTarget();
						Message msgclose = mHandler.obtainMessage();
						msgclose.what = SocketNioThread.SOCKET_CLOSE;
						msgclose.sendToTarget();
					}
					break;
				case SOCKET_SEND_FINISH:
					if (mSendDataSuccessListen != null) {
						byte[] msenddatasuccess = (byte[]) msg.obj;
						mSendDataSuccessListen
								.OnNIOSendDataSuccess(msenddatasuccess);
					}
					break;
				case SOCKET_RECEIVE_DATA:
					byte[] mreceivedata = (byte[]) msg.obj;
					receiveData(mreceivedata);
					break;
				case SOCKET_CLOSE:
					destoryThread();
					break;
				case SOCKET_REQUEST_LINK:
					initNIOSocket();
					break;
				default:
					break;
				}
			}
		};
		try {
			String localIP = SocketHelper.getLocalIP(mContext);
			mSocketLocalAddr = new InetSocketAddress(localIP,
					SocketHelper.getTargetPort(mContext));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Log.i(TAG, "get local host IP faile:" + e1.getMessage());
		}

	}

	public static SocketNioThread getInstance(Context context) {
		if (mThread == null) {
			mThread = new SocketNioThread(context);
			mThread.start();
		}
		return mThread;
	}

	public interface OnNIOReceiveDataListen {
		void OnNIOReceiveData(byte[] data);
	}

	public interface OnNIOSendDataSuccessListen {
		void OnNIOSendDataSuccess(byte[] data);
	}

	/**
	 * 连接状态监听
	 * 
	 * @author Administrator
	 * 
	 */
	public interface OnNIOConnectListen {
		void OnNIOContectBegined(SocketChannel socketchannel, String message);

		void OnNIOConnectSuccess(SocketChannel socketchannel, String message);

		void OnNIOConnectFaile(SocketChannel socketchannel, String message);

	}

	public void setOnNIOReceiveDataListen(
			OnNIOReceiveDataListen receivenioDataListen) {
		mReceiveDataListen = receivenioDataListen;
	}

	public void setOnNIOSendDataSuccessListen(
			OnNIOSendDataSuccessListen onnioSendDataSuccessListen) {
		mSendDataSuccessListen = onnioSendDataSuccessListen;
	}

	public void setOnNIOConnectListen(OnNIOConnectListen nioconnectListen) {
		this.mConnectListen = nioconnectListen;
	}

	public Handler getHandler() {
		return mHandler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Looper.prepare();

		Looper.loop();
	}

	private void destroyNIOSocket() {

		try {
			if (mSelector != null) {
				mSelector.close();
				mSelector = null;
			}

			if (mSocketChannel != null) {
				mSocketChannel.close();
				mSocketChannel = null;
			}
			mConnectAndReadableThread = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i(TAG, "destroyNIOSocket failed:" + e.getMessage());
		}

	}

	private void receiveData(byte[] data) {
		if (data == null || data.length < 1) {
			return;
		}
		if (mReceiveDataListen != null) {
			mReceiveDataListen.OnNIOReceiveData(data);
		}
	}

	private int sendData(byte[] data) {
		int count = 0;
		if (data != null)
			Log.i(TAG, "socket channel ready to send data size:" + data.length);
		if (data == null || data.length < 1) {
			return count;
		}
		if (mSocketChannel != null && mSocketChannel.isOpen()
				&& mSocketChannel.isConnected()) {
			try {
				ByteBuffer mByteBuffer = ByteBuffer.allocate(data.length);
				mByteBuffer.put(data);
				mByteBuffer.flip();
				count = mSocketChannel.write(mByteBuffer);
				return count;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i(TAG, "write data to socket channel failed:" + e.getMessage());
				return -1;
			} finally {
			}

		} else {
			Log.i(TAG, "write data to socket channel failed:connext failed:the channel is closed");
			if (mConnectListen != null) {
				mConnectListen.OnNIOConnectFaile(mSocketChannel,
						"socket is  not connected or close");
			}
			return -1;
		}
	}

	private void initNIOSocket() {
		try {
			destroyNIOSocket();

			mSocketChannel = SocketChannel.open();
			mSocketChannel.configureBlocking(false);
			mSocketChannel.socket().setSoTimeout(RECEIVE_TIME_OUT);
			mSocketChannel.socket().setSoLinger(true, 1000);
			mSocketChannel.socket().setKeepAlive(true);

			mSelector = Selector.open();

			if (mSocketLocalAddr != null) {
				Log.i(TAG, "Local IP is:"
						+ mSocketLocalAddr.getAddress().toString());
			}
			if (mSocketRemoteAddr != null) {
				if (mConnectAndReadableThread == null) {
					mConnectAndReadableThread = new ConnectAndReadableThread();
					mConnectAndReadableThread.start();
				} else {
					mConnectAndReadableThread = null;
					mConnectAndReadableThread = new ConnectAndReadableThread();
					mConnectAndReadableThread.start();
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i(TAG, "init socket channel failed:" + e.getMessage());
			Message msgfailed = mHandler.obtainMessage();
			msgfailed.what = SocketNioThread.SOCKET_CONNECT_FLAILED;
			msgfailed.sendToTarget();
			Message msgclose = mHandler.obtainMessage();
			msgclose.what = SocketNioThread.SOCKET_CLOSE;
			msgclose.sendToTarget();
		}
	}

	public SocketChannel getSocketChannel() {
		return mSocketChannel;
	}

	/**
	 * 连接和接受数据事件监听线程
	 * 
	 * @author LC work room
	 * 
	 */
	private class ConnectAndReadableThread extends Thread {
		public ConnectAndReadableThread() {
			this.setDaemon(true);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				if (mSocketChannel != null && mSelector != null
						&& mSocketRemoteAddr != null) {
					mSocketChannel.register(mSelector, SelectionKey.OP_READ);
					Message msgbegin = mHandler.obtainMessage();
					msgbegin.what = SocketNioThread.SOCKET_BEGIN_CONNECT;
					msgbegin.sendToTarget();
					mSocketChannel.connect(mSocketRemoteAddr);

					while (mSelector != null && mSelector.isOpen()
							&& mSelector.select() > 0) {
						Iterator<SelectionKey> it = mSelector.selectedKeys()
								.iterator();
						while (it.hasNext()) {
							SelectionKey key = (SelectionKey) it.next();
							if (key.isConnectable()) {
								SocketChannel mConnectChannel = (SocketChannel) key
										.channel();
								if (mConnectChannel.isConnectionPending()) {
									if (mConnectChannel.finishConnect()) {
										mConnectChannel
												.register(
														mSelector,
														SelectionKey.OP_READ
																| SelectionKey.OP_WRITE);
										Message msgfinish = mHandler
												.obtainMessage();
										msgbegin.what = SocketNioThread.SOCKET_CONNECT_FINISH;
										msgbegin.sendToTarget();
									}
								}
							}
							if (key.isReadable()) {
								SocketChannel mReadChannel = (SocketChannel) key
										.channel();
								int count = 0;
								receivebuffer.clear();
								while ((count = mReadChannel
										.read(receivebuffer)) > 0) {
									receivebuffer.flip();
									byte[] mreceiveData = new byte[count];
									receivebuffer.get(mreceiveData);
									Message msgfinish = mHandler
											.obtainMessage();
									msgbegin.what = SocketNioThread.SOCKET_RECEIVE_DATA;
									msgbegin.obj = mreceiveData;
									msgbegin.sendToTarget();
								}
							}

							if (key.isWritable()) {

							}

						}

					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i(SocketNioThread.TAG,
						"ConnectAndReadableThread exception:" + e.getMessage());
				Message msg = mHandler.obtainMessage();
				msg.what = SocketNioThread.SOCKET_CONNECT_FLAILED;
				msg.sendToTarget();
			}

		}
	}

	public void InitRemoteAddr(String IP) {
		mSocketRemoteAddr = new InetSocketAddress(IP, SOCKET_PORT);
	}

	public void InitRemoteAddr(String IP, int port) {
		if (port > 1024) {
			SOCKET_PORT = port;
		}
		mSocketRemoteAddr = new InetSocketAddress(IP, SOCKET_PORT);
	}

	public void destoryThread() {
		destroyNIOSocket();
		mHandler.removeMessages(SOCKET_SEND_DATA);
		mHandler.removeMessages(SOCKET_SEND_FINISH);
		mHandler.removeMessages(SOCKET_RECEIVE_DATA);
		mHandler.removeMessages(SOCKET_BEGIN_CONNECT);
		mHandler.removeMessages(SOCKET_CONNECT_FINISH);
		mHandler.removeMessages(SOCKET_CONNECT_FLAILED);
		Message msg = mHandler.obtainMessage();
		msg.setTarget(null);
		mThread = null;
	}
}
