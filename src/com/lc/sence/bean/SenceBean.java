package com.lc.sence.bean;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import com.lc.common.bean.LCBaseBean;
import com.lc.group.bean.GroupBean;
/**
 * ������̨����
 * 
 * @author LC work room
 *
 */
public class SenceBean extends LCBaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3004590829790127271L;
	private HashMap<Integer, List<GroupBean>> groups;		//�������������б�ӳ�䣬��Ӧ�����༭�����������pannerλ�ã�����չ
	private HashMap<Integer, Integer> divideTimes;			//ÿ����֮��ļ��ʱ�䣬���������ÿ��ʱ�����������չ
	private HashMap<Integer, Integer> colorArray;			//ÿ��panner��Ӧ����ɫֵ������չ
	private boolean loopOrStop;								//ѭ������ͣ���Ƿ����ã�
	private boolean edited = false;							//�༭��ʶ���Ƿ����ã�
	
	public HashMap<Integer, List<GroupBean>> getGroups() {
		return groups;
	}
	public void setGroups(HashMap<Integer, List<GroupBean>> groups) {
		this.groups = groups;
	}
	
	@SuppressLint("UseSparseArrays")
	public void putSenceGroups(int key, List<GroupBean> senceGroups){
		if(groups == null){
			groups = new HashMap<Integer, List<GroupBean>>();
		}
		groups.put(key, senceGroups);
	}
	
	public List<GroupBean> getSenceGroups(int key){
		if(groups == null){
			return null;
		}else{
			return groups.get(key);
		}
	}
	
	public boolean isLoopOrStop() {
		return loopOrStop;
	}
	public void setLoopOrStop(boolean loopOrStop) {
		this.loopOrStop = loopOrStop;
	}
	public boolean isEdited() {
		return edited;
	}
	public void setEdited(boolean edited) {
		this.edited = edited;
	}
	public HashMap<Integer, Integer> getDivideTimes() {
		return divideTimes;
	}
	public void setDivideTimes(HashMap<Integer, Integer> divideTimes) {
		this.divideTimes = divideTimes;
	}
	
	@SuppressLint("UseSparseArrays") 
	public void putTime(int key, int time){
		if(divideTimes == null){
			divideTimes = new HashMap<Integer, Integer>();
		}
		divideTimes.put(key, time);
	}
	
	@SuppressLint("UseSparseArrays") 
	public int getTime(int key){
		if(divideTimes == null){
			divideTimes = new HashMap<Integer, Integer>();
			return 1;
		}else{
			return divideTimes.get(key);
		}
	}
	public HashMap<Integer, Integer> getColorArray() {
		return colorArray;
	}
	public void setColorArray(HashMap<Integer, Integer> colorArray) {
		this.colorArray = colorArray;
	}
}
