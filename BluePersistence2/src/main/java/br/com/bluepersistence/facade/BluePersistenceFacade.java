package br.com.bluepersistence.facade;

import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import br.com.bluepersistence.dao.GenericDAO;
import br.com.bluepersistence.dao.GenericDAOImpl;
import br.com.bluepersistence.dao.Query;
import br.com.bluepersistence.database.BluePersistenceDBAdapter;

public class BluePersistenceFacade {
	
	private BluePersistenceDBAdapter dbPersistence;
	private GenericDAO crud;
	private SQLiteDatabase database;
	
	public BluePersistenceFacade(Context context, String banco){
		dbPersistence = new BluePersistenceDBAdapter(context, banco);
		crud = new GenericDAOImpl();
	}
	
	public String criarBanco(Object bean){
		String retorno = "";
		try {
			dbPersistence.criarBanco(bean);
			retorno = "Banco criado com sucesso";
		}catch (SQLException e) {
			retorno =  "Erro ao criar banco de dados, destalhes: "+e;
		} catch (NoSuchFieldException e) {			
			e.printStackTrace();
		} catch (IllegalAccessException e) {			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {			
			e.printStackTrace();
		}		
		return retorno;
	}
	
	public String criarTabela(Object bean){
		String retorno = "";
		try {
			dbPersistence.criarTabela(bean);
			retorno = "Tabela:"+bean.getClass().getName()+" criado(a) com sucesso.";
		}catch (SQLException e) {
			retorno =  "Erro ao criar tabela, destalhes: "+e;
		} catch (NoSuchFieldException e) {
			retorno =  "Erro ao criar tabela, destalhes: "+e;			
		} catch (IllegalAccessException e) {
			retorno =  "Erro ao criar tabela, destalhes: "+e;			
		} catch (IllegalArgumentException e) {
			retorno =  "Erro ao criar tabela, destalhes: "+e;			
		}
		return retorno;
	}
	
	public boolean abrirConexao(String caminho){
		boolean retorno = false;
		try {
			database = dbPersistence.abrir(caminho);
			retorno = true;
		}catch (SQLException e) {
			retorno = false;
		}
		return retorno;
	}
	
	public boolean fecharConexao(){		
		boolean retorno = false;
		try {
			dbPersistence.fechar();
			retorno = true;
		}catch (SQLException e) {
			retorno = false;
		}
		return retorno;
	}
	
	public String salvar(Object bean){
		String retorno = "";
		try {
			crud.salvar(bean, database);
			retorno = "Informação salva com sucesso!";
		} catch (SQLException e) {			
			retorno = "Erro ao salvar informação, detalhes: "+e;
		} catch (NoSuchFieldException e) {			
			retorno = "Erro ao salvar informação, detalhes: "+e;
		} catch (IllegalAccessException e) {			
			retorno = "Erro ao salvar informação, detalhes: "+e;
		} catch (IllegalArgumentException e) {			
			retorno = "Erro ao salvar informação, detalhes: "+e;
		}
		return retorno;
	}
	
	public String excluir(Object bean){
		String retorno = "";
		try {
			crud.excluir(bean, database);
			retorno = "Informação excluída com sucesso!";
		} catch (SQLException e) {			
			retorno = "Erro ao excluir informação, detalhes: "+e;
		} catch (NoSuchFieldException e) {			
			retorno = "Erro ao excluir informação, detalhes: "+e;
		} catch (IllegalAccessException e) {			
			retorno = "Erro ao excluir informação, detalhes: "+e;
		} catch (IllegalArgumentException e) {			
			retorno = "Erro ao excluir informação, detalhes: "+e;
		}
		return retorno;
	}
	
	public String atualizar(Object bean){
		String retorno = "";
		try {
			crud.atualizar(bean, database);
			retorno = "Informação atualizada com sucesso!";
		} catch (SQLException e) {			
			retorno = "Erro ao atualizar informação, detalhes: "+e;
		} catch (NoSuchFieldException e) {			
			retorno = "Erro ao atualizar informação, detalhes: "+e;
		} catch (IllegalAccessException e) {			
			retorno = "Erro ao atualizar informação, detalhes: "+e;
		} catch (IllegalArgumentException e) {			
			retorno = "Erro ao atualizar informação, detalhes: "+e;
		}
		return retorno;
	}
	
	public List<?> consultar(Query query){		
		return crud.consultar(query, database);		
	}	
}
