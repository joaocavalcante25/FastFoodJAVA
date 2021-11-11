package model.entities;

public class Ing_Prod {
	private int ing_prod_id;
	private int qtdeIng;
	
	private Produto produto;
	private Ingrediente ingrediente;
	
	public int getIng_prod_id() {
		return ing_prod_id;
	}

	public void setIng_prod_id(int ing_prod_id) {
		this.ing_prod_id = ing_prod_id;
	}

	public int getQtdeIng() {
		return qtdeIng;
	}

	public void setQtdeIng(int qtdeIng) {
		this.qtdeIng = qtdeIng;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	@Override
	public String toString() {
		return "\nID do produto <-> ingrediente: " + ing_prod_id + ", ID do produto:" + produto.getProduto_id() + ", ID do ingrediente: " + ingrediente.getIngrediente_id() + ", Quantidade de ingrediente: " + qtdeIng;
	}

	public Ing_Prod() {
		
	}
	
}
