package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Country;
import util.DBConnection;

public class CountryDAO implements Dao<Country> {

	@Override
	public List<Country> getAllRecords() {
		List<Country> countryList = new ArrayList<Country>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			Statement stmt = conn.createStatement();
			
			ResultSet results = stmt.executeQuery("SELECT * FROM country");
			
			while(results.next()) {
				Country country = new Country();
				
				country.setId(results.getInt(1));
				
				ZoneDAO zoneDao = new ZoneDAO();
				country.setZone(zoneDao.getByID(results.getLong(2)));
				
				LanguageDAO languageDao = new LanguageDAO();
				country.setLanguage(languageDao.getByID(results.getLong(3)));

				country.setName(results.getString(4));
				country.setCode(results.getString(5));
				country.setDescription(results.getString(6));
				
				countryList.add(country);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return countryList;
	}

	@Override
	public Country getByID(long id) {
		Country country = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM country WHERE id=?");
			stmt.setLong(1, id);
			
			ResultSet result = stmt.executeQuery();
			
			while(result.next()) {
				country = new Country();
				
				country.setId(result.getInt(1));
				
				ZoneDAO zoneDao = new ZoneDAO();
				country.setZone(zoneDao.getByID(result.getLong(2)));
				
				LanguageDAO languageDao = new LanguageDAO();
				country.setLanguage(languageDao.getByID(result.getLong(3)));

				country.setName(result.getString(4));
				country.setCode(result.getString(5));
				country.setDescription(result.getString(6));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return country;
	}

	@Override
	public int insert(Country country) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO country VALUES (null, ?, ?, ?, ?, ?)");
			stmt.setLong(1, country.getZone().getId());
			stmt.setLong(2, country.getLanguage().getId());
			stmt.setString(3, country.getName());
			stmt.setString(4, country.getCode());
			stmt.setString(5, country.getDescription());
			
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
	public int update(Country country) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE country SET zone_id=?, language_id=?, name=?, code=?, description=? WHERE id=?");
			stmt.setLong(1, country.getZone().getId());
			stmt.setLong(2, country.getLanguage().getId());
			stmt.setString(3, country.getName());
			stmt.setString(4, country.getCode());
			stmt.setString(5, country.getDescription());
			stmt.setLong(6, country.getId());
			
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
	public int delete(Country country) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM country WHERE id=?");
			stmt.setLong(1, country.getId());
			
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
