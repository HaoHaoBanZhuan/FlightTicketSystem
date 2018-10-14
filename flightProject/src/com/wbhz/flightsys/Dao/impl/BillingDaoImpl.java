package com.wbhz.flightsys.Dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wbhz.flightsys.Dao.BillingDao;
import com.wbhz.flightsys.Dao.RowMappers.BillingRowMapper;
import com.wbhz.flightsys.Dao.RowMappers.FlightNumberRowMappers;
import com.wbhz.flightsys.component.constant.UserSysConstant;
import com.wbhz.flightsys.entity.Billing;
import com.wbhz.flightsys.entity.FlightNumber;
import com.wbhz.flightsys.jdbc.JdbcTemplate;

public class BillingDaoImpl implements BillingDao {

	/**
	 * 增加订单
	 */
	@Override
	public void insert(Billing billing) {
		String sql = "insert into t_billings (t_flightnumber,t_startplace,t_endplace,t_takeofftime,t_price,t_name,t_username ,t_userID) values(?,?,?,?,?,?,?,?)" ;
		List<Object> list  = new ArrayList<Object>();
		list.add(billing.getFlightNumber());
		list.add(billing.getStartPlace());
		list.add(billing.getEndPlace());
		list.add(billing.getTakeOffTime());
		list.add(billing.getPrice());
		list.add(billing.getName());
		list.add(UserSysConstant.userName);
		list.add(billing.getUserID());
		try {
			JdbcTemplate.insert(sql, list);
			sql = "select Last_insert_id() as t_ordernumber";
			FlightNumber flightNumber = JdbcTemplate.selectOne(sql, new FlightNumberRowMappers(),null);
			billing.setOrderNumber(flightNumber.getFlightNumber());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 按照订单号删除订单
	 */
	@Override
	public void deleteByOrderNumber(int orderNumber) {
		String sql = "delete from t_billings where t_ordernumber = ?";
		List<Object> list = new ArrayList<Object>() ;
		list.add(orderNumber);
		try {
			JdbcTemplate.delete(sql, list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * 查询订单
	 */
	@Override
	public List<Billing> queryAll() {
		String sql = "select t_ordernumber , t_flightnumber,t_startplace,t_endplace,t_takeofftime,t_price,t_name,t_username,t_userID from t_billings";
		List<Billing> list = new ArrayList<Billing>();
		try {
			list = JdbcTemplate.selectList(sql, new BillingRowMapper(), null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 通过订单号查找订单
	 */
	@Override
	public Billing getBillingByOrderNumber(int orderNumber) {
		Billing billing = null;
		List<Object> list = new ArrayList<Object>();
		list.add(orderNumber);
		String sql = "select t_ordernumber , t_flightnumber,t_startplace,t_endplace,t_takeofftime,t_price,t_name,t_username,t_userID from t_billings where t_ordernumber = ?";
		try {
			billing = JdbcTemplate.selectOne(sql, new BillingRowMapper(), list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return billing;
	}

}
