package br.com.bluepersistence.builder;

import java.lang.reflect.Field;

import br.com.bluepersistence.annotation.DroidPrimaryKey;
import br.com.bluepersistence.annotation.DroidTable;

public class BuiderSQLDelete implements BuiderSQL {

	public StringBuilder sql(Object bean) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
		StringBuilder sql = new StringBuilder("");
		Class<? extends Object> classe = bean.getClass();
		DroidTable droidTable = classe.getAnnotation(DroidTable.class);		
		sql.append("DELETE FROM ");		
		sql.append(droidTable.tabela());		
		sql.append(" ");		
		for(Field campo : classe.getDeclaredFields()){				
			Field valorCampo = classe.getDeclaredField(campo.getName());
			valorCampo.setAccessible(true);
			DroidPrimaryKey droidPK = campo.getAnnotation(DroidPrimaryKey.class);
			if (droidPK instanceof DroidPrimaryKey) {
				sql.append("WHERE ");
				sql.append(campo.getName());
				sql.append(" = ");
				sql.append(valorCampo.get(bean));
				sql.append(";");
			}
		}		
		return sql;
	}
}
