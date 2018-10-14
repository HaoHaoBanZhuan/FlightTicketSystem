package com.wbhz.flightsys.entity;


public class Flight {
	//航班号
	private String flightNumber ;
	//起飞时间
	private String takeOffTime ;
	//飞行时间 
	private String flyingTime ;
	//出发地
	private String startPlace ;
	//目的地
	private String endPlace ;
	//总票数
	private int ticketmax ;
	//剩余票量
	private int ticketLeft ;
	//票价
	private double price ;
	
	/*
	 * setter getter方法
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getTakeOffTime() {
		return takeOffTime;
	}
	public void setTakeOffTime(String takeOffTime) {
		this.takeOffTime = takeOffTime;
	}
	public String getFlyingTime() {
		return flyingTime;
	}
	public void setFlyingTime(String flyingTime) {
		this.flyingTime = flyingTime;
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
	public int getTicketLeft() {
		return ticketLeft;
	}
	public void setTicketLeft(int ticketLeft) {
		this.ticketLeft = ticketLeft;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getTicketmax() {
		return ticketmax;
	}
	public void setTicketmax(int ticketmax) {
		this.ticketmax = ticketmax;
	}


	public Flight() {
		super();
	}
	public Flight(String flightNumber) {
		super();
		this.flightNumber = flightNumber;
	}
	
	
	/**
	 * toString方法
	 */
	@Override
	public String toString() {
		return "航班号："+"【"+flightNumber+"】"+" 起飞时间:"+"【"+takeOffTime+"】"+" 飞行时间:"+"【"+flyingTime+"】"+" 出发地:"+"【"+startPlace+"】"+" 目的地:"+"【"+endPlace+"】"+" 总票数:"+"【"+ticketmax+"】"+" 剩余票数:"+"【"+ticketLeft+"】"+" 票价:"+"【"+price+"】" ;
	}
	
	

}
