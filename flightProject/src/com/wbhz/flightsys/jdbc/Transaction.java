package com.wbhz.flightsys.jdbc;
/**
 * 事物：开启、提交、回滚
 * @author Mr.t
 *
 */
public interface Transaction {
	/**
	 * 开启事物
	 */
	public abstract void begin(); 
	/**
	 * 提交
	 */
	public abstract void commit(); 
	/**
	 * 回滚
	 */
	public abstract void rollBack(); 

}
