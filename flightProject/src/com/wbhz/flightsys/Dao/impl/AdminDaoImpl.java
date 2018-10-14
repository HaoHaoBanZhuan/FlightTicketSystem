package com.wbhz.flightsys.Dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wbhz.flightsys.Dao.AdminDao;
import com.wbhz.flightsys.Dao.RowMappers.AdminRowMappers;
import com.wbhz.flightsys.entity.Admin;
import com.wbhz.flightsys.jdbc.JdbcTemplate;

public class AdminDaoImpl implements AdminDao {

	/**
	 * 查询所有的用户
	 */
	@Override
	public List<Admin> queryAll() {
		String sql = "select t_id,t_username,t_pwd,t_name,t_phonenumber ,t_times ,t_locktime from t_admin" ;
		List<Admin> list = new ArrayList<Admin>();
		try {
			list = JdbcTemplate.selectList(sql, new AdminRowMappers(), null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list ;
	}

	/**
	 * 通过用户名获得信息
	 */
	@Override
	public Admin getAdminInformationByUserName(String userName) {
		String sql = "select t_id,t_username,t_pwd,t_name,t_phonenumber ,t_times ,t_locktime from t_admin where t_username = ?" ;
		List<Object> list = new ArrayList<Object>();
		list.add(userName);
		Admin admin = null ;
		try {
			admin = JdbcTemplate.selectOne(sql, new AdminRowMappers(), list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admin;
	}

	
	/**
	 * 修改管理员的信息
	 */
	@Override
	public void upadate(Admin admin) {
		List<Object> list = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("t_username = ").append("'").append(admin.getUserName()).append("'");
		if (null != admin.getPwd()) {
			sb.append(" , t_pwd= ?");
			list.add(admin.getPwd());
		}
		if (null != Integer.valueOf(admin.getLogintimes())) {
			sb.append(" , t_times= ?");
			list.add(admin.getLogintimes());
		}
		if (null != admin.getLockTime()) {
			sb.append(" , t_locktime= ?");
			list.add(admin.getLockTime());
		}
		String sql = new StringBuffer().append("update t_admin set ").append(sb.toString()).append(" where t_username = ")
				.append("'").append(admin.getUserName()).append("'").toString();
		try {
			JdbcTemplate.update(sql, list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

}
