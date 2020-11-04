package bean;

import java.io.Serializable;
import java.sql.Timestamp;

import util.NotificationStatus;

public class Notification implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private User sender;
	private User recipient;
	private String message;
	private Timestamp dateCreated;
	private NotificationStatus status;
	
	public Notification() {
		
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public User getSender() {
		return sender;
	}
	
	public void setSender(User sender) {
		this.sender = sender;
	}
	
	public User getRecipient() {
		return recipient;
	}
	
	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public NotificationStatus getStatus() {
		return status;
	}
	
	public void setStatus(NotificationStatus status) {
		this.status = status;
	}

}
