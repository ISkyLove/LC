package com.lc.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;

import com.lc.R;
import com.lc.common.GridViewBaseAdapter;
import com.lc.data.SenceItem;

public class SenceAdapter extends GridViewBaseAdapter {

	/**
	 * �Ƿ��ѡ��Ĭ��Ϊ��ѡ
	 */
	private boolean isMutilClick = false;

	public SenceAdapter(Context context, List list, boolean ismuilclick) {
		super(context, list);
		// TODO Auto-generated constructor stub
		this.isMutilClick = ismuilclick;
	}

	private int cur_position = -1;

	public void setFocusPosition(int position) {
		this.cur_position = position;
	}

	public int getCurFocusPosition() {
		return this.cur_position;
	}

	@Override
	protected void bindView(int position, ViewHolder viewholder) {
		// TODO Auto-generated method stub
		SenceItem item = (SenceItem) mList.get(position);
		viewholder.tvItemLeft.setBackgroundDrawable(item.getItemLeftDra());
		viewholder.tvItemRight.setVisibility(View.GONE);
		viewholder.tvItemMiddle.setText(item.getName());
		/**
		 * ��ѡ��ʽ
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
		 * ��ѡ��ʽ
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
