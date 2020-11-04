package bean;

public class ImageSetting {
	
	private long id;
	private String keyname;
	private byte[] imageval;
	
	public ImageSetting() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKeyname() {
		return keyname;
	}

	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}

	public byte[] getImageval() {
		return imageval;
	}

	public void setImageval(byte[] imageval) {
		this.imageval = imageval;
	}

}
