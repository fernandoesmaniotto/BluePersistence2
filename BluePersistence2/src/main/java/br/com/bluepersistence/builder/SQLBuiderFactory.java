package br.com.bluepersistence.builder;


public class SQLBuiderFactory {
	
	public static BuiderSQL getInstance(String operacao){
		if(operacao.equalsIgnoreCase("CRIAR_TABELA"))
			return new BuiderSQLCreateTable();
		else if(operacao.equalsIgnoreCase("INSERIR"))
			return new BuiderSQLInsert();
		else if(operacao.equalsIgnoreCase("EXCLUIR"))
			return new BuiderSQLDelete();
		else if (operacao.equalsIgnoreCase("CRIAR_BANCO"))
			return new BuiderSQLCreateDatabase();
		else
			return new BuiderSQLUpdate();		
	}
	
	public static BuiderSelectSQL getInstance(){
		return new BuiderSQLSelect();
	}	
}
