package br.com.bluepersistence.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class BluePersistenceDatabaseHelper extends SQLiteOpenHelper {
	
	private String banco;
	private String sqlCreateDatabase;
	private Object bean;
	private static final int VERSAO = 1;
	
	public BluePersistenceDatabaseHelper(Context context, String banco) {
		super(context, banco, null, VERSAO);	
		this.banco = banco;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {		
		db.execSQL(sqlCreateDatabase);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int antigaVersao, int novaVersao) {
		if ((novaVersao - antigaVersao) > 2)  
            onCreate(db); 
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public String getSqlCreateDatabase() {
		return sqlCreateDatabase;
	}

	public void setSqlCreateDatabase(String sqlCreateDatabase) {
		this.sqlCreateDatabase = sqlCreateDatabase;
	}
	
	
}
