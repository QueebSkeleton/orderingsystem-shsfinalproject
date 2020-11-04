package bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import util.OrderStatus;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private Client client;
	private Employee employee;
	private List<ItemOrder> itemOrderList;
	private List<OrderCoupon> orderCouponList;
	private ModeOfPayment modeOfPayment;
	private ModeOfDelivery modeOfDelivery;
	private Timestamp dateOrdered;
	private OrderStatus status;
	private String notes;
	private String address;
	private Country country;
	
	public Order() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<ItemOrder> getItemOrderList() {
		return itemOrderList;
	}

	public void setItemOrderList(List<ItemOrder> itemOrderList) {
		this.itemOrderList = itemOrderList;
	}
	
	public List<OrderCoupon> getOrderCouponList() {
		return orderCouponList;
	}

	public void setOrderCouponList(List<OrderCoupon> orderCouponList) {
		this.orderCouponList = orderCouponList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void addItemOrder(ItemOrder itemOrder) {
		itemOrderList.add(itemOrder);
		itemOrder.setOrder(this);
	}
	
	public void removeItemOrder(ItemOrder itemOrder) {
		itemOrderList.remove(itemOrder);
		itemOrder.setOrder(null);
	}

	public ModeOfPayment getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(ModeOfPayment modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}

	public ModeOfDelivery getModeOfDelivery() {
		return modeOfDelivery;
	}

	public void setModeOfDelivery(ModeOfDelivery modeOfDelivery) {
		this.modeOfDelivery = modeOfDelivery;
	}
	
	public Timestamp getDateOrdered() {
		return dateOrdered;
	}
	
	public void setDateOrdered(Timestamp dateOrdered) {
		this.dateOrdered = dateOrdered;
	}
	
	public OrderStatus getStatus() {
		return status;
	}
	
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
}
