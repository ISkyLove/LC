package com.lc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;

public class LCLogPrint {
	private Context mContext;

	private Process mProcess = null;

	public static String TAG = LCLogPrint.class.getSimpleName();

	public static String LOG_BASEFILE_PATH = Environment
			.getExternalStorageDirectory() + "/LC/LOG";

	public LCLogPrint(Context context) {
		this.mContext = context;
	}

	public void makeLogFile() {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						mProcess = Runtime.getRuntime().exec(" logcat -v time");
						String filePath = LOG_BASEFILE_PATH + File.separator
								+ "Log";
						File mfile = new File(LOG_BASEFILE_PATH);
						if (!mfile.exists()) {
							mfile.mkdirs();
						}
						FileOutputStream mFileOutputStream;
						mFileOutputStream = new FileOutputStream(filePath);
						InputStream mInputStream = mProcess.getInputStream();
						byte[] mbyte = new byte[1024];
						do {
							int lenght = mInputStream.read(mbyte);
							mFileOutputStream.write(mbyte, 0, lenght);
						} while (mInputStream.read() != -1);
						mInputStream.close();
						mFileOutputStream.flush();
						mFileOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();

		}

	}

	public void destoryProcess() {
		if (mProcess != null) {
			mProcess.destroy();
		}
	}
}
