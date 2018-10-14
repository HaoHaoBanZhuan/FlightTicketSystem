package com.wbhz.flightsys.entity;

public class User {
	//身份证
	private String userID ;
	//姓名
	private String name ;
	//用户名
	private String userName ;
	//密码
	private String pwd ;
	//支付密码
	private String confirmPwd ;
	//性别
	private String sex ;
	//手机号
	private String phoneNumber ;
	//家庭住址
	private String address ;
	//登陆次数
	private int loginTimes ;
	//登陆日期
	private String loginDate ;
	//锁定时间
	private String locktime ;
	/**
	 * 无参构造
	 */
	public User() {
		super();
	}
	
	/**
	 * 用户名构造器
	 * @param userName
	 */
	public User(String userName) {
		super();
		this.userName = userName;
	}
	
	/**
	 * 用户名、登录时间、锁定时间构造器
	 * @param userName
	 * @param loginTimes
	 * @param locktime
	 */
	public User(String userName, int loginTimes, String locktime) {
		super();
		this.userName = userName;
		this.loginTimes = loginTimes;
		this.locktime = locktime;
	}
	/**
	 * 用户名、登陆次构造器
	 * @param userName
	 * @param loginTimes
	 */
	public User(String userName, int loginTimes) {
		super();
		this.userName = userName;
		this.loginTimes = loginTimes;
	}
	/**
	 * 用户名、锁定时间构造器
	 * @param userName
	 * @param locktime
	 */
	public User(String userName, String locktime) {
		super();
		this.userName = userName;
		this.locktime = locktime;
	}
	
	
	//setter getter 方法
	public String getConfirmPwd() {
		return confirmPwd;
	}
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getLoginTimes() {
		return loginTimes;
	}
	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}
	public String getLocktime() {
		return locktime;
	}
	public void setLocktime(String locktime) {
		this.locktime = locktime;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	
	@Override
	public String toString() {
		return "用户名："+"["+userName+"]"+"姓名："+"["+name+"]"+"手机号："+"["+phoneNumber+"]"+"上次登陆日期："+"["+loginDate+"]"+"账户冻结至："+"["+locktime+"]";
	}
	
	

}
