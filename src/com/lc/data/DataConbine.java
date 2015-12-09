package com.lc.data;

import java.util.ArrayList;
import java.util.List;

import com.lc.common.DataUtils;

/**
 * 组织发送的数据
 * 
 * @author LC work room
 * 
 */
public class DataConbine {
	private List<Byte> mdatas;
	private byte dataBegin;
	private byte dataEnd;
	private byte dataCommand;
	private static char DEFAULT_VALUE = 0xFF;

	public DataConbine(char command) {
		mdatas = new ArrayList<Byte>();
		dataBegin = DataUtils.getByte(DataConfig.DATA_BEGIN);
		dataEnd = DataUtils.getByte(DataConfig.DATA_END);
		this.dataCommand = DataUtils.getByte(command);
		mdatas.add(dataBegin);
		mdatas.add(dataCommand);
	}

	public void addByte(byte data) {
		mdatas.add(data);
	}

	public byte[] getTotalData() {

		byte[] mtotalbyte = new byte[8];
		for (int i = 0; i < (mtotalbyte.length - 1); i++) {
			if (i < mdatas.size()) {
				mtotalbyte[i] = mdatas.get(i).byteValue();
				continue;
			}
			mtotalbyte[i] = DataUtils.getByte(DEFAULT_VALUE);
		}
		mtotalbyte[7] = dataEnd;
		return mtotalbyte;
	}

	@Override
	public String toString() {
		StringBuffer mStringBuffer = new StringBuffer();
		mStringBuffer.append("data: ");
		for (Byte mdata : mdatas) {
			mStringBuffer.append(mdata.byteValue());
			mStringBuffer.append(" ");
		}
		return mStringBuffer.toString();
	}
}
