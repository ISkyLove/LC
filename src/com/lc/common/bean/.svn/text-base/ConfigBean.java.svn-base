package com.lc.common.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.lc.group.bean.GroupBean;
import com.lc.sence.bean.SenceBean;
import com.lc.show.bean.ShowBean;

/**
 * 设置对象bean
 * 
 * @author LC work room
 *
 */
public class ConfigBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8663029751695823119L;
	private int memberNum;						//灯数量
	private int groupNum;						//组数量
	private int senceNum;						//场景数量
	private int showNum;						//show数量
	private ArrayList<MemberBean> members;		//灯列表
	private ArrayList<GroupBean> groups;		//组列表
	private ArrayList<SenceBean> sences;		//场景列表
	private ArrayList<ShowBean> shows;			//show列表

	public int getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(int memberNum) {
		if (memberNum >= 0)
			this.memberNum = memberNum;
	}

	public int getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(int groupNum) {
		if (groupNum >= 0)
			this.groupNum = groupNum;
	}

	public int getSenceNum() {
		return senceNum;
	}

	public void setSenceNum(int senceNum) {
		if (senceNum >= 0)
			this.senceNum = senceNum;
	}

	public int getShowNum() {
		return showNum;
	}

	public void setShowNum(int showNum) {
		if (showNum >= 0)
			this.showNum = showNum;
	}

	public ArrayList<MemberBean> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<MemberBean> members) {
		this.members = members;
	}

	public ArrayList<GroupBean> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<GroupBean> groups) {
		this.groups = groups;
	}

	public ArrayList<SenceBean> getSences() {
		return sences;
	}

	public void setSences(ArrayList<SenceBean> sences) {
		this.sences = sences;
	}

	public ArrayList<ShowBean> getShows() {
		return shows;
	}

	public void setShows(ArrayList<ShowBean> shows) {
		this.shows = shows;
	}

	
}
