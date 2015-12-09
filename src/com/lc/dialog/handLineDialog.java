package com.lc.dialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lc.R;
import com.lc.socket.SocketHelper;

import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class handLineDialog {
	private Context mContext;
	private LCBaseDialog mBaseDialog;
	private View rootView;
	private EditText etIP;
	private EditText etPort;
	private OnHandlineDialogListen mHandlineDialogListen;

	public void setOnHandlineDialogListen(
			OnHandlineDialogListen onHandlineDialogListen) {
		this.mHandlineDialogListen = onHandlineDialogListen;
	}

	public interface OnHandlineDialogListen {
		void OnOkClick(String IP, int port);

		void OnCancalClick();
	}

	public void setBK(String color) {
		if (mBaseDialog != null) {
			if (mBaseDialog.getBaseView() != null) {
				mBaseDialog.getBaseView().setBackgroundColor(
						Color.parseColor(color));
			}
		}
		if (rootView != null) {
			rootView.setBackgroundColor(Color.parseColor(color));
		}
	}

	public void setBK(int color) {
		if (mBaseDialog != null) {
			if (mBaseDialog.getBaseView() != null) {
				mBaseDialog.getBaseView().setBackgroundColor(color);
			}
		}
		if (rootView != null) {
			rootView.setBackgroundColor(color);
		}
	}

	public handLineDialog(Context context) {
		mContext = context;
		mBaseDialog = new LCBaseDialog(mContext);
		rootView = LayoutInflater.from(mContext).inflate(
				R.layout.hand_line_socket_layout, null);
		etIP = (EditText) rootView.findViewById(R.id.hand_line_IP);
		etPort = (EditText) rootView.findViewById(R.id.hand_line_PORT);
		etIP.setText(SocketHelper.getTargetIP(mContext));
		etIP.setSelection(etIP.getText().length());
		etPort.setText(String.valueOf(SocketHelper.getTargetPort(mContext)));
		mBaseDialog.setTitle("温馨提示");
		mBaseDialog.isCancelableOnTouchOutside(true);
		mBaseDialog.setDuration(700);
		mBaseDialog.setButtonRightText("连接");
		mBaseDialog.setButtonLeftText("取消");
		mBaseDialog.setCustomView(rootView, mContext);
		mBaseDialog.setButtonLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mHandlineDialogListen != null) {
					mHandlineDialogListen.OnCancalClick();
				}
			}
		});
		mBaseDialog.setButtonRightClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String IP = etIP.getText().toString();
				String PORT = etPort.getText().toString();
				int port = Integer.parseInt(PORT);
				Pattern patternIP = Pattern
						.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
				Matcher matcherIP = patternIP.matcher(IP);
				if (matcherIP.matches()) {
					Pattern patternPort = Pattern.compile("^[0-9_]+$");
					Matcher matcherPort = patternPort.matcher(PORT);
					if (matcherPort.matches() && port > 1023) {
						if (mHandlineDialogListen != null) {
							mHandlineDialogListen.OnOkClick(IP, port);
						}
					} else {
						Toast.makeText(mContext, "输入的端口错误", Toast.LENGTH_SHORT);
					}
				} else {
					Toast.makeText(mContext, "输入的IP格式错误", Toast.LENGTH_SHORT);
				}
			}
		});
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
				mBaseDialog = null;
			}
		}
	}
}
