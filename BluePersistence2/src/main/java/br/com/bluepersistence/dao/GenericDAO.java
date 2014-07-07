package br.com.bluepersistence.dao;

import java.util.List;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public interface GenericDAO {
	
	public void salvar(Object bean, SQLiteDatabase database) throws SQLException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException;
	public void excluir(Object bean, SQLiteDatabase database) throws SQLException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException;
	public void atualizar(Object bean, SQLiteDatabase database) throws SQLException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException;
	public List<?> consultar(Query query, SQLiteDatabase database) ;

}
