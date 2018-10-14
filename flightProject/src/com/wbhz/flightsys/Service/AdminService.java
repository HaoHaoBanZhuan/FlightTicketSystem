package com.wbhz.flightsys.Service;


import java.util.List;

import com.wbhz.flightsys.Exception.UserSysException;
import com.wbhz.flightsys.entity.Admin;


public interface AdminService {
	public abstract Admin login(Admin admin) ;
	public abstract Admin getAdminInformationByUserName(String userName) ;
	public abstract void unlockUserCount(String userName);
	public abstract void unlockAdminCount(String userName);
	public abstract List<Admin> querryAll(); 
	

}
