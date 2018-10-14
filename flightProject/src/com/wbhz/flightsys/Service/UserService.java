package com.wbhz.flightsys.Service;

import java.sql.SQLException;
import java.util.List;

import com.wbhz.flightsys.Exception.UserSysException;
import com.wbhz.flightsys.entity.Flight;
import com.wbhz.flightsys.entity.User;

public interface UserService {
	public abstract void register(User user) throws UserSysException;
	public abstract void updata(User user) ;
	public abstract boolean queryCheck(User user) throws UserSysException;
	public abstract User login(User user) ;
	public abstract User getUserByUserName(String userName);
	public abstract List<Flight> getFlightInformation(Flight flight) throws SQLException;
	public abstract List<User> queryAll();
	
}
