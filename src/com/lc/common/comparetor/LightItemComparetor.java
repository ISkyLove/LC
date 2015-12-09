package com.lc.common.comparetor;

import java.util.Comparator;

import com.lc.data.LightItem;

public class LightItemComparetor implements Comparator<LightItem> {

	@Override
	public int compare(LightItem lhs, LightItem rhs) {
		if(lhs.getItemId() < rhs.getItemId()){
			return -1;
		}else if(lhs.getItemId() > rhs.getItemId()){
			return 1;
		}
		return 0;
	}

}
