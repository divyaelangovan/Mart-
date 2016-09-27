package com.niit.martzone.dao;

import java.util.List;

import com.niit.martzone.model.Users;

public interface UsersDAO {
	public List<Users> list();

	public Users get(String id);

	public void saveOrUpdate(Users users);
	
	public void delete(String id);
	
	public boolean isValidUser(String username, String password);
	

}
