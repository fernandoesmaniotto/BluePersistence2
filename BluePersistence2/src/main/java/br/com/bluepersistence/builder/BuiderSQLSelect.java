package br.com.bluepersistence.builder;

import br.com.bluepersistence.annotation.DroidTable;
import br.com.bluepersistence.dao.Query;

public class BuiderSQLSelect implements BuiderSelectSQL {

	public StringBuilder sql(Query query) {
		StringBuilder sql = new StringBuilder("");	
		Class<? extends Object> classe = query.getTabela().getClass();
		DroidTable droidTable = classe.getAnnotation(DroidTable.class);
		sql.append("SELECT ");
		if(query.getColunas().size() == 0)
			sql.append("*");
		else
			query.anexarColunas(query.getColunas());		
		sql.append(" FROM ");
		sql.append(droidTable.tabela());		
		sql.append(query.anexarWhere(query.getColunaWhere()));
		sql.append(query.anexarAnd(query.getColunaAnd()));
		sql.append(query.anexarOrderBy(query.getColunaOrderBy()));
		return sql;
	}
}
