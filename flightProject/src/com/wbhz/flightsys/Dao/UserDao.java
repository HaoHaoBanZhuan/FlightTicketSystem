package com.wbhz.flightsys.Dao;



import java.util.List;

import com.wbhz.flightsys.entity.User;

public interface UserDao {
	public abstract void addUser(User user) ;
	public abstract void updataUserInformation(User user) ;
	public abstract List<User> queryAll();
	public abstract User getByCondition(User user);
}
