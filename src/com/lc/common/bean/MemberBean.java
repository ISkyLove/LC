package com.lc.common.bean;

/**
 * 灯对应的bean
 * 
 * @author LC work room
 *
 */
public class MemberBean extends LCBaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7110066020001251477L;
	private boolean groupedFlag;			//是否已被分组标志
	private int belongedGroupId = -1;		//被分组的组id
	private byte alpha = 122;
	public boolean isGroupedFlag() {
		return groupedFlag;
	}
	public void setGroupedFlag(boolean groupedFlag) {
		this.groupedFlag = groupedFlag;
	}
	public int getBelongedGroupId() {
		return belongedGroupId;
	}
	public void setBelongedGroupId(int belongedGroupId) {
		this.belongedGroupId = belongedGroupId;
	}
	public byte getAlpha() {
		return alpha;
	}
	public void setAlpha(byte alpha) {
		this.alpha = alpha;
	}			

}
