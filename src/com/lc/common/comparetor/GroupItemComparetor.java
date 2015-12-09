package com.lc.common.comparetor;

import java.util.Comparator;

import com.lc.data.GroupItem;
import com.lc.data.LightItem;

public class GroupItemComparetor implements Comparator<GroupItem> {

	@Override
	public int compare(GroupItem lhs, GroupItem rhs) {
		if(lhs.getItemId() < rhs.getItemId()){
			return -1;
		}else if(lhs.getItemId() > rhs.getItemId()){
			return 1;
		}
		return 0;
	}

}
