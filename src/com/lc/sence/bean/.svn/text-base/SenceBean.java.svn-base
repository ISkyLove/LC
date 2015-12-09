package com.lc.sence.bean;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import com.lc.common.bean.LCBaseBean;
import com.lc.group.bean.GroupBean;
/**
 * 场景后台数据
 * 
 * @author LC work room
 *
 */
public class SenceBean extends LCBaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3004590829790127271L;
	private HashMap<Integer, List<GroupBean>> groups;		//场景包含的组列表映射，对应场景编辑中组所加入的panner位置，备拓展
	private HashMap<Integer, Integer> divideTimes;			//每个组之间的间隔时间，按需可满足每个时间独立，备拓展
	private HashMap<Integer, Integer> colorArray;			//每个panner对应的颜色值，备拓展
	private boolean loopOrStop;								//循环或暂停，是否有用？
	private boolean edited = false;							//编辑标识，是否有用？
	
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
