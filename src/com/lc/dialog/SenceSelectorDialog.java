package com.lc.dialog;


import java.util.List;

import com.lc.LCConfig;
import com.lc.R;
import com.lc.common.DataUtils;
import com.lc.common.IItemPostionVisitor;
import com.lc.data.SenceItem;
import com.lc.sence.bean.SenceBean;
import com.lc.view.LCSenceView;
import com.lc.view.LCSenceView.OnSenceItemClickListen;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
/**
 * 选择场景对话框
 * 
 * @author LC work room
 *
 */
public class SenceSelectorDialog extends Dialog implements OnSenceItemClickListen{
	
	private Context mContext;
	private IItemPostionVisitor itemPostionVisitor;		//获取位置回调，点击item后调用
	private List<SenceItem> senceItems;					//显示的数据列表
	private LCSenceView mSenceView;
	

	public SenceSelectorDialog(Context context, int theme) {
		super(context, R.style.dialog_tran);
		mContext = context;
		init();
	}
	
	public SenceSelectorDialog(Context context) {
		super(context, R.style.dialog_tran);
		mContext = context;
		init();
	}
	
	@SuppressLint("InflateParams") 
	private void init(){
		View contentView = LayoutInflater.from(mContext).inflate(R.layout.v1_sence_select_dialog, null);
		LinearLayout gridView = (LinearLayout) contentView.findViewById(R.id.sence_select_list_gv);
		mSenceView = new LCSenceView(mContext, false);
		mSenceView.setColumnsNum(2);
		updateSenceItems();
		mSenceView.setOnSenceItemClickListen(this);
		gridView.addView(mSenceView.getRootView());
		TextView tvClose = (TextView)contentView.findViewById(R.id.sence_select_close_tv);
		tvClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (SenceSelectorDialog.this.isShowing()) {
					SenceSelectorDialog.this.dismiss();
				}
			}
		});
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, 900);
		this.addContentView(contentView, params);
	}


	public IItemPostionVisitor getItemPostionVisitor() {
		return itemPostionVisitor;
	}

	public void setItemPostionVisitor(IItemPostionVisitor itemPostionVisitor) {
		this.itemPostionVisitor = itemPostionVisitor;
	}
	

	public List<SenceItem> getSenceItems() {
		updateSenceItems();
		return senceItems;
	}
	
	/**
	 * 更新列表，每次操作都进行更新
	 */
	private void updateSenceItems(){
		List<SenceBean> senceBeans = LCConfig.getSences();
		senceItems = DataUtils.coverSenceBeansToSenceItems(mContext, senceBeans);
		mSenceView.changeData(senceItems);
	}

	@Override
	public void OnSenceItemClick(int position, SenceItem senceitem) {
		if(itemPostionVisitor != null){
			itemPostionVisitor.visiteItemPos(position);
		}
	}

}
