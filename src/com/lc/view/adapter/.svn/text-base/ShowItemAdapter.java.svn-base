package com.lc.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lc.R;
import com.lc.common.GridViewBaseAdapter;
import com.lc.data.BaseItem;
import com.lc.data.GroupItem;
import com.lc.data.SenceItem;

public class ShowItemAdapter extends BaseAdapter {

	private Context mContext = null;
	protected List mList = null;
	private int cur_position = -1;

	public ShowItemAdapter(Context context, List list) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mList = list;
	}

	public void setFocusPosition(int position) {
		this.cur_position = position;
	}

	public int getCurFocusPosition() {
		return this.cur_position;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		if ((position % 2) == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mList != null) {
			return mList.size()*2;
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (mList != null) {
			return mList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		int type = getItemViewType(position);

		ViewHolder mHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.v1_base_item_layout, null);
			mHolder = new ViewHolder();
			mHolder.rlLayout = (RelativeLayout) convertView
					.findViewById(R.id.v1_base_item_layout);
			mHolder.tvItemLeft = (TextView) convertView
					.findViewById(R.id.v1_base_item_left);
			mHolder.tvItemMiddle = (TextView) convertView
					.findViewById(R.id.v1_base_item_midle);
			mHolder.tvItemRight = (TextView) convertView
					.findViewById(R.id.v1_base_item_right);
			mHolder.tvItemRightEx = convertView
					.findViewById(R.id.v1_base_item_right_ex);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}

		if (type == 0) {
			AbsListView.LayoutParams param = new AbsListView.LayoutParams(
					android.view.ViewGroup.LayoutParams.FILL_PARENT, 200);
			convertView.setLayoutParams(param);
			SenceItem item = (SenceItem) mList.get(position/2);
			mHolder.tvItemLeft.setBackgroundDrawable(item.getItemLeftDra());
			mHolder.tvItemRight.setVisibility(View.GONE);
			mHolder.tvItemMiddle.setText(item.getName());
			if (position == cur_position) {
				mHolder.rlLayout
						.setBackgroundResource(R.drawable.v1_base_item_btn_press);
			} else {
				mHolder.rlLayout
						.setBackgroundResource(R.drawable.v1_base_item_btn_unpress);
			}
		} else if (type == 1) {
			AbsListView.LayoutParams param = new AbsListView.LayoutParams(
					android.view.ViewGroup.LayoutParams.FILL_PARENT, 100);
			convertView.setLayoutParams(param);
			mHolder.tvItemMiddle.setVisibility(View.GONE);
			mHolder.tvItemRight.setVisibility(View.GONE);
			mHolder.tvItemLeft
					.setBackgroundResource(R.drawable.v1_setting_group_panner_add);
			mHolder.rlLayout.setBackgroundDrawable(null);

		}
		return convertView;
	}

	public void changeData(List list) {
		this.mList = list;
		this.notifyDataSetChanged();
	}

	protected class ViewHolder {
		public RelativeLayout rlLayout;
		public TextView tvItemLeft;
		public TextView tvItemMiddle;
		public TextView tvItemRight;
		public View tvItemRightEx;
	}

}
