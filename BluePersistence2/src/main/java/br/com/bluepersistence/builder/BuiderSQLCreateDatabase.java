package br.com.bluepersistence.builder;

import java.lang.reflect.Field;

import br.com.bluepersistence.annotation.DroidColumn;
import br.com.bluepersistence.annotation.DroidPrimaryKey;
import br.com.bluepersistence.annotation.DroidTable;
import br.com.bluepersistence.database.DataType;


public class BuiderSQLCreateDatabase implements BuiderSQL {
	
	public StringBuilder sql(Object bean) {
		StringBuilder sql = new StringBuilder();	
		String notNull = "";		
		Class<? extends Object> classe = bean.getClass();
		DroidTable droidTable = classe.getAnnotation(DroidTable.class);
		sql.append("CREATE TABLE IF NOT EXISTS ");
		sql.append(droidTable.tabela());
		sql.append(" (");
		for (Field campo : classe.getDeclaredFields()) {			
			DroidPrimaryKey droidPK = campo.getAnnotation(DroidPrimaryKey.class);
			DroidColumn droidColumn = campo.getAnnotation(DroidColumn.class);				
			if (droidPK instanceof DroidPrimaryKey) {
				notNull = (droidPK.obrigatorio() == true)?" NOT NULL ":" "; 
				sql.append(campo.getName());
				sql.append(" INTEGER PRIMARY KEY AUTOINCREMENT");
				sql.append(notNull);
				notNull = "";					
			}				
			if (droidColumn instanceof DroidColumn) {
				notNull = (droidColumn.obrigatorio() == true)?" NOT NULL ":" ";
				if (DataType.INTEGER.name().equals(droidColumn.tipo())){
					sql.append(",");
					sql.append(campo.getName());
					sql.append(" ");
					sql.append(droidColumn.tipo());
					sql.append(notNull);
				}
				else if(DataType.BOOLEAN.name().equals(droidColumn.tipo())){
					sql.append(",");
					sql.append(campo.getName());
					sql.append(" ");
					sql.append(droidColumn.tipo());
					sql.append(notNull);
				}						
				else if(DataType.DATE.name().equals(droidColumn.tipo())){
					sql.append(",");
					sql.append(campo.getName());
					sql.append(" ");
					sql.append(droidColumn.tipo());
					sql.append(notNull);
				}					
				else if(DataType.DECIMAL.name().equals(droidColumn.tipo())){
					sql.append(",");
					sql.append(campo.getName());
					sql.append(" ");
					sql.append(droidColumn.tipo());
					sql.append(droidColumn.tamanhoPrecisao());
					sql.append(notNull);
				}						
				else if(DataType.BLOB.name().equals(droidColumn.tipo())){
					sql.append(",");
					sql.append(campo.getName());
					sql.append(" ");
					sql.append(droidColumn.tipo());						
					sql.append(notNull);
				}						
				else if(DataType.DOUBLE.name().equals(droidColumn.tipo())){
					sql.append(",");
					sql.append(campo.getName());
					sql.append(" ");
					sql.append(droidColumn.tipo());						
					sql.append(notNull);
				}						
				else if(DataType.FLOAT.name().equals(droidColumn.tipo())){
					sql.append(",");
					sql.append(campo.getName());
					sql.append(" ");
					sql.append(droidColumn.tipo());						
					sql.append(notNull);
				}						
				else if(DataType.CHAR.name().equals(droidColumn.tipo())){
					sql.append(",");
					sql.append(campo.getName());
					sql.append(" ");
					sql.append(droidColumn.tipo());		
					sql.append("(");
					sql.append(droidColumn.tamanho());
					sql.append(")");										
					sql.append(notNull);
				}						
				else if((DataType.VARCHAR.name().equals(droidColumn.tipo()) && (!droidColumn.chave()))){
					sql.append(", ");
					sql.append(campo.getName());
					sql.append(" ");
					sql.append(droidColumn.tipo());		
					sql.append("(");
					sql.append(droidColumn.tamanho());
					sql.append(")");										
					sql.append(notNull);
				}						
				else if (DataType.TEXT.name().equals(droidColumn.tipo())){
					sql.append(",");
					sql.append(campo.getName());
					sql.append(" ");
					sql.append(droidColumn.tipo());		
					sql.append("(");
					sql.append(droidColumn.tamanho());
					sql.append(")");										
					sql.append(notNull);
				}						
				notNull = "";
			}			
		}	
		sql.append(");");
		return sql;		
	}
}
