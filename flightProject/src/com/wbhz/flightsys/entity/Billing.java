package com.wbhz.flightsys.entity;

public class Billing {
	//订单号
	private int orderNumber ;
	//航班号
	private String flightNumber ;
	//出发地
	private String startPlace ;
	//目的地
	private String endPlace ;
	//起飞时间
	private String takeOffTime ;
	//票价
	private double price ;
	//乘客姓名
	private String name ;
	//下单账户名称
	private String userName ;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	//乘客身份证号
	private String userID ;
	
	
	/*
	 * setter getter方法
	 */
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getStartPlace() {
		return startPlace;
	}
	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}
	public String getEndPlace() {
		return endPlace;
	}
	public void setEndPlace(String endPlace) {
		this.endPlace = endPlace;
	}
	public String getTakeOffTime() {
		return takeOffTime;
	}
	public void setTakeOffTime(String takeOffTime) {
		this.takeOffTime = takeOffTime;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	@Override
	public String toString() {
		return "订单号:["+orderNumber+"]航班号:["+flightNumber+"]出发地:["+startPlace+"]目的地:["+endPlace+"]起飞时间:["+takeOffTime+"]票价:["+price+"]乘客姓名:["+name+"]乘客身份证号:["+userID+"]" ;
		
		
	}
	
	

}
