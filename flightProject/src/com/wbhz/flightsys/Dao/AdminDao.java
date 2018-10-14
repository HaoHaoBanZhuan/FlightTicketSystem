package com.wbhz.flightsys.Dao;

import java.util.List;

import com.wbhz.flightsys.entity.Admin;

public interface AdminDao {
	public abstract List<Admin> queryAll();
	public abstract Admin getAdminInformationByUserName(String userName) ;
	public abstract void upadate(Admin admin);
	
}
