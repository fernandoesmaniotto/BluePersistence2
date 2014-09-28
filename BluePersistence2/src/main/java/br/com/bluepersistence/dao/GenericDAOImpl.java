package br.com.bluepersistence.dao;

import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.bluepersistence.builder.BuilderSQLDelete;
import br.com.bluepersistence.builder.BuilderSQLInsert;
import br.com.bluepersistence.builder.BuilderSQLSelect;
import br.com.bluepersistence.builder.BuilderSQLUpdate;
import br.com.bluepersistence.builder.BuilderSQL;
import br.com.bluepersistence.builder.BuilderSelectSQL;
import br.com.bluepersistence.cursor.android.builder.BluePersistenceCursor;

public class GenericDAOImpl implements GenericDAO{

	public void salvar(Object bean, SQLiteDatabase database) throws SQLException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
		BuilderSQL inserir = new BuilderSQLInsert();
		database.execSQL(inserir.sql(bean).toString());
	}

	public void excluir(Object bean, SQLiteDatabase database) throws SQLException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
		BuilderSQL excluir = new BuilderSQLDelete();
		database.execSQL(excluir.sql(bean).toString());	
	}

	public void atualizar(Object bean, SQLiteDatabase database) throws SQLException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
		BuilderSQL atualizar = new BuilderSQLUpdate();
		database.execSQL(atualizar.sql(bean).toString());	
	}

	public List<?> consultar(Query query, SQLiteDatabase database) {
		BuilderSelectSQL consultar = new BuilderSQLSelect();
		Cursor cursor = database.rawQuery(consultar.sql(query).toString(), null);	
		BluePersistenceCursor listagem = new BluePersistenceCursor();		
		return listagem.listar(cursor, query.getTabela());
	}	

}
