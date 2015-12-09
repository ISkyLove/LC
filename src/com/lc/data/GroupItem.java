package com.lc.data;

import android.content.Context;

public class GroupItem extends BaseItem {
	
	private boolean select = false;

	public GroupItem(Context context) {
		super(context);
	}

	public boolean isContainObject(LightItem childItem){
		
		for(int i=0;i<mChildlist.size();i++){
			LightItem mItem=(LightItem)mChildlist.get(i);
			if(mItem.getItemId()==childItem.getItemId()){
				return true;
			}
		}
		return false;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}


}
