package br.com.bluepersistence.dao;

import java.util.List;
import java.util.Map;

public abstract class AppendQuery {
	
	public StringBuilder anexarColunas(List<String> anexarColunas){
		StringBuilder anexoColuna = new StringBuilder("");
		boolean primeiroValor = true;
		for(String s: anexarColunas){
			if(primeiroValor)
				anexoColuna.append("");
			else
				anexoColuna.append(",");
			anexoColuna.append(s);
		}
		return anexoColuna;		
	}
	
	public StringBuilder anexarWhere(Map<String, Object> campoWhere){
		StringBuilder anexoWhere = new StringBuilder("");
		for(Map.Entry<String, Object> map : campoWhere.entrySet()){
			anexoWhere.append(" WHERE ");
			anexoWhere.append(map.getKey());
			anexoWhere.append(" = ");
			if(map.getValue() instanceof String){
				anexoWhere.append("'");
				anexoWhere.append(map.getValue());
				anexoWhere.append("'");
			}				
			else
				anexoWhere.append(map.getValue());
		}
		return anexoWhere;
	}
	
	public StringBuilder anexarAnd(Map<String, Object> campoAnd){
		StringBuilder anexoAnd = new StringBuilder("");		
		for(Map.Entry<String, Object> map : campoAnd.entrySet()){			
			anexoAnd.append(" AND ");
			anexoAnd.append(map.getKey());
			anexoAnd.append(" = ");
			if(map.getValue() instanceof String){
				anexoAnd.append("'");
				anexoAnd.append(map.getValue());
				anexoAnd.append("'");
			}else
				anexoAnd.append(map.getValue());			
		}
		return anexoAnd;
	}
	
	public StringBuilder anexarOrderBy(Map<String, String> campoOrdem){
		StringBuilder anexoOrdem = new StringBuilder("");		
		for(Map.Entry<String, String> map : campoOrdem.entrySet()){			
			anexoOrdem.append(" ORDER BY ");
			anexoOrdem.append(map.getKey());
			anexoOrdem.append(" ");
			anexoOrdem.append(map.getValue());			
		}
		return anexoOrdem;
	}
}
