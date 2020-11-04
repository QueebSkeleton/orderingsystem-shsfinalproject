package bean;

import java.io.Serializable;

public class Currency implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String code;
	private String description;
	private double exchangeRate;
	
	public Currency() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getExchangeRate() {
		return exchangeRate;
	}
	
	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
}
