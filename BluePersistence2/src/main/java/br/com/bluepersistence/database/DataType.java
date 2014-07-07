package br.com.bluepersistence.database;

public enum DataType{
	
	INTEGER("Armazena valor inteiro 0...N, contendo 8 bytes"), 
	BOOLEAN("São armazenados valor 0 para false e 1 para true"), 
	DATE("Valor data são armazenados em forma de número"), 
	DECIMAL("Armazenados valores com a precisão de 5 casas decimais"), 
	BLOB("São armazenados valores de imagens"), 
	DOUBLE("Armazena valores numericos"), 
	FLOAT("Armazena valores numericos"), 
	CHAR("Armazena valores de texto."), 
	VARCHAR("Armazena valores de texto."), 
	TEXT("Armazena valores texto usando a códificação de banco de dados (UTF-8, UTF-16BE or UTF-16LE)."),
	REAL("Armazena valores númericos");
	
	private String descricao;
	
	DataType(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}




