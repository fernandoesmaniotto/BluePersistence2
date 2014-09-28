package br.com.bluepersistence.builder;

import br.com.bluepersistence.dao.Query;

public interface BuilderSelectSQL {
	
	public StringBuilder sql(Query query);

}
