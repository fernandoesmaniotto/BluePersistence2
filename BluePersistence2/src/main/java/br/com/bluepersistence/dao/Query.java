package br.com.bluepersistence.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query extends AppendQuery {
	
	private List<String> colunas = new ArrayList<String>();
	private Map<String, Object> colunaWhere = new HashMap<String, Object>();
	private Map<String, Object> colunaAnd = new HashMap<String, Object>();
	private Map<String, String> colunaOrderBy = new HashMap<String, String>();
	private Object tabela;	
	
	public Query from(Object tabela){
		this.tabela = tabela;
		return this;
	}
	
	public Query coluna(String coluna){
		colunas.add(coluna);
		return this;
	}
	
	public Query where(String campoWhere, Object valorWhere){
		colunaWhere.put(campoWhere, valorWhere);
		return this;
	}
	
	public Query and(String campoAnd, Object valorAnd){
		colunaAnd.put(campoAnd, valorAnd);
		return this;
	}
	
	public Query orderBy(String campoOrdem, String tipoOrdem){
		colunaOrderBy.put(campoOrdem, tipoOrdem);
		return this;
	}
	
	public List<String> getColunas() {
		return colunas;
	}

	public void setColunas(List<String> colunas) {
		this.colunas = colunas;
	}

	public Map<String, Object> getColunaWhere() {
		return colunaWhere;
	}

	public void setColunaWhere(Map<String, Object> colunaWhere) {
		this.colunaWhere = colunaWhere;
	}

	public Map<String, Object> getColunaAnd() {
		return colunaAnd;
	}

	public void setColunaAnd(Map<String, Object> colunaAnd) {
		this.colunaAnd = colunaAnd;
	}

	public Map<String, String> getColunaOrderBy() {
		return colunaOrderBy;
	}

	public void setColunaOrderBy(Map<String, String> colunaOrderBy) {
		this.colunaOrderBy = colunaOrderBy;
	}

	public Object getTabela() {
		return tabela;
	}

	public void setTabela(Object tabela) {
		this.tabela = tabela;
	}
}
