package com.lc.dialog;

import java.util.List;

import android.content.Context;

import com.lc.data.GroupItem;
import com.lc.view.LCGridView.DoubleClickCallBackInterface;
import com.lc.view.LCGroupView;

public class GroupListDialog {

	private Context mContext;
	private LCBaseDialog mBaseDialog;
	private LCGroupView mGroupView;
	
	public GroupListDialog(Context context) {
		mContext = context;
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		mBaseDialog = new LCBaseDialog(mContext);
		mGroupView = new LCGroupView(mContext, false);
		mBaseDialog.setTitle("场景下的组");
		mBaseDialog.setCanceledOnTouchOutside(true);
		mBaseDialog.setCustomView(mGroupView.getRootView(), mContext);
	}
	
	public void showDialog(){
		if(mBaseDialog != null && !mBaseDialog.isShowing()){
			mBaseDialog.show();
		}
	}
	
	public void dismissDialog(){
		if(mBaseDialog != null && mBaseDialog.isShowing()){
			mBaseDialog.dismiss();
		}
	}
	
	public void chanData(List<GroupItem> groupItems){
		mGroupView.changeData(groupItems);
	}
	
	public void setDoublClick(DoubleClickCallBackInterface doubleClickCallBack){
		mGroupView.setDoubleClickCallback(doubleClickCallBack);
	}
	
}
