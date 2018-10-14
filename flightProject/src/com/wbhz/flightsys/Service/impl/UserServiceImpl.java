package com.wbhz.flightsys.Service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.wbhz.flightsys.Dao.FlightDao;
import com.wbhz.flightsys.Dao.UserDao;
import com.wbhz.flightsys.Exception.UserSysException;
import com.wbhz.flightsys.Service.UserService;
import com.wbhz.flightsys.component.constant.FlightSysConstant;
import com.wbhz.flightsys.component.util.DateUtil;
import com.wbhz.flightsys.entity.Flight;
import com.wbhz.flightsys.entity.User;
import com.wbhz.flightsys.jdbc.Transaction;

public class UserServiceImpl implements UserService {
	private UserDao userDao;
	private FlightDao flightDao ;
	private Transaction tx;
	
	
	public void setFlightDao(FlightDao flightDao) {
		this.flightDao = flightDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setTx(Transaction tx) {
		this.tx = tx;
	}

	/**
	 * 用户注册
	 */
	@Override
	public void register(User user) { 
			userDao.addUser(user);
	}
	
	/**
	 * 用户登陆
	 */
	public User login(User user) {
		Logger logger = Logger.getLogger(this.getClass());
		//1、判断账户是否存在
		User result = userDao.getByCondition(user);
		if (null == result) {
			System.out.println("账户不存在");
		} else {
			//登陆次数，超过24小时未登陆，次数还原 账号被锁定后，登陆次数也还原
			
			// 日期向前减一天
			Date loginDate = new Date();
			Calendar loginCalendar = new GregorianCalendar();
			loginCalendar.setTime(loginDate);
			loginCalendar.add(loginCalendar.DATE, -1);
			loginDate = loginCalendar.getTime();
			if (result.getLoginDate().compareTo(DateUtil.date2Str(loginDate, "yyyy-MM-dd")) < 0 ) {
				result.setLoginTimes(3);
			} 
			//更新登陆日期
			result.setLoginDate(DateUtil.date2Str(new Date(), "yyyy-MM-dd"));
			userDao.updataUserInformation(result);
			
			//2、判断是否还有登陆次数，并且未锁定
			if (result.getLoginTimes() > 0 && (DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss").compareTo(result.getLocktime()) >= 0) ) {
				//3、判断密码是否正确
				if (result.getPwd().equals(user.getPwd())) {
					String msg = "成功登陆——>用户名：【"+result.getUserName()+"】" ;
					logger.info(msg);
					System.out.println("恭喜，登陆成功");
					result.setLoginTimes(3);
					userDao.updataUserInformation(new User(user.getUserName(), result.getLoginTimes()));
					return result ;
				} else {
					String msg = "登陆失败——>用户名：【"+result.getUserName()+"】 原因：【密码错误】";
					logger.info(msg);
					if (result.getLoginTimes() != 1) {
						System.out.println("密码错误，您还有"+(result.getLoginTimes()-1)+"次机会");
					} 
					result.setLoginTimes(result.getLoginTimes()-1);
					userDao.updataUserInformation(result);
				}
			} 
			if(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss").compareTo(result.getLocktime()) < 0) {
				String msg = "登陆失败——>用户名：【"+result.getUserName()+"】 原因：【账号处于锁定状态】";
				logger.info(msg);
				System.out.println("账户处于锁定状态");
			}else if(!(result.getLoginTimes() > 0)){
				// 日期向后顺延一天
				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				calendar.add(calendar.DATE, 1);
				date = calendar.getTime();
				String msg = "登陆失败——>用户名：【"+result.getUserName()+"】 原因：【密码错误-尝试过多】   锁定时间至：【"+DateUtil.date2Str(date, "yyyy-MM-dd HH:mm:ss")+"】";
				logger.info(msg);
				//更新数据库（登陆次数还原，锁定时间更新）
				userDao.updataUserInformation(new User(user.getUserName(), 3, DateUtil.date2Str(date, "yyyy-MM-dd HH:mm:ss")));
				System.out.println("账户被锁定");
			}
		}
		return null ;
	}

	/**
	 * 用户是否存在/密码是否正确
	 * @throws UserSysException 
	 */
	public boolean queryCheck(User user) throws UserSysException {
		// list存储所有用户的信息
		FlightSysConstant.isExist = false;
		User result = userDao.getByCondition(user);
		if (result == null) {
			FlightSysConstant.isExist = false ;
		} else {
			FlightSysConstant.isExist = true ;
			if (user.getPwd() != null && user.getPwd().equals(result.getPwd())) {
				FlightSysConstant.isPwdCorrect = true ;
			} else {
				FlightSysConstant.isPwdCorrect = false ;
			}
		}
		return FlightSysConstant.isExist;
	}

	/**
	 * 用户修改信息
	 */
	@Override
	public void updata(User user) {
		tx.begin();
		userDao.updataUserInformation(user);
		tx.commit();
	}

	/**
	 * 用户名查找用户信息
	 */
	@Override
	public User getUserByUserName(String userName) {
		User user = null ;
		user = userDao.getByCondition(new User(userName));
		return user ;
	}

	/**
	 * 筛选出可预订的机票
	 */
	@Override
	public List<Flight> getFlightInformation(Flight flight) throws SQLException {
		List<Flight> list = new ArrayList<Flight>();
		List<Flight> tempList = new ArrayList<Flight>();
		tempList = flightDao.getBycondition(flight);
		Iterator<Flight> it = tempList.iterator();
		while(it.hasNext()){
			Flight targetFlight = it.next();
			if (targetFlight.getTakeOffTime().compareTo(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss")) > 0) {
				list.add(targetFlight);
			}
		}
		return list;
	}
	
	/**
	 * 查找所有用户
	 */
	@Override
	public List<User> queryAll(){
		List<User> list = userDao.queryAll();
		return list ;
	}
	
	
	
	
	
	

}
