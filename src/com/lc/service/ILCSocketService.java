package com.lc.service;

import com.lc.common.bean.LCBaseBean;
import com.lc.service.LCSocketService.OnReceiveDataListen;
import com.lc.service.LCSocketService.OnSocketSendDataSuccessListen;
import com.lc.service.LCSocketService.OnSocketServiceConnectListen;

/**
 * Socket 服务帮助接口
 * 调用该接口必须先添加监听(连接状态监听、数据获取接听),然后再调用是自动连接ConnectByAuto或者手动连接ConnectByHand
 * 
 * @author LC work room
 * 
 */
public interface ILCSocketService {
	/**
	 * 连接状态监听
	 * 
	 * @param onsocketserviceConnectListen
	 */
	void addSocketServiceConnectListen(
			OnSocketServiceConnectListen onsocketserviceConnectListen);

	/**
	 * 数据获取监听
	 * 
	 * @param onReceiveDataListen
	 */
	void addReceiveDataListen(OnReceiveDataListen onReceiveDataListen);

	/**
	 * 删除连接状态监听
	 * 
	 * @param onSocketServiceConnectListen
	 */
	void removeSocketServiceConnectListen(
			OnSocketServiceConnectListen onSocketServiceConnectListen);

	/**
	 * 删除数据获取接听
	 * 
	 * @param onReceiveDataListen
	 */
	void removeReceiveDataListen(OnReceiveDataListen onReceiveDataListen);

	/**
	 * 添加发送数据成功监听
	 * 
	 * @param onSendDataSuccessListen
	 */
	void addOnSocketSendDataSuccessListen(
			OnSocketSendDataSuccessListen onSocketSendDataSuccessListen);

	/**
	 * 删除发送数据监听
	 * 
	 * @param onSendDataSuccessListen
	 */
	void removeOnSocketSendDataSuccessListen(
			OnSocketSendDataSuccessListen onSocketSendDataSuccessListen);

	/**
	 * 发送数据
	 * 
	 * @param data
	 */
	void sendData(byte[] data, LCBaseBean basebean);

	/**
	 * 自动连接
	 */
	void ConnectByAuto();

	/**
	 * 手动连接
	 * 
	 * @param remoteIP
	 * @param remotePort
	 */
	void ConnectByHand(String remoteIP, int remotePort);

	/**
	 * 是否连接
	 * 
	 * @return
	 */
	boolean isConnnect();
}
