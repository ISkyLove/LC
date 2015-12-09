package com.lc.view;

import com.lc.R;
import com.lc.common.DataUtils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ColorViewBottom implements OnClickListener {
	private Context mContext;
	private View rootView;
	private SeekBar mSeekBar;
	private SeekBar mSeekBar2;
	private TextView tvColor1;
	private TextView tvColor2;
	private TextView tvColor3;
	private TextView tvColor4;
	private TextView tvColor5;
	private TextView tvColor6;
	private TextView tvColor7;
	private LinearLayout llColor;
	private String[] mCardColor = new String[] { "#ffffff", "#ff9900",
			"#ff3300", "#6666cc", "#663399", "#660066", "#66CC00" };

	private OnProgressUpdateListen mOnProgressUpdateListen;
	private OnProgressUpdate2Listen mOnProgressUpdate2Listen;

	private OnColorCardClickListen mColorCardClickListen;

	public void setOnProgressUpdateListen(
			OnProgressUpdateListen onProgressUpdateListen) {
		this.mOnProgressUpdateListen = onProgressUpdateListen;
	}

	public void setOnProgressUpdate2Listen(
			OnProgressUpdate2Listen onProgressUpdate2Listen) {
		this.mOnProgressUpdate2Listen = onProgressUpdate2Listen;
	}

	public void setOnColorCardClickListen(
			OnColorCardClickListen onColorCardClickListen) {
		this.mColorCardClickListen = onColorCardClickListen;
	}

	public interface OnProgressUpdateListen {
		void OnProgressUpdate(int progress);
	}

	public interface OnProgressUpdate2Listen {
		void OnProgressUpdate2(int progress);
	}

	public interface OnColorCardClickListen {
		void OnColorCardClick(int position, int color);
	}

	public ColorViewBottom(Context context) {
		this.mContext = context;
		rootView = LayoutInflater.from(mContext).inflate(
				R.layout.v1_color_view_bottom, null);
		initView();
		initEvent();
	}

	public View getRootView() {
		return rootView;
	}

	public void setSeeekBarVisiable(int seekbar1, int seekbar2) {
		mSeekBar.setVisibility(seekbar1);
		mSeekBar2.setVisibility(seekbar2);
	}

	private void initView() {
		// TODO Auto-generated method stub
		mSeekBar = (SeekBar) rootView
				.findViewById(R.id.v1_color_view_bottom_right);
		mSeekBar2 = (SeekBar) rootView
				.findViewById(R.id.v1_color_view_bottom_right2);
		tvColor1 = (TextView) rootView.findViewById(R.id.v1_color_view_pick_1);
		tvColor2 = (TextView) rootView.findViewById(R.id.v1_color_view_pick_2);
		tvColor3 = (TextView) rootView.findViewById(R.id.v1_color_view_pick_3);
		tvColor4 = (TextView) rootView.findViewById(R.id.v1_color_view_pick_4);
		tvColor5 = (TextView) rootView.findViewById(R.id.v1_color_view_pick_5);
		tvColor6 = (TextView) rootView.findViewById(R.id.v1_color_view_pick_6);
		tvColor7 = (TextView) rootView.findViewById(R.id.v1_color_view_pick_7);
		llColor = (LinearLayout) rootView.findViewById(R.id.v1_color_view_pick);
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		tvColor1.setOnClickListener(this);
		tvColor2.setOnClickListener(this);
		tvColor3.setOnClickListener(this);
		tvColor4.setOnClickListener(this);
		tvColor5.setOnClickListener(this);
		tvColor6.setOnClickListener(this);
		tvColor7.setOnClickListener(this);
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (DataUtils.isFastMove()) {
					if (mOnProgressUpdateListen != null) {
						mOnProgressUpdateListen.OnProgressUpdate(progress);
					}
				}
			}
		});
		mSeekBar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (!DataUtils.isFastMove()) {
					if (mOnProgressUpdate2Listen != null) {
						if (progress < 10) {
							progress = 0;
						}

						if (progress > 245) {
							progress = 255;
						}
						mOnProgressUpdate2Listen.OnProgressUpdate2(progress);
					}
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (mColorCardClickListen != null) {
			int position = -1;
			switch (v.getId()) {
			case R.id.v1_color_view_pick_1:
				position = 0;
				break;
			case R.id.v1_color_view_pick_2:
				position = 1;
				break;
			case R.id.v1_color_view_pick_3:
				position = 2;
				break;
			case R.id.v1_color_view_pick_4:
				position = 3;
				break;
			case R.id.v1_color_view_pick_5:
				position = 4;
				break;
			case R.id.v1_color_view_pick_6:
				position = 5;
				break;
			case R.id.v1_color_view_pick_7:
				position = 6;
				break;

			default:
				break;
			}
			if (position >= 0 && (position) < mCardColor.length) {
				mColorCardClickListen.OnColorCardClick(position,
						Color.parseColor(mCardColor[position]));
			}
		}
	}
}
