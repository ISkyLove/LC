package com.lc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.util.Log;

/**
 * Ӧ���쳣����
 * 
 * @author Lin
 * 
 */
public class LCUncatchExcaptionHandler implements UncaughtExceptionHandler {
	private Context mContext;

	private static String TAG = LCUncatchExcaptionHandler.class.getSimpleName();

	public static String EXCEPTION_BASEFILE_PATH = Environment
			.getExternalStorageDirectory() + "/LC/EXCEPTION";

	/**
	 * ϵͳĬ���쳣����
	 */
	private UncaughtExceptionHandler mHandler = null;

	/**
	 * Ӧ����Ϣ
	 */
	private Map<String, String> minfos = new HashMap<String, String>();

	private static LCUncatchExcaptionHandler mLcUncatchExcaptionHandler = null;

	private LCUncatchExcaptionHandler(Context context) {
		this.mContext = context;
	}

	public static synchronized LCUncatchExcaptionHandler getInstance(
			Context context) {
		if (mLcUncatchExcaptionHandler == null) {
			mLcUncatchExcaptionHandler = new LCUncatchExcaptionHandler(context);
		}
		return mLcUncatchExcaptionHandler;
	}

	public void init() {
		if (mLcUncatchExcaptionHandler != null) {
			mHandler = Thread.getDefaultUncaughtExceptionHandler();
			Thread.setDefaultUncaughtExceptionHandler(mLcUncatchExcaptionHandler);
		}

	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		if (ex != null) {
			StringBuffer sb = new StringBuffer();
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			ex.printStackTrace(printWriter);
			Throwable cause = ex.getCause();
			while (cause != null) {
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			printWriter.close();

			String result = writer.toString();
			initAppInfo();
			for (Map.Entry<String, String> entry : minfos.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				sb.append(key + "=" + value + "\n");
			}
			sb.append(result);
			Log.e(TAG, sb.toString());
			writeExceptionInFile(sb.toString());

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e(TAG, "error : ", e);
			}

			/**
			 * �ر�Ӧ��
			 */
			// android.os.Process.killProcess(android.os.Process.myPid());
			// System.exit(1);

			/**
			 * �������ɲ�׽�쳣������Ӧ��
			 */
			Intent intent = new Intent();
			intent.setClass(mContext, LCMainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(intent);
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	private void initAppInfo() {

		try {
			PackageManager mpm = mContext.getPackageManager();
			PackageInfo minfo = mpm.getPackageInfo(mContext.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			minfos.put("packageName", minfo.packageName);
			minfos.put("packageName", minfo.versionName);
			minfos.put("packageName", String.valueOf(minfo.versionCode));
			minfos.put("lastUpdateTime", String.valueOf(minfo.firstInstallTime));
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "initAppInfo:" + "Ӧ���쳣����ȡӦ�ð汾��Ϣʧ��," + e.getMessage());
		}
	}

	private void writeExceptionInFile(String content) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			try {
				StringBuffer mBuffer = new StringBuffer();
				mBuffer.append("LCException");
				/**
				 * ��־ʱ�� ��ʱ����
				 */
				// SimpleDateFormat mFormat = new SimpleDateFormat(
				// "yyyy-MM-dd--HH-mm-ss");
				// String time = mFormat.format(new Date());
				// mBuffer.append(time);
				String filePath = EXCEPTION_BASEFILE_PATH + File.separator
						+ mBuffer.toString();
				File mfile = new File(EXCEPTION_BASEFILE_PATH);
				if (!mfile.exists()) {
					mfile.mkdirs();
				}
				FileOutputStream mFileOutputStream;
				mFileOutputStream = new FileOutputStream(filePath);
				mFileOutputStream.write(content.getBytes());
				mFileOutputStream.flush();
				mFileOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG,
						"writeExceptionInFile:" + "��¼Ӧ���쳣�ر�ʧ��,"
								+ e.getMessage());
			}

		}
	}
}
