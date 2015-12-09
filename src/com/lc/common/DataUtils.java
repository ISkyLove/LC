package com.lc.common;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import com.lc.LCConfig;
import com.lc.common.bean.LCBaseBean;
import com.lc.common.bean.MemberBean;
import com.lc.data.BaseItem.TYPE;
import com.lc.data.ChangeDataUtils;
import com.lc.data.GroupItem;
import com.lc.data.LightItem;
import com.lc.data.SenceItem;
import com.lc.data.ShowItem;
import com.lc.group.bean.GroupBean;
import com.lc.sence.bean.SenceBean;
import com.lc.show.bean.ShowBean;

import android.graphics.Color;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;

/**
 * ����ת�������� ȡ��Ͱ�λ
 * 
 * @author LC work room
 * 
 */
public class DataUtils {
	/**
	 * ��intת��Ϊbyte,ȡ��Ͱ�λ
	 * 
	 * @param value
	 * @return
	 */
	public static byte getByte(int value) {
		return (byte) (value & 0xff);
	}

	/**
	 * ��charת��Ϊbyte,ȡ��Ͱ�λ
	 * 
	 * @param value
	 * @return
	 */
	public static byte getByte(char value) {
		return (byte) (value & 0xff);
	}

	/**
	 * �ж�x��y�����Ƿ��ڴ���view���棬������ϵͳһΪ��������ϵ
	 * 
	 * @param x
	 * @param y
	 * @param view
	 * @return
	 */
	public static boolean isContains(int x, int y, View view) {
		int[] location = new int[2];
		view.getLocationInWindow(location);
		int width = view.getMeasuredWidth();
		int height = view.getMeasuredHeight();
		Rect mRect = new Rect(location[0], location[1], location[0] + width,
				location[1] + height);
		return mRect.contains(x, y);
	}

	/**
	 * byte ת 16 �����ַ���
	 * 
	 * @param data
	 * @return
	 */
	public static String byte2HexStr(byte[] data) {
		String stmp = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < data.length; n++) {
			stmp = Integer.toHexString(data[n] & 0xFF);
			sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
			sb.append(" ");
		}
		return sb.toString().toUpperCase().trim();
	}

	/**
	 * �����ݱ仯���棨�Ƿ�����Ҫ����
	 * 
	 * @param baseBean
	 *            ����ĵ�item
	 */
	public static void changeLight(LCBaseBean baseBean) {
	}

	/**
	 * ���Ƽ����鱣��
	 * 
	 * @param mContext
	 *            ������
	 * @param data
	 *            ��id
	 * @param baseBean
	 *            ��item
	 */
	@SuppressWarnings("deprecation")
	public static void addLightToGroup(Context mContext, int data,
			LCBaseBean baseBean) {
		MemberBean bean = DataUtils
				.coverLightItemToMemberBean((LightItem) baseBean);
		bean.setGroupedFlag(true);
		ChangeDataUtils.addOrAlterMemberToGroup(mContext, data, bean);
	}

	/**
	 * �������Ƴ���
	 * 
	 * @param mContext
	 *            ������
	 * @param data
	 *            ��id
	 * @param baseBean
	 *            ��item
	 */
	@SuppressWarnings("deprecation")
	public static void removeLightFromGroup(Context mContext, int data,
			LCBaseBean baseBean) {
		ChangeDataUtils.deleteGroupMember(mContext, data, baseBean.getId());
	}

	/**
	 * ������볡������
	 * 
	 * @param mContext
	 *            ������
	 * @param data
	 *            ����id
	 * @param data2
	 *            pannerλ��
	 * @param baseBean
	 *            ��item
	 */
	@SuppressWarnings("deprecation")
	public static void addGroupToSence(Context mContext, int data, int data2,
			LCBaseBean baseBean) {
		GroupBean bean = DataUtils
				.coverGroupItemToGroupBean((GroupItem) baseBean);
		ChangeDataUtils.addOrAlterGroupToSence(mContext, data, bean, data2);
	}

	/**
	 * �ı�����ɫ����
	 * 
	 * @param baseBean
	 *            ��item
	 * @param r
	 *            ��
	 * @param g
	 *            ��
	 * @param b
	 *            ��
	 */
	public static void changeSenceColor(LCBaseBean baseBean) {
		SenceBean bean = DataUtils
				.coverSenceItemToSenceBean((SenceItem) baseBean);
		ChangeDataUtils.alterOrAddSence(bean, baseBean.getName());
	}

	/**
	 * �ı䳡�����ʱ�䱣��
	 * 
	 * @param baseBean
	 *            ����item
	 * @param data
	 *            ���ʱ��
	 */
	public static void changeSenceTime(LCBaseBean baseBean, int data, int data2) {
		SenceBean bean = LCConfig.getSences().get(baseBean.getId());
		HashMap<Integer, Integer> timeArray = new HashMap<Integer, Integer>();
		for (int i = 0; i < 2; i++) {
			timeArray.put(i, data2);
		}
		bean.setDivideTimes(timeArray);
		ChangeDataUtils.alterOrAddSence(bean, bean.getName());
	}

	/**
	 * �ı�show���ʱ��
	 * 
	 * @param baseBean
	 *            show item
	 * @param data
	 *            ���ʱ��
	 */
	public static void changeShowTime(LCBaseBean baseBean, int data) {
		ShowBean bean = DataUtils.coverShowItemToShowBean((ShowItem) baseBean);
		HashMap<Integer, Integer> timeArray = new HashMap<Integer, Integer>();
		for (int i = 0; i < 2; i++) {
			timeArray.put(i, data);
		}
		bean.setDivideTimes(timeArray);
		ChangeDataUtils.alterOrAddShow(bean, bean.getName());
	}

	/**
	 * ����������show����
	 * 
	 * @param context
	 *            ������
	 * @param data1
	 *            show id
	 * @param baseBean
	 *            show item
	 */
	public static void addSenceToShow(Context context, int data1,
			LCBaseBean baseBean) {
		ShowBean bean = DataUtils.coverShowItemToShowBean((ShowItem) baseBean);
		ChangeDataUtils.alterOrAddShow(bean, bean.getName());
	}

	/**
	 * �ɵ�beanת���ɵ�item
	 * 
	 * @param context
	 *            ������
	 * @param member
	 *            ��bean
	 * @return ת����ĵ�item
	 */
	public static LightItem coverMemberBeanToLightItem(Context context,
			MemberBean member) {
		LightItem item = new LightItem(context);
		item.setItemColor(member.getColor());
		item.setItemId(member.getId());
		item.setItemName(member.getName());
		item.setItemRemark(member.getRemark());
		return item;
	}

	/**
	 * ����beanת������item
	 * 
	 * @param context
	 *            ������
	 * @param group
	 *            ��bean
	 * @return ת�������item
	 */
	public static GroupItem coverGroupBeanToGroupItem(Context context,
			GroupBean group) {
		GroupItem item = new GroupItem(context);
		item.setItemColor(group.getColor());
		item.setItemId(group.getId());
		item.setItemName(group.getName());
		item.setItemRemark(group.getRemark());
		item.setItemType(TYPE.TYPE_GROUP);
		item.changeChildData(DataUtils.coverMembersToLights(context,
				group.getChildList()));
		return item;
	}

	/**
	 * �ɳ���beanת���ɳ���item
	 * 
	 * @param context
	 *            ������
	 * @param sence
	 *            ����bean
	 * @return ת����ĳ���item
	 */
	public static SenceItem coverSenceBeanToSenceItem(Context context,
			SenceBean sence) {

		if (sence != null) {
			SenceItem item = new SenceItem(context);
			item.setItemColor(sence.getColor());
			item.setItemId(sence.getId());
			item.setItemName(sence.getName());
			item.setItemRemark(sence.getRemark());
			item.setItemType(TYPE.TYPE_SENCE);
			int[] colors = new int[5];
			if (sence.getColorArray() != null) {
				for (int i = 0; i < 5; i++) {
					colors[i] = sence.getColorArray().get(i) == null ? Color
							.parseColor("#FFFFFF") : sence.getColorArray().get(
							i);
				}
				item.setPannColor(colors);
			}
			if (sence.getGroups() == null) {
				item.setChildPaneData1(new ArrayList<GroupItem>());
				item.setChildPaneData2(new ArrayList<GroupItem>());
				item.setChildPaneData3(new ArrayList<GroupItem>());
				item.setChildPaneData4(new ArrayList<GroupItem>());
				item.setChildPaneData5(new ArrayList<GroupItem>());
			} else {
				item.setChildPaneData1(DataUtils.coverGroupBeansToGroupItems(
						context, sence.getGroups().get(0)));
				item.setChildPaneData2(DataUtils.coverGroupBeansToGroupItems(
						context, sence.getGroups().get(1)));
				item.setChildPaneData3(DataUtils.coverGroupBeansToGroupItems(
						context, sence.getGroups().get(2)));
				item.setChildPaneData4(DataUtils.coverGroupBeansToGroupItems(
						context, sence.getGroups().get(3)));
				item.setChildPaneData5(DataUtils.coverGroupBeansToGroupItems(
						context, sence.getGroups().get(4)));
			}
			return item;
		}
		return null;
	}

	/**
	 * ��show beanת����show item
	 * 
	 * @param context
	 *            ������
	 * @param show
	 *            show bean
	 * @return ת�����show item
	 */
	public static ShowItem coverShowBeanToShowItem(Context context,
			ShowBean show) {
		ShowItem item = new ShowItem(context);
		item.setItemColor(show.getColor());
		item.setItemId(show.getId());
		item.setItemName(show.getName());
		item.setItemRemark(show.getRemark());
		item.setItemType(TYPE.TYPE_SHOW);
		ArrayList<SenceItem> items = new ArrayList<SenceItem>();
		if (show.getSences() != null) {
			for (int i = 0; i < 3; i++) {
				items.add(DataUtils.coverSenceBeanToSenceItem(context, show
						.getSences().get(i)));
			}
		}
		item.changeItemChildData(items);
		return item;
	}

	/**
	 * ����ת����bean
	 * 
	 * @param context
	 *            ������
	 * @param list
	 *            ��bean�б�
	 * @return ת����ĵ�item�б�
	 */
	public static List<LightItem> coverMembersToLights(Context context,
			List<MemberBean> list) {
		List<LightItem> items = new ArrayList<LightItem>();
		if (list != null) {
			for (MemberBean bean : list) {
				items.add(DataUtils.coverMemberBeanToLightItem(context, bean));
			}
		}
		return items;
	}

	public static ArrayList<MemberBean> coverLightsToMember(List<LightItem> list) {
		ArrayList<MemberBean> beans = new ArrayList<MemberBean>();
		if (list != null) {
			for (LightItem item : list) {
				beans.add(DataUtils.coverLightItemToMemberBean(item));
			}
		}
		return beans;
	}

	/**
	 * ����ת����bean
	 * 
	 * @param context
	 *            ������
	 * @param list
	 *            ��bean�б�
	 * @return ת�������item�б�
	 */
	public static List<GroupItem> coverGroupBeansToGroupItems(Context context,
			List<GroupBean> list) {
		List<GroupItem> items = new ArrayList<GroupItem>();
		if (list != null) {
			for (GroupBean bean : list) {
				items.add(DataUtils.coverGroupBeanToGroupItem(context, bean));
			}
		}
		return items;
	}

	public static List<GroupBean> coverGroupItemsToGroupBeans(
			List<GroupItem> list) {
		List<GroupBean> items = new ArrayList<GroupBean>();
		if (list != null) {
			for (GroupItem item : list) {
				items.add(DataUtils.coverGroupItemToGroupBean(item));
			}
		}
		return items;
	}

	/**
	 * ����ת������bean
	 * 
	 * @param context
	 *            ������
	 * @param list
	 *            ����bean�б�
	 * @return ת����ĳ���item�б�
	 */
	public static List<SenceItem> coverSenceBeansToSenceItems(Context context,
			List<SenceBean> list) {
		List<SenceItem> items = new ArrayList<SenceItem>();
		if (list != null) {
			for (SenceBean bean : list) {
				items.add(DataUtils.coverSenceBeanToSenceItem(context, bean));
			}
		}
		return items;
	}

	/**
	 * ����ת��show bean
	 * 
	 * @param context
	 *            ������
	 * @param list
	 *            show bean�б�
	 * @return ת�����show item�б�
	 */
	public static List<ShowItem> coverShowBeansToShowItems(Context context,
			List<ShowBean> list) {
		List<ShowItem> items = new ArrayList<ShowItem>();
		if (list != null) {
			for (ShowBean bean : list) {
				items.add(DataUtils.coverShowBeanToShowItem(context, bean));
			}
		}
		return items;
	}

	/**
	 * ����itemת��Ϊ��bean
	 * 
	 * @param item
	 *            ��item
	 * @return ת����ĵ�bean
	 */
	public static MemberBean coverLightItemToMemberBean(LightItem item) {
		MemberBean bean = new MemberBean();
		bean.setColor(item.getItemColor());
		bean.setId(item.getItemId());
		bean.setName(item.getItemName());
		bean.setRemark(item.getItemRemark());
		return bean;
	}

	/**
	 * ����itemת��Ϊ��bean
	 * 
	 * @param item
	 *            ��item
	 * @return ת�������bean
	 */
	public static GroupBean coverGroupItemToGroupBean(GroupItem item) {
		GroupBean bean = new GroupBean();
		bean.setColor(item.getItemColor());
		bean.setId(item.getItemId());
		bean.setName(item.getItemName());
		bean.setRemark(item.getItemRemark());
		bean.setChildList(DataUtils.coverLightsToMember(item.getItemChildData()));
		return bean;
	}

	/**
	 * ������itemת��Ϊ����bean
	 * 
	 * @param item
	 *            ����item
	 * @return ת����ĳ���bean
	 */
	public static SenceBean coverSenceItemToSenceBean(SenceItem item) {
		SenceBean bean = new SenceBean();
		if (item != null) {
			HashMap<Integer, Integer> colorArray = new HashMap<Integer, Integer>();
			for (int i = 0; i < 5; i++) {
				colorArray.put(i, item.getPannColor()[i]);
			}
			bean.setColorArray(colorArray);
			bean.setId(item.getItemId());
			bean.setName(item.getItemName());
			bean.setRemark(item.getItemRemark());
			HashMap<Integer, List<GroupBean>> groups = new HashMap<Integer, List<GroupBean>>();
			groups.put(0, DataUtils.coverGroupItemsToGroupBeans(item
					.getChildPaneData1()));
			groups.put(1, DataUtils.coverGroupItemsToGroupBeans(item
					.getChildPaneData2()));
			groups.put(2, DataUtils.coverGroupItemsToGroupBeans(item
					.getChildPaneData3()));
			groups.put(3, DataUtils.coverGroupItemsToGroupBeans(item
					.getChildPaneData4()));
			groups.put(4, DataUtils.coverGroupItemsToGroupBeans(item
					.getChildPaneData5()));
			bean.setGroups(groups);

		}
		return bean;
	}

	/**
	 * ��show itemת��Ϊshow bean
	 * 
	 * @param item
	 *            show item
	 * @return ת�����show bean
	 */
	public static ShowBean coverShowItemToShowBean(ShowItem item) {
		ShowBean bean = new ShowBean();
		bean.setColor(item.getItemColor());
		bean.setId(item.getItemId());
		bean.setName(item.getItemName());
		bean.setRemark(item.getItemRemark());
		HashMap<Integer, SenceBean> sences = new HashMap<Integer, SenceBean>();
		if (item.getItemChildData() != null) {
			int length = item.getItemChildData().size();
			for (int i = 0; i < length; i++) {
				sences.put(i, DataUtils
						.coverSenceItemToSenceBean((SenceItem) item
								.getItemChildData().get(i)));
			}
		}
		bean.setSences(sences);
		return bean;
	}

	private static long lastMoveTime;

	public static boolean isFastMove() {
		long time = System.currentTimeMillis();
		long timeD = time - lastMoveTime;
		if (0 < timeD && timeD < 200) {
			return true;
		}
		lastMoveTime = time;
		return false;
	}
}
