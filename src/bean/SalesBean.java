package bean;

import java.io.Serializable;
import java.util.Date;

public class SalesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date date;
	private double sales;
	
	public SalesBean() {}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public double getSales() {
		return sales;
	}
	
	public void setSales(double sales) {
		this.sales = sales;
	}
	
}
