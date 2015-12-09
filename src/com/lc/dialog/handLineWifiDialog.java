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

public class handLineWifiDialog {
	private Context mContext;
	private LCBaseDialog mBaseDialog;
	private View rootView;
	private EditText etSSID;
	private EditText etPW;
	private OnHandlineWifiDialogListen mHandlineDialogListen;

	public void setOnHandlineWifiDialogListen(
			OnHandlineWifiDialogListen onHandlineDialogListen) {
		this.mHandlineDialogListen = onHandlineDialogListen;
	}

	public interface OnHandlineWifiDialogListen {
		void OnOkClick(String ssid, String pw);

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
				mBaseDialog.getBaseView().setBackgroundColor(
						color);
			}
		}
		if (rootView != null) {
			rootView.setBackgroundColor(color);
		}
	}

	public handLineWifiDialog(Context context) {
		mContext = context;
		mBaseDialog = new LCBaseDialog(mContext);
		rootView = LayoutInflater.from(mContext).inflate(
				R.layout.hand_line_wifi_layout, null);
		etSSID = (EditText) rootView.findViewById(R.id.hand_line_SSID);
		etPW = (EditText) rootView.findViewById(R.id.hand_line_PASSWORD);
		etSSID.setText(SocketHelper.getTargetSSID(mContext));
		etSSID.setSelection(etSSID.getText().length());
		etPW.setText(String.valueOf(SocketHelper.getTargetPW(mContext)));
		mBaseDialog.setTitle("温馨提示");
		mBaseDialog.isCancelableOnTouchOutside(true);
		mBaseDialog.setDuration(700);
		mBaseDialog.setButtonRightText("确定");
		mBaseDialog.setButtonLeftText("取消");
		mBaseDialog.setCustomView(rootView, mContext);
		mBaseDialog.setButtonLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// if (mHandlineDialogListen != null) {
				// mHandlineDialogListen.OnCancalClick();
				// }
				mBaseDialog.dismiss();
			}
		});
		mBaseDialog.setButtonRightClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ssid = etSSID.getText().toString();
				String pw = etPW.getText().toString();
				SocketHelper.saveTargetSSID(ssid, mContext);
				SocketHelper.saveTargetPW(pw, mContext);
				if (mHandlineDialogListen != null) {
					mHandlineDialogListen.OnOkClick(ssid, pw);
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
