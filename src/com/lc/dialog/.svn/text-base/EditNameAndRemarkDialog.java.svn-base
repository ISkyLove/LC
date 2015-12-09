package com.lc.dialog;

import com.lc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

/**
 * 编辑名字和备注对话框
 * 
 * @author LC work room
 * 
 */
public class EditNameAndRemarkDialog {

	private Context mContext;
	private LCBaseDialog mBaseDialog;
	private EditText etName; // 名字
	private EditText etRemark; // 备注
	private IChangeNameAndRemarkInterface changeNameAndRemark; // 点击确定回调

	public EditNameAndRemarkDialog(Context context) {
		mContext = context;
		init();
	}

	@SuppressLint("InflateParams")
	private void init() {
		mBaseDialog = new LCBaseDialog(mContext);
		View contentView = LayoutInflater.from(mContext).inflate(
				R.layout.v1_edit_name_and_remark_dlg_layout, null);
		etName = (EditText) contentView
				.findViewById(R.id.v1_lc_setting_favor_edit_name);
		etRemark = (EditText) contentView
				.findViewById(R.id.v1_lc_setting_favor_edit_extern);

		mBaseDialog.setTitle("修改名字和备注");
		mBaseDialog.isCancelableOnTouchOutside(true);
		mBaseDialog.setDuration(700);
		mBaseDialog.setButtonRightText("确定");
		mBaseDialog.setButtonLeftText("取消");
		mBaseDialog.setCustomView(contentView, mContext);

		mBaseDialog.setButtonLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mBaseDialog.dismiss();
			}
		});

		mBaseDialog.setButtonRightClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (changeNameAndRemark != null) {
					String name = etName.getText().toString().trim();
					String remark = etRemark.getText().toString().trim();
					// 没有输入返回空
					changeNameAndRemark.changeNameAndRemark(
							name.equals("") ? null : name,
							remark.equals("") ? null : remark);
				}

			}
		});
	}

	public void show() {
		if (!mBaseDialog.isShowing()) {
			mBaseDialog.show();
		}
	}

	public void dismiss() {
		if (mBaseDialog.isShowing()) {
			mBaseDialog.dismiss();
		}
	}

	public IChangeNameAndRemarkInterface getChangeNameAndRemark() {
		return changeNameAndRemark;
	}

	public void setChangeNameAndRemark(
			IChangeNameAndRemarkInterface changeNameAndRemark) {
		this.changeNameAndRemark = changeNameAndRemark;
	}

	public interface IChangeNameAndRemarkInterface {
		public void changeNameAndRemark(String name, String remark);
	}
}
