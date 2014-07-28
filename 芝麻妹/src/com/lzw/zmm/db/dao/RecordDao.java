package com.lzw.zmm.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.androidex.db.BaseDBHelp;
import com.lzw.zmm.bean.Record;

public class RecordDao extends BaseDBHelp{
	
	private static final String RECORD_TABLE_NAME = "scanner";
	private static final String RECORD_FIELD_TITLE = "title";
	private static final String RECORD_FIELD_COVER_URL = "cover_url";
	private static final String RECORD_FIELD_TIMESTAMP = "timestamp";
	
	public RecordDao(Context context) {
		
		super(context, "zhima.db", 1/*Integer.parseInt(AppInfoUtil.getVersionCode())*/);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(getTableRecordCreateSql());
	}

	private String getTableRecordCreateSql(){
		
		return "CREATE TABLE IF NOT EXISTS "+RECORD_TABLE_NAME+" ("+FIELD__ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
												 					 +RECORD_FIELD_TITLE+" TEXT, "
												 					 +RECORD_FIELD_COVER_URL+" TEXT, "		
												 					 +RECORD_FIELD_TIMESTAMP+" TEXT"+		
												 				  ")";
	}
	
	public void saveScanner(Record record){
		
		if(record == null)
			return;
		
		SQLiteDatabase db = null;
		try{
			
			String sql = "INSERT INTO "+RECORD_TABLE_NAME+" ("+RECORD_FIELD_TITLE+", "
															   +RECORD_FIELD_COVER_URL+", "
															   +RECORD_FIELD_TIMESTAMP+") "
															   +"values (?,?,?)";
			db = getWritableDatabase();
			db.execSQL(sql, new String[]{record.getTitle(),
										 record.getCoverUrl(),
										 String.valueOf(System.currentTimeMillis())});
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			closeDB(db);
		}
	}
	
	public void deleteRecord(int id){
		
		SQLiteDatabase db = null;
		try{
			
			String sql = "DELETE FROM "+RECORD_TABLE_NAME+" WHERE "+FIELD__ID+" = ?";
			db = getWritableDatabase();
			db.execSQL(sql, new String[]{String.valueOf(id)});
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			closeDB(db);
		}		
	}
	
	public List<Record> getAllRecord(){
		
		ArrayList<Record> records = new ArrayList<Record>();
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try{
			
//			String sql = "SELECT "+FIELD__ID+", "
//								  +RECORD_FIELD_TITLE+", "
//								  +RECORD_FIELD_COVER_URL+", "
//								  +RECORD_FIELD_TIMESTAMP+" FROM "+RECORD_TABLE_NAME+" ORDER BY "+FIELD__ID+" DESC";
//			
//			db = getReadableDatabase();
//			cursor = db.rawQuery(sql, null);
			Record record = null;
//			while(cursor.moveToNext()){
//				
//				scanner = new Record();
//				scanner.setId(cursor.getInt(0));
//				scanner.setTitle(cursor.getString(1));
//				scanner.setCoverUrl(cursor.getString(2));
//				scanner.setTimeStamp(cursor.getLong(3));
//				records.add(scanner);
//			}
			for(int i=0; i<50; i++){
				
				record = new Record();
				record.setId(i+1);
				record.setTitle("补水保湿包面霜50ml补水保湿包面霜50ml补水保湿包面霜50ml补水保湿包面霜50ml补水保湿包面霜50ml"+(i+1));
				record.setCoverUrl("http://www.asdfjasdfj.com");
				record.setTimeStamp(System.currentTimeMillis()+i+1);
				records.add(record);
			}
			
		}catch(Exception e){
			
			records.clear();
			e.printStackTrace();
			
		}finally{
			closeCursorAndDB(cursor, db);
		}
		return records;
	}
}
