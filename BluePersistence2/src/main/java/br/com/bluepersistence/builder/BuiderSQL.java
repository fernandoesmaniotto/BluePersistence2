package br.com.bluepersistence.builder;

public interface BuiderSQL {
	
	public StringBuilder sql(Object bean) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException;

}
