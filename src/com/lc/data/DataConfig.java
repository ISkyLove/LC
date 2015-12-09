package com.lc.data;

/**
 * ���������������
 * 
 * @author LC work room
 * 
 */
public class DataConfig {
	/**
	 * wifi ����
	 */
	public static final String SSID = "Baiyi_LED";
	public static final String PASSWORD = "laigudou123";
	/**
	 * ����ͷ
	 */
	public static final char DATA_BEGIN = 0x65;
	/**
	 * ����β
	 */
	public static final char DATA_END = 0xaf;
	/**
	 * ������ţ����鰴ť����ʱȡɫ�鷢����ɫ����
	 */
	public static final char GROUP_COLOR_CHANGE = 0x01;
	/**
	 * ������ţ����鰴ť����
	 */
	public static final char GROUP_LONG_CLICK = 0x02;
	/**
	 * ѡ�еƾߣ���������з��͵�����
	 */
	public static final char GROUP_ADD_LIGHT = 0x03;
	/**
	 * ����3��ƾ��б��еĵư���
	 */
	public static final char LIGHT_LONG_CLICK = 0x04;
	/**
	 * ѡ�еƾߣ���������
	 */
	public static final char GROUP_REMOVE_LIGHT = 0x05;
	/**
	 * ѡ�г��������,���������
	 */
	public static final char SENCE_PANE_ADD_GROUP = 0x06;
	/**
	 * ѡ�г��������,����ɾ����
	 */
	public static final char SENCE_PANE_REMOVE_GROUP = 0x07;

	/**
	 * �������ʱ��������ɫ�ı�
	 */
	public static final char SENCE_PANE_COLOR_CHANGE = 0x08;
	/**
	 * ������̣��༭ʱ��
	 */
	public static final char SENCE_TIME_SETTING = 0x09;
	/**
	 * �����������
	 */
	public static final char SENCE_RUNNING = 0x0A;
	/**
	 * �������ֹͣ
	 */
	public static final char SENCE_STOPPING = 0x0B;

	/**
	 * ѡ��Show�����,������ӳ���
	 */
	public static final char SHOW_PANE_ADD_SENCE = 0x0C;

	/**
	 * SHOW��̣��༭ʱ��
	 */
	public static final char SHOW_TIME_SETTING = 0x0D;
	/**
	 * SHOW�������
	 */
	public static final char SHOW_RUNNING = 0x0E;
	/**
	 * SHOW���ֹͣ
	 */
	public static final char SHOW_STOPPING = 0x0F;
	/**
	 * �ȵ�ɫ��
	 */
	public static final char LIGHT_RIGHT = 0x11;
	/**
	 * �ȵ�����
	 */
	public static final char LIGHT_RIGHT2 = 0x13;
	/**
	 * ��������ɫ��
	 */
	public static final char SENCE_SETTING_RIGHT = 0x12;
	/**
	 * ������������
	 */
	public static final char SENCE_SETTING_RIGHT2 = 0x14;
	/**
	 * �������
	 */
	public static final char DATA_NEWDATA = 0x15;

	/**
	 * Ĭ������
	 */
	public static final char DATA_OFFSETS = 0xFF;

	public static final char DATA_RESET = 0x22;
	/**
	 * ��ʼ��
	 */
	public static final char DATA_RESET_GROUP_BEGIN1 = 0x45;
	public static final char DATA_RESET_GROUP_BEGIN2 = 0x54;
	public static final char DATA_RESET_GROUP_BEGIN3 = 0x14;
	public static final char DATA_RESET_GROUP_END1 = 0x78;
	public static final char DATA_RESET_GROUP_END2 = 0x87;
	public static final char DATA_RESET_GROUP_END3 = 0x12;

	public static final char DATA_RESET_SENCE_BEGIN1 = 0xb5;
	public static final char DATA_RESET_SENCE_BEGIN2 = 0x5b;
	public static final char DATA_RESET_SENCE_BEGIN3 = 0x13;
	public static final char DATA_RESET_SENCE_END1 = 0xc5;
	public static final char DATA_RESET_SENCE_END2 = 0x5c;
	public static final char DATA_RESET_SENCE_END3 = 0x13;

	public static final char DATA_RESET_SHOW_BEGIN1 = 0xd5;
	public static final char DATA_RESET_SHOW_BEGIN2 = 0x5d;
	public static final char DATA_RESET_SHOW_BEGIN3 = 0x14;
	public static final char DATA_RESET_SHOW_END1 = 0x25;
	public static final char DATA_RESET_SHOW_END2 = 0x52;
	public static final char DATA_RESET_SHOW_END3 = 0x14;

}
