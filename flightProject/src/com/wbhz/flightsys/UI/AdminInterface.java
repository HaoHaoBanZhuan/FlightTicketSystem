package com.wbhz.flightsys.UI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.wbhz.flightsys.Exception.FlightSysException;
import com.wbhz.flightsys.Exception.UserSysException;
import com.wbhz.flightsys.Factory.ObjectFactory;
import com.wbhz.flightsys.Service.AdminService;
import com.wbhz.flightsys.Service.FlightAdminService;
import com.wbhz.flightsys.Service.UserService;
import com.wbhz.flightsys.component.constant.UserSysConstant;
import com.wbhz.flightsys.component.util.DateUtil;
import com.wbhz.flightsys.entity.Admin;
import com.wbhz.flightsys.entity.Flight;
import com.wbhz.flightsys.entity.User;

/**
 * 管理员的功能
 * 
 * @author Mr.t
 *
 */
public class AdminInterface {
	private FlightAdminService flightAdminService = (FlightAdminService) ObjectFactory.getObject("flightAdminService");
	private AdminService adminService = (AdminService) ObjectFactory.getObject("adminService");
	private UserService userService = (UserService) ObjectFactory.getObject("userService");
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 管理员登录功能
	 */
	@SuppressWarnings("resource")
	public Admin login() {
		System.out.println("****************登录界面*****************");
		Scanner input = new Scanner(System.in);
		Admin admin = new Admin();
		System.out.print("请输入您的账号：");
		String userName = input.next();
		admin.setUserName(userName);
		System.out.print("请输入此账号的密码：");
		String pwd = input.next();
		admin.setPwd(pwd);
		// 判断用户是否存在
		Admin result = adminService.login(admin);
		UserSysConstant.userName = userName;
		UserSysConstant.pwd = pwd;
		if (result != null) {
			return result;
		} else {
			return null;
		}

	}

	/**
	 * 添加航班功能
	 */
	@SuppressWarnings("resource")
	public void add() {
		Scanner input = new Scanner(System.in);
		try {
			Flight flight = new Flight();
			BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
			//航班号
			while (true) {
				System.out.print("请输入航班号：");
				String flightNumber = input.next();
				if (flightNumber.length() <= 10) {
					flight.setFlightNumber(flightNumber);
					break;
				} else {
					System.out.println("航班号长度必须小于10");
				}
			}
			//起飞时间
			while (true) {
				System.out.print("请输入起飞时间：");
				String takeOffTime = read.readLine();
				StringBuffer sb = new StringBuffer();
				sb.append(
						"^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|")
						.append("([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]")
						.append("?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[135")
						.append("78])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])")
						.append("|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|(")
						.append("[2][0-4]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
				if (takeOffTime.matches(sb.toString()) && takeOffTime.compareTo(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss")) > 0) {
					flight.setTakeOffTime(takeOffTime);
					break;
				} else {
					System.out.println("时间格式错误 例如1970-01-01 00:00:00(注意区分平、闰年2月) 且起飞时间不可早于当前时间");
				}
			}
			//飞行时间
			while (true) {
				System.out.print("请输入飞行时间（分钟）：");
				String flyingTime = input.next();
				if (flyingTime.length() <= 20) {
					flight.setFlyingTime(flyingTime);
					break;
				} else {
					System.out.println("时间不能超过9999999分钟");
				}
			}
			//出发地
			while (true) {
				System.out.print("请输入出发地：");
				String startPlace = input.next();
				if (startPlace.length() <= 40) {
					flight.setStartPlace(startPlace);
					break;
				} else {
					System.out.println("地址长度必须小于40");
				}
			}
			//目的地
			while (true) {
				System.out.print("请输入目的地：");
				String endPlace = input.next();
				if (endPlace.length() <= 40) {
					flight.setEndPlace(endPlace);
					break;
				} else {
					System.out.println("地址长度必须小于40");
				}
			}
			//总票数
			System.out.print("请输入总票数量：");
			flight.setTicketmax(input.nextInt());
			//剩余票数
			while (true) {
				System.out.print("请输入剩余票量：");
				int ticketLeft = input.nextInt();
				if (ticketLeft <= flight.getTicketmax()) {
					flight.setTicketLeft(ticketLeft);
					break;
				} else {
					System.out.println("不可超出最大票量");
					continue;
				}
			}
			//票价
			System.out.print("请输入票价：");
			flight.setPrice(input.nextDouble());

			flightAdminService.add(flight);
			System.out.println("添加成功");
			// 日志记录
			String msg = "添加航班——>" + flight.toString();
			logger.debug(msg);
		} catch (FlightSysException e) {
			System.out.println(e.getMessage());
		} catch (InputMismatchException e) {
			System.out.println("输入值的类型不正确");
			this.add();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			this.add();
		}
	}

	/**
	 * 按照航班号删除航班
	 * 
	 * @throws FlightSysException
	 */
	@SuppressWarnings("resource")
	public void deleteByFlightNumber() {
		// 先把所有航班显示出来
		try {
			flightAdminService.getBycondition(new Flight());
			Scanner input = new Scanner(System.in);
			System.out.print("请输入您要删除的航班号:");
			String flightNumber = input.next();
			flightAdminService.deleteByFlightNumber(flightNumber);
			System.out.println("删除成功");
			String msg = "删除航班——>成功  航班号为：【" + flightNumber + "】";
			logger.debug(msg);

		} catch (FlightSysException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 多字段查询
	 * 
	 * @throws FlightSysException
	 */
	public void getByCondition() {
		try {
			flightAdminService.getBycondition(this.chooseFlight(new Flight()));
		} catch (FlightSysException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 按照航班号查航班
	 */
	@SuppressWarnings("resource")
	public void getByFlightNumber() {
		Scanner input = new Scanner(System.in);
		try {
			System.out.print("请输入所查询航班：");
			flightAdminService.getByFlightNumber(input.next());
		} catch (FlightSysException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("航班不存在");
		}
	}

	/**
	 * 按照航班号修改航班
	 */
	@SuppressWarnings("resource")
	public void updata() {
		Scanner input = new Scanner(System.in);
		try {
			flightAdminService.getBycondition(new Flight());
			System.out.print("请输入想要修改的航班：");
			String flightNumber = input.next();
			// 是否存在此航班
			flightAdminService.getByFlightNumber(flightNumber);
			// 若存在修改航班信息
			flightAdminService.updata(this.updateAttributeChoose(new Flight()), flightNumber);
			System.out.println("修改成功");
			//日志记录
			String msg = "修改航班信息——>成功  " + new Flight(flightNumber).toString();
			logger.debug(msg);
		} catch (FlightSysException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("航班不存在");
		}
	}

	/**
	 * 筛选航班的条件
	 * 
	 * @param flight
	 * @return
	 */
	@SuppressWarnings("resource")
	private Flight chooseFlight(Flight flight) {
		Scanner input = new Scanner(System.in);
		//是否继续添加条件
		boolean isNext = true;
		//Y/N输入是否正确
		boolean isCorrect = true;
		while (isNext) {
			System.out.print("请选择筛选内容：1、出发地 2、目的地 3、起飞时间 4、航班号 5、查看全部航班:");
			String key = input.next();
			if (key.equals("1")) {
				System.out.print("请输入筛选项的值：");
				String value = input.next();
				flight.setStartPlace(value);
			} else if (key.equals("2")) {
				System.out.print("请输入筛选项的值：");
				String value = input.next();
				flight.setEndPlace(value);
			} else if (key.equals("3")) {
				System.out.print("请输入筛选项的值：");
				String value = input.nextLine();
				flight.setTakeOffTime(value);
			} else if (key.equals("4")) {
				System.out.print("请输入筛选项的值：");
				String value = input.next();
				flight.setFlightNumber(value);
			} else if (key.equals("5")) {
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
	 * 选择需要修改的航班的属性
	 * 
	 * @param flight
	 * @return
	 */
	private Flight updateAttributeChoose(Flight flight) {
		Scanner input = new Scanner(System.in);
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		//判断是否继续修改属性
		boolean isNext = true;
		//Y/N输入是否正确
		boolean isCorrect = true;
		try {
			while (isNext) {
				System.out.print("请选择您想要修改的属性(1、起飞时间：2、票价)：");
				String key = input.next();
				if (key.equals("1")) {
					while (true) {
						System.out.print("请输入修改项的值：");
						String value = read.readLine();
						if (value.compareTo(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss")) > 0) {
							flight.setTakeOffTime(value);
							break;
						} else {
							System.out.println("修改时间不可以比当前时间早");
						}

					}
				}
				if (key.equals("2")) {
					Double value;
					while (true) {
						System.out.print("请输入修改项的值：");
						value = input.nextDouble();
						if (value >= 0) {
							flight.setPrice(value);
							break;
						} else {
							System.out.println("票价数据不正确");
						}
					}
				} else if(!(key.equals("2") || key.equals("1"))){
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
			this.updateAttributeChoose(flight);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			this.updateAttributeChoose(flight);
		}
		return flight;
	}

	/**
	 * 用户解锁
	 */
	@SuppressWarnings("resource")
	public void unlockUser() {
		Scanner input = new Scanner(System.in);
		System.out.println("所有被封名单如下");
		List<User> list = userService.queryAll();
		Iterator<User> it = list.iterator();
		List<User> userlist = new ArrayList<User>();
		while (it.hasNext()) {
			User user = (User) it.next();
			if (user.getLocktime().compareTo(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss")) > 0) {
				// 用户处于锁定状态
				System.out.println(user.toString());
				userlist.add(user);
			}
		}
		if (userlist.size() != 0) {
			System.out.print("请输入需要解封的用户名：");
			String userName = input.next();
			adminService.unlockUserCount(userName);
		} else {
			System.out.println("暂无封禁用户");
		}
	}

	/**
	 * 管理员解锁
	 */
	public void unlockAdmin() {
		Scanner input = new Scanner(System.in);
		System.out.println("被封管理员名单如下");
		List<Admin> list = adminService.querryAll();
		Iterator<Admin> it = list.iterator();
		List<Admin> adminlist = new ArrayList<Admin>();
		while (it.hasNext()) {
			Admin admin = (Admin) it.next();
			if (admin.getLockTime().compareTo(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss")) > 0) {
				// 用户处于锁定状态
				System.out.println(admin.toString());
				adminlist.add(admin);
			}
		}
		if (adminlist.size() != 0) {
			System.out.print("请输入需要解封的用户名：");
			String userName = input.next();
			adminService.unlockAdminCount(userName);
		} else {
			System.out.println("暂无封禁管理员");
		}
	}

}
