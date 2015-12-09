package com.lc.dialog;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * ɾ���Ի���
 * 
 * @author LC work room
 * 
 */
public class DeleteDialog {

	private Context mContext;
	private LCBaseDialog mBaseDialog;
	private IDeleteCallbackInterface deleteCallback; // ���ȷ��ʱ�ص�

	public DeleteDialog(Context context) {
		mContext = context;
		mBaseDialog = new LCBaseDialog(mContext);
		init();
	}

	public void init() {
		mBaseDialog.setCanceledOnTouchOutside(true);
		mBaseDialog.setTitle("�Ƿ��Ƴ���ѡ�");
		mBaseDialog.setButtonLeftText("ȡ��");
		mBaseDialog.setButtonLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		mBaseDialog.setButtonRightText("ȷ��");
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
