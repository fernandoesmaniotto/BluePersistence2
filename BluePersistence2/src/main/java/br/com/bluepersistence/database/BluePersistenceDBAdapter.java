package br.com.bluepersistence.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.bluepersistence.builder.BuiderSQL;
import br.com.bluepersistence.builder.SQLBuiderFactory;

public final class BluePersistenceDBAdapter {

	private SQLiteDatabase database;
	private BluePersistenceDatabaseHelper blueDbHelper;	

	public BluePersistenceDBAdapter(Context context, String banco) {			
		blueDbHelper = new BluePersistenceDatabaseHelper(context, banco);
	}

	public BluePersistenceDBAdapter criarBanco(Object bean) throws SQLException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException {	
		BuiderSQL criarBanco = SQLBuiderFactory.getInstance("CRIAR_BANCO");		
		blueDbHelper.setSqlCreateDatabase(criarBanco.sql(bean).toString());
		blueDbHelper.setBean(bean);		
		database = blueDbHelper.getWritableDatabase();
		return this;
	}
	
	public void criarTabela(Object bean) throws SQLException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException{
		blueDbHelper.setBean(bean);		
		BuiderSQL criarTabela = SQLBuiderFactory.getInstance("CRIAR_TABELA");
		database.execSQL(criarTabela.sql(bean).toString());
	}
	
	public SQLiteDatabase abrir(String caminho) throws SQLException {		
		return database = SQLiteDatabase.openDatabase(caminho+blueDbHelper.getBanco(), null, SQLiteDatabase.OPEN_READWRITE);		
	}

	public void fechar() throws SQLException {
		blueDbHelper.close();
	}
}
