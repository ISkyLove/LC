package com.lc.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.lc.R;
import com.lc.common.GridViewBaseAdapter;
import com.lc.data.ShowItem;

public class ShowAdapter extends GridViewBaseAdapter {

	private int cur_position = -1;

	public void setFocusPosition(int position) {
		this.cur_position = position;
	}

	public int getCurFocusPosition() {
		return this.cur_position;
	}

	public ShowAdapter(Context context, List list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void bindView(int position, ViewHolder viewholder) {
		// TODO Auto-generated method stub
		ShowItem item = (ShowItem) mList.get(position);
		viewholder.tvItemLeft.setBackgroundDrawable(item.getItemLeftDra());
		viewholder.tvItemRight.setVisibility(View.GONE);
		viewholder.tvItemMiddle.setText(item.getName());
		if (position == cur_position) {
			viewholder.rlLayout
					.setBackgroundResource(R.drawable.v1_base_item_btn_press);
		} else {
			viewholder.rlLayout
					.setBackgroundResource(R.drawable.v1_base_item_btn_unpress);
		}
	}

}
