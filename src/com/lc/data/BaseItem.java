package com.lc.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;

import com.lc.R;
import com.lc.common.bean.LCBaseBean;

/**
 * �����б������� Ĭ�ϰ���id�����ͼƬ���Զ������ơ��Ҳ�ͼƬ����ɫ�Լ�һ��Ĭ�ϵ����°����������б�mChildlist
 * 
 * @author LC work room
 * 
 */
public class BaseItem extends LCBaseBean {

	protected Drawable mLeftDra;
	protected ShapeDrawable mRightDra;

	protected Context mContext;
	private float[] outRadii = new float[] { 0, 0, 8, 8, 8, 8, 0, 0 };
	private boolean isFoceus = false;

	public enum TYPE {
		TYPE_LIGHT, TYPE_GROUP, TYPE_SENCE, TYPE_SHOW
	}

	/**
	 * ��ǰ�����������
	 */
	private TYPE cur_type = TYPE.TYPE_LIGHT;

	public BaseItem(Context context) {
		this.mContext = context;
		/**
		 * ��Բ����Բ��ͼ
		 */
		OvalShape mOvalShape = new OvalShape();
		/**
		 * Բ�Ǿ���
		 */
		RoundRectShape mRectShape = new RoundRectShape(outRadii, null, null);

		mChildlist = new ArrayList<>();
		mRightDra = new ShapeDrawable(mOvalShape);
		setItemColor(Color.parseColor("#ffffff"));
		initLeftDra(cur_type);
	}

	public void setItemRectMode() {
		/**
		 * Բ�Ǿ���
		 */
		RoundRectShape mRectShape = new RoundRectShape(outRadii, null, null);
		mRightDra.setShape(mRectShape);
	}

	private void initLeftDra(TYPE type) {
		switch (type) {
		case TYPE_LIGHT:
			cur_type = TYPE.TYPE_LIGHT;
			mLeftDra = mContext.getResources().getDrawable(
					R.drawable.v1_icon_light);
			break;
		case TYPE_GROUP:
			cur_type = TYPE.TYPE_GROUP;
			mLeftDra = mContext.getResources().getDrawable(
					R.drawable.v1_icon_group);
			break;
		case TYPE_SENCE:
			cur_type = TYPE.TYPE_SENCE;
			mLeftDra = mContext.getResources().getDrawable(
					R.drawable.v1_icon_sence);
			break;
		case TYPE_SHOW:
			cur_type = TYPE.TYPE_SHOW;
			mLeftDra = mContext.getResources().getDrawable(
					R.drawable.v1_icon_show);
			break;
		default:
			break;
		}
	}

	public Drawable getItemLeftDra() {
		return mLeftDra;
	}

	public ShapeDrawable getItemRightDra() {
		return mRightDra;
	}

	public String getItemName() {
		return this.name;
	}

	public void setItemName(String strname) {
		this.name=strname;
	}

	public void setItemRemark(String strremark) {
		this.remark=strremark;;
	}

	public String getItemRemark() {
		return remark;
	}

	public int getItemColor() {
		return mcolor;
	}

	public void setItemColor(int color) {
		this.mcolor = color;
		mRightDra.getPaint().setColor(mcolor);
	}

	public TYPE getItemType() {
		return cur_type;
	}

	public void setItemType(TYPE type) {
		this.cur_type = type;
		initLeftDra(cur_type);
	}

	public List getItemChildData() {
		return mChildlist;
	}

	public boolean isItemFoceus() {
		return isFoceus;
	}

	public void setItemFoceus(boolean isFoceus) {
		this.isFoceus = isFoceus;
	}

	public void changeItemChildData(List data) {
		this.mChildlist = data;
	}

	public int getItemId() {
		return id;
	}

	public void setItemId(int id) {
		this.id = id;
	}
}
