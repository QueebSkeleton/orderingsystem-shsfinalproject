package bean;

public class StocksBean {

	private Food food;
	private int cartStocks;
	private int pendingStocks;
	
	public Food getFood() {
		return food;
	}
	
	public int getCartStocks() {
		return cartStocks;
	}
	
	public int getPendingStocks() {
		return pendingStocks;
	}
	
	public void setFood(Food food) {
		this.food = food;
	}
	
	public void setCartStocks(int cartStocks) {
		this.cartStocks = cartStocks;
	}
	
	public void setPendingStocks(int pendingStocks) {
		this.pendingStocks = pendingStocks;
	}
	
}
