package com.lc.common.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

/**
 * 基础数据类 默认包含id、自定义名称、备注、颜色以及一个默认的向下包含的数据列表mChildlist
 * 
 * @author LC work room
 * 
 */
public class LCBaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5239584875954273859L;
	protected int id;
	/**
	 * 向下嵌套数据列表
	 */
	protected List mChildlist;
	/**
	 * 自定义名称
	 */
	protected String name = "item";
	/**
	 * 备注
	 */
	protected String remark = "remark";
	protected int mcolor = Color.parseColor("#FFFFFF");

	public LCBaseBean() {
		mChildlist = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getColor() {
		return mcolor;
	}

	public void setColor(int color) {
		this.mcolor = color;
	}

	public List getChildData() {
		return mChildlist;
	}

	public void changeChildData(List data) {
		this.mChildlist = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void addChild(LCBaseBean child){
		if(this.mChildlist == null){
			this.mChildlist = new ArrayList<>();
		}
		mChildlist.add(child);
	}
	
	public boolean removeChild(int postion){
		if(this.mChildlist == null){
			return false;
		}else{
			if(postion >= this.mChildlist.size()){
				return false;
			}else{
				this.mChildlist.remove(postion);
				return true;
			}
		}
	}

}
