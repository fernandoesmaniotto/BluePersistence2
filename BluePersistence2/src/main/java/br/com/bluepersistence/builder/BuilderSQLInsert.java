package br.com.bluepersistence.builder;

import java.lang.reflect.Field;

import br.com.bluepersistence.annotation.DroidColumn;
import br.com.bluepersistence.annotation.DroidForeignKey;
import br.com.bluepersistence.annotation.DroidTable;

public class BuilderSQLInsert implements BuilderSQL {

	public StringBuilder sql(Object bean) throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
		StringBuilder sql = new StringBuilder("");
		Class<? extends Object> classe = bean.getClass();
		DroidTable droidTable = classe.getAnnotation(DroidTable.class);
		sql.append("INSERT INTO ");		
		sql.append(droidTable.tabela());		
		sql.append("(");		
		boolean primeiroAtributo = true;
		boolean primeiroValor = true;			
		for (Field campo : classe.getDeclaredFields()) {				
			DroidColumn droidColumn = campo.getAnnotation(DroidColumn.class);
			DroidForeignKey droidFK = campo.getAnnotation(DroidForeignKey.class);			
			if (droidColumn instanceof DroidColumn) {					
				if(primeiroAtributo)
					sql.append("");
				else
					sql.append(", ");					
				sql.append(campo.getName());
				primeiroAtributo = false;
			}
			else if (droidFK instanceof DroidForeignKey) {
				if(primeiroAtributo)
					sql.append("");
				else
					sql.append(", ");					
				sql.append(campo.getName());
				primeiroAtributo = false;					
			}				
		}
		sql.append(")");
		sql.append(" VALUES ");
		sql.append("(");
		for (Field campo : classe.getDeclaredFields()) {
			Field valorCampo = classe.getDeclaredField(campo.getName());
			valorCampo.setAccessible(true);
			DroidColumn droidColumn = campo.getAnnotation(DroidColumn.class);
			DroidForeignKey droidFK = campo.getAnnotation(DroidForeignKey.class);
			if (droidColumn instanceof DroidColumn) {
				if(primeiroValor)
					sql.append("");
				else
					sql.append(",");		
				sql.append("'");
				sql.append(valorCampo.get(bean));
				sql.append("'");
				primeiroValor = false;		
			}
			else if (droidFK instanceof DroidForeignKey) {
				if(primeiroValor)
					sql.append("");
				else
					sql.append(",");		
				sql.append("'");
				sql.append(valorCampo.get(bean));
				sql.append("'");
				primeiroValor = false;		
			}
		}
		sql.append(");");
		return sql;
	}
}
