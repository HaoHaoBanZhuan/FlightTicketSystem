package com.wbhz.flightsys.jdbc.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.wbhz.flightsys.jdbc.JdbcUtil;
import com.wbhz.flightsys.jdbc.Transaction;

public class TransactionImpl implements Transaction {
	

	@Override
	public void begin() {
		Connection conn = JdbcUtil.getConnection();
		try {
			//关闭自动提交
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void commit() {
		Connection conn = JdbcUtil.getConnection();
		try {
			//提交
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//关闭连接
			JdbcUtil.close();
		}

	}

	@Override
	public void rollBack() {
		Connection conn = JdbcUtil.getConnection();
		try {
			//回滚
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			//关闭连接
			JdbcUtil.close();
		}
	}

}
