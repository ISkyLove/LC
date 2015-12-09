package com.lc.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lc.LCConfig;
import com.lc.common.bean.MemberBean;
import com.lc.group.bean.GroupBean;
import com.lc.sence.bean.SenceBean;
import com.lc.show.bean.ShowBean;

public class SynDataTranslateHelper {
	
	/**
	 * ͬ��������
	 * 
	 * @param groupDatas	һ��16 * 10��byte���飬������id�������б�id
	 */
	public static void SynGroupData(byte[] groupDatas){
		int tmp = LCConfig.getGroupNum();
		for(int i = 0; i < LCConfig.getGroupNum(); i++){
			ArrayList<MemberBean> members = new ArrayList<MemberBean>();
			for(int j = 2; j < 10; j++){
				if(groupDatas[i * 10 + j] == -1){
					ChangeDataUtils.setGroupMembers((int)groupDatas[i * 10] - 1, members);
					break;
				}else{
					MemberBean bean = LCConfig.getMembers().get((int) groupDatas[i * 10 + j] - 1);
					bean.setGroupedFlag(true);
					members.add(bean);
				}
			}
		}
	}
	
	/**
	 * ͬ����������
	 * 
	 * @param senceDatas	һ��16 * 5 * 13��byte���飬��������id���������б�id���������б���ɫ
	 * @param time			һ��16��byte���飬�����������б�ʱ����
	 */
	public static void SynSenceData(byte[] senceDatas, byte[] time){
		for(int i = 0; i < LCConfig.getSenceNum(); i++){
			HashMap<Integer, List<GroupBean>> senceGroups = new HashMap<Integer, List<GroupBean>>();
			HashMap<Integer, Integer> colors = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> times = new HashMap<Integer, Integer>();
			for(int j = 0; j < 5; j++){
				ArrayList<GroupBean> groups = new ArrayList<GroupBean>();
				for(int k = 2; k < 10; k++){
					if(senceDatas[i * 5 * 13 + j * 13 + k] == -1){
						senceGroups.put(j, groups);
						break;
					}else{
						groups.add(LCConfig.getGroups().get(senceDatas[i * 5 * 13 + j * 13 + k] - 1));
					}
				}
				int color = (senceDatas[i * 5 * 13 + j * 13 + 10] - 1) << 16 | (senceDatas[i * 5 * 13 + j * 13 + 11] - 1) << 8 | (senceDatas[i * 5 * 13 + j * 13 + 12] - 1);
				colors.put(j, color);
				times.put(j, (int) time[i] - 1);
			}
			SenceBean sence = LCConfig.getSences().get(i);
			sence.setColorArray(colors);
			sence.setDivideTimes(times);
			sence.setGroups(senceGroups);
			ChangeDataUtils.alterOrAddSence(sence, sence.getName());
		}
	}
	
	/**
	 * ͬ��show����
	 * 
	 * @param showDatas		һ��16 * 5��byte���飬����show id��show���б�id��show���б�ʱ����
	 */
	public static void SynShowData(byte[] showDatas){
		for(int i = 0; i < LCConfig.getShowNum(); i++){
			HashMap<Integer, SenceBean> showSences = new HashMap<Integer, SenceBean>();
			HashMap<Integer, Integer> times = new HashMap<Integer,Integer>();
			for(int j = 1; j < 4; j++){
				if(showDatas[i * 5 + j] == -1){
					break;
				}else{
					showSences.put(j - 1, LCConfig.getSences().get(showDatas[i * 5 + j] - 1));
				}
			}
			for(int j = 0; j < 4; j++){
				times.put(j, (int) showDatas[i * 5 + 4] - 1);
			}
			ShowBean show = LCConfig.getShows().get(i);
			show.setDivideTimes(times);
			show.setSences(showSences);
			ChangeDataUtils.alterOrAddShow(show, show.getName());
		}
	}
}
