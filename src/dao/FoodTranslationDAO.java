package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Food;
import bean.FoodTranslation;
import util.DBConnection;

public class FoodTranslationDAO implements Dao<FoodTranslation> {

	@Override
	public List<FoodTranslation> getAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<FoodTranslation> getTranslationListByFood(Food food) {
		List<FoodTranslation> translationList = new ArrayList<FoodTranslation>();
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM foodtranslation WHERE food_id=?");
			stmt.setLong(1, food.getId());
			
			ResultSet results = stmt.executeQuery();
			
			while(results.next()) {
				FoodTranslation foodTranslation = new FoodTranslation();
				
				foodTranslation.setId(results.getLong(1));
				
				FoodDAO foodDao = new FoodDAO();
				foodTranslation.setFood(foodDao.getByID(results.getLong(2)));
				
				LanguageDAO languageDao = new LanguageDAO();
				foodTranslation.setLanguage(languageDao.getByID(results.getLong(3)));
				
				foodTranslation.setName(results.getString(4));
				foodTranslation.setDescription(results.getString(5));
				
				translationList.add(foodTranslation);
			}
			
			results.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return translationList;
	}

	@Override
	public FoodTranslation getByID(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public FoodTranslation getByFoodAndLanguageId(long foodId, long languageId) {
		FoodTranslation translation = null;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM foodtranslation WHERE food_id=? AND language_id=?");
			stmt.setLong(1, foodId);
			stmt.setLong(2, languageId);
			
			ResultSet result = stmt.executeQuery();
			
			if(result.next()) {
				translation = new FoodTranslation();
				
				translation.setId(result.getLong(1));
				
				FoodDAO foodDao = new FoodDAO();
				translation.setFood(foodDao.getByID(result.getLong(2)));
				
				LanguageDAO languageDao = new LanguageDAO();
				translation.setLanguage(languageDao.getByID(result.getLong(3)));
				
				translation.setName(result.getString(4));
				translation.setDescription(result.getString(5));
			}
			
			result.close();
			stmt.close();
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return translation;
	}

	@Override
	public int insert(FoodTranslation translation) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO foodtranslation VALUES (null, ?, ?, ?, ?)");
			stmt.setLong(1, translation.getFood().getId());
			stmt.setLong(2, translation.getLanguage().getId());
			stmt.setString(3, translation.getName());
			stmt.setString(4, translation.getDescription());
			
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
	public int update(FoodTranslation translation) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("UPDATE foodtranslation SET name=?, description=? WHERE id=?");
			stmt.setString(1, translation.getName());
			stmt.setString(2, translation.getDescription());
			stmt.setLong(3, translation.getId());
			
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
	public int delete(FoodTranslation translation) {
		int status = 0;
		
		try {
			Connection conn = DBConnection.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM foodtranslation WHERE id=?");
			stmt.setLong(1, translation.getId());
			
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
