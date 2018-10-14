package com.wbhz.flightsys.UI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.wbhz.flightsys.Exception.FlightSysException;
import com.wbhz.flightsys.Exception.UserSysException;
import com.wbhz.flightsys.Factory.ObjectFactory;
import com.wbhz.flightsys.Service.BillingService;
import com.wbhz.flightsys.Service.FlightAdminService;
import com.wbhz.flightsys.Service.UserService;
import com.wbhz.flightsys.component.constant.UserSysConstant;
import com.wbhz.flightsys.component.util.DateUtil;
import com.wbhz.flightsys.entity.Billing;
import com.wbhz.flightsys.entity.Flight;
import com.wbhz.flightsys.entity.User;
import com.wbhz.flightsys.test.AppTest;

public class UserInterface {
	private UserService userService = (UserService) ObjectFactory.getObject("userService");
	private BillingService billingService = (BillingService) ObjectFactory.getObject("billingService");
	private FlightAdminService flightAdminService = (FlightAdminService) ObjectFactory.getObject("flightAdminService");
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 用户注册
	 */
	@SuppressWarnings("resource")
	public void register() {
		Scanner input = new Scanner(System.in);
		System.out.println("****************请按照提示步骤进行账号注册*****************");
		//登录密码的格式是否正确
		boolean loginPwdform = false;
		//支付密码的格式是否正确
		boolean confirmPwdform = false;
		User user = new User();
		//用户名
		String userName = null;
		try {
			//用户名是否重复
			boolean isSame = true;
			while (isSame) {
				System.out.print("请输入用户名:");
				userName = input.next();
				if (userName.length() > 10) {
					System.out.println("姓名长度必须小于10");
					continue;
				}
				//方法返回的即为是否重复
				isSame = userService.queryCheck(new User(userName));
				if (isSame) {
					System.out.println("用户名重复");
				}
			}
			user.setUserName(userName);
			
			//登陆密码
			String pwd = null;
			while (!loginPwdform) {
				System.out.print("请输入登陆密码(必须是字母开头,16位内):");
				pwd = input.next();
				if (pwd.matches("^[a-zA-Z][a-zA-Z0-9]{0,15}")) {
					loginPwdform = true;
				} else {
					System.out.println("登陆密码格式不正确");
				}
			}
			user.setPwd(pwd);
			
			//支付密码
			String confirmPwd = null;
			while (!confirmPwdform) {
				System.out.print("请输入您的支付密码（六位纯数字）：");
				confirmPwd = input.next();
				if (confirmPwd.matches("^\\d{6}")) {
					confirmPwdform = true;
				} else {
					System.out.println("支付密码格式不正确");
				}
			}
			user.setConfirmPwd(confirmPwd);
			
			//姓名
			String name ;
			while (true) {
				System.out.print("请输入您的真实姓名:");
				name = input.next();
				if (name.length() <= 10) {
					break ;
				}
			}
			user.setName(name);
			
			//性别
			while (true) {
				System.out.print("请输入您的性别:");
				String sex = input.next();
				if (sex.equals("男") || sex.equals("女") || sex.equals("妖")) {
					user.setSex(sex);
					break;
				} else {
					System.out.println("请输入正确的性别(提示：男/女/妖)");
					continue;
				}
			}
			
			//身份证号
			String userID = null;
			//身份证号是否存在
			boolean isUserIDExist;
			do {
				isUserIDExist = true;
				System.out.print("请输入您的身份证号:");
				userID = input.next();
				List<User> list = userService.queryAll();
				Iterator<User> it = list.iterator();
				while (it.hasNext()) {
					User temp = (User) it.next();
					if (userID.equals(temp.getUserID())) {
						System.out.println("一张身份证仅允许注册一个账号！！");
						isUserIDExist = false;
						continue;
					}
				}
				//身份证的正则表达式
				if (!(userID.matches("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$"))) {
					System.out.println("请输入正确的身份证号");
					isUserIDExist = false;
				}
			} while (!isUserIDExist);
			user.setUserID(userID);
			
			//手机号
			while (true) {
				System.out.print("请输入您的手机号码:");
				String phoneNumber = input.next() ;
				if (phoneNumber.matches("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$")) {
					user.setPhoneNumber(phoneNumber);
					break ;
				}else{
					System.out.println("手机号格式不正确");
					continue;
				}
			}
			
			//家庭住址
			while (true) {
				System.out.print("请输入您的家庭住址:");
				String address = input.next();
				if (address.length() <= 100) {
					user.setAddress(address);
					break;
				} else {
					System.out.println("地址长度小于100");
					continue;
				}
			}
			
			//注册日志记录
			userService.register(user);
			String message ="注册账户——>用户名：【"+user.getUserName()+"】 密码：【"+user.getPwd()+"】 支付密码：【"+user.getConfirmPwd()+"】";
			logger.info(message);
			
		} catch (UserSysException e) {
			System.out.println(e.getMessage());
			this.register();
		} catch (InputMismatchException e) {
			System.out.println("数值类型不匹配");
			this.register();
		}
		System.out.println("注册完成");
		System.out.println();
		System.out.println();
	}

	
	/**
	 * 用户登陆界面
	 * 日志记录在service层
	 */
	@SuppressWarnings("resource")
	public User login() {
		System.out.println("****************登录界面*****************");
		Scanner input = new Scanner(System.in);
		User user = new User();
		System.out.print("请输入您的账号：");
		String userName = input.next();
		user.setUserName(userName);
		System.out.print("请输入此账号的密码：");
		String pwd = input.next();
		user.setPwd(pwd);
		//判断用户是否存在
		User result = userService.login(user);
		//记录当前正在操作软件的用户、及其密码
		UserSysConstant.userName = userName ;
		UserSysConstant.pwd = pwd ;
		if (result != null) {
			return result ;
		}else {
			return null ;
		}
	}

	/**
	 * 通过绑定的身份证找回登陆密码
	 */
	@SuppressWarnings("resource")
	public void findPwdBack() {
		System.out.println("**********************密码找回*********************");
		Scanner input = new Scanner(System.in);
		//密码格式标记
		boolean layout = false;
		System.out.print("请输入您账号：");
		String userName = input.next();
		User user = userService.getUserByUserName(userName);
		try {
			//判断输入的用户名是否正确
			if (user != null) {
				user = userService.getUserByUserName(userName);
				System.out.print("请输入绑定的的身份证号：");
				String userID = input.next();
				//判断绑定的ID与输入的ID是否一致
				if (user.getUserID().equals(userID)) {
					String pwd1 = null;
					String pwd2 = null;
					//判断新密码的格式
					while (!layout) {
						System.out.print("请输入新密码(必须是字母开头,16位内):");
						pwd1 = input.next();
						System.out.print("请再次输入密码:");
						pwd2 = input.next();
						if (!(pwd1.equals(pwd2))) {
							System.out.println("两次输入不一致");
							continue ;
						}
						if (pwd1.matches("^[a-zA-Z][a-zA-Z0-9]{0,15}")) {
							layout = true;
						} else {
							System.out.println("登陆密码格式不正确");
						}
					}
					//更改成功，更新数据库
					user.setPwd(pwd1);
					userService.updata(user);
					System.out.println("重置密码成功！");
					//更改密码日志记录
					String msg = "密码找回——>用户名：【"+user.getUserName()+"】 新密码：【"+user.getPwd()+"】";
					logger.info(msg);
				}
				else {
					System.out.println("身份证号错误！");
				}
			}else if (user == null) {
				System.out.println("不存在此账号。");
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
		System.out.println();
		System.out.println();
	}

	/**
	 * 修改用户信息
	 * 可修改项目：1、密码 2、手机号码 3、家庭住址 
	 */
	public void updata() {
		System.out.println("***********************个人信息修改***********************");
		User user = this.updateAttributeChoose(userService.getUserByUserName(UserSysConstant.userName));
		userService.updata(user);
		StringBuffer sb= new StringBuffer();
		sb.append("个人信息修改——> 用户名：【"+UserSysConstant.userName+"】");
		if (null != user.getPwd()) {
			sb.append("新密码：【"+user.getPwd()+"】");
		}
		if (null != user.getPhoneNumber()) {
			sb.append("新手机号:【"+user.getPhoneNumber()+"】");
		}
		if (null != user.getAddress() ) {
			sb.append("新家庭住址:【"+user.getAddress()+"】");
		}
		logger.info(sb.toString());
		System.out.println("修改成功");
		System.out.println();
		System.out.println();
	}

	/**
	 * 查询可预订航班
	 */
	public void selectAvailableFlights() {
		System.out.println("*************************筛选航班结果**************************");
		try {
			if (userService.getFlightInformation(this.chooseFlight(new Flight())).size() == 0) {
				System.out.println("暂无符合要求的航班");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			this.selectAvailableFlights();
		}
		System.out.println();
		System.out.println();
	}

	/**
	 * 订票
	 */
	@SuppressWarnings({ "resource", "static-access" })
	public void orderTicket() {
		Scanner input = new Scanner(System.in);
		System.out.println("************************售票预订******************************");
		//记录你输入支付密码的次数
		int isConfirmPwdCorrect = 3;
		List<Flight> list = null;
		Flight flight = null;
		String flightNumberChoice = null;
		//车票的信息-乘车人以及其身份证号
		Billing billing = new Billing();
		System.out.print("请输入乘客的姓名：");
		billing.setName(input.next());
		String userID = null ;
		while (true) {
			System.out.print("请输入乘客的身份证号：");
			userID = input.next() ;
			if (userID.matches("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$")) {
				billing.setUserID(userID);
				break ;
			}else {
				System.out.println("身份证号格式错误");
				continue ;
			}
			
		}
		// 返回选择的航班的消息
		try {
			//将符合条件的航班 存入list
			list = flightAdminService.getBycondition(this.chooseFlight(new Flight()));
			// 记录乘客所选的航班号
			System.out.print("请选择要乘坐的航班号：");
			flightNumberChoice = input.next();
			//将选择的航班存入flight
			for (int i = 1; i <= list.size(); i++) {
				if (list.get(i - 1).getFlightNumber().equals(flightNumberChoice)) {
					flight = list.get(i - 1);
					List<Billing> billingList = billingService.queryAll();
					for (int j = 0; j < billingList.size(); j++) {
						if (billingList.size() != 0) {
							if (billingList.get(j).getUserID().equals(userID) && billingList.get(j).getFlightNumber().equals(flightNumberChoice)) {
								throw new FlightSysException("同一航班，一个人只能预定一个");
							}
						}
					}
				} 
			}
			if (flight == null ) {
					System.out.println("输入的航班号错误，请重新下单");
			}
			// 生成订单的信息
			billing.setStartPlace(flight.getStartPlace());
			billing.setEndPlace(flight.getEndPlace());
			billing.setFlightNumber(flight.getFlightNumber());
			billing.setPrice(flight.getPrice());
			billing.setTakeOffTime(flight.getTakeOffTime());
			while (isConfirmPwdCorrect != 0) {
				System.out.print("请输入您的支付密码，以确认订单:");
				String confirmPwd = input.next();
				if (confirmPwd.equals(userService.getUserByUserName(UserSysConstant.userName).getConfirmPwd())) {
					// 添加订单 机票数量的修改
					billingService.add(billing, flightNumberChoice);
					// 返回订单号
					String msg = "订票成功——>订票人：【"+UserSysConstant.userName+"】订单号为："+billing.getOrderNumber()+flight.toString();
					logger.info(msg);
					System.out.println("订票成功，订单号为：" + billing.getOrderNumber());
					break;
				} else {
					isConfirmPwdCorrect--;
					System.out.println("密码错误,还有"+isConfirmPwdCorrect+"次尝试机会");
				}
			}
			if (isConfirmPwdCorrect == 0) {
				// 将账户锁定
				String msg = "账户锁定——>原因：【购票时，支付密码错误超过限制】";
				logger.info(msg);
				System.out.println("该账户已被锁定，请于24小时后尝试，或者联系客服（0484213）");
				// 日期向后顺延一天
				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				calendar.add(calendar.DATE, 1);
				date = calendar.getTime();
				userService.updata(new User(UserSysConstant.userName, DateUtil.date2Str(date, "yyyy-MM-dd HH:mm:ss")));
				AppTest.app();
			}
		} catch (FlightSysException e) {
			System.out.println(e.getMessage());
		} catch (InputMismatchException e) {
			System.out.println("您的输入不正确");
		}catch (NullPointerException e) {
			System.out.println("不存在此航班");
			this.orderTicket();
		}
		System.out.println();
		System.out.println();
	}

	/**
	 * 退票
	 */
	public void returnTicket() {
		System.out.println("*********************退票**************************");
		int isConfirmPwdCorrect = 3;
		Scanner input = new Scanner(System.in);
		//查询出所有订单号
		List<Billing> list = billingService.queryAll();
		//存储所有的该名字下的订单号
		List<Integer> orderNumberList = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getUserName().equals(UserSysConstant.userName)) {
				System.out.println(list.get(i).toString());
				orderNumberList.add(list.get(i).getOrderNumber());
			}
		}
		System.out.print("请选择您要退票的订单号：");
		int orderNumber = input.nextInt();
		try {
			//是否存在所选择的订单号
			if (orderNumberList.contains(Integer.valueOf(orderNumber))) {
				//取出指定的订单号所有信息
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getOrderNumber() == orderNumber) {
						//判断支付密码是否正确
						while (isConfirmPwdCorrect != 0) {
							System.out.print("请输入您的支付密码，以确认删除订单:");
							String confirmPwd = input.next();
							if (confirmPwd.equals(userService.getUserByUserName(UserSysConstant.userName).getConfirmPwd())) {
								String msg = "退票成功——>退票人：【"+UserSysConstant.userName+"】订单号为："+list.get(i).getOrderNumber();
								logger.info(msg);
								billingService.delete(list.get(i).getOrderNumber());
								System.out.println("删除成功");
								//登陆成功，还原登陆次数
								userService.updata(new User(UserSysConstant.userName, 3));
								break;
							} else {
								isConfirmPwdCorrect--;
								System.out.println("密码错误");
								if (isConfirmPwdCorrect <= 0) {
									// 将账户锁定
									String msg = "账户锁定——>原因：【退票时，支付密码错误超过限制】";
									logger.info(msg);
									System.out.println("该账户已被锁定，请于24小时后尝试，或者联系客服（0484213）");
									// 日期向后顺延一天
									Date date = new Date();
									Calendar calendar = new GregorianCalendar();
									calendar.setTime(date);
									calendar.add(Calendar.DATE, 1);
									date = calendar.getTime();
									userService.updata(
											new User(UserSysConstant.userName, DateUtil.date2Str(date, "yyyy-MM-dd HH:mm:ss")));
									//账户被锁定之后，强制退出
									return ;
								}
							}
						}
						
					}
				}
			} else {
				throw new NullPointerException("您所选的航班号不正确");
			}
		} catch (FlightSysException e) {
			System.out.println(e.getMessage());
			this.returnTicket();
		} catch (NullPointerException e) {
			System.out.println("订单号不存在，无法退票");
		} catch (Exception e) {
			System.out.println("航班不存在");
//			this.updata();
		}
		System.out.println();
		System.out.println();
	}

	/**
	 * 修改用户属性选择
	 * @param user
	 * @return user
	 */
	public User updateAttributeChoose(User user) {
		Scanner input = new Scanner(System.in);
		//判断123输入的选项是否存在
		boolean isNext = true;
		//判断Y/N输入的是否正确
		boolean isCorrect = true;
		//判断修改的面格式是否正确
		boolean loginPwdform = false;
		user.setUserName(UserSysConstant.userName);
		try {
			while (isNext) {
				System.out.println("请选择想要修改的信息");
				System.out.print("1、密码 2、手机号码 3、家庭住址 ：");
				String key = input.next();
				if ("1".equals(key)) {
					String pwd = null;
					while (!loginPwdform) {
						System.out.print("请输入登陆密码(必须是字母开头,16位内):");
						pwd = input.next();
						if (pwd.matches("^[a-zA-Z][a-zA-Z0-9]{0,15}")) {
							loginPwdform = true;
						} else {
							System.out.println("登陆密码格式不正确");
						}
					}
					user.setPwd(pwd);
				} else if (key.equals("2")) {
					while (true) {
						System.out.print("请输入您的手机号码:");
						String phoneNumber = input.next() ;
						if (phoneNumber.matches("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$")) {
							user.setPhoneNumber(phoneNumber);
							break ;
						}else{
							System.out.println("手机号格式不正确");
							continue;
						}
					}
				} else if (key.equals("3")) {
					System.out.print("请输入修改项的值：");
					String value = input.next();
					user.setAddress(value);
				} else {
					System.out.println("请键入正确的选项。");
					continue;
				}
				isCorrect = true;
				while (isCorrect) {
					System.out.print("继续添加修改项目？（Y/N）");
					String choice = input.next();
					if ("Y".equalsIgnoreCase(choice)) {
						isNext = true;
						isCorrect = false;
					} else if ("N".equalsIgnoreCase(choice)) {
						isNext = false;
						break;
					} else {
						System.out.println("请键入正确的选项。");
						isCorrect = true;
					}
				}
			}
		} catch (InputMismatchException e) {
			System.out.println("输入值的类型不正确");
			this.updateAttributeChoose(user);
		} 
		return user;
	}

	/**
	 * 航班的筛选
	 * @param flight
	 * @return 筛选出来的flight
	 */
	private Flight chooseFlight(Flight flight) {
		Scanner input = new Scanner(System.in);
		boolean isNext = true;
		boolean isCorrect = true;
		while (isNext) {
			System.out.print("请选择筛选内容：1、出发地 2、目的地 3、起飞时间 4、航班号:");
			String key = input.next();
			if (key .equals("1")) {
				System.out.print("请输入筛选项的值：");
				String value = input.next();
				flight.setStartPlace(value);
			} else if (key .equals("2")) {
				System.out.print("请输入筛选项的值：");
				String value = input.next();
				flight.setEndPlace(value);
			} else if (key .equals("3")) {
				System.out.print("请输入筛选项的值：");
				String value = input.nextLine();
				flight.setTakeOffTime(value);
			} else if (key .equals("4")) {
				System.out.print("请输入筛选项的值：");
				String value = input.next();
				flight.setFlightNumber(value);
			} else {
				System.out.println("请键入正确的选项。");
				continue;
			}
			isCorrect = true;
			while (isCorrect) {
				System.out.print("继续添加筛选条件？（Y/N）");
				String choice = input.next();
				if ("Y".equalsIgnoreCase(choice)) {
					isNext = true;
					isCorrect = false;
				} else if ("N".equalsIgnoreCase(choice)) {
					isNext = false;
					break;
				} else {
					System.out.println("请键入正确的选项。");
					isCorrect = true;
				}
			}
		}
		return flight;
	}

	/**
	 * 改签
	 * @param orderNumber
	 */
	public void updateBilling(){
		Scanner input = new Scanner(System.in);
		System.out.println("************************机票改签******************************");
		System.out.print("请输入乘客的身份证号：");
		String userID = input.next();
		//查询所有订单
		List<Billing> list = billingService.queryAll();
		//筛选后的订单
		List<Billing> result = new ArrayList<Billing>();
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getUserID().equals(userID)) {
				System.out.println(list.get(i).toString());
				result.add(list.get(i));
			}
		}
		//若筛选出来有结果
		if (result.size() != 0) {
			System.out.print("请选择需要改签的订单号：");
			int ordernumber = input.nextInt();
			Flight flight = null ;
			for (int i = 0; i < result.size(); i++) {
				if (result.get(i).getOrderNumber() == ordernumber) {
					try {
						List<Flight> flightlist = flightAdminService.getBycondition(this.chooseFlight(new Flight()));
						System.out.print("请选择要更改的航班号：");
						String flightNumberChoice = input.next();
						for (int x = 1; x <= flightlist.size(); x++) {
							if (flightlist.get(x - 1).getFlightNumber().equals(flightNumberChoice)) {
								flight = flightlist.get(x - 1);
							} 
						}
						if (flight == null ) {
							System.out.println("输入的航班号错误，请重新下单");
						}
						Billing billingNew = new Billing();
						String flightNumber = flight.getFlightNumber();
						billingNew.setFlightNumber(flightNumber);
						String startPlace  = flight.getStartPlace();
						billingNew.setStartPlace(startPlace);
						String endPlace = flight.getEndPlace();
						billingNew.setEndPlace(endPlace);
						String takeOffTime = flight.getTakeOffTime();
						billingNew.setTakeOffTime(takeOffTime);
						double price = flight.getPrice();
						billingNew.setPrice(price);
						String name = result.get(i).getName();
						billingNew.setName(name);
						billingNew.setUserID(userID);
						//先新增订单，再将原订单删除
						billingService.add(billingNew, flightNumber);
						billingService.delete(ordernumber);
					} catch (FlightSysException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			if (flight == null) {
					System.out.println("您输入的订单号不正确");
			}
		} else {
			System.out.println("该乘客未预订航班");
		}
	}
	
	/**
	 * 查询当前用户已经下的订单
	 */
	public void showBillings(){
		List<Billing> list = billingService.queryAll();
		//是否有订单
		if (list.size() != 0) {
			Iterator<Billing> it = list.iterator();
			while (it.hasNext()) {
				Billing billing = (Billing) it.next();
				System.out.println(billing.toString());

			} 
		}else {
			System.out.println("暂无订单。");
		}
	}
	
	
}
