package bean;

import java.io.Serializable;

public class ItemOrder implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private Order _order;
	private Food food;
	private int quantity;
	
	public ItemOrder() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Order getOrder() {
		return _order;
	}

	public void setOrder(Order order) {
		this._order = order;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
