package br.com.bluepersistence.cursor.android.builder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import br.com.bluepersistence.annotation.DroidColumn;
import br.com.bluepersistence.database.DataType;

public class BluePersistenceCursor {
	
	public List<?> listar(Cursor cursor, Object bean){
		Object novoBean = bean;
		List<Object> listaBean = new ArrayList<Object>();
		Class<? extends Object> classe = bean.getClass();		
		if (cursor.getCount() > 0) {			
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						try {
							novoBean = bean.getClass().newInstance();
						} catch (IllegalAccessException e1) {							
							e1.printStackTrace();
						} catch (InstantiationException e1) {							
							e1.printStackTrace();
						}
						for (Field valorCampo : classe.getDeclaredFields()) {							
							valorCampo.setAccessible(true);				
							try {							
								DroidColumn droidColumn = valorCampo.getAnnotation(DroidColumn.class);
								if (droidColumn instanceof DroidColumn) {
									if (DataType.INTEGER.toString().equals(droidColumn.tipo())) {
										if (valorCampo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(valorCampo.getName()))))
											valorCampo.set(novoBean, cursor.getInt(cursor.getColumnIndex(valorCampo.getName())));
									}
									if (DataType.DATE.toString().equals(droidColumn.tipo())) {
										if (valorCampo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(valorCampo.getName()))))
											valorCampo.set(novoBean, cursor.getString(cursor.getColumnIndex(valorCampo.getName())));
									}
									if (DataType.BOOLEAN.toString().equals(droidColumn.tipo())) {
										if (valorCampo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(valorCampo.getName())))) {
											if (cursor.getInt(cursor.getColumnIndex(valorCampo.getName())) == 0)
												valorCampo.set(novoBean, false);
											else
												valorCampo.set(novoBean, true);
										}						
									}
									if (DataType.BLOB.toString().equals(droidColumn.tipo())) {
										if (valorCampo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(valorCampo.getName()))))
											valorCampo.set(novoBean, cursor.getBlob(cursor.getColumnIndex(valorCampo.getName())));
									}
									if (DataType.REAL.toString().equals(droidColumn.tipo())) {
										if (valorCampo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(valorCampo.getName()))))
											valorCampo.set(novoBean, cursor.getDouble(cursor.getColumnIndex(valorCampo.getName())));
									}
									if (DataType.FLOAT.toString().equals(droidColumn.tipo())) {
										if (valorCampo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(valorCampo.getName()))))
											valorCampo.set(novoBean, cursor.getFloat(cursor.getColumnIndex(valorCampo.getName())));
									}
									if (DataType.DECIMAL.toString().equals(droidColumn.tipo())) {
										if (valorCampo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(valorCampo.getName()))))
											valorCampo.set(novoBean, cursor.getFloat(cursor.getColumnIndex(valorCampo.getName())));
									}								
									if (DataType.CHAR.toString().equals(droidColumn.tipo())) {
										if (valorCampo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(valorCampo.getName()))))
											valorCampo.set(novoBean, cursor.getString(cursor.getColumnIndex(valorCampo.getName())));
									}
									if (DataType.VARCHAR.toString().equals(droidColumn.tipo())) {
										if (valorCampo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(valorCampo.getName()))))
											valorCampo.set(novoBean, cursor.getString(cursor.getColumnIndex(valorCampo.getName())));
									}
									if (DataType.TEXT.toString().equals(droidColumn.tipo())) {
										if (valorCampo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(valorCampo.getName()))))
											valorCampo.set(novoBean, cursor.getString(cursor.getColumnIndex(valorCampo.getName())));
									}
							}else 
								valorCampo.set(novoBean, cursor.getInt(cursor.getColumnIndex(valorCampo.getName())));							
							} catch (SecurityException e) {			
								e.printStackTrace();
							} catch (IllegalArgumentException e) {				
								e.printStackTrace();
							} catch (IllegalAccessException e) {				
								e.printStackTrace();
							}							
						}	
						listaBean.add(novoBean);
					} while (cursor.moveToNext());
				}
			}			
		}		
		if (cursor != null && !cursor.isClosed()) 
			cursor.close();				
		return listaBean;
	}
}
