package bean;

import java.io.Serializable;

public class OrderCoupon implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Order order;
	private Coupon coupon;
	
	public OrderCoupon() {
		
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	
}
