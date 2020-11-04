package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Notification;
import bean.User;
import util.DBConnection;
import util.NotificationStatus;

public class NotificationDAO {
	
	public List<Notification> getNotificationListByRecipient(User recipient) {
		List<Notification> notificationList = new ArrayList<Notification>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM notification WHERE recipient_id=? ORDER BY dateCreated DESC LIMIT 10");
			stmt.setLong(1, recipient.getId());
			
			ResultSet results = stmt.executeQuery();
			
			while(results.next()) {
				Notification notification = new Notification();
				
				notification.setId(results.getLong(1));
				notification.setSender(null);
				notification.setRecipient(recipient);
				notification.setMessage(results.getString(4));
				notification.setDateCreated(results.getTimestamp(5));
				notification.setStatus(NotificationStatus.valueOf(results.getString(6)));
				
				notificationList.add(notification);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return notificationList;
	}
	
	public int insert(Notification notification) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO notification VALUES (null, ?, ?, ?, ?, ?)");
			stmt.setLong(1, notification.getSender().getId());
			stmt.setLong(2, notification.getRecipient().getId());
			stmt.setString(3, notification.getMessage());
			stmt.setTimestamp(4, notification.getDateCreated());
			stmt.setString(5, notification.getStatus().toString());
			
			status = stmt.executeUpdate();
			
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public int seeAllNotifications() {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE notification SET status=?");
			stmt.setString(1, NotificationStatus.SEEN.toString());
			
			status = stmt.executeUpdate();
			
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	public long getUnreadNotificationCount() {
		long count = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet result = stmt.executeQuery("SELECT COUNT(*) FROM notification WHERE status='UNREAD'");
			
			if(result.next())
				count = result.getLong(1);
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}

}
