package com.wbhz.flightsys.entity;

public class Admin {
	//身份证号
	private String id ;
	//用户名
	private String userName ;
	//密码
	private String pwd ;
	//真实姓名
	private String name ;
	//手机号
	private String phoneNunber ;
	//登陆次数
	private int logintimes ;
	//锁定时间
	private String lockTime ;
	
	
	public Admin(String userName, String pwd) {
		super();
		this.userName = userName;
		this.pwd = pwd;
	}
	public Admin(String userName, int logintimes, String lockTime) {
		super();
		this.userName = userName;
		this.logintimes = logintimes;
		this.lockTime = lockTime;
	}
	public Admin() {
		super();
	}
	public Admin(String userName) {
		super();
		this.userName = userName;
	}
	public int getLogintimes() {
		return logintimes;
	}
	public void setLogintimes(int logintimes) {
		this.logintimes = logintimes;
	}
	public String getLockTime() {
		return lockTime;
	}
	public void setLockTime(String lockTime) {
		this.lockTime = lockTime;
	}
	/*
	 * setter  getter 方法
	 */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNunber() {
		return phoneNunber;
	}
	public void setPhoneNunber(String phoneNunber) {
		this.phoneNunber = phoneNunber;
	}
	
	/**
	 * 重写toString方法
	 */
	@Override
	public String toString() {
		return "管理员名："+"["+userName+"]"+"姓名："+"["+name+"]"+"手机号："+"["+phoneNunber+"]"+"账户冻结至："+"["+lockTime+"]";
	}
	
	

}
