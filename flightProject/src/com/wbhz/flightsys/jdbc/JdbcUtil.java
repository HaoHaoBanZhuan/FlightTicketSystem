package com.wbhz.flightsys.jdbc;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * JDBC工具类
 * @author Mr.t
 *
 */
public class JdbcUtil {
	//每个线程都有个自己的Connection
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	private static DataSource ds ;
	
	static{
		Properties properties = new Properties();
		try {
			properties.load(JdbcUtil.class.getClassLoader().getResourceAsStream("resource/db.properties"));
			ds = BasicDataSourceFactory.createDataSource(properties);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 获取连接
	 * @return
	 */
	public static Connection  getConnection(){
		
		//问，你手上有吗
		Connection conn = null;
		conn = threadLocal.get();
		if(null == conn){//手上没有
			//拿一个Connection
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//放在手上
			threadLocal.set(conn);
		}
		return conn;
	}
	
	/**
	 * 关闭prepared statement和resultset
	 * @param pstmt
	 * @param rs
	 */
	public static void close(PreparedStatement pstmt,ResultSet rs){
		if(null != rs){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(null != pstmt){
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭connection
	 */
	public static void close(){
		Connection conn = threadLocal.get();
		if(null != conn){//手上有一个conn
			threadLocal.remove();
			try {
				conn.close();//归还
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

}
