package bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class Coupon implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private Timestamp dateCreated;
	private Timestamp validUntil;
	private String code;
	private double discountAmount;
	
	public Coupon() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Timestamp getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(Timestamp validUntil) {
		this.validUntil = validUntil;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public double getDiscountAmount() {
		return discountAmount;
	}
	
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	
}
