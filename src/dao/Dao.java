package dao;

import java.util.List;

public interface Dao<T> {

	public abstract List<T> getAllRecords();
	
	public abstract T getByID(long id);
	
	public abstract int insert(T t);
	
	public abstract int update(T t);
	
	public abstract int delete(T t);
	
}
