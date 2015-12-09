package com.lc;

import com.lc.common.bean.LCBaseBean;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class LCFragment extends Fragment {
	protected int left;
	protected int top;
	protected int right;
	protected int bottom;

	protected abstract View bindView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return bindView(inflater, container, savedInstanceState);
	}

	protected OnSendDataListen mSendDataListen;

	public void setOnSendDataListen(OnSendDataListen onSendDataListen) {
		this.mSendDataListen = onSendDataListen;
	}

	public interface OnSendDataListen {
		void sendData(byte[] data, LCBaseBean basebean);
	}

	public void setRange(int l, int t, int r, int b) {
		this.left = l;
		this.top = t;
		this.right = r;
		this.bottom = b;
	}

}
