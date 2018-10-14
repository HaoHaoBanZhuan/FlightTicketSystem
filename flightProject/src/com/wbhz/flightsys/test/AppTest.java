package com.wbhz.flightsys.test;
import java.util.Scanner;

import com.wbhz.flightsys.UI.AdminInterface;
import com.wbhz.flightsys.UI.UserInterface;
import com.wbhz.flightsys.entity.Admin;
import com.wbhz.flightsys.entity.User;
public class AppTest {
	
	public static void main(String[] args) {
		app();
	}
	
	
	/**
	 * 主界面功能
	 */
	@SuppressWarnings("resource")
	public static void app(){
		Scanner input = new Scanner(System.in);
		UserInterface UI = new UserInterface();
		AdminInterface AI = new AdminInterface();
		while (true) {
			System.out.println("************欢迎使用网博机票预订系统************");
			System.out.println("|----------------------------------------------|");
			System.out.println("|       功能            |             选项      　          |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|       注册            |              壹                     |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|       登陆            |              贰                     |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|     忘记密码         |              叁                     |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|     退出系统         |              肆                     |");
			System.out.println("|----------------------------------------------|");
			System.out.print("请输入您要选择的功能(数字)：");
			String functionChoice = input.next();
			switch (functionChoice) {
			case "1":
				UI.register();
				break;
			case "2":
				loginChoose(UI, AI);
				break;
			case "3":
				UI.findPwdBack();
				break;
			case "4":
				System.out.println("拜拜了，您嘞");
				System.exit(0);
				break;
			default:
				System.out.println("您的输入不正确！");
				continue;
			}
		}
	}

	/**
	 * 用户登陆/管理员登陆选择
	 * @param UI
	 * @param AI
	 */
	@SuppressWarnings("resource")
	public static void loginChoose(UserInterface UI, AdminInterface AI) {
		Scanner input = new Scanner(System.in);
		while (true) {
			System.out.print("请选择用户类型：1、会员 2、管理员 0、返回主界面：");
			String key = input.next();
			switch (key) {
			case "1":
				userFunction(UI,UI.login());
				break;
			case "2":
				adminFunction(AI,AI.login());
				break;
			case "0":
				app();
			default:
				System.out.println("请键入正确的选项。");
				break;
			}
		}
	}
	
	/**
	 * 用户功能界面
	 * @param UI
	 */
	@SuppressWarnings("resource")
	public static void userFunction(UserInterface UI,User user){
		Scanner input= new Scanner(System.in);
		while (user != null) {
			System.out.println("******************用户功能界面******************");
			System.out.println("|----------------------------------------------|");
			System.out.println("|       功能            |             选项      　          |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|     查询航班         |              壹                     |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|       订票            |              贰                     |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|       改签            |              叁                     |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|       退票            |              肆                     |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|    修改用户信息    |              伍                     |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|     我的订单         |              陆                     |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|     返回主界面     |              零                     |");
			System.out.println("|----------------------------------------------|");
			System.out.print("请输入您要选择的功能(数字)：");
			String functionChoice = input.next();
			switch (functionChoice) {
			case "1":
				UI.selectAvailableFlights();
				break;
			case "2":
				UI.orderTicket();
				break;
			case "3":
				UI.updateBilling();
				break;
			case "4":
				UI.returnTicket();
				break ;
			case "5":
				UI.updata();
				break ;
			case "6":
				UI.showBillings();
				break ;
			case "0":
				app();
				break;
			default:
				System.out.println("您的输入不正确！");
				continue;
			}
		}
	}
	
	/**
	 * 管理员登录界面
	 */
	@SuppressWarnings("resource")
	public static void adminFunction(AdminInterface AI,Admin admin){
		Scanner input= new Scanner(System.in);
		while (admin != null) {
			System.out.println("******************超级用户功能界面******************");
			System.out.println("|----------------------------------------------|");
			System.out.println("|       功能            |             选项      　          |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|     查询航班         |              壹                     |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|     删除航班         |              贰                     |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|     修改航班         |              叁                     |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|     添加航班         |              肆                     |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|     解封用户         |              伍                     |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|     解封管理         |              陆                     |");
			System.out.println("|----------------------------------------------|");
			System.out.println("|    返回主界面       |              零                     |");
			System.out.println("|----------------------------------------------|");
			System.out.print("请输入您要选择的功能(数字)：");
			String functionChoice = input.next();
			switch (functionChoice) {
			case "1":
				AI.getByCondition();
				break;
			case "2":
				AI.deleteByFlightNumber();
				break;
			case "3":
				AI.updata();
				break;
			case "4":
				AI.add();
				break ;
			case "5":
				AI.unlockUser();
				break ;
			case "6":
				AI.unlockAdmin();
				break ;
			case "0":
				app();
				break;
			default:
				System.out.println("您的输入不正确！");
				continue;
			}
		}
	}

}
