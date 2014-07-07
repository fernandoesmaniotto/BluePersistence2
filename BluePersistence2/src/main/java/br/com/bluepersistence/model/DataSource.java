package br.com.bluepersistence.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.bluepersistence.annotation.DroidColumn;
import br.com.bluepersistence.annotation.DroidForeignKey;
import br.com.bluepersistence.annotation.DroidPrimaryKey;
import br.com.bluepersistence.annotation.DroidTable;

public class DataSource {

	private static String bancoFramework;
	private static int VERSAO_BANCO = 1;
	private static Object beanFramework;
	private static DatabaseHelper androidDbHelper;
	private static SQLiteDatabase androidDb;	
	public static Context androidCtx;	
	private static Object cod;
	private static Object tipo;

	public DataSource(Context ctx, String banco) {		
		bancoFramework = banco;
		androidCtx = ctx;				
	}

	public DataSource() {

	}

	@SuppressWarnings("static-access")
	public SQLiteDatabase criaBanco(Object bean) throws SQLException {		
		this.beanFramework = bean;
		this.androidDbHelper = new DatabaseHelper(androidCtx);	
		this.androidDbHelper.getDatabase();
		this.VERSAO_BANCO = VERSAO_BANCO+1;
		return androidDb;		
	}

	public void salvar(Object bean) {		
		Class<? extends Object> classe = bean.getClass();
		DroidTable droidTable = classe.getAnnotation(DroidTable.class);
		String sql = "INSERT INTO " + droidTable.tabela() + "(";
		try {
			for (Field campo : classe.getDeclaredFields()) {
				campo.getName();
				DroidColumn droiColumn = campo.getAnnotation(DroidColumn.class);
				DroidForeignKey droidFK = campo
						.getAnnotation(DroidForeignKey.class);
				if (droiColumn instanceof DroidColumn) {
					sql = sql + "," + campo.getName();
				}
				if (droidFK instanceof DroidForeignKey) {
					sql = sql + "," + campo.getName();
				}
			}
			sql = sql + ") VALUES (";
			for (Field campo : classe.getDeclaredFields()) {
				Field campod = classe.getDeclaredField(campo.getName());
				campod.setAccessible(true);
				DroidColumn droiColumn = campo.getAnnotation(DroidColumn.class);
				DroidForeignKey droidFK = campo
						.getAnnotation(DroidForeignKey.class);
				if (droiColumn instanceof DroidColumn) {
					sql = sql + "," + "'" + campod.get(bean) + "'";
				}
				if (droidFK instanceof DroidForeignKey) {
					sql = sql + "," + "'" + campod.get(bean) + "'";
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		sql = sql.replace("(,", "(");
		androidDb.execSQL(sql + ");");

	}

	public void atualizar(Object bean) {		
		Class<? extends Object> classe = bean.getClass();
		DroidTable droidTable = classe.getAnnotation(DroidTable.class);
		String sqlUpdate = "UPDATE " + droidTable.tabela() + " SET ";
		try {
			for (Field campo : classe.getDeclaredFields()) {
				campo.getName();
				Field campod = classe.getDeclaredField(campo.getName());
				DroidColumn droiColumn = campo.getAnnotation(DroidColumn.class);
				DroidPrimaryKey droidPK = campo
						.getAnnotation(DroidPrimaryKey.class);
				DroidForeignKey droidFK = campo
						.getAnnotation(DroidForeignKey.class);
				campod.setAccessible(true);
				if (droiColumn instanceof DroidColumn) {
					if (INTEGER.INTEGER.name().equals(droiColumn.tipo()))
						sqlUpdate = sqlUpdate + campo.getName() + " = "
								+ campod.get(bean) + ", ";
					if (NUMERIC.DATE.name().equals(droiColumn.tipo()))
						sqlUpdate = sqlUpdate + campo.getName() + " = "
								+ campod.get(bean) + ", ";
					if (NUMERIC.BOOLEAN.name().equals(droiColumn.tipo()))
						sqlUpdate = sqlUpdate + campo.getName() + " = "
								+ campod.get(bean) + ", ";
					if (NONE.BLOB.name().equals(droiColumn.tipo()))
						sqlUpdate = sqlUpdate + campo.getName() + " = "
								+ campod.get(bean) + ", ";
					if (REAL.DOUBLE.name().equals(droiColumn.tipo()))
						sqlUpdate = sqlUpdate + campo.getName() + " = "
								+ campod.get(bean) + ", ";
					if (REAL.FLOAT.name().equals(droiColumn.tipo()))
						sqlUpdate = sqlUpdate + campo.getName() + " = "
								+ campod.get(bean) + ", ";
					if (NUMERIC.DECIMAL.name().equals(droiColumn.tipo()))
						sqlUpdate = sqlUpdate + campo.getName() + " = "
								+ campod.get(bean) + ", ";
					if (TEXT.CHAR.name().equals(droiColumn.tipo()))
						sqlUpdate = sqlUpdate + campo.getName() + " = " + "'"
								+ campod.get(bean) + "'" + ", ";
					if (TEXT.VARCHAR.name().equals(droiColumn.tipo()))
						sqlUpdate = sqlUpdate + campo.getName() + " = " + "'"
								+ campod.get(bean) + "'" + ", ";
					if (TEXT.TEXT.name().equals(droiColumn.tipo()))
						sqlUpdate = sqlUpdate + campo.getName() + " = " + "'"
								+ campod.get(bean) + "'" + ", ";
				}
				if (droidFK instanceof DroidForeignKey) {
					sqlUpdate = sqlUpdate + campo.getName() + " = " + "'"
							+ campod.get(bean) + "'" + ", ";
				}

				if (droidPK instanceof DroidPrimaryKey) {
					cod = campod.get(bean);
					tipo = campo.getName();
				}
			}
			sqlUpdate = sqlUpdate + "WHERE " + tipo + "=" + cod + "";
			sqlUpdate = sqlUpdate.replace(", WHERE", "\nWHERE ");
			System.out.println("SQL montado: "+sqlUpdate);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		androidDb.execSQL(sqlUpdate + ";");
	}

	public void excluir(Object bean) {		
		Class<? extends Object> classe = bean.getClass();
		DroidTable droidTable = classe.getAnnotation(DroidTable.class);
		String sqlDelete = "DELETE FROM " + droidTable.tabela() + " ";
		try {
			for (Field campo : classe.getDeclaredFields()) {
				campo.getName();
				Field campod = classe.getDeclaredField(campo.getName());
				campod.setAccessible(true);
				DroidPrimaryKey droidPK = campo
						.getAnnotation(DroidPrimaryKey.class);
				if (droidPK instanceof DroidPrimaryKey) {
					cod = campod.get(bean);
					tipo = campo.getName();
				}
			}
			sqlDelete = sqlDelete + "WHERE " + tipo + "=" + cod + "";
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		androidDb.execSQL(sqlDelete + ";");
	}
	
	
	public Object consultar(Object bean) {
		String campoWhere="";
		Object codigo = null;
		Class<? extends Object> classe = bean.getClass();
		DroidTable droidTable = classe.getAnnotation(DroidTable.class);		
		for (Field campo : classe.getDeclaredFields()) {
			campo.getName();
			Field campod = null;
			try {
				campod = classe.getDeclaredField(campo.getName());
				campod.setAccessible(true);
			} catch (SecurityException e) {				
				e.printStackTrace();
			} catch (NoSuchFieldException e) {				
				e.printStackTrace();
			}			
			DroidPrimaryKey droidPK = campo.getAnnotation(DroidPrimaryKey.class);
			if (droidPK instanceof DroidPrimaryKey) {
				try {
					codigo = campod.get(bean);
					campoWhere = campo.getName();
				} catch (IllegalArgumentException e) {					
					e.printStackTrace();
				} catch (IllegalAccessException e) {					
					e.printStackTrace();
				}				
			}
		}
		if (codigo == null) 
			codigo = 0;		
		Cursor cursor = androidDb.rawQuery(
				"SELECT * FROM " + droidTable.tabela()+" as tabela where tabela."+campoWhere+" = "+codigo, null);
		if (cursor.getCount() > 0) {
			for (Field campo : classe.getDeclaredFields()) {
				campo.getName();
				campo.setAccessible(true);				
				try {
				cursor.moveToFirst();
				DroidColumn droidColumn = campo.getAnnotation(DroidColumn.class);
				if (droidColumn instanceof DroidColumn) {
					if (INTEGER.INTEGER.name().equals(droidColumn.tipo())) {
						if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
							campo.set(bean, cursor.getInt(cursor.getColumnIndex(campo.getName())));
					}
					if (NUMERIC.DATE.name().equals(droidColumn.tipo())) {
						if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
							campo.set(bean, cursor.getString(cursor.getColumnIndex(campo.getName())));
					}
					if (NUMERIC.BOOLEAN.name().equals(droidColumn.tipo())) {
						if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName())))) {
							if (cursor.getInt(cursor.getColumnIndex(campo.getName())) == 0)
								campo.set(bean, false);
							else
								campo.set(bean, true);
						}						
					}
					if (NONE.BLOB.name().equals(droidColumn.tipo())) {
						if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
							campo.set(bean, cursor.getBlob(cursor.getColumnIndex(campo.getName())));
					}
					if (REAL.DOUBLE.name().equals(droidColumn.tipo())) {
						if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
							campo.set(bean, cursor.getDouble(cursor.getColumnIndex(campo.getName())));
					}
					if (REAL.FLOAT.name().equals(droidColumn.tipo())) {
						if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
							campo.set(bean, cursor.getFloat(cursor.getColumnIndex(campo.getName())));
					}
					if (TEXT.CHAR.name().equals(droidColumn.tipo())) {
						if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
							campo.set(bean, cursor.getString(cursor.getColumnIndex(campo.getName())));
					}
					if (TEXT.VARCHAR.name().equals(droidColumn.tipo())) {
						if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
							campo.set(bean, cursor.getString(cursor.getColumnIndex(campo.getName())));
					}
					if (TEXT.TEXT.name().equals(droidColumn.tipo())) {
						if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
							campo.set(bean, cursor.getString(cursor.getColumnIndex(campo.getName())));
					}
				}									
				} catch (SecurityException e) {			
					e.printStackTrace();
				} catch (IllegalArgumentException e) {				
					e.printStackTrace();
				} catch (IllegalAccessException e) {				
					e.printStackTrace();
				}
			}		
		}		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}		
		return bean;
	}
	
	public List<Object> listar(Object bean) {	
		Object object = bean;
		List<Object> listaBean = new ArrayList<Object>();
		Class<? extends Object> classe = bean.getClass();
		DroidTable droidTable = classe.getAnnotation(DroidTable.class);
		Cursor cursor = androidDb.rawQuery("SELECT * FROM " + droidTable.tabela(), null);
		if (cursor.getCount() > 0) {			
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						try {
							object = bean.getClass().newInstance();
						} catch (IllegalAccessException e1) {							
							e1.printStackTrace();
						} catch (InstantiationException e1) {							
							e1.printStackTrace();
						}
						for (Field campo : classe.getDeclaredFields()) {							
							campo.getName();
							campo.setAccessible(true);				
							try {							
								DroidColumn droidColumn = campo.getAnnotation(DroidColumn.class);
								if (droidColumn instanceof DroidColumn) {
									if (INTEGER.INTEGER.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getInt(cursor.getColumnIndex(campo.getName())));
									}
									if (NUMERIC.DATE.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getString(cursor.getColumnIndex(campo.getName())));
									}
									if (NUMERIC.BOOLEAN.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName())))) {
											if (cursor.getInt(cursor.getColumnIndex(campo.getName())) == 0)
												campo.set(object, false);
											else
												campo.set(object, true);
										}						
									}
									if (NONE.BLOB.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getBlob(cursor.getColumnIndex(campo.getName())));
									}
									if (REAL.DOUBLE.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getDouble(cursor.getColumnIndex(campo.getName())));
									}
									if (REAL.FLOAT.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getFloat(cursor.getColumnIndex(campo.getName())));
									}
									if (NUMERIC.DECIMAL.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getFloat(cursor.getColumnIndex(campo.getName())));
									}								
									if (TEXT.CHAR.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getString(cursor.getColumnIndex(campo.getName())));
									}
									if (TEXT.VARCHAR.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getString(cursor.getColumnIndex(campo.getName())));
									}
									if (TEXT.TEXT.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getString(cursor.getColumnIndex(campo.getName())));
									}
							}else {
								campo.set(object, cursor.getInt(cursor.getColumnIndex(campo.getName())));
							}
							} catch (SecurityException e) {			
								e.printStackTrace();
							} catch (IllegalArgumentException e) {				
								e.printStackTrace();
							} catch (IllegalAccessException e) {				
								e.printStackTrace();
							}							
						}	
						listaBean.add(object);
					} while (cursor.moveToNext());
				}
			}			
		}		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}		
		return listaBean;
	}
	
	public List<Object> listarCondicaoAnd(Object bean, String where, String and) {	
		Object object = bean;
		String campoWhere="";
		String campoAnd="";
		Object valorWhere = null;
		Object valorAnd = null;
		List<Object> listaBean = new ArrayList<Object>();
		Class<? extends Object> classe = bean.getClass();
		DroidTable droidTable = classe.getAnnotation(DroidTable.class);
		for (Field campo : classe.getDeclaredFields()) {
			campo.getName();
			Field campod = null;
			try {
				campod = classe.getDeclaredField(campo.getName());
				campod.setAccessible(true);
			} catch (SecurityException e) {				
				e.printStackTrace();
			} catch (NoSuchFieldException e) {				
				e.printStackTrace();
			}			
			DroidColumn droidColumn = campo.getAnnotation(DroidColumn.class);
			if (droidColumn instanceof DroidColumn) {
				try {
					if (where.equals(campo.getName())) {						
						campoWhere = campo.getName();
						if (TEXT.CHAR.name().equals(droidColumn.tipo())) 
							valorWhere = "'"+campod.get(bean)+"'";						
						else if (TEXT.VARCHAR.name().equals(droidColumn.tipo())) 
							valorWhere = "'"+campod.get(bean)+"'";						
						else if (TEXT.TEXT.name().equals(droidColumn.tipo())) 
							valorWhere = "'"+campod.get(bean)+"'";
						else 
							valorWhere = campod.get(bean);							
					}
					if(and.equals(campo.getName())){
						campoAnd = campo.getName();
						if (TEXT.CHAR.name().equals(droidColumn.tipo())) 
							valorAnd = "'"+campod.get(bean)+"'";						
						else if (TEXT.VARCHAR.name().equals(droidColumn.tipo())) 
							valorAnd = "'"+campod.get(bean)+"'";						
						else if (TEXT.TEXT.name().equals(droidColumn.tipo())) 
							valorAnd = "'"+campod.get(bean)+"'";								
					}
				} catch (IllegalArgumentException e) {					
					e.printStackTrace();
				} catch (IllegalAccessException e) {					
					e.printStackTrace();
				}				
			}
		}
		
		Log.v("SQL montado", "SELECT * FROM " + droidTable.tabela()+ 
                " where "+campoWhere+" = "+ valorWhere +" and " +campoAnd+ " = " +valorAnd);
		
		Cursor cursor = androidDb.rawQuery("SELECT * FROM " + droidTable.tabela()+ 
				                           " where "+campoWhere+" = "+ valorWhere +" and " +campoAnd+ " = " +valorAnd,  null);
		
		
		if (cursor.getCount() > 0) {			
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						try {
							object = bean.getClass().newInstance();
						} catch (IllegalAccessException e1) {							
							e1.printStackTrace();
						} catch (InstantiationException e1) {							
							e1.printStackTrace();
						}
						for (Field campo : classe.getDeclaredFields()) {							
							campo.getName();
							campo.setAccessible(true);				
							try {							
								DroidColumn droidColumn = campo.getAnnotation(DroidColumn.class);
								if (droidColumn instanceof DroidColumn) {
									if (INTEGER.INTEGER.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getInt(cursor.getColumnIndex(campo.getName())));
									}
									if (NUMERIC.DATE.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getString(cursor.getColumnIndex(campo.getName())));
									}
									if (NUMERIC.BOOLEAN.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName())))) {
											if (cursor.getInt(cursor.getColumnIndex(campo.getName())) == 0)
												campo.set(object, false);
											else
												campo.set(object, true);
										}						
									}
									if (NONE.BLOB.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getBlob(cursor.getColumnIndex(campo.getName())));
									}
									if (REAL.DOUBLE.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getDouble(cursor.getColumnIndex(campo.getName())));
									}
									if (REAL.FLOAT.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getFloat(cursor.getColumnIndex(campo.getName())));
									}
									if (NUMERIC.DECIMAL.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getFloat(cursor.getColumnIndex(campo.getName())));
									}								
									if (TEXT.CHAR.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getString(cursor.getColumnIndex(campo.getName())));
									}
									if (TEXT.VARCHAR.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getString(cursor.getColumnIndex(campo.getName())));
									}
									if (TEXT.TEXT.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getString(cursor.getColumnIndex(campo.getName())));
									}
							}else {
								campo.set(object, cursor.getInt(cursor.getColumnIndex(campo.getName())));
							}
							} catch (SecurityException e) {			
								e.printStackTrace();
							} catch (IllegalArgumentException e) {				
								e.printStackTrace();
							} catch (IllegalAccessException e) {				
								e.printStackTrace();
							}							
						}	
						listaBean.add(object);
					} while (cursor.moveToNext());
				}
			}			
		}		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}		
		return listaBean;
	}
	
	
	public List<Object> listarCondicao(Object bean) {	
		Object object = bean;
		String campoWhere="";
		Object valor = null;
		List<Object> listaBean = new ArrayList<Object>();
		Class<? extends Object> classe = bean.getClass();
		DroidTable droidTable = classe.getAnnotation(DroidTable.class);
		for (Field campo : classe.getDeclaredFields()) {
			campo.getName();
			Field campod = null;
			try {
				campod = classe.getDeclaredField(campo.getName());
				campod.setAccessible(true);
			} catch (SecurityException e) {				
				e.printStackTrace();
			} catch (NoSuchFieldException e) {				
				e.printStackTrace();
			}			
			DroidColumn droidColumn = campo.getAnnotation(DroidColumn.class);
			if (droidColumn instanceof DroidColumn) {
				try {
					if (campod.get(bean) != null) {						
						campoWhere = campo.getName();
						if (TEXT.CHAR.name().equals(droidColumn.tipo())) 
							valor = "'"+campod.get(bean)+"'";						
						else if (TEXT.VARCHAR.name().equals(droidColumn.tipo())) 
							valor = "'"+campod.get(bean)+"'";						
						else if (TEXT.TEXT.name().equals(droidColumn.tipo())) 
							valor = "'"+campod.get(bean)+"'";
						else 
							valor = campod.get(bean);						
					}
				} catch (IllegalArgumentException e) {					
					e.printStackTrace();
				} catch (IllegalAccessException e) {					
					e.printStackTrace();
				}				
			}
		}
		Cursor cursor = androidDb.rawQuery("SELECT * FROM " + droidTable.tabela()+ " where "+campoWhere+" = "+valor, null);
		if (cursor.getCount() > 0) {			
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						try {
							object = bean.getClass().newInstance();
						} catch (IllegalAccessException e1) {							
							e1.printStackTrace();
						} catch (InstantiationException e1) {							
							e1.printStackTrace();
						}
						for (Field campo : classe.getDeclaredFields()) {							
							campo.getName();
							campo.setAccessible(true);				
							try {							
								DroidColumn droidColumn = campo.getAnnotation(DroidColumn.class);
								if (droidColumn instanceof DroidColumn) {
									if (INTEGER.INTEGER.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getInt(cursor.getColumnIndex(campo.getName())));
									}
									if (NUMERIC.DATE.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getString(cursor.getColumnIndex(campo.getName())));
									}
									if (NUMERIC.BOOLEAN.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName())))) {
											if (cursor.getInt(cursor.getColumnIndex(campo.getName())) == 0)
												campo.set(object, false);
											else
												campo.set(object, true);
										}						
									}
									if (NONE.BLOB.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getBlob(cursor.getColumnIndex(campo.getName())));
									}
									if (REAL.DOUBLE.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getDouble(cursor.getColumnIndex(campo.getName())));
									}
									if (REAL.FLOAT.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getFloat(cursor.getColumnIndex(campo.getName())));
									}
									if (NUMERIC.DECIMAL.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getFloat(cursor.getColumnIndex(campo.getName())));
									}								
									if (TEXT.CHAR.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getString(cursor.getColumnIndex(campo.getName())));
									}
									if (TEXT.VARCHAR.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getString(cursor.getColumnIndex(campo.getName())));
									}
									if (TEXT.TEXT.name().equals(droidColumn.tipo())) {
										if (campo.getName().equals(cursor.getColumnName(cursor.getColumnIndex(campo.getName()))))
											campo.set(object, cursor.getString(cursor.getColumnIndex(campo.getName())));
									}
							}else {
								campo.set(object, cursor.getInt(cursor.getColumnIndex(campo.getName())));
							}
							} catch (SecurityException e) {			
								e.printStackTrace();
							} catch (IllegalArgumentException e) {				
								e.printStackTrace();
							} catch (IllegalAccessException e) {				
								e.printStackTrace();
							}							
						}	
						listaBean.add(object);
					} while (cursor.moveToNext());
				}
			}			
		}		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}		
		return listaBean;
	}
	
	public Map<String,String> criptografarDados(Object bean) {				
		Class<? extends Object> classe = bean.getClass();	
		Map<String, String> listaBean = new HashMap<String, String>();
		for (Field campo : classe.getDeclaredFields()) {							
			campo.getName();
			campo.setAccessible(true);				
			try {
				DroidPrimaryKey droidPK = campo.getAnnotation(DroidPrimaryKey.class);
				DroidColumn droidColumn = campo.getAnnotation(DroidColumn.class);
				if ((droidColumn instanceof DroidColumn) && (droidColumn.criptografia()) || 
					(droidPK instanceof DroidPrimaryKey) && (droidPK.criptografia() == true)) {
					Field campod = classe.getDeclaredField(campo.getName());
					campod.setAccessible(true);
					listaBean.put(campo.getName(), (String)campod.get(bean));					
				}
			} catch (SecurityException e) {			
				e.printStackTrace();
			} catch (IllegalArgumentException e) {				
				e.printStackTrace();
			} catch (NoSuchFieldException e) {				
				e.printStackTrace();
			} catch (IllegalAccessException e) {				
				e.printStackTrace();
			}			
		}		
		return listaBean;
	}
	

	public List<String> listaSimples(Object bean, String campoPesquisa) {		
		Class<? extends Object> classe = bean.getClass();
		DroidTable droidTable = classe.getAnnotation(DroidTable.class);
		List<String> lista = new ArrayList<String>();
		Cursor cursor = androidDb.rawQuery(
				"SELECT tabela."+campoPesquisa+" FROM " + droidTable.tabela()+ " as tabela ", null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					lista.add(cursor.getString(cursor.getInt(0)));
				} while (cursor.moveToNext());
			}
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return lista;
	}

	public String apagaTabela(Object bean) {		
		Class<? extends Object> classe = bean.getClass();
		DroidTable droidTable = classe.getAnnotation(DroidTable.class);		
		androidDb.execSQL("DROP TABLE IF EXISTS " + droidTable.tabela());		
		return "Tabela [" + droidTable.tabela() + "] excluída com sucesso";
	}
	
	public String apagaDados(Object bean) {			
		Class<? extends Object> classe = bean.getClass();
		DroidTable droidTable = classe.getAnnotation(DroidTable.class);		
		androidDb.execSQL("DELETE FROM " + droidTable.tabela());		
		return "Dados da tabela [" + droidTable.tabela() + "] excluídos com sucesso";
	}

	public String alteraTabela(Object bean, String addCampo, String nomeTabela) {
		Log.e("LOG", "INICIO");
		Class<? extends Object> classe = bean.getClass();
		DroidTable droidTable = classe.getAnnotation(DroidTable.class);
		Log.e("LOG", "MEIO");
		if (addCampo != "") {
			Log.e("LOG", "DEPOIS DO IF");
			androidDb.execSQL("ALTER TABLE " + droidTable.tabela() + " ADD "
					+ addCampo + "");
			return "Tabela [" + droidTable.tabela() + "] alterada com sucesso";
		} else if (nomeTabela != "") {
			androidDb.execSQL("ALTER TABLE " + droidTable.tabela()
					+ " RENAME TO " + nomeTabela + "");
			return "Tabela [" + droidTable.tabela() + "] alterada com sucesso";
		}
		return "";

	}

	public void close() {
		androidDbHelper.close();		
	}

	public static class DatabaseHelper extends SQLiteOpenHelper {
		DataSource banco;
		DataSource ds = new DataSource();
		@Override
		public void onOpen(SQLiteDatabase db) {
			super.onOpen(db);
			if (!db.isReadOnly()) {
				db.execSQL("PRAGMA foreign_keys=ON;");
			}
		}
		
		DatabaseHelper(Context context) {				
			super(context, bancoFramework, null, VERSAO_BANCO);	
			Log.v("DATASOURCE", "Passou no DatabaseHelper");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {	
			Log.v("DATASOURCE", "Passou no onCreate");
			db.execSQL(ds.montaSQL(beanFramework));
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.v("DATASOURCE", "Passou no onUpgrade");
			db.execSQL(ds.montaSQL(beanFramework));
			onCreate(db);
		}
		
		public SQLiteDatabase getDatabase() {
			return this.getWritableDatabase();
		}
	}

	public SQLiteDatabase abrirConexao(String caminho, String banco) {			
		return androidDb = SQLiteDatabase.openDatabase(caminho+banco, null, SQLiteDatabase.OPEN_READWRITE);		
	}

	public String montaSQL(Object bean) {	
		Log.v("DATASOURCE", "Passou no montaSQL");
		String[] dadosCampo = new String[10];
		String[] dadosTabela = new String[10];
		String[] dadosReferencia = new String[10];
		String notNull = "";
		int i = 0, a = 0;
		Class<? extends Object> classe = bean.getClass();
		DroidTable droiTable = classe.getAnnotation(DroidTable.class);

		String sql = "CREATE TABLE IF NOT EXISTS " + droiTable.tabela() + "(";
		for (Field campo : classe.getDeclaredFields()) {
			try {
				DroidPrimaryKey droidPK = campo
						.getAnnotation(DroidPrimaryKey.class);
				DroidColumn droiColumn = campo.getAnnotation(DroidColumn.class);
				DroidForeignKey droidFK = campo
						.getAnnotation(DroidForeignKey.class);

				if (droidPK instanceof DroidPrimaryKey) {
					notNull = (droidPK.obrigatorio() == true)?" NOT NULL ":" "; 
					sql = sql + "," + campo.getName() + " INTEGER PRIMARY KEY "+notNull+"";
					notNull="";					
				}
				
				//Função adicionada a qual acrescenta a funcionalidade de se utilizar
				//um campo String como chave primária da tabela.
				if (droiColumn instanceof DroidColumn) {
					notNull = (droiColumn.obrigatorio() == true)?" NOT NULL ":" "; 
					if ((TEXT.VARCHAR.name().equals(droiColumn.tipo()) && (droiColumn.chave()))) {
						sql = sql + "," + campo.getName() + " "+droiColumn.tipo()+" " +
								" ("+droiColumn.tamanho()+") PRIMARY KEY "+notNull+" ";
					notNull="";					
					}
				}

				if (droiColumn instanceof DroidColumn) {
					notNull = (droiColumn.obrigatorio() == true)?" NOT NULL ":" ";
					if (INTEGER.INTEGER.name().equals(droiColumn.tipo()))
						sql = sql + "," + campo.getName() + " "
								+ droiColumn.tipo()+notNull;
					if (NUMERIC.BOOLEAN.name().equals(droiColumn.tipo()))
						sql = sql + "," + campo.getName() + " "
								+ droiColumn.tipo()+notNull;
					if (NUMERIC.DATE.name().equals(droiColumn.tipo()))
						sql = sql + "," + campo.getName() + " "
								+ droiColumn.tipo()+notNull;
					if (NUMERIC.DECIMAL.name().equals(droiColumn.tipo()))
						sql = sql + "," + campo.getName() + " "
								+ droiColumn.tipo()+droiColumn.tamanhoPrecisao()+notNull;
					if (NONE.BLOB.name().equals(droiColumn.tipo()))
						sql = sql + "," + campo.getName() + " "
								+ droiColumn.tipo()+notNull;
					if (REAL.DOUBLE.name().equals(droiColumn.tipo()))
						sql = sql + "," + campo.getName() + " "
								+ droiColumn.tipo()+notNull;
					if (REAL.FLOAT.name().equals(droiColumn.tipo()))
						sql = sql + "," + campo.getName() + " "
								+ droiColumn.tipo()+notNull;
					if (TEXT.CHAR.name().equals(droiColumn.tipo()))
						sql = sql + "," + campo.getName() + " "
								+ droiColumn.tipo() + " ("
								+ droiColumn.tamanho() + ") "+notNull;
					if ((TEXT.VARCHAR.name().equals(droiColumn.tipo()) && 
						(!droiColumn.chave())))
						sql = sql + "," + campo.getName() + " "
								+ droiColumn.tipo() + " ("
								+ droiColumn.tamanho() + ") "+notNull;
					if (TEXT.TEXT.name().equals(droiColumn.tipo()))
						sql = sql + "," + campo.getName() + " "
								+ droiColumn.tipo() + " ("
								+ droiColumn.tamanho() + ") "+notNull;
					notNull="";
				}
				if (droidFK instanceof DroidForeignKey) {
					a = a + 1;
					while (i < a) {
						dadosTabela[i] = droidFK.tabela();
						dadosReferencia[i] = droidFK.referencia();
						dadosCampo[i] = campo.getName();
						i++;
					}

				}

			} catch (SecurityException e) {
				Log.e("SQL montada", sql);
				e.printStackTrace();
			}
		}
		if (dadosCampo[0] == null) {
			sql = sql.replace("(,", "(");			
			return sql + ");";
		} else {
			for (int dadosCamp = 0; dadosCamp < i; dadosCamp++) {
				sql = sql + "," + dadosCampo[dadosCamp] + " INTEGER";
			}
			for (int dadosTabRef = 0; dadosTabRef < i; dadosTabRef++) {
				sql = sql + " , FOREIGN KEY (" + dadosTabela[dadosTabRef]
						+ ") REFERENCES " + dadosTabela[dadosTabRef] + "("
						+ dadosReferencia[dadosTabRef] + ")";
				sql = sql.replace("(,", "(");
			}
			System.out.println("SQL montado "+sql +");");
			return sql + ");";
		}
		
		
	}

}
