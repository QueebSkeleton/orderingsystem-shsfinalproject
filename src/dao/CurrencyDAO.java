package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Currency;
import util.DBConnection;

public class CurrencyDAO implements Dao<Currency> {
	
	@Override
	public List<Currency> getAllRecords() {
		List<Currency> currencyList = new ArrayList<Currency>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("SELECT * FROM currency");
			
			while(results.next()) {
				Currency currency = new Currency();
				
				currency.setId(results.getInt(1));
				currency.setCode(results.getString(2));
				currency.setDescription(results.getString(3));
				currency.setExchangeRate(results.getDouble(4));
				
				currencyList.add(currency);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return currencyList;
	}

	@Override
	public Currency getByID(long id) {
		Currency currency = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM currency WHERE id=?");
			stmt.setLong(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				currency = new Currency();
				
				currency.setId(result.getInt(1));
				currency.setCode(result.getString(2));
				currency.setDescription(result.getString(3));
				currency.setExchangeRate(result.getDouble(4));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return currency;
	}

	@Override
	public int insert(Currency currency) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO currency VALUES (null, ?, ?, ?)");
			stmt.setString(1, currency.getCode());
			stmt.setString(2, currency.getDescription());
			stmt.setDouble(3, currency.getExchangeRate());
			
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
	public int update(Currency currency) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE currency SET code=?, description=?, exchangeRate=? WHERE id=?");
			stmt.setString(1, currency.getCode());
			stmt.setString(2, currency.getDescription());
			stmt.setDouble(3, currency.getExchangeRate());
			stmt.setLong(4, currency.getId());
			
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
	public int delete(Currency currency) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM currency WHERE id=?");
			stmt.setLong(1, currency.getId());
			
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
