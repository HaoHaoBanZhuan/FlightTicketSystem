package com.wbhz.flightsys.Service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.wbhz.flightsys.Dao.AdminDao;
import com.wbhz.flightsys.Dao.UserDao;
import com.wbhz.flightsys.Exception.UserSysException;
import com.wbhz.flightsys.Service.AdminService;
import com.wbhz.flightsys.component.util.DateUtil;
import com.wbhz.flightsys.entity.Admin;
import com.wbhz.flightsys.entity.User;
/**
 * 管理员只要登陆功能
 * @author Mr.t
 *
 */
public class AdminServiceImpl implements AdminService {
	private AdminDao adminDao ;
	private UserDao userDao;
	
	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	/**
	 * 管理员登陆(传入用户名、密码)
	 */
	public Admin login(Admin admin) {
		Logger logger = Logger.getLogger(this.getClass());
		Admin temp = adminDao.getAdminInformationByUserName(admin.getUserName()) ;
		//先判断用户是否存在
		if (null == temp) {
			System.out.println("账户不存在");
		}else{
			if (temp.getLogintimes() > 0 && (DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss").compareTo(temp.getLockTime()) >= 0) ) {
				//3、判断密码是否正确
				if (admin.getPwd().equals(temp.getPwd())) {
					String msg = "成功登陆——>用户名：【"+admin.getUserName()+"】" ;
					logger.debug(msg);
					System.out.println("恭喜，登陆成功");
					temp.setLogintimes(3);
					adminDao.upadate(temp);
					return temp ;
				} else {
					String msg = "登陆失败——>用户名：【"+admin.getUserName()+"】 原因：【密码错误】";
					logger.debug(msg);
					if (temp.getLogintimes() != 1) {
						System.out.println("密码错误，您还有"+(temp.getLogintimes()-1)+"次机会");
					} 
					temp.setLogintimes(temp.getLogintimes()-1);
					adminDao.upadate(temp);
				}
			}
			if(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss").compareTo(temp.getLockTime()) < 0) {
				String msg = "登陆失败——>用户名：【"+admin.getUserName()+"】 原因：【账号处于锁定状态】";
				logger.info(msg);
				System.out.println("账户处于锁定状态");
			}else if(!(temp.getLogintimes() > 0)){
				// 日期向后顺延一天
				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				calendar.add(calendar.DATE, 1);
				date = calendar.getTime();
				//更新数据库（登陆次数还原，锁定时间更新）
				String msg = "登陆失败——>用户名：【"+admin.getUserName()+"】 原因：【密码错误-尝试过多】   锁定时间至：【"+DateUtil.date2Str(date, "yyyy-MM-dd HH:mm:ss")+"】";
				logger.debug(msg);
				adminDao.upadate(new Admin(admin.getUserName(), 3, DateUtil.date2Str(date, "yyyy-MM-dd HH:mm:ss")));
				System.out.println("账户被锁定");
			}
		}
		return null ;
	}
	
	
	/**
	 * 通过用户名获取管理员信息
	 */
	@Override
	public Admin getAdminInformationByUserName(String userName) {
		Admin admin = new Admin();
		admin = adminDao.getAdminInformationByUserName(userName);
		return admin;
		
	}
	
	/**
	 * 解封用户
	 */
	@Override
	public void unlockUserCount(String  userName){
		Logger logger = Logger.getLogger(this.getClass());
		if (null != userDao.getByCondition(new User(userName)) ) {
			userDao.updataUserInformation(new User(userName, DateUtil.date2Str(new Date(), "yyyy-MM-dd hh:mm:ss")));
			userDao.updataUserInformation(new User(userName, 3));
			System.out.println("解封成功");
			String msg = "用户解封——> 用户名：【"+userName+"】";
			logger.debug(msg);
		}else {
			System.out.println("不存在该用户");
		}
	}
	
	
	/**
	 * 解封管理员
	 */
	@Override
	public void unlockAdminCount(String  userName){
		Logger logger = Logger.getLogger(this.getClass());
		if (null != adminDao.getAdminInformationByUserName(userName) ) {
			adminDao.upadate(new Admin(userName, 3,DateUtil.date2Str(new Date(), "yyyy-MM-dd hh:mm:ss")));
			System.out.println("解封成功");
			String msg = "管理员解封——> 用户名：【"+userName+"】";
			logger.debug(msg);
		}else {
			System.out.println("不存在该管理员");
		}
	}

	/**
	 * 查询所有管理员信息
	 * @return
	 */
	public List<Admin> querryAll(){
		List<Admin> list =adminDao.queryAll();
		return list;
		
	}
	
	
	

	
	
}
