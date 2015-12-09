package com.lc.data;

/**
 * 发送数据命令辅助类
 * 
 * @author LC work room
 * 
 */
public class DataConfig {
	/**
	 * wifi 设置
	 */
	public static final String SSID = "Baiyi_LED";
	public static final String PASSWORD = "laigudou123";
	/**
	 * 数据头
	 */
	public static final char DATA_BEGIN = 0x65;
	/**
	 * 数据尾
	 */
	public static final char DATA_END = 0xaf;
	/**
	 * 命令代号，灯组按钮按下时取色块发送颜色数据
	 */
	public static final char GROUP_COLOR_CHANGE = 0x01;
	/**
	 * 命令代号，灯组按钮长按
	 */
	public static final char GROUP_LONG_CLICK = 0x02;
	/**
	 * 选中灯具，拖入分组中发送的数据
	 */
	public static final char GROUP_ADD_LIGHT = 0x03;
	/**
	 * 长按3秒灯具列表中的灯按键
	 */
	public static final char LIGHT_LONG_CLICK = 0x04;
	/**
	 * 选中灯具，拉出分组
	 */
	public static final char GROUP_REMOVE_LIGHT = 0x05;
	/**
	 * 选中场景编程器,容器添加组
	 */
	public static final char SENCE_PANE_ADD_GROUP = 0x06;
	/**
	 * 选中场景编程器,容器删除组
	 */
	public static final char SENCE_PANE_REMOVE_GROUP = 0x07;

	/**
	 * 场景编程时，容器颜色改变
	 */
	public static final char SENCE_PANE_COLOR_CHANGE = 0x08;
	/**
	 * 场景编程，编辑时间
	 */
	public static final char SENCE_TIME_SETTING = 0x09;
	/**
	 * 场景点击运行
	 */
	public static final char SENCE_RUNNING = 0x0A;
	/**
	 * 场景点击停止
	 */
	public static final char SENCE_STOPPING = 0x0B;

	/**
	 * 选中Show编程器,容器添加场景
	 */
	public static final char SHOW_PANE_ADD_SENCE = 0x0C;

	/**
	 * SHOW编程，编辑时间
	 */
	public static final char SHOW_TIME_SETTING = 0x0D;
	/**
	 * SHOW点击运行
	 */
	public static final char SHOW_RUNNING = 0x0E;
	/**
	 * SHOW点击停止
	 */
	public static final char SHOW_STOPPING = 0x0F;
	/**
	 * 等的色温
	 */
	public static final char LIGHT_RIGHT = 0x11;
	/**
	 * 等的亮度
	 */
	public static final char LIGHT_RIGHT2 = 0x13;
	/**
	 * 场景设置色温
	 */
	public static final char SENCE_SETTING_RIGHT = 0x12;
	/**
	 * 场景设置亮度
	 */
	public static final char SENCE_SETTING_RIGHT2 = 0x14;
	/**
	 * 清空数据
	 */
	public static final char DATA_NEWDATA = 0x15;

	/**
	 * 默认数据
	 */
	public static final char DATA_OFFSETS = 0xFF;

	public static final char DATA_RESET = 0x22;
	/**
	 * 初始化
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
