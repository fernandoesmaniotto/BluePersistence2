package br.com.bluepersistence.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.bluepersistence.builder.BuilderSQL;
import br.com.bluepersistence.builder.BuilderSQLCreateDatabase;
import br.com.bluepersistence.builder.BuilderSQLCreateTable;

public final class BluePersistenceDBAdapter {

	private SQLiteDatabase database;
	private BluePersistenceDatabaseHelper blueDbHelper;	

	public BluePersistenceDBAdapter(Context context, String banco) {			
		blueDbHelper = new BluePersistenceDatabaseHelper(context, banco);
	}

	public BluePersistenceDBAdapter criarBanco(Object bean) throws SQLException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException {	
		BuilderSQL criarBanco = new BuilderSQLCreateDatabase();	
		blueDbHelper.setSqlCreateDatabase(criarBanco.sql(bean).toString());
		blueDbHelper.setBean(bean);		
		database = blueDbHelper.getWritableDatabase();
		return this;
	}
	
	public void criarTabela(Object bean) throws SQLException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException{
		blueDbHelper.setBean(bean);		
		BuilderSQL criarTabela = new BuilderSQLCreateTable();
		database.execSQL(criarTabela.sql(bean).toString());
	}
	
	public SQLiteDatabase abrir(String caminho) throws SQLException {		
		return database = SQLiteDatabase.openDatabase(caminho+blueDbHelper.getBanco(), null, SQLiteDatabase.OPEN_READWRITE);		
	}

	public void fechar() throws SQLException {
		blueDbHelper.close();
	}
}
