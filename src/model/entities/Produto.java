package model.entities;

public class Produto {
	
	private int produto_id;
	private String nome;
	private double valor;
	private String descricao;
	private String dataProducao;
	private int qtdEstoque;
	
	private Categoria categoria;
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getProduto_id() {
		return produto_id;
	}
	
	public void setProduto_id(int produto_id) {
		this.produto_id = produto_id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public double getValor() {
		return valor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDataProducao() {
		return dataProducao;
	}
	
	public void setDataProducao(String dataProducao) {
		this.dataProducao = dataProducao;
	}
	
	public int getQtdEstoque() {
		return qtdEstoque;
	}
	
	public void setQtdEstoque(int qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + produto_id;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (produto_id != other.produto_id)
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "\nID do produto: " + produto_id + ", Nome: " + nome + ", Preco: " + valor + ", Descricao: " + descricao + ", Data de fabricacao: " + dataProducao + ", Quantidade em estoque: " + qtdEstoque + ", Codigo da categoria: " + categoria.getCategoria_id();
	}

	public Produto() {
		
	}
	
}
