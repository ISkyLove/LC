package com.lc.dialog;

import com.lc.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * �趨ѡ��ʱ�����Ի���
 * 
 * @author LC work room
 *
 */
public class SetTimeDialog {
	
	private Context mContext;
	private LCBaseDialog mBaseDialog;
	private int time = 1;						//���趨��ʱ��
	private EditText etTimeEditor;				//�趨ʱ��ı༭et
	private String preText;						//��һ���趨��ʱ��
	private TimerGetterInterface timerGetter;	//�������趨ʱ��Ľӿ�
	
	public SetTimeDialog(Context context) {
		mContext = context;
		initViews();
	}
	
	@SuppressLint("InflateParams") 
	private void initViews() {
		mBaseDialog = new LCBaseDialog(mContext);
		View contentView = LayoutInflater.from(mContext).inflate(R.layout.v1_edit_time_dialog_layout, null);
		etTimeEditor = (EditText) contentView.findViewById(R.id.edit_time_time_et);
		
		preText = etTimeEditor.getText().toString();
		
		Button btnPlus = (Button) contentView.findViewById(R.id.edit_time_plus_btn);
		Button btnMinus = (Button) contentView.findViewById(R.id.edit_time_minus_btn);
		//ʱ��+1
		btnPlus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				time++;
				etTimeEditor.setText(String.valueOf(time));
			}
		});
		//ʱ��0��С��1��Ϊ1
		btnMinus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				time--;
				if(time < 1){
					time = 1;
				}
				etTimeEditor.setText(String.valueOf(time));
			}
		});
		
		//��ʱ��ʱС��1�趨Ϊ��һ�ε�ʱ��
		etTimeEditor.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String string = etTimeEditor.getText().toString();
				if(string.equals("")){
					time = 1;
					return;
				}
				
				if(string.startsWith("-")){
					Toast.makeText(mContext, "�������븺��", Toast.LENGTH_SHORT).show();
					etTimeEditor.setText(preText);
				}else if(Integer.valueOf(string) > 255){
					Toast.makeText(mContext, "���ֻ������3600��", Toast.LENGTH_SHORT).show();
					etTimeEditor.setText(255 + "");
					time = 255;
				}else{
					time = Integer.valueOf(string);
					preText = string;
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		mBaseDialog.setTitle("�趨ʱ��");
		mBaseDialog.isCancelableOnTouchOutside(true);
		mBaseDialog.setDuration(700);
		mBaseDialog.setButtonRightText("ȷ��");
		mBaseDialog.setButtonLeftText("ȡ��");
		mBaseDialog.setCustomView(contentView, mContext);
		mBaseDialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				etTimeEditor.setFocusable(false);
				etTimeEditor.setEnabled(false);
			}
		});
		
		//ȷ��ʱ���û�ȡʱ��ӿ�
		mBaseDialog.setButtonLeftClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mBaseDialog.dismiss();
				
			}
		});
		
		mBaseDialog.setButtonRightClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(timerGetter != null){
					timerGetter.onGetTime(time);
					mBaseDialog.dismiss();
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
	
	public TimerGetterInterface getTimerGetter() {
		return timerGetter;
	}

	public void setTimerGetter(TimerGetterInterface timerGetter) {
		this.timerGetter = timerGetter;
	}

	/**
	 * ��ȡ���趨��ʱ��Ľӿڣ����÷�Ҫʵ������ӿڣ������޷���ȡʱ��ֵ
	 * 
	 * @author LC work room
	 *
	 */
	public interface TimerGetterInterface{
		public void onGetTime(int time);
	}
	
	

}
