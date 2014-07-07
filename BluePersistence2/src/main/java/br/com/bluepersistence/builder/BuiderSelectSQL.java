package br.com.bluepersistence.builder;

import br.com.bluepersistence.dao.Query;

public interface BuiderSelectSQL {
	
	public StringBuilder sql(Query query);

}
