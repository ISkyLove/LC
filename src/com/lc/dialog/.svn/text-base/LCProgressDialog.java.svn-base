package com.lc.dialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lc.R;
import com.lc.socket.SocketHelper;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class LCProgressDialog {
	private Context mContext;
	private LCBaseDialog mBaseDialog;
	private View rootView;

	public LCProgressDialog(Context context) {
		mContext = context;
		mBaseDialog = new LCBaseDialog(mContext);
		rootView = LayoutInflater.from(mContext).inflate(
				R.layout.v1_progress_layout, null);
		mBaseDialog.setTitle("初始化数据中，请稍候。。。");
		mBaseDialog.isCancelableOnTouchOutside(false);
		mBaseDialog.setCancelable(false);
		mBaseDialog.setDuration(700);
		mBaseDialog.setBtnVisiable(View.GONE, View.GONE);
		mBaseDialog.setCustomView(rootView, mContext);
	}

	public void showDialog() {
		if (mBaseDialog != null && !mBaseDialog.isShowing()) {
			mBaseDialog.show();
		}
	}

	public void destoryDialog() {
		if (mBaseDialog != null) {
			if (mBaseDialog.isShowing()) {
				mBaseDialog.dismiss();
			}
		}
	}
}
