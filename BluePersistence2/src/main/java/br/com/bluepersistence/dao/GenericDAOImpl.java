package br.com.bluepersistence.dao;

import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.bluepersistence.builder.BuiderSQL;
import br.com.bluepersistence.builder.BuiderSelectSQL;
import br.com.bluepersistence.builder.SQLBuiderFactory;
import br.com.bluepersistence.cursor.android.builder.BluePersistenceCursor;

public class GenericDAOImpl implements GenericDAO{

	public void salvar(Object bean, SQLiteDatabase database) throws SQLException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
		BuiderSQL inserir = SQLBuiderFactory.getInstance("INSERIR");		
		database.execSQL(inserir.sql(bean).toString());
	}

	public void excluir(Object bean, SQLiteDatabase database) throws SQLException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
		BuiderSQL excluir = SQLBuiderFactory.getInstance("EXCLUIR");
		database.execSQL(excluir.sql(bean).toString());	
	}

	public void atualizar(Object bean, SQLiteDatabase database) throws SQLException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
		BuiderSQL atualizar = SQLBuiderFactory.getInstance("ATUALIZAR");
		database.execSQL(atualizar.sql(bean).toString());	
	}

	public List<?> consultar(Query query, SQLiteDatabase database) {
		BuiderSelectSQL consultar = SQLBuiderFactory.getInstance();	
		Cursor cursor = database.rawQuery(consultar.sql(query).toString(), null);	
		BluePersistenceCursor listagem = new BluePersistenceCursor();		
		return listagem.listar(cursor, query.getTabela());
	}	

}
