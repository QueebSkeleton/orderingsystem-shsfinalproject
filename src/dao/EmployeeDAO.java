package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Employee;
import util.DBConnection;
import util.Role;

public class EmployeeDAO implements Dao<Employee> {
	
	@Override
	public List<Employee> getAllRecords() {
		List<Employee> employeeList = new ArrayList<Employee>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("SELECT user.*, employee.* FROM user" + "\n" +
												  "INNER JOIN employee ON employee.id = user.id" + "\n" +
												  "WHERE user.role = 'EMPLOYEE'");
			
			while(results.next()) {
				Employee employee = new Employee();

				employee.setId(results.getLong(1));
				employee.setUsername(results.getString(2));
				employee.setPassword(results.getString(3));
				employee.setImage(results.getBytes(6));
				employee.setFirstName(results.getString(7));
				employee.setMiddleName(results.getString(8));
				employee.setLastName(results.getString(9));
				employee.setBirthdate(results.getDate(10));
				employee.setAddress(results.getString(11));
				employee.setContactNumber(results.getLong(12));
				employee.setEmailAddress(results.getString(13));
				
				employeeList.add(employee);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return employeeList;
	}

	@Override
	public Employee getByID(long id) {
		Employee employee = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT user.*, employee.* FROM user" + "\n" +
														   "INNER JOIN employee ON employee.id = user.id" + "\n" +
														   "WHERE user.role = 'EMPLOYEE' AND user.id = ?");
			stmt.setLong(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				employee = new Employee();

				employee.setId(result.getLong(1));
				employee.setUsername(result.getString(2));
				employee.setPassword(result.getString(3));
				employee.setImage(result.getBytes(6));
				employee.setFirstName(result.getString(7));
				employee.setMiddleName(result.getString(8));
				employee.setLastName(result.getString(9));
				employee.setBirthdate(result.getDate(10));
				employee.setAddress(result.getString(11));
				employee.setContactNumber(result.getLong(12));
				employee.setEmailAddress(result.getString(13));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return employee;
	}

	@Override
	public int insert(Employee employee) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO user VALUES (null, ?, ?, ?)");
			stmt.setString(1, employee.getUsername());
			stmt.setString(2, employee.getPassword());
			stmt.setString(3, Role.EMPLOYEE.toString());
			
			status = stmt.executeUpdate();
			
			if(status <= 0)
				return status;
			
			stmt = conn.prepareStatement("INSERT INTO employee VALUES (LAST_INSERT_ID(), ?, ?, ?, ?, ?, ?, ?, ?)");
			stmt.setBytes(1, employee.getImage());
			stmt.setString(2, employee.getFirstName());
			stmt.setString(3, employee.getMiddleName());
			stmt.setString(4, employee.getLastName());
			stmt.setDate(5, employee.getBirthdate());
			stmt.setString(6, employee.getAddress());
			stmt.setLong(7, employee.getContactNumber());
			stmt.setString(8, employee.getEmailAddress());
			
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
	public int update(Employee employee) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE user SET username=?, password=? WHERE id=?");
			stmt.setString(1, employee.getUsername());
			stmt.setString(2, employee.getPassword());
			stmt.setLong(3, employee.getId());
			
			status = stmt.executeUpdate();
			
			if(status <= 0)
				return status;
			
			stmt = conn.prepareStatement("UPDATE employee SET firstName=?, middleName=?, lastName=?, birthdate=?, address=?, contactNumber=?, emailAddress=? WHERE id=?");
			stmt.setString(1, employee.getFirstName());
			stmt.setString(2, employee.getMiddleName());
			stmt.setString(3, employee.getLastName());
			stmt.setDate(4, employee.getBirthdate());
			stmt.setString(5, employee.getAddress());
			stmt.setLong(6, employee.getContactNumber());
			stmt.setString(7, employee.getEmailAddress());
			stmt.setLong(8, employee.getId());
			
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
	
	public int updateImage(Employee employee) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE employee SET image=? WHERE id = ?");
			stmt.setBytes(1, employee.getImage());
			stmt.setLong(2, employee.getId());
			
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
	public int delete(Employee employee) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM user WHERE id=?");
			stmt.setLong(1, employee.getId());
			
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
