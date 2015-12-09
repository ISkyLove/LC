package com.lc.data.database;

import com.lc.common.LogUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ���ݿ������ �ؼ�����KEY_��ʼ ���ݱ�����TABLE_��ʼ ��������COMMAND_�_ʼ create table ������������������������ drop
 * table ���� insert into ������������������ values ��ֵ�������� update ���� set ����=xxx where xxx
 * delete from ���� where ���� Like������ %���� _ ����ʹ�á�%��������0�����߶���ַ�ƥ�䣬_�������ⵥ���ַ�ƥ�䡣
 * Order��Ϊasc��Ĭ�����򣩣�desc������ select xxx from ����1 inner join ����2 on ����1.���� =
 * ����2.���� select xxx from ����1 left outer join ����2 on ����1.���� = ����2.���� create
 * table ?(_id integer primary key autoincrement
 * 
 * @author Lin
 * 
 */
public class DBAdapter {
	public static final String TAG = "LCDatabaseInfo";
	public static final String DATABASE_NAME = "monon";
	public static final int DATABASE_VERSION = 1;
	private Context mContext;
	private DBHelper mDBHelper;
	private SQLiteDatabase mWDatabase;
	private SQLiteDatabase mRDatabase;
	private static DBAdapter mInstance;

	private DBAdapter(Context context) {
		this.mContext = context;
		mDBHelper = new DBHelper(mContext);

	}

	public static DBAdapter getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DBAdapter(context);
		}
		return mInstance;
	}

	public class DBHelper extends SQLiteOpenHelper {
		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("create table monon(_id integer primary key autoincrement)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS monon");
			onCreate(db);
		}
	}

	public DBHelper OpenDB() {
		if (mDBHelper != null) {
			mWDatabase = mDBHelper.getWritableDatabase();
			mRDatabase = mDBHelper.getReadableDatabase();
			return mDBHelper;
		}
		return null;
	}

	public void CloseDB() {
		if (mDBHelper != null) {
			mDBHelper.close();
		}
	}

	/**
	 * Ĭ�����ò���Ŀ�ֵ�����Ĭ������
	 * 
	 * @param table
	 * @param contentValues
	 * @return ����row ID
	 */
	public long insertRow(String table, ContentValues contentValues) {
		long _id = mWDatabase.insert(table, null, contentValues);
		if (_id > 0) {
			LogUtils.i(TAG, "�������ݳɹ�");
		} else {
			LogUtils.i(TAG, "��������ʧ��");
		}
		return _id;
	}

	/**
	 * 
	 * @param table
	 * @param whereClause
	 * @param whereArgs
	 * @return ��Ӱ�������
	 */
	public int deleteRows(String table, String whereClause, String[] whereArgs) {
		int affect_num = mWDatabase.delete(table, whereClause, whereArgs);
		if (affect_num > 0) {
			LogUtils.i(TAG, "ɾ�����ݳɹ�,��Ӱ����Ŀ:" + affect_num);
		} else {
			LogUtils.i(TAG, "ɾ������ʧ��");
		}
		return affect_num;
	}

	public int updateRows(String table, ContentValues contentvalues,
			String whereClause, String[] whereArgs) {
		int affect_num = mWDatabase.update(table, contentvalues, whereClause,
				whereArgs);
		if (affect_num > 0) {
			LogUtils.i(TAG, "�������ݳɹ�,��Ӱ����Ŀ:" + affect_num);
		} else {
			LogUtils.i(TAG, "��������ʧ��");
		}
		return affect_num;
	}

	/**
	 * ���ݲ��ң�Ĭ������ָ���Ƶ�����λ�õ�һλ
	 * 
	 * @param table
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @param limit
	 * @return
	 */
	public Cursor queryRows(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit) {
		Cursor mCursor = mRDatabase.query(table, columns, selection,
				selectionArgs, groupBy, having, orderBy, limit);
		if (mCursor != null) {
			mCursor.moveToFirst();
			LogUtils.i(TAG, "�������ݳɹ�,��������:" + mCursor.getCount() + "��");
		} else {
			LogUtils.i(TAG, "��������ʧ��");
		}
		return mCursor;
	}

	/**
	 * ����table���ֶ� �����������table�����ݶ�
	 * 
	 * @param tablename
	 * @param column
	 * @param type
	 * @param defValue
	 * @return
	 */
	public void AlterTable(String tablename, String column, String type,
			String defValue) {
		String alterStr = "ALTER TABLE " + tablename + " ADD COLUMN " + column
				+ " " + type + " DEFAULT " + defValue;
		mWDatabase.execSQL(alterStr);
	}

}
