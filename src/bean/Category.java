package bean;

import java.io.Serializable;

public class Category implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private String description;
	
	public Category() {
		
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

}
