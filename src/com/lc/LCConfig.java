package com.lc;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Color;

import com.lc.common.LogUtils;
import com.lc.common.bean.ConfigBean;
import com.lc.common.bean.MemberBean;
import com.lc.group.bean.GroupBean;
import com.lc.sence.bean.SenceBean;
import com.lc.show.bean.ShowBean;

/**
 * �����࣬���û������������б�
 * 
 * @author LC work room
 *
 */
public class LCConfig {

	//��ʼ�������������°�װʱû������ʱ�������ṩ�޸������ӿڣ��޸ĺ�������ʼֵ��ͬ����ʼֵ������
	public static int INIT_LIGHTS_NUM = 64;		//��ʼ������
	public static int INIT_GROUPS_NUM = 16;		//��ʼ������
	public static int INIT_SENCES_NUM = 16;		//��ʼ��������
	public static int INIT_SHOWS_NUM = 16;		//��ʼshow����

	private static ConfigBean config;					//���ö����ں�ȫ���б������ֶ�

	/**
	 * ��ȡ���ö���
	 * 
	 * @return
	 */
	public static ConfigBean getConfig() {
		return config;
	}

	/**
	 * ���û�ָ����ö���ÿ�ν��������潫�����һ��
	 * 
	 * @param configBean	���ö��󸱱�
	 */
	public static void retainConfig(ConfigBean configBean) {
		LCConfig.config = configBean;
		if (LCConfig.config == null) {
			init();
		}
	}

	public static void setMemberNum(int memberNum) {
		if (config != null) {
			config.setMemberNum(memberNum);
		}
	}

	public static void setGroupNum(int groupNum) {
		if (config != null) {
			config.setGroupNum(groupNum);
		}
	}

	public static void setSenceNum(int senceNum) {
		if (config != null) {
			config.setSenceNum(senceNum);
		}
	}

	public static void setShowNum(int showNum) {
		if (config != null) {
			config.setShowNum(showNum);
		}
	}

	public static int getMemberNum() {
		return config == null ? 0 : config.getMemberNum();
	}

	public static int getGroupNum() {
		return config == null ? 0 : config.getGroupNum();
	}

	public static int getSenceNum() {
		return config == null ? 0 : config.getSenceNum();
	}

	public static int getShowNum() {
		return config == null ? 0 : config.getShowNum();
	}

	public static void setMembers(ArrayList<MemberBean> members) {
		if (members == null
				|| (members != null && members.size() <= LCConfig
						.getMemberNum())) {
			if (config != null) {
				config.setMembers(members);
			}
		} else {
			LogUtils.e(LCConfig.class, "�Ƶ��������������õ�����");
		}
	}

	public static void setGroups(ArrayList<GroupBean> groups) {
		if (groups == null
				|| (groups != null && groups.size() <= LCConfig.getGroupNum())) {
			if (config != null) {
				config.setGroups(groups);
			}
		} else {
			LogUtils.e(LCConfig.class, "����������������õ�����");
		}
	}

	public static void setSences(ArrayList<SenceBean> sences) {
		if (sences == null
				|| (sences != null && sences.size() <= LCConfig.getSenceNum())) {

			if (config != null) {
				config.setSences(sences);
			}
		} else {
			LogUtils.e(LCConfig.class, "�������������������õ�����");
		}
	}

	public static void setShows(ArrayList<ShowBean> shows) {
		if (shows == null
				|| (shows != null && shows.size() <= LCConfig.getShowNum())) {
			if (config != null) {
				config.setShows(shows);
			}
		} else {
			LogUtils.e(LCConfig.class, "Show���������������õ�����");
		}
	}

	public static ArrayList<MemberBean> getMembers() {
		return config == null ? null : config.getMembers();
	}

	public static ArrayList<GroupBean> getGroups() {
		return config == null ? null : config.getGroups();
	}

	public static ArrayList<SenceBean> getSences() {
		return config == null ? null : config.getSences();
	}

	public static ArrayList<ShowBean> getShows() {
		return config == null ? null : config.getShows();
	}

	/**
	 * ��ʼ�����ö��󣬰������������ͳ�ʼ���б�
	 */
	private static void init() {
		config = new ConfigBean();
		
		LCConfig.setMemberNum(INIT_LIGHTS_NUM);
		LCConfig.setGroupNum(INIT_GROUPS_NUM);
		LCConfig.setSenceNum(INIT_SENCES_NUM);
		LCConfig.setShowNum(INIT_SHOWS_NUM);
		
		ArrayList<MemberBean> members = new ArrayList<MemberBean>();
		for (int i = 0; i < INIT_LIGHTS_NUM; i++) {
			MemberBean bean = new MemberBean();
			bean.setId(i);
			bean.setName(String.valueOf(i + 1) + "�ŵ�");
			members.add(bean);
		}
		LCConfig.setMembers(members);

		ArrayList<GroupBean> groups = new ArrayList<GroupBean>();
		for (int i = 0; i < INIT_GROUPS_NUM; i++) {
			GroupBean bean = new GroupBean();
			bean.setId(i);
			bean.setName(String.valueOf(i + 1) + "����");
			groups.add(bean);
		}
		LCConfig.setGroups(groups);

		ArrayList<SenceBean> sences = new ArrayList<SenceBean>();
		for (int i = 0; i < INIT_SENCES_NUM; i++) {
			SenceBean bean = new SenceBean();
			HashMap<Integer, Integer> colorArray = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> timeArray = new HashMap<Integer, Integer>();
			for(int j = 0; j < 5; j++){
				colorArray.put(j, Color.parseColor("#FFFFFF"));
				timeArray.put(j, 1);
			}
			bean.setColorArray(colorArray);
			bean.setDivideTimes(timeArray);
			bean.setId(i);
			bean.setName(String.valueOf(i + 1) + "�ų���");
			sences.add(bean);
		}
		LCConfig.setSences(sences);

		ArrayList<ShowBean> shows = new ArrayList<ShowBean>();
		for (int i = 0; i < INIT_SHOWS_NUM; i++) {
			ShowBean bean = new ShowBean();
			HashMap<Integer, Integer> timeArray = new HashMap<Integer, Integer>();
			for(int j = 0; j < 2; j++){
				timeArray.put(j, 1);
			}
			bean.setId(i);
			bean.setName(String.valueOf(i + 1) + "��Show");
			shows.add(bean);
		}
		LCConfig.setShows(shows);
	}
	
	/**
	 * ����Ϊ��ʼֵ
	 */
	public static void reset(){
		LCConfig.init();
	}

}
