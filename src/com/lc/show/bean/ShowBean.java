package com.lc.show.bean;

import java.util.HashMap;

import android.annotation.SuppressLint;

import com.lc.common.bean.LCBaseBean;
import com.lc.sence.bean.SenceBean;

/**
 * show后台数据
 * 
 * @author LC work room
 *
 */
public class ShowBean extends LCBaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8071993407935079503L;
	private HashMap<Integer, SenceBean> sences;		//show中包含的场景映射，对应panner位置，备拓展
	private HashMap<Integer, Integer> divideTimes;	//show中场景之间的间隔时间，备拓展
	private boolean loopOrStop;  // true为stop，false为loop，是否有用？
	private boolean edited = false;	//是否被编辑过标识，是否有用？
	
	public HashMap<Integer, SenceBean> getSences() {
		return sences;
	}
	public void setSences(HashMap<Integer, SenceBean> sences) {
		this.sences = sences;
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
	@SuppressLint("UseSparseArrays") 
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
			divideTimes.put(0, 1);
			divideTimes.put(1, 1);
			divideTimes.put(2, 1);
			return 1;
		}else{
			return divideTimes.get(key);
		}
	}
}
