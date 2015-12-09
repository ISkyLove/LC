package com.lc.data.database;

import com.lc.common.LogUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类 关键字以KEY_开始 数据表名以TABLE_开始 命令行以COMMAND_開始 create table 表名（列名，列名，……） drop
 * table 表名 insert into 表名（列名，……） values （值，……） update 表名 set 列名=xxx where xxx
 * delete from 表名 where 条件 Like经常和 %或者 _ 搭配使用。%可与任意0个或者多个字符匹配，_可与任意单个字符匹配。
 * Order分为asc（默认升序），desc（降序） select xxx from 表名1 inner join 表名2 on 表名1.列名 =
 * 表名2.列名 select xxx from 表名1 left outer join 表名2 on 表名1.列名 = 表名2.列名 create
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
	 * 默认设置插入的空值不填充默认数据
	 * 
	 * @param table
	 * @param contentValues
	 * @return 返回row ID
	 */
	public long insertRow(String table, ContentValues contentValues) {
		long _id = mWDatabase.insert(table, null, contentValues);
		if (_id > 0) {
			LogUtils.i(TAG, "插入数据成功");
		} else {
			LogUtils.i(TAG, "插入数据失败");
		}
		return _id;
	}

	/**
	 * 
	 * @param table
	 * @param whereClause
	 * @param whereArgs
	 * @return 受影响的数量
	 */
	public int deleteRows(String table, String whereClause, String[] whereArgs) {
		int affect_num = mWDatabase.delete(table, whereClause, whereArgs);
		if (affect_num > 0) {
			LogUtils.i(TAG, "删除数据成功,受影响数目:" + affect_num);
		} else {
			LogUtils.i(TAG, "删除数据失败");
		}
		return affect_num;
	}

	public int updateRows(String table, ContentValues contentvalues,
			String whereClause, String[] whereArgs) {
		int affect_num = mWDatabase.update(table, contentvalues, whereClause,
				whereArgs);
		if (affect_num > 0) {
			LogUtils.i(TAG, "更新数据成功,受影响数目:" + affect_num);
		} else {
			LogUtils.i(TAG, "更新数据失败");
		}
		return affect_num;
	}

	/**
	 * 数据查找，默认数据指针移到数据位置第一位
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
			LogUtils.i(TAG, "查找数据成功,返回数据:" + mCursor.getCount() + "条");
		} else {
			LogUtils.i(TAG, "查找数据失败");
		}
		return mCursor;
	}

	/**
	 * 增加table的字段 尽量避免添加table的数据段
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
