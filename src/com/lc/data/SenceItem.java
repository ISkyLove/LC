package com.lc.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;

/**
 * 场景数据基础类
 * 
 * @author LC work room
 * 
 */
public class SenceItem extends BaseItem {
	public SenceItem(Context context) {
		super(context);
	}

	private boolean isSelect = false;

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	private int[] pannColor = new int[] { Color.parseColor("#ffffff"),
			Color.parseColor("#ffffff"), Color.parseColor("#ffffff"),
			Color.parseColor("#ffffff"), Color.parseColor("#ffffff") };
	private List<GroupItem> mChildPaneData1 = new ArrayList<GroupItem>();
	private List<GroupItem> mChildPaneData2 = new ArrayList<GroupItem>();
	private List<GroupItem> mChildPaneData3 = new ArrayList<GroupItem>();
	private List<GroupItem> mChildPaneData4 = new ArrayList<GroupItem>();
	private List<GroupItem> mChildPaneData5 = new ArrayList<GroupItem>();

	public int[] getPannColor() {
		return pannColor;
	}

	public void setPannColor(int[] pannColor) {
		this.pannColor = pannColor;
	}

	public List<GroupItem> getChildPaneData1() {
		return mChildPaneData1;
	}

	public void setChildPaneData1(List<GroupItem> ChildPaneData1) {
		this.mChildPaneData1 = ChildPaneData1;
	}

	public List<GroupItem> getChildPaneData2() {
		return mChildPaneData2;
	}

	public void setChildPaneData2(List<GroupItem> ChildPaneData2) {
		this.mChildPaneData2 = ChildPaneData2;
	}

	public List<GroupItem> getChildPaneData3() {
		return mChildPaneData3;
	}

	public void setChildPaneData3(List<GroupItem> ChildPaneData3) {
		this.mChildPaneData3 = ChildPaneData3;
	}

	public List<GroupItem> getChildPaneData4() {
		return mChildPaneData4;
	}

	public void setChildPaneData4(List<GroupItem> ChildPaneData4) {
		this.mChildPaneData4 = ChildPaneData4;
	}

	public List<GroupItem> getChildPaneData5() {
		return mChildPaneData5;
	}

	public void setChildPaneData5(List<GroupItem> ChildPaneData5) {
		this.mChildPaneData5 = ChildPaneData5;
	}

}
