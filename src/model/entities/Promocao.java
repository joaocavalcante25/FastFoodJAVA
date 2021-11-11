package model.entities;

public class Promocao {
	private int promocao_id;
	private String tipo;
	private String duracao;
	private String descricao;
	private double preco;
	
	private Produto produto;
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getPromocao_id() {
		return promocao_id;
	}
	
	public void setPromocao_id(int promocao_id) {
		this.promocao_id = promocao_id;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getDuracao() {
		return duracao;
	}
	
	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public double getPreco() {
		return preco;
	}
	
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + promocao_id;
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
		Promocao other = (Promocao) obj;
		if (promocao_id != other.promocao_id)
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "\nID da promocao: " + promocao_id + ", Tipo: " + tipo + ", Validade: " + duracao + ", Descricao: "
				+ descricao + ", Preco: " + preco + ", ID do produto: " + produto.getProduto_id();
	}

	public Promocao() {
		
	}
	
	
}
