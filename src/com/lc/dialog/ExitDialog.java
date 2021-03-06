package com.lc.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 退出对话框
 * 
 * @author LC work room
 * 
 */
public class ExitDialog {

	private Context mContext;
	private LCBaseDialog mBaseDialog;
	private IExitInterface exitCallback; // 点击确定时回调

	public ExitDialog(Context context) {
		mContext = context;
		mBaseDialog = new LCBaseDialog(mContext);
		init();
	}

	public void setBK(String color) {
		if (mBaseDialog != null) {
			if (mBaseDialog.getBaseView() != null) {
				mBaseDialog.getBaseView().setBackgroundColor(
						Color.parseColor(color));
			}
		}
	}

	public void setBK(int color) {
		if (mBaseDialog != null) {
			if (mBaseDialog.getBaseView() != null) {
				mBaseDialog.getBaseView().setBackgroundColor(color);
			}
		}
	}

	public void init() {
		mBaseDialog.setCanceledOnTouchOutside(true);
		mBaseDialog.setTitle("是否退出？");
		mBaseDialog.setButtonLeftText("取消");
		mBaseDialog.setButtonLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		mBaseDialog.setButtonRightText("确定");
		mBaseDialog.setButtonRightClick(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (exitCallback != null) {
					exitCallback.exitCallback();
					dismiss();
				}
			}
		});
	}

	public void show() {
		if (mBaseDialog != null && !mBaseDialog.isShowing()) {
			mBaseDialog.show();
		}
	}

	public void dismiss() {
		if (mBaseDialog != null && mBaseDialog.isShowing()) {
			mBaseDialog.dismiss();
		}
	}

	public IExitInterface getExitCallback() {
		return exitCallback;
	}

	public void setExitCallback(IExitInterface exitCallback) {
		this.exitCallback = exitCallback;
	}

	public interface IExitInterface {
		public void exitCallback();
	}
}
