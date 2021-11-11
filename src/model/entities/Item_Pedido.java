package model.entities;

public class Item_Pedido {
	private int item_pedido_id;
	private int qtdeProdutos;
	private int qtdeIngredientes;
	private String status;
	
	private Produto produto;
	private Ingrediente ingrediente;
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public int getItem_pedido_id() {
		return item_pedido_id;
	}

	public void setItem_pedido_id(int item_pedido_id) {
		this.item_pedido_id = item_pedido_id;
	}

	public int getQtdeProdutos() {
		return qtdeProdutos;
	}

	public void setQtdeProdutos(int qtdeProdutos) {
		this.qtdeProdutos = qtdeProdutos;
	}

	public int getQtdeIngredientes() {
		return qtdeIngredientes;
	}

	public void setQtdeIngredientes(int qtdeIngredientes) {
		this.qtdeIngredientes = qtdeIngredientes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + item_pedido_id;
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
		Item_Pedido other = (Item_Pedido) obj;
		if (item_pedido_id != other.item_pedido_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		if(qtdeIngredientes==0) {
			return "ID do item_pedido: " + item_pedido_id + ", ID do produto:" + produto.getProduto_id() + ", Quantidade de produtos: " + qtdeProdutos;
		}else {
			return "ID do item_pedido: " + item_pedido_id + ", ID do ingrediente:" + ingrediente.getIngrediente_id() + ", Quantidade de ingredientes: " + qtdeIngredientes;
		}
	}

	public Item_Pedido() {
	}
	
	
}
