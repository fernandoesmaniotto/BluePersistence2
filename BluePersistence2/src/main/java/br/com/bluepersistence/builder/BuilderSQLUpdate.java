package br.com.bluepersistence.builder;

import java.lang.reflect.Field;

import br.com.bluepersistence.annotation.DroidColumn;
import br.com.bluepersistence.annotation.DroidForeignKey;
import br.com.bluepersistence.annotation.DroidPrimaryKey;
import br.com.bluepersistence.annotation.DroidTable;
import br.com.bluepersistence.database.DataType;

public class BuilderSQLUpdate implements BuilderSQL {

	public StringBuilder sql(Object bean) {
		StringBuilder sql = new StringBuilder("");
		StringBuilder campoWhere = new StringBuilder();
		Class<? extends Object> classe = bean.getClass();
		DroidTable droidTable = classe.getAnnotation(DroidTable.class);		
		sql.append("UPDATE ");
		sql.append(droidTable.tabela());
		sql.append(" SET ");
		boolean primeiroValor = true;
		try {
			for (Field campo : classe.getDeclaredFields()) {				
				Field valorCampo = classe.getDeclaredField(campo.getName());
				DroidColumn droidColumn = campo.getAnnotation(DroidColumn.class);
				DroidPrimaryKey droidPK = campo.getAnnotation(DroidPrimaryKey.class);
				DroidForeignKey droidFK = campo.getAnnotation(DroidForeignKey.class);
				valorCampo.setAccessible(true);
				if (droidColumn instanceof DroidColumn) {
					if((DataType.INTEGER.name().equals(droidColumn.tipo())) && 
							(valorCampo.get(bean) != null)){
						if(primeiroValor)
							sql.append("");
						else
							sql.append(", ");
						sql.append(campo.getName());
						sql.append(" = ");
						sql.append(valorCampo.get(bean));						
						primeiroValor = false;
					}
					else if((DataType.DATE.name().equals(droidColumn.tipo())) && 
							(valorCampo.get(bean) != null)){	
						if(primeiroValor)
							sql.append("");
						else
							sql.append(", ");
						sql.append(campo.getName());
						sql.append(" = ");
						sql.append(valorCampo.get(bean));						
						primeiroValor = false;
					}						
					else if((DataType.BOOLEAN.name().equals(droidColumn.tipo())) && 
							(valorCampo.get(bean) != null)){
						if(primeiroValor)
							sql.append("");
						else
							sql.append(", ");
						sql.append(campo.getName());
						sql.append(" = ");
						sql.append(valorCampo.get(bean));						
						primeiroValor = false;
					}					
					else if((DataType.BLOB.name().equals(droidColumn.tipo())) && 
							(valorCampo.get(bean) != null)){
						if(primeiroValor)
							sql.append("");
						else
							sql.append(", ");
						sql.append(campo.getName());
						sql.append(" = ");
						sql.append(valorCampo.get(bean));						
						primeiroValor = false;
					}						
					else if((DataType.DOUBLE.name().equals(droidColumn.tipo())) && 
							(valorCampo.get(bean) != null)){
						if(primeiroValor)
							sql.append("");
						else
							sql.append(", ");
						sql.append(campo.getName());
						sql.append(" = ");
						sql.append(valorCampo.get(bean));						
						primeiroValor = false;
					}						
					else if((DataType.FLOAT.name().equals(droidColumn.tipo())) && 
							(valorCampo.get(bean) != null)){
						if(primeiroValor)
							sql.append("");
						else
							sql.append(", ");
						sql.append(campo.getName());
						sql.append(" = ");
						sql.append(valorCampo.get(bean));						
						primeiroValor = false;
					}						
					else if((DataType.DECIMAL.name().equals(droidColumn.tipo())) && 
							(valorCampo.get(bean) != null)){
						if(primeiroValor)
							sql.append("");
						else
							sql.append(", ");
						sql.append(campo.getName());
						sql.append(" = ");
						sql.append(valorCampo.get(bean));						
						primeiroValor = false;
					}						
					else if((DataType.CHAR.name().equals(droidColumn.tipo()))	&& 
							(valorCampo.get(bean) != null)){
						if(primeiroValor)
							sql.append("");
						else
							sql.append(", ");
						sql.append(campo.getName());
						sql.append(" = ");
						sql.append("'");
						sql.append(valorCampo.get(bean));
						sql.append("'");						
						primeiroValor = false;
					}						
					else if((DataType.VARCHAR.name().equals(droidColumn.tipo())) && 
							(valorCampo.get(bean) != null)){
						if(primeiroValor)
							sql.append("");
						else
							sql.append(", ");
						sql.append(campo.getName());
						sql.append(" = ");
						sql.append("'");
						sql.append(valorCampo.get(bean));
						sql.append("'");
						primeiroValor = false;
					}						
					else if((DataType.TEXT.name().equals(droidColumn.tipo())) && 
							(valorCampo.get(bean) != null)){
						if(primeiroValor)
							sql.append("");
						else
							sql.append(", ");
						sql.append(campo.getName());
						sql.append(" = ");
						sql.append("'");
						sql.append(valorCampo.get(bean));
						sql.append("'");						
						primeiroValor = false;
					}						
				}
				else if (droidFK instanceof DroidForeignKey) {					
					sql.append(campo.getName());
					sql.append(" = ");
					sql.append("'");
					sql.append(valorCampo.get(bean));
					sql.append("'");
					sql.append(", ");
				}
				else if (droidPK instanceof DroidPrimaryKey) {						
					campoWhere.append(" WHERE ");
					campoWhere.append(campo.getName());
					campoWhere.append(" = ");
					campoWhere.append(valorCampo.get(bean));
					campoWhere.append(";");					
				}
			}
			sql.append(campoWhere);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return sql;
	}
}
