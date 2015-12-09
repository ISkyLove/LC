package com.lc.service;

import com.lc.common.bean.LCBaseBean;
import com.lc.service.LCNIOSocketService.OnReceiveDataListen;
import com.lc.service.LCNIOSocketService.OnSocketSendDataSuccessListen;
import com.lc.service.LCNIOSocketService.OnSocketServiceConnectListen;


/**
 * Socket ��������ӿ�
 * ���øýӿڱ�������Ӽ���(����״̬���������ݻ�ȡ����),Ȼ���ٵ������Զ�����ConnectByAuto�����ֶ�����ConnectByHand
 * 
 * @author LC work room
 * 
 */
public interface ILCNIOSocketService {
	/**
	 * ����״̬����
	 * 
	 * @param onsocketserviceConnectListen
	 */
	void addSocketServiceConnectListen(
			OnSocketServiceConnectListen onsocketserviceConnectListen);

	/**
	 * ���ݻ�ȡ����
	 * 
	 * @param onReceiveDataListen
	 */
	void addReceiveDataListen(OnReceiveDataListen onReceiveDataListen);

	/**
	 * ɾ������״̬����
	 * 
	 * @param onSocketServiceConnectListen
	 */
	void removeSocketServiceConnectListen(
			OnSocketServiceConnectListen onSocketServiceConnectListen);

	/**
	 * ɾ�����ݻ�ȡ����
	 * 
	 * @param onReceiveDataListen
	 */
	void removeReceiveDataListen(OnReceiveDataListen onReceiveDataListen);

	/**
	 * ��ӷ������ݳɹ�����
	 * 
	 * @param onSendDataSuccessListen
	 */
	void addOnSocketSendDataSuccessListen(
			OnSocketSendDataSuccessListen onSocketSendDataSuccessListen);

	/**
	 * ɾ���������ݼ���
	 * 
	 * @param onSendDataSuccessListen
	 */
	void removeOnSocketSendDataSuccessListen(
			OnSocketSendDataSuccessListen onSocketSendDataSuccessListen);

	/**
	 * ��������
	 * 
	 * @param data
	 */
	void sendData(byte[] data,LCBaseBean basebean);

	/**
	 * �Զ�����
	 */
	void ConnectByAuto();

	/**
	 * �ֶ�����
	 * 
	 * @param remoteIP
	 * @param remotePort
	 */
	void ConnectByHand(String remoteIP, int remotePort);
}
