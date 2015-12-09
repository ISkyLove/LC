package com.lc.dialog;

import com.lc.LCConfig;
import com.lc.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

/**
 * 重置对话框
 * 
 * @author LC work room
 * 
 */
public class ResetDialog {

	private Context mContext;
	private LCBaseDialog mBaseDialog;
	private EditText etLightNum;
	private EditText etGroupNum;
	private EditText etSenceNum;
	private EditText etShowNum;

	public ResetDialog(Context context) {
		mContext = context;
		mBaseDialog = new LCBaseDialog(mContext);
		init();
	}

	private OnOKClickListen mOkClickListen;

	public void setOnOKClickListen(OnOKClickListen onOkClickListen) {
		this.mOkClickListen = onOkClickListen;
	}

	public interface OnOKClickListen {
		void OnOKClick();
	}

	public void init() {
		View content = LayoutInflater.from(mContext).inflate(
				R.layout.v1_reset_dialog_layout, null);
		etGroupNum = (EditText) content
				.findViewById(R.id.v1_reset_group_num_et);
		etLightNum = (EditText) content
				.findViewById(R.id.v1_reset_light_num_et);
		etSenceNum = (EditText) content
				.findViewById(R.id.v1_reset_sence_num_et);
		etShowNum = (EditText) content.findViewById(R.id.v1_reset_show_num_et);
		mBaseDialog.setCanceledOnTouchOutside(true);
		mBaseDialog.setTitle("重置数据？");
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
				reset();
				if (mOkClickListen != null) {
					mOkClickListen.OnOKClick();
				}
			}
		});
		mBaseDialog.setCustomView(content, mContext);
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

	private void reset() {
		LCConfig.INIT_GROUPS_NUM = Integer.valueOf(etGroupNum.getText()
				.toString().equals("") ? 16 + "" : etGroupNum.getText()
				.toString());
		LCConfig.INIT_LIGHTS_NUM = Integer.valueOf(etLightNum.getText()
				.toString().equals("") ? 64 + "" : etLightNum.getText()
				.toString());
		LCConfig.INIT_SENCES_NUM = Integer.valueOf(etSenceNum.getText()
				.toString().equals("") ? 16 + "" : etSenceNum.getText()
				.toString());
		LCConfig.INIT_SHOWS_NUM = Integer.valueOf(etShowNum.getText()
				.toString().equals("") ? 16 + "" : etShowNum.getText()
				.toString());
		LCConfig.reset();

	}
}
