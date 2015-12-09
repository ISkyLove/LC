package com.lc.view.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lc.R;
import com.lc.common.GridViewBaseAdapter;
import com.lc.data.BaseItem;
import com.lc.data.GroupItem;

public class SencePannerAdapter extends BaseAdapter {

	private int cur_position = -1;

	public void setFocusPosition(int position) {
		this.cur_position = position;
	}

	public int getCurFocusPosition() {
		return this.cur_position;
	}

	private View rootView = null;
	private Context mContext = null;
	protected List mList = new ArrayList<BaseItem>();

	public SencePannerAdapter(Context context, List list) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mList != null) {
			return mList.size();
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
		ViewHolder mHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.v1_base_item_small_layout, null);
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
		GroupItem item = (GroupItem) mList.get(position);
		mHolder.tvItemLeft.setBackgroundDrawable(item.getItemLeftDra());
		mHolder.tvItemRight.setBackgroundDrawable(item.getItemRightDra());
		mHolder.tvItemMiddle.setText(item.getName());
		AbsListView.LayoutParams param = new AbsListView.LayoutParams(300, 120);
		convertView.setLayoutParams(param);
		return convertView;
	}

	public void changeData(List list) {
		this.mList = list;
		this.notifyDataSetChanged();
	}

	public List<GroupItem> getList() {
		return mList;
	}

	protected class ViewHolder {
		public RelativeLayout rlLayout;
		public TextView tvItemLeft;
		public TextView tvItemMiddle;
		public TextView tvItemRight;
		public View tvItemRightEx;
	}

}
