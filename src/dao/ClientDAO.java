package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Client;
import util.DBConnection;
import util.Role;

public class ClientDAO implements Dao<Client> {
	
	@Override
	public List<Client> getAllRecords() {
		List<Client> clientList = new ArrayList<Client>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("SELECT user.*, client.* FROM user" + "\n" +
												  "INNER JOIN client ON client.id = user.id" + "\n" +
												  "WHERE user.role = 'CLIENT'");
			
			while(results.next()) {
				Client client = new Client();
				
				client.setId(results.getLong(1));
				client.setUsername(results.getString(2));
				client.setPassword(results.getString(3));
				client.setFirstName(results.getString(6));
				client.setMiddleName(results.getString(7));
				client.setLastName(results.getString(8));
				client.setAddress(results.getString(9));
				client.setContactNumber(results.getLong(10));
				client.setEmailAddress(results.getString(11));
				client.setImage(results.getBytes(12));
				
				clientList.add(client);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return clientList;
	}

	@Override
	public Client getByID(long id) {
		Client client = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT user.*, client.* FROM user" + "\n" +
														   "INNER JOIN client ON client.id = user.id" + "\n" +
														   "WHERE user.role = 'CLIENT' AND user.id = ?");
			stmt.setLong(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				client = new Client();

				client.setId(result.getLong(1));
				client.setUsername(result.getString(2));
				client.setPassword(result.getString(3));
				client.setFirstName(result.getString(6));
				client.setMiddleName(result.getString(7));
				client.setLastName(result.getString(8));
				client.setAddress(result.getString(9));
				client.setContactNumber(result.getLong(10));
				client.setEmailAddress(result.getString(11));
				client.setImage(result.getBytes(12));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return client;
	}

	@Override
	public int insert(Client client) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO user VALUES (null, ?, ?, ?)");
			stmt.setString(1, client.getUsername());
			stmt.setString(2, client.getPassword());
			stmt.setString(3, Role.CLIENT.toString());
			
			status = stmt.executeUpdate();
			
			stmt = conn.prepareStatement("INSERT INTO client VALUES (LAST_INSERT_ID(), ?, ?, ?, ?, ?, ?, ?)");
			stmt.setString(1, client.getFirstName());
			stmt.setString(2, client.getMiddleName());
			stmt.setString(3, client.getLastName());
			stmt.setString(4, client.getAddress());
			stmt.setLong(5, client.getContactNumber());
			stmt.setString(6, client.getEmailAddress());
			stmt.setBytes(7, client.getImage());
			
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

	@Override
	public int update(Client client) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE user SET username=?, password=? WHERE id=?");
			stmt.setString(1, client.getUsername());
			stmt.setString(2, client.getPassword());
			stmt.setLong(3, client.getId());
			
			status = stmt.executeUpdate();
			
			if(status <= 0)
				return status;
			
			stmt = conn.prepareStatement("UPDATE client SET firstName=?, middleName=?, lastName=?, address=?, contactNumber=?, emailAddress=? WHERE id = ?");
			stmt.setString(1, client.getFirstName());
			stmt.setString(2, client.getMiddleName());
			stmt.setString(3, client.getLastName());
			stmt.setString(4, client.getAddress());
			stmt.setLong(5, client.getContactNumber());
			stmt.setString(6, client.getEmailAddress());
			stmt.setLong(7, client.getId());
			
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
	
	public int updateImage(Client client) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE client SET image=? WHERE id = ?");
			stmt.setBytes(1, client.getImage());
			stmt.setLong(2, client.getId());
			
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

	@Override
	public int delete(Client client) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM user WHERE id=?");
			stmt.setLong(1, client.getId());
			
			status = stmt.executeUpdate();
			
			if(status <= 0)
				return status;
			
			stmt = conn.prepareStatement("DELETE FROM client WHERE id=?");
			stmt.setLong(1, client.getId());
			
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

}
