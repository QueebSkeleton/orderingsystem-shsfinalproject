package model;

import java.util.List;

import bean.Food;
import bean.ItemOrder;
import bean.Order;
import bean.StocksBean;
import dao.FoodDAO;

public class StocksHandler {
	
	public static List<StocksBean> foodStocksList;
	
	public static void loadStocksList() {
		FoodDAO foodDao = new FoodDAO();
		foodStocksList = foodDao.getStocksBeanList();
	}
	
	public static void reloadStocksList() {
		List<StocksBean> tempList = foodStocksList;
		
		FoodDAO foodDao = new FoodDAO();
		foodStocksList = foodDao.getStocksBeanList();
		
		for(int i = 0; i < foodStocksList.size(); i++) {
			if(i >= tempList.size())
				break;
			
			StocksBean foodStocks = foodStocksList.get(i);
			StocksBean pastStocks = tempList.get(i);
			
			if(foodStocks.getFood().getId() == pastStocks.getFood().getId()) {
				foodStocks.getFood().setUnitsInStock(pastStocks.getFood().getUnitsInStock());
				foodStocks.setCartStocks(pastStocks.getCartStocks());
				foodStocks.setPendingStocks(pastStocks.getPendingStocks());
			}
		}
	}
	
	public static void addOrUpdateFromCart(Food food, int quantity) {
		for(StocksBean stocksBean : foodStocksList) {
			if(stocksBean.getFood().getId() == food.getId()) {
				int unitsInStock = stocksBean.getFood().getUnitsInStock();
				stocksBean.getFood().setUnitsInStock(unitsInStock + quantity);
				
				int cartStocks = stocksBean.getCartStocks();
				stocksBean.setCartStocks(cartStocks - quantity);
				
				break;
			}
		}
	}
	
	public static void placeOrder(Order order) {
		for(StocksBean stocksBean : foodStocksList) {
			for(ItemOrder itemOrder : order.getItemOrderList()) {
				if(stocksBean.getFood().getId() == itemOrder.getFood().getId()) {
					int cartStocks = stocksBean.getCartStocks();
					stocksBean.setCartStocks(cartStocks - itemOrder.getQuantity());
					
					int pendingStocks = stocksBean.getPendingStocks();
					stocksBean.setPendingStocks(pendingStocks + itemOrder.getQuantity());
					
					FoodDAO foodDao = new FoodDAO();
					foodDao.update(stocksBean.getFood());
					
					break;
				}
			}
		}
	}
	
	public static void removeItemOrdersFromCart(List<ItemOrder> itemOrderList) {
		for(StocksBean stocksBean : foodStocksList) {
			for(ItemOrder itemOrder : itemOrderList) {
				if(stocksBean.getFood().getId() == itemOrder.getFood().getId()) {
					Food food = stocksBean.getFood();
					
					int unitsInStock = food.getUnitsInStock();
					food.setUnitsInStock(unitsInStock + itemOrder.getQuantity());
					
					int cartStocks = stocksBean.getCartStocks();
					stocksBean.setCartStocks(cartStocks - itemOrder.getQuantity());
					
					FoodDAO foodDao = new FoodDAO();
					foodDao.update(stocksBean.getFood());
					
					break;
				}
			}
		}
	}
	
	public static void denyOrCancelOrder(Order order) {
		for(StocksBean stocksBean : foodStocksList) {
			for(ItemOrder itemOrder : order.getItemOrderList()) {
				if(stocksBean.getFood().getId() == itemOrder.getFood().getId()) {
					Food food = stocksBean.getFood();
					
					int unitsInStock = food.getUnitsInStock();
					food.setUnitsInStock(unitsInStock + itemOrder.getQuantity());
					
					int pendingStocks = stocksBean.getPendingStocks();
					stocksBean.setPendingStocks(pendingStocks - itemOrder.getQuantity());
					
					FoodDAO foodDao = new FoodDAO();
					foodDao.update(stocksBean.getFood());
					
					break;
				}
			}
		}
	}

}
