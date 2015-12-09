package com.lc.group.bean;

import java.util.ArrayList;

import com.lc.common.bean.LCBaseBean;
import com.lc.common.bean.MemberBean;
/**
 * group后台数据
 * 
 * @author LC work room
 *
 */
public class GroupBean extends LCBaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4777565442519251549L;
	private ArrayList<MemberBean> childList;	//组中包含的灯成员列表

	public ArrayList<MemberBean> getChildList() {
		return childList;
	}

	public void setChildList(ArrayList<MemberBean> childList) {
		this.childList = childList;
	}

}
