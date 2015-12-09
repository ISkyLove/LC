package com.lc.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.SparseArray;
import android.widget.Toast;

import com.lc.LCConfig;
import com.lc.common.LogUtils;
import com.lc.common.bean.ConfigBean;
import com.lc.common.bean.MemberBean;
import com.lc.group.bean.GroupBean;
import com.lc.sence.bean.SenceBean;
import com.lc.show.bean.ShowBean;

/**
 * 数据操作工具类
 * 
 * @author LC work room
 *
 */
public class ChangeDataUtils {
	
	//首先获取配置对象
	private static ConfigBean config = LCConfig.getConfig();
	
	/**
	 * 设置组中的灯成员
	 * 
	 * @param context		上下文，用于显示提示
	 * @param groupId		当前设置的组id
	 * @param groupMembers	要设置进当前组的灯成员列表
	 */
	public static void setGroupMembers(int groupId, ArrayList<MemberBean> groupMembers){
		
		//配置对象是否为null，null则不允许设置组的灯成员，理应不会出现这种情况
		if(config == null){
			LogUtils.e(ChangeDataUtils.class, "设置组中的灯成员失败，配置对象为空，请检查是否没有初始化或人工设置为null了");
			return;
		}
		
		//获取组列表，若组列表为null，则不允许设置组的灯成员，理应不会出现这种情况
		ArrayList<GroupBean> list = LCConfig.getGroups();
		if(list == null){
			LogUtils.e(ChangeDataUtils.class, "设置组中的灯成员失败，组列表为空，请检查是否没有初始化或人工设置为null了");
			return;
		}
		
		//寻找当前id对应的组，存在该组则设置灯成员，并进行更新，否则提示不存在该组
		int count = 0;
		for(GroupBean group : list){
			if(group.getId() == groupId){
				group.setChildList(groupMembers);
				list.remove(count);
				list.add(count, group);
				LCConfig.setGroups(list);
				return;
			}
			count++;
		}
		
		LogUtils.e(ChangeDataUtils.class, "设置组中的灯成员失败，找不到id对应的组，请检查是否id的合法性或组列表是否有异常");
	}
	
	/**
	 * 设置场景中的组
	 * 
	 * @param context		上下文，用于显示提示
	 * @param senceId		当前设置的场景id
	 * @param senceGroups	要设置进当前场景的组成员映射表
	 */
	public static void setSenceGroup(int senceId, HashMap<Integer, List<GroupBean>> senceGroups){
		
		//配置对象是否为null，null则不允许设置场景的组成员，理应不会出现这种情况
		if(config == null){
			LogUtils.e(ChangeDataUtils.class, "设置场景中的组成员失败，配置对象为空，请检查是否没有初始化或人工设置为null了");
			return;
		}
		
		//获取场景列表，若场景列表为null，则不允许设置场景的组成员，理应不会出现这种情况
		ArrayList<SenceBean> list = LCConfig.getSences();
		if(list == null){
			LogUtils.e(ChangeDataUtils.class, "设置场景中的组成员失败，场景列表为空，请检查是否没有初始化或人工设置为null了");
			return;
		}
		
		//寻找当前id对应的场景，存在该场景则设置组成员，并进行更新，否则提示不存在该场景
		int count = 0;
		for(SenceBean sence : list){
			if(sence.getId() == senceId){
				sence.setGroups(senceGroups);
				list.remove(count);
				list.add(count, sence);
				LCConfig.setSences(list);
				return;
			}
			count++;
		}
		
		LogUtils.e(ChangeDataUtils.class, "设置场景中的组成员失败，找不到id对应的场景，请检查是否id的合法性或场景列表是否有异常");
	}
	
	/**
	 * 设置show中的场景成员
	 * 
	 * @param context		上下文，用于显示提示
	 * @param showId		当前设置的show id
	 * @param showSences	要设置进当前show的场景成员映射表
	 */
	public static void setShowSences(int showId, HashMap<Integer, SenceBean> showSences){
		
		//配置对象是否为null，null则不允许设置show的场景成员，理应不会出现这种情况
		if(config == null){
			LogUtils.e(ChangeDataUtils.class, "设置show中的场景成员失败，配置对象为空，请检查是否没有初始化或人工设置为null了");
			return;
		}
		
		//获取show列表，若show列表为null，则不允许设置show的场景成员，理应不会出现这种情况
		ArrayList<ShowBean> list = LCConfig.getShows();
		if(list == null){
			LogUtils.e(ChangeDataUtils.class, "设置show中的场景成员失败，show列表为空，请检查是否没有初始化或人工设置为null了");
			return;
		}
		
		//寻找当前id对应的show，存在该show则设置场景成员，并进行更新，否则提示不存在该show
		int count = 0;
		for(ShowBean show : list){
			if(show.getId() == showId){
				show.setSences(showSences);
				list.remove(count);
				list.add(count, show);
				LCConfig.setShows(list);
				return;
			}
			count++;
		}
		
		LogUtils.e(ChangeDataUtils.class, "设置show中的场景成员失败，找不到id对应的show，请检查是否id的合法性或show列表是否有异常");
	}
	
	public static List<MemberBean> getGroupMembers(int groupId){
		if(config == null){
			return null;
		}
		
		ArrayList<GroupBean> groups = config.getGroups();
		if(groups == null || groups.size() == 0){
			return null;
		}
		for(GroupBean bean : groups){
			if(bean.getId() == groupId){
				return (List<MemberBean>) bean.getChildData();
			}
		}
		return null;
	}
	
	public List<GroupBean> getSenceGroups(int senceId, int position){
		if(config == null){
			return null;
		}
		
		ArrayList<SenceBean> sences = config.getSences();
		if(sences == null || sences.size() == 0){
			return null;
		}
		for(SenceBean bean : sences){
			if(bean.getId() == senceId){
				HashMap<Integer, List<GroupBean>> groupBeans = bean.getGroups();
				if(groupBeans == null){
					return null;
				}else{
					return groupBeans.get(position);
				}
			}
		}
		return null;
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	//                                       灯成员操作                                                                             //
	/////////////////////////////////////////////////////////////////////////////////
	/**
	 * 修改灯成员，存在该灯成员时进行修改，不存在则添加
	 * 
	 * @param member		修改或添加的灯成员
	 * @param memberName	修改或添加的灯成员名字
	 * @return				修改或添加成功返回true，否则返回false
	 */
	public static boolean alterOrAddMember(MemberBean member, String memberName){
		
		//配置对象为空则不允许修改
		if(config == null){
			LogUtils.e(ChangeDataUtils.class, "修改或增加灯成员失败，配置对象为空");
			return false;
		}
		
		ArrayList<MemberBean> list = LCConfig.getMembers();
		//如果灯列表为空或没有成员则直接进行增加操作
		if(list == null || list.size() == 0){
			list = new ArrayList<MemberBean>();
			//重新设置id和name，屏蔽传入的member中的对应字段
			member.setId(LCConfig.getMemberNum() + 1);
			if(memberName == null){
				member.setName(String.valueOf(LCConfig.getMemberNum() + 1) + "号灯");
			}else{
				member.setName(memberName);
			}
			list.add(member);
			LCConfig.setMembers(list);
			LCConfig.setMemberNum(LCConfig.getMemberNum() + 1);
			return true;
		}
		
		int length = list.size();
		for(int i = 0; i < length; i++){
			if(member.getId() == list.get(i).getId()){
				list.remove(i);
				list.add(i, member);
				LCConfig.setMembers(list);
				return true;
			}
		}
		
		//重新设置id和name，屏蔽传入的member中的对应字段
		member.setId(LCConfig.getMemberNum() + 1);
		if(memberName == null){
			member.setName(String.valueOf(LCConfig.getMemberNum() + 1) + "号灯");
		}else{
			member.setName(memberName);
		}
		list.add(member);
		LCConfig.setMembers(list);
		LCConfig.setMemberNum(LCConfig.getMemberNum() + 1);
		return true;
	}
	
	/**
	 * 删除灯成员
	 * 
	 * @param memberId		需要删除的灯成员id
	 * @return				成功删除返回true，否则返回false
	 */
	public static boolean deleteMember(int memberId){
		//配置对象为空则不允许删除
		if(config == null){
			LogUtils.e(ChangeDataUtils.class, "删除灯成员失败，配置对象为空");
			return false;
		}
		
		//灯列表为空则不允许删除
		ArrayList<MemberBean> list = LCConfig.getMembers();
		if(list == null || list.size() == 0){
			LogUtils.e(ChangeDataUtils.class, "删除灯成员失败，灯列表为空");
			return false;
		}
		
		//找到id对应的灯成员则进行删除并更新列表，找不到则返回失败
		int length = list.size();
		for(int i = 0; i < length; i++){
			if(memberId == list.get(i).getId()){
				list.remove(i);
				LCConfig.setMemberNum(LCConfig.getMemberNum() - 1);
				return true;
			}
		}
		
		LogUtils.e(ChangeDataUtils.class, "删除灯成员失败，找不到id对应的灯成员");
		return false;
	}
	
/////////////////////////////////////////////////////////////////////////////////
//                                       组                                                                                                //
/////////////////////////////////////////////////////////////////////////////////
	/**
	 * 修改或添加灯成员到组中，组中已包含该成员则进行修改，否则进行添加
	 * 建议在界面操作过程中设置好后调用set函数
	 * 
	 * @param context	上下文用于提示
	 * @param groupId	当前操作的组id
	 * @param member	需要修改或添加的组成员
	 */
	@Deprecated
	public static void addOrAlterMemberToGroup(Context context, int groupId, MemberBean member){
		//配置对象为空或组列表为空时不允许添加
		if(config == null || LCConfig.getGroups() == null || LCConfig.getGroupNum() == 0){
			Toast.makeText(context, "分组失败，该组不存在", Toast.LENGTH_SHORT).show();
			LogUtils.e(ChangeDataUtils.class, "修改或添加灯成员到组中失败，配置对象或组列表为空");
			return;
		}
		
		//寻找id对应的组
		ArrayList<GroupBean> list = LCConfig.getGroups();
		int length = LCConfig.getGroupNum();
		for(int i = 0; i < length; i++){
			//存在该id对应的组
			if(groupId == list.get(i).getId()){
				ArrayList<MemberBean> list1 = list.get(i).getChildList();
				//添加的条件为：该组的灯成员列表为空或灯成员所属组id与本组id不同
				if(list1 == null || list1.size() == 0 || member.getBelongedGroupId() != groupId){
					if(list1 == null){
						list1 = new ArrayList<MemberBean>();
					}
					member.setBelongedGroupId(groupId);
					list1.add(member);
					ChangeDataUtils.setGroupMembers(groupId, list1);
					ChangeDataUtils.alterOrAddMember(member, member.getName());
					return;
				}
				
				//两个id相同则进行修改
				if(groupId == member.getBelongedGroupId()){
				int length1 = list1.size();
				for(int j = 0; j < length1; j++){
					if(member.getId() == list1.get(j).getId()){
						list1.remove(j);
						list1.add(j, member);
						ChangeDataUtils.setGroupMembers(groupId, list1);
						return;
					}
				}
				
				member.setBelongedGroupId(groupId);
				list1.add(member);
				ChangeDataUtils.setGroupMembers(groupId, list1);
				ChangeDataUtils.alterOrAddMember(member, member.getName());
				}
			}
		}
	}
	
	/**
	 * 修改或添加组
	 * 
	 * @param group			需要修改或添加的组对象
	 * @param groupName		该组对象的名字
	 * @return				修改或添加返回true，否则返回false
	 */
	public static boolean alterOrAddGroup(GroupBean group, String groupName){
		
		//配置对象为空在不允许修改或添加
		if(config == null){
			LogUtils.e(ChangeDataUtils.class, "修改或增加组失败，配置对象为空");
			return false;
		}
		
		//寻找对应的组，存在该组则修改，否则添加
		ArrayList<GroupBean> list = LCConfig.getGroups();
		//组列表为空时直接添加
		if(list == null || list.size() == 0){
			group.setId(LCConfig.getGroupNum() + 1);
			
			if(groupName == null){
				group.setName(String.valueOf(LCConfig.getGroupNum() + 1) + "号组");
			}else{
				group.setName(groupName);
			}
			
			list = new ArrayList<GroupBean>();
			list.add(group);
			LCConfig.setGroups(list);
			LCConfig.setGroupNum(LCConfig.getGroupNum() + 1);
			return true;
		}
		
		int length = list.size();
		for(int i = 0; i < length; i++){
			if(group.getId() == list.get(i).getId()){
				list.remove(i);
				list.add(i, group);
				LCConfig.setGroups(list);
				return true;
			}
		}
		
		//不存在该组时添加
		group.setId(LCConfig.getGroupNum() + 1);
		
		if(groupName == null){
			group.setName(String.valueOf(LCConfig.getGroupNum() + 1) + "号组");
		}else{
			group.setName(groupName);
		}
		
		list.add(group);
		LCConfig.setGroups(list);
		LCConfig.setGroupNum(LCConfig.getGroupNum() + 1);
		return true;
	}
	
	/**
	 * 删除组中的灯成员
	 * 建议在界面操作中修改后调用set函数
	 * 
	 * @param groupId		当前操作的组id
	 * @param memberId		需要删除的灯成员id
	 * @return				删除成功返回true，否则返回false
	 */
	@Deprecated
	public static boolean deleteGroupMember(Context context,int groupId, int memberId){
		//配置对象为空则不允许操作
		if(config == null){
			LogUtils.e(ChangeDataUtils.class, "删除组中灯成员失败，配置对象为空");
			return false;
		}
		
		ArrayList<GroupBean> list = LCConfig.getGroups();
		if(list == null || list.size() == 0){
			LogUtils.e(ChangeDataUtils.class, "删除组中灯成员失败，组列表为空");
			return false;
		}
		
		int length = list.size();
		for(int i = 0; i < length; i++){
			if(groupId == list.get(i).getId()){
				ArrayList<MemberBean> groupMembers = list.get(i).getChildList();
				
				if(groupMembers == null || groupMembers.size() == 0){
					LogUtils.e(ChangeDataUtils.class, "删除组中灯成员失败，组中的灯列表为空");
					return false;
				}
				
				int childLenght = groupMembers.size();
				for(int j = 0; j < childLenght; j++){
					if(memberId == groupMembers.get(j).getId()){
						groupMembers.remove(j);
						break;
					}
				}
				ChangeDataUtils.setGroupMembers(groupId, groupMembers);
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 删除组
	 * 
	 * @param groupId	需要删除的组id
	 * @return			删除成功返回true，否则返回false
	 */
	public static boolean deleteGroup(int groupId){
		//配置对象为空则不允许操作
		if(config == null){
			LogUtils.e(ChangeDataUtils.class, "删除组失败，配置对象为空");
			return false;
		}
		
		ArrayList<GroupBean> list = LCConfig.getGroups();
		if(list == null || list.size() == 0){
			LogUtils.e(ChangeDataUtils.class, "删除组失败，组列表为空");
			return false;
		}
		
		int length = list.size();
		for(int i = 0; i < length; i++){
			if(groupId == list.get(i).getId()){
				list.remove(i);
				LCConfig.setMemberNum(LCConfig.getGroupNum() - 1);
				return true;
			}
		}
		
		return false;
	}
	
/////////////////////////////////////////////////////////////////////////////////
//                                       场景                                                                                           //
/////////////////////////////////////////////////////////////////////////////////
	/**
	 * 修改或添加组到场景
	 * 建议在界面操作中设置好后调用set函数
	 * 
	 * @param context		上下文用于提示
	 * @param senceId		当前操作的场景id
	 * @param group			需要修改或加入的组
	 * @param position		容器对应的位置
	 */
	@Deprecated
	public static void addOrAlterGroupToSence(Context context, int senceId, GroupBean group, int position){
		//配置对象为空或场景列表为空则不允许操作
		if(config == null || LCConfig.getSences() == null || LCConfig.getSenceNum() == 0){
			Toast.makeText(context, "分场景失败，该场景不存在", Toast.LENGTH_SHORT).show();
			LogUtils.e(ChangeDataUtils.class, "修改或添加组到场景中失败，配置对象或场景列表为空");
			return;
		}
		
		//查找id对应的场景
		ArrayList<SenceBean> list = LCConfig.getSences();
		int length = LCConfig.getSenceNum();
		for(int i = 0; i < length; i++){
			//找到对应场景则修改
			if(senceId == list.get(i).getId()){
				HashMap<Integer, List<GroupBean>> list1 = list.get(i).getGroups();
				if(list1 == null){
					list1 = new HashMap<Integer, List<GroupBean>>();
				}
				
				List<GroupBean> list2 = list1.get(position);
				if(list2 == null){
					list2 = new ArrayList<GroupBean>();
					list2.add(group);
					list1.put(position,list2);
				}else{
					int listLength = list2.size();
					boolean addItemflag = false;
					for(int j = 0; j < listLength; j++){
						if(list2.get(j).getId() == group.getId()){
							list2.remove(j);
							list2.add(j, group);
							addItemflag = true;
						}
					}
					if(!addItemflag){
						list2.add(group);
					}
					list1.put(position, list2);
				}
				ChangeDataUtils.alterOrAddGroup(group, group.getName());
				ChangeDataUtils.setSenceGroup(senceId, list1);
				return;
				
			}
		}
		//找不到则提示
		Toast.makeText(context, "分场景失败，该场景不存在", Toast.LENGTH_SHORT).show();
		LogUtils.e(ChangeDataUtils.class, "修改或添加组到场景中失败，找不到对应的场景");
	}
	
	/**
	 * 修改或添加场景
	 * 
	 * @param sence			需要修改或添加的场景
	 * @param senceName		需要修改或添加的场景名称
	 * @return				修改或添加成功返回true，否则返回false
	 */
	public static boolean alterOrAddSence(SenceBean sence, String senceName){
		//配置对象为空则不允许操作
		if(config == null){
			LogUtils.e(ChangeDataUtils.class, "修改或添加场景失败，配置对象为空");
			return false;
		}
		
		//场景列表为空则直接添加
		ArrayList<SenceBean> list = LCConfig.getSences();
		if(list == null || list.size() == 0){
			sence.setId(LCConfig.getSenceNum() + 1);
			
			if(senceName == null){
				sence.setName(String.valueOf(LCConfig.getSenceNum() + 1) + "号场景");
			}else{
				sence.setName(senceName);
			}
			
			list = new ArrayList<SenceBean>();
			list.add(sence);
			LCConfig.setSences(list);
			LCConfig.setSenceNum(LCConfig.getSenceNum() + 1);
			return true;
		}
		
		int length = list.size();
		for(int i = 0; i < length; i++){
			//找到id对应的场景则修改
			if(sence.getId() == list.get(i).getId()){
				list.remove(i);
				list.add(i, sence);
				LCConfig.setSences(list);
				return true;
			}
		}
		
		//找不到id对应场景则添加
		sence.setId(LCConfig.getSenceNum() + 1);
		
		if(senceName == null){
			sence.setName(String.valueOf(LCConfig.getSenceNum() + 1) + "号场景");
		}else{
			sence.setName(senceName);
		}
		
		list.add(sence);
		LCConfig.setSences(list);
		LCConfig.setSenceNum(LCConfig.getSenceNum() + 1);
		return true;
	}
	
	/**
	 * 删除场景中的组
	 * 建议界面操作设置后调用set函数
	 * 
	 * @param context		
	 * @param senceId		当前操作的场景
	 * @param position		容器位置
	 * @return				删除成功则返回true，否则返回false
	 */
	@Deprecated
	public static boolean deleteSenceGroup(Context context, int senceId, int position, int deleteBeanId){
		//配置对象不存在则不允许操作
		if(config == null){
			LogUtils.e(ChangeDataUtils.class, "删除场景中的组失败，配置对象不存在");
			return false;
		}
		
		//场景列表不存在则不允许操作
		ArrayList<SenceBean> list = LCConfig.getSences();
		if(list == null || list.size() == 0){
			LogUtils.e(ChangeDataUtils.class, "删除场景中的组失败，场景列表为空");
			return false;
		}
		
		//查找id对应的场景
		int length = list.size();
		for(int i = 0; i < length; i++){
			//存在则修改
			if(senceId == list.get(i).getId()){
				HashMap<Integer, List<GroupBean>> senceGroup = list.get(i).getGroups();
				
				if(senceGroup == null){
					LogUtils.e(ChangeDataUtils.class, "删除场景中的组失败，场景中的组列表为空");
					return false;
				}
				
				if(senceGroup.get(position) != null){
					List<GroupBean> list1 = senceGroup.get(position);
					int listLength = list1.size();
					for(int j = 0; j < listLength; j++){
						if(deleteBeanId == list1.get(j).getId()){
							list1.remove(j);
							break;
						}
					}
					senceGroup.put(position, list1);
					ChangeDataUtils.setSenceGroup(senceId, senceGroup);
				}
				return true;
			}
		}
		
		LogUtils.e(ChangeDataUtils.class, "删除场景中的组失败，对应场景不存在");
		return false;
	}
	
	/**
	 * 删除场景
	 * 
	 * @param senceId		需要删除的场景id
	 * @return				删除成功返回true，否则返回false
	 */
	public static boolean deleteSence(int senceId){
		//配置对象不存在则不允许操作
		if(config == null){
			LogUtils.e(ChangeDataUtils.class, "删除场景失败，配置对象不存在");
			return false;
		}
		
		//场景列表为空则不允许操作
		ArrayList<SenceBean> list = LCConfig.getSences();
		if(list == null || list.size() == 0){
			LogUtils.e(ChangeDataUtils.class, "删除场景失败，场景列表为空");
			return false;
		}
		
		//找到id对应的场景则删除
		int length = list.size();
		for(int i = 0; i < length; i++){
			if(senceId == list.get(i).getId()){
				list.remove(i);
				LCConfig.setSenceNum(LCConfig.getSenceNum() - 1);
				return true;
			}
		}
		
		LogUtils.e(ChangeDataUtils.class, "删除场景失败，需要删除的场景不存在");
		return false;
	}
	
/////////////////////////////////////////////////////////////////////////////////
//                                       show                                  //                                                       //
/////////////////////////////////////////////////////////////////////////////////
	/**
	 * 修改或添加场景到show
	 * 建议界面操作中设置后调用set函数
	 * 
	 * @param context		上下文用于提示
	 * @param showId		当前操作的show
	 * @param sence			需要修改或添加到show的场景
	 * @param position		容器位置
	 */
	@Deprecated
	public static void addOrAlterSenceToShow(Context context, int showId, SenceBean sence, int position){
		//配置对象为空或show列表为空则不允许操作
		if(config == null || LCConfig.getShows() == null || LCConfig.getShowNum() == 0){
			Toast.makeText(context, "show设置失败，该show不存在", Toast.LENGTH_SHORT).show();
			LogUtils.e(ChangeDataUtils.class, "修改或添加场景到show失败，配置对象或show列表为空");
			return;
		}
		
		//查找id对应的show
		ArrayList<ShowBean> list = LCConfig.getShows();
		int length = LCConfig.getSenceNum();
		for(int i = 0; i < length; i++){
			if(showId == list.get(i).getId()){
				HashMap<Integer, SenceBean> list1 = list.get(i).getSences();
				if(list1 == null){
					list1 = new HashMap<Integer, SenceBean>();
				}
				list1.put(position, sence);
				ChangeDataUtils.alterOrAddSence(sence, sence.getName());
				ChangeDataUtils.setShowSences(showId, list1);
				return;
			}
		}
		
		//找不到则提示
		Toast.makeText(context, "修改或添加场景到show失败，该show不存在", Toast.LENGTH_SHORT).show();
		LogUtils.e(ChangeDataUtils.class, "修改或添加场景到show中失败，找不到对应的show");
	}
	
	/**
	 * 修改或添加show
	 * 
	 * @param show			需要修改或添加的show
	 * @param showName		需要修改或添加的show名字
	 * @return				修改或添加成功则返回true，否则返回false
	 */
	public static boolean alterOrAddShow(ShowBean show, String showName){
		//配置对象为空则不允许操作
		if(config == null){
			LogUtils.e(ChangeDataUtils.class, "修改或添加show失败，配置对象为空");
			return false;
		}
		
		ArrayList<ShowBean> list = LCConfig.getShows();
		//show列表为空时直接添加
		if(list == null || list.size() == 0){
			show.setId(LCConfig.getShowNum() + 1);
			
			if(showName == null){
				show.setName(String.valueOf(LCConfig.getShowNum() + 1) + "号show");
			}else{
				show.setName(showName);
			}
			
			list = new ArrayList<ShowBean>();
			list.add(show);
			LCConfig.setShows(list);
			LCConfig.setMemberNum(LCConfig.getMemberNum() + 1);
			return true;
		}
		
		//找到对应的show对象则进行修改
		int length = list.size();
		for(int i = 0; i < length; i++){
			if(show.getId() == list.get(i).getId()){
				list.remove(i);
				list.add(i, show);
				LCConfig.setShows(list);
				return true;
			}
		}
		
		//找不到对应show对象则进行添加
		show.setId(LCConfig.getShowNum() + 1);
		
		if(showName == null){
			show.setName(String.valueOf(LCConfig.getShowNum() + 1) + "号show");
		}else{
			show.setName(showName);
		}
		
		list.add(show);
		LCConfig.setShows(list);
		LCConfig.setMemberNum(LCConfig.getMemberNum() + 1);
		return true;
	}
	
	/**
	 * 删除show中的场景
	 * 建议界面操作设置后调用set函数
	 * 
	 * @param context
	 * @param showId		当前操作的show
	 * @param position		容器位置
	 * @return				删除成功则返回true，否则返回false
	 */
	@Deprecated
	public static boolean deleteShowSence(Context context, int showId, int position, SenceBean deleteBean){
		//配置对象为空则不允许操作
		if(config == null){
			LogUtils.e(ChangeDataUtils.class, "删除show中的场景失败，配置对象为空");
			return false;
		}
		
		//show列表为空则不允许操作
		ArrayList<ShowBean> list = LCConfig.getShows();
		if(list == null || list.size() == 0){
			LogUtils.e(ChangeDataUtils.class, "删除show中的场景失败，show列表为空");
			return false;
		}
		
		//查找id对应的show
		int length = list.size();
		for(int i = 0; i < length; i++){
			//找到则进行操作
			if(showId == list.get(i).getId()){
				HashMap<Integer, SenceBean> showSences = list.get(i).getSences();
				
				if(showSences == null){
					LogUtils.e(ChangeDataUtils.class, "删除show中的场景失败，show中场景列表为空");
					return false;
				}
				
				if(showSences.get(position) != null){
					showSences.put(position, null);
					ChangeDataUtils.setShowSences(showId, showSences);
				}
				
				return true;
			}
		}
		//找不到则记录
		LogUtils.e(ChangeDataUtils.class, "删除show中的场景失败，该show不存在");
		return false;
	}
	
	/**
	 * 删除show
	 * 
	 * @param showId		需要删除的show id
	 * @return				删除成功则返回true，否则返回false
	 */
	public static boolean deleteShow(int showId){
		//配置对象为空则不允许操作
		if(config == null){
			LogUtils.e(ChangeDataUtils.class, "删除show失败，配置对象不存在");
			return false;
		}
		
		//show列表为空则不允许操作
		ArrayList<ShowBean> list = LCConfig.getShows();
		if(list == null || list.size() == 0){
			LogUtils.e(ChangeDataUtils.class, "删除show失败，show列表为空");
			return false;
		}
		
		//查找id对应的show，找到则删除并更新
		int length = list.size();
		for(int i = 0; i < length; i++){
			if(showId == list.get(i).getId()){
				list.remove(i);
				LCConfig.setShowNum(LCConfig.getShowNum() - 1);
				return true;
			}
		}
		
		//找不到则记录
		LogUtils.e(ChangeDataUtils.class, "删除show失败，无对应的show");
		return false;
	}

}
