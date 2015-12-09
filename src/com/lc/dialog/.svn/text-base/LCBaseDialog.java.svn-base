package com.lc.dialog;

import com.lc.R;
import com.lc.animation.BaseEffects;
import com.lc.animation.ColorUtils;
import com.lc.animation.Effectstype;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LCBaseDialog extends Dialog implements DialogInterface {

	private static Context mContext;

	private TextView tvTitle;

	private LinearLayout llTop;

	private LinearLayout llContent;

	private View baseView;

	private Effectstype type = null;

	private Button mButtonLeft;

	private Button mButtonRight;

	private int mDuration = -1;

	private boolean isCancelable = true;

	public LCBaseDialog(Context context) {
		super(context, R.style.dialog_untran);

		init(context);

	}

	public LCBaseDialog(Context context, int theme) {
		super(context, R.style.dialog_untran);
		init(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View getBaseView() {
		return baseView;
	}

	private void init(Context context) {
		mContext = context;
		baseView = View.inflate(context, R.layout.base_dialog_layout, null);

		llTop = (LinearLayout) baseView.findViewById(R.id.base_dialog_top);
		llContent = (LinearLayout) baseView
				.findViewById(R.id.base_dialog_content);

		tvTitle = (TextView) baseView.findViewById(R.id.base_dialog_title);

		mButtonLeft = (Button) baseView.findViewById(R.id.base_dialog_btn_left);
		mButtonRight = (Button) baseView
				.findViewById(R.id.base_dialog_btn_right);
		setContentView(baseView);
		this.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialogInterface) {

				if (type == null) {
					type = Effectstype.Flipv;
				}
				start(type);

			}
		});

	}

	public void setTitle(CharSequence title) {
		toggleView(llTop, title);
		tvTitle.setText(title);
	}

	public void setDuration(int duration) {
		this.mDuration = duration;
	}

	public void setEffect(Effectstype type) {
		this.type = type;
	}

	public void setButtonLeftText(CharSequence text) {
		toggleView(mButtonLeft, text);
		mButtonLeft.setText(text);
	}

	public void setButtonRightText(CharSequence text) {
		toggleView(mButtonRight, text);
		mButtonRight.setText(text);
	}

	public void setButtonLeftClick(View.OnClickListener click) {
		mButtonLeft.setOnClickListener(click);
	}

	public void setButtonRightClick(View.OnClickListener click) {
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		mButtonRight.setOnClickListener(click);
	}

	public void setCustomView(int resId, Context context) {
		View customView = View.inflate(context, resId, null);
		if (llContent.getChildCount() > 0) {
			llContent.removeAllViews();
		}
		llContent.addView(customView);
	}

	public void setCustomView(View view, Context context) {
		if (llContent.getChildCount() > 0) {
			llContent.removeAllViews();
		}
		llContent.addView(view);
	}

	public void setBtnVisiable(int leftvisiable, int rightvisiable) {
		mButtonLeft.setVisibility(leftvisiable);
		mButtonRight.setVisibility(rightvisiable);
	}

	public void isCancelableOnTouchOutside(boolean cancelable) {
		this.isCancelable = cancelable;
		this.setCanceledOnTouchOutside(cancelable);
	}

	public void isCancelable(boolean cancelable) {
		this.isCancelable = cancelable;
		this.setCancelable(cancelable);
	}

	private void toggleView(View view, Object obj) {
		if (obj == null) {
			view.setVisibility(View.GONE);
		} else {
			view.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void show() {
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		super.show();
	}

	private void start(Effectstype type) {
		BaseEffects animator = type.getAnimator();
		if (mDuration != -1) {
			animator.setDuration(Math.abs(mDuration));
		}
		animator.start(baseView);
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}
}
