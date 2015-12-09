package com.lc.show.bean;

import java.util.HashMap;

import android.annotation.SuppressLint;

import com.lc.common.bean.LCBaseBean;
import com.lc.sence.bean.SenceBean;

/**
 * show��̨����
 * 
 * @author LC work room
 *
 */
public class ShowBean extends LCBaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8071993407935079503L;
	private HashMap<Integer, SenceBean> sences;		//show�а����ĳ���ӳ�䣬��Ӧpannerλ�ã�����չ
	private HashMap<Integer, Integer> divideTimes;	//show�г���֮��ļ��ʱ�䣬����չ
	private boolean loopOrStop;  // trueΪstop��falseΪloop���Ƿ����ã�
	private boolean edited = false;	//�Ƿ񱻱༭����ʶ���Ƿ����ã�
	
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
