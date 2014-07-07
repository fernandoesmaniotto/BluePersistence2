package br.com.bluepersistence.database;

public enum DataType{
	
	INTEGER("Armazena valor inteiro 0...N, contendo 8 bytes"), 
	BOOLEAN("S�o armazenados valor 0 para false e 1 para true"), 
	DATE("Valor data s�o armazenados em forma de n�mero"), 
	DECIMAL("Armazenados valores com a precis�o de 5 casas decimais"), 
	BLOB("S�o armazenados valores de imagens"), 
	DOUBLE("Armazena valores numericos"), 
	FLOAT("Armazena valores numericos"), 
	CHAR("Armazena valores de texto."), 
	VARCHAR("Armazena valores de texto."), 
	TEXT("Armazena valores texto usando a c�difica��o de banco de dados (UTF-8, UTF-16BE or UTF-16LE)."),
	REAL("Armazena valores n�mericos");
	
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




