package com.lc.view.adapter;

import java.util.List;

import android.content.Context;

import com.lc.R;
import com.lc.common.GridViewBaseAdapter;
import com.lc.data.GroupItem;

public class GroupAdapter extends GridViewBaseAdapter {

	private int cur_position = -1;
	/**
	 * 是否多选，默认为单选
	 */
	private boolean isMutilClick = false;

	@SuppressWarnings("rawtypes")
	public GroupAdapter(Context context, List list, boolean ismuilclick) {
		super(context, list);
		this.isMutilClick = ismuilclick;
	}

	public void setFocusPosition(int position) {
		this.cur_position = position;
	}

	public int getCurFocusPosition() {
		return this.cur_position;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void bindView(int position, ViewHolder viewholder) {
		GroupItem item = (GroupItem) mList.get(position);
		viewholder.tvItemLeft.setBackgroundDrawable(item.getItemLeftDra());
		viewholder.tvItemRight.setBackgroundDrawable(item.getItemRightDra());
		viewholder.tvItemMiddle.setText(item.getName());
		/**
		 * 单选样式
		 */
		if (!isMutilClick) {
			if (position == cur_position) {
				viewholder.rlLayout
						.setBackgroundResource(R.drawable.v1_base_item_btn_press);
			} else {
				viewholder.rlLayout
						.setBackgroundResource(R.drawable.v1_base_item_btn_unpress);
			}
		}
		/**
		 * 多选样式
		 */
		if (isMutilClick) {
			if (item.isItemFoceus()) {
				viewholder.rlLayout
						.setBackgroundResource(R.drawable.v1_base_item_btn_press);
			} else {
				viewholder.rlLayout
						.setBackgroundResource(R.drawable.v1_base_item_btn_unpress);
			}
		}
	}

}
