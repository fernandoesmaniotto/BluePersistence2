package br.com.bluepersistence.builder;

public interface BuilderSQL {
	
	public StringBuilder sql(Object bean) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException;

}
