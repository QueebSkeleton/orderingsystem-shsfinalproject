package bean;

import java.io.Serializable;

public class Banner implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private byte[] image;
	private String description;
	
	public Banner() {
		
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public byte[] getImage() {
		return image;
	}

	public String getDescription() {
		return description;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
