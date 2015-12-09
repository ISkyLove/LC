package com.lc.dialog;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 删除对话框
 * 
 * @author LC work room
 * 
 */
public class DeleteDialog {

	private Context mContext;
	private LCBaseDialog mBaseDialog;
	private IDeleteCallbackInterface deleteCallback; // 点击确定时回调

	public DeleteDialog(Context context) {
		mContext = context;
		mBaseDialog = new LCBaseDialog(mContext);
		init();
	}

	public void init() {
		mBaseDialog.setCanceledOnTouchOutside(true);
		mBaseDialog.setTitle("是否移除所选项？");
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

				if (deleteCallback != null) {
					deleteCallback.deleteCallback();
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

	public IDeleteCallbackInterface getDeleteCallback() {
		return deleteCallback;
	}

	public void setDeleteCallback(IDeleteCallbackInterface deleteCallback) {
		this.deleteCallback = deleteCallback;
	}

	public interface IDeleteCallbackInterface {
		public void deleteCallback();
	}
}
