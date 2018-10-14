package com.wbhz.flightsys.Dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wbhz.flightsys.Dao.UserDao;
import com.wbhz.flightsys.Dao.RowMappers.UserRowMappers;
import com.wbhz.flightsys.entity.User;
import com.wbhz.flightsys.jdbc.JdbcTemplate;

public class UserDaoImpl implements UserDao {

	/**
	 * 用户注册
	 */
	@Override
	public void addUser(User user) {
		List<Object> list = new ArrayList<Object>();
		String sql = "insert into t_user(t_userid,t_name,t_username,t_pwd,t_confirmpwd ,t_sex,t_phonenumber,t_address,t_logintimes,t_logindate,t_locktime) values(?,?,?,?,?,?,?,?,3,?,?)";
		list.add(user.getUserID());
		list.add(user.getName());
		list.add(user.getUserName());
		list.add(user.getPwd());
		list.add(user.getConfirmPwd());
		list.add(user.getSex());
		list.add(user.getPhoneNumber());
		list.add(user.getAddress());
		list.add("1970-01-01");
		list.add("1970-01-01 00:00:00");
		try {
			JdbcTemplate.insert(sql, list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改用户信息
	 */
	@Override
	public void updataUserInformation(User user) {
		List<Object> list = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("t_username = ").append("'").append(user.getUserName()).append("'");
		if (null != user.getPwd()) {
			sb.append(" , t_pwd= ?");
			list.add(user.getPwd());
		}
		if (null != user.getPhoneNumber()) {
			sb.append(" , t_phonenumber= ?");
			list.add(user.getPhoneNumber());
		}
		if (null != user.getAddress()) {
			sb.append(" , t_address= ?");
			list.add(user.getAddress());
		}
		if (null != Integer.valueOf(user.getLoginTimes())) {
			sb.append(" , t_logintimes= ?");
			list.add(user.getLoginTimes());
		}
		if (null != user.getLocktime()) {
			sb.append(" , t_locktime= ?");
			list.add(user.getLocktime());
		}
		if (null != user.getLoginDate()) {
			sb.append(" , t_logindate= ?");
			list.add(user.getLoginDate());
		}

		String sql = new StringBuffer().append("update t_user set ").append(sb.toString()).append(" where t_username = ")
				.append("'").append(user.getUserName()).append("'").toString();
		try {
			JdbcTemplate.update(sql, list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询所有用户信息
	 */
	@Override
	public List<User> queryAll() {
		String sql = "select t_userid,t_name,t_username,t_pwd,t_confirmpwd ,t_sex,t_phonenumber,t_address,t_logintimes,t_logindate,t_locktime  from t_user ";
		List<User> list = null;
		try {
			list = JdbcTemplate.selectList(sql, new UserRowMappers(), null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public User getByCondition(User user) {
		List<Object> list =new ArrayList<Object>();
		String sql ="select t_userid,t_name,t_username,t_pwd,t_confirmpwd ,t_sex,t_phonenumber,t_address,t_logintimes,t_logindate,t_locktime from t_user where t_username = ? ";
		if (null != user.getUserName() ) {
			list.add(user.getUserName());
		}
		User result = null ;
		try {
			result = JdbcTemplate.selectOne(sql, new UserRowMappers(), list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  result ;
	}
	
	

}
