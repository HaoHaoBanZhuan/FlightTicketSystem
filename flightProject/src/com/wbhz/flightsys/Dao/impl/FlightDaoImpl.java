package com.wbhz.flightsys.Dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wbhz.flightsys.Dao.FlightDao;
import com.wbhz.flightsys.Dao.RowMappers.FlightRowMappers;
import com.wbhz.flightsys.Exception.FlightSysException;
import com.wbhz.flightsys.entity.Flight;
import com.wbhz.flightsys.jdbc.JdbcTemplate;

public class FlightDaoImpl implements FlightDao {

	/**
	 * 添加新航班
	 */
	@Override
	public void add(Flight flight) throws SQLException{
		String sql = "insert into t_flight(t_flightnumber,t_takeofftime,t_flyingtime,t_startplace,t_endplace,t_ticketmax,t_ticketleft,t_price) values (?,?,?,?,?,?,?,?)";
		List<Object> list = new ArrayList<Object>();
		list.add(flight.getFlightNumber());
		list.add(flight.getTakeOffTime());
		list.add(flight.getFlyingTime());
		list.add(flight.getStartPlace());
		list.add(flight.getEndPlace());
		list.add(flight.getTicketmax());
		list.add(flight.getTicketLeft());
		list.add(flight.getPrice());
		JdbcTemplate.insert(sql, list);
		

	}

	/**
	 * 航班号删
	 */
	@Override
	public void delete(String flightNumber) throws SQLException{
		List<Object> list = new ArrayList<Object>();
		list.add(flightNumber);
		String sql = "delete from t_flight where t_flightNumber = ?";
		JdbcTemplate.delete(sql, list);
	}

	/**
	 * 修改起飞时间、修改票价、修改
	 */
	@Override
	public void updata(Flight flight ,String flightNumber) throws SQLException{
		List<Object> list = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("t_flightnumber = ").append("'").append(flightNumber).append("'");
		if (null != flight.getTakeOffTime()) {
			sb.append(" , t_takeofftime= ?");
			list.add(flight.getTakeOffTime());
		}
		if (0.0 != flight.getPrice()) {
			sb.append(" , t_price= ?");
			list.add(flight.getPrice());
		}
		String sql = new StringBuffer().append("update t_flight set ").append(sb).append(" where t_flightnumber = ").append("'").append(flightNumber).append("'").toString();
		JdbcTemplate.update(sql, list);
	}

	/**
	 * 多字段查询
	 */
	@Override
	public List<Flight> getBycondition(Flight flight) throws SQLException {
		String sql ;
		StringBuffer sb = new StringBuffer();
		sb.append("select t_flightnumber,t_takeofftime,t_flyingtime,t_startplace,t_endplace,t_ticketmax,t_ticketleft,t_price from t_flight where 1=1");
		List<Object> list = new ArrayList<Object>();
		//出发地
		if (null != flight.getStartPlace()) {
			sb.append(" and t_startplace=?");
			list.add(flight.getStartPlace());
		}
		//目的地
		if (null != flight.getEndPlace()) {
			sb.append(" and t_endplace=?");
			list.add(flight.getEndPlace());
		}
		//起飞时间
		if (null != flight.getTakeOffTime()) {
			sb.append(" and t_takeofftime=?");
			list.add(flight.getTakeOffTime());
		}
		//航班号
		if (null != flight.getFlightNumber()) {
			sb.append(" and t_flightnumber=?");
			list.add(flight.getFlightNumber());
		}
		sql = sb.toString();
		return JdbcTemplate.selectList(sql, new FlightRowMappers(), list);
	}
	

	/**
	 * 航班号查询
	 * @throws FlightSysException 
	 * @throws SQLException 
	 */
	@Override
	public Flight getByFlightNumber(String flightNumber) throws FlightSysException {
		Flight flight = null ;
		List<Object> list = new ArrayList<Object>();
		list.add(flightNumber);
		String sql = "select t_flightnumber,t_takeofftime,t_flyingtime,t_startplace,t_endplace,t_ticketmax,t_ticketleft,t_price from t_flight where t_flightnumber = ?";
		try {
			flight = JdbcTemplate.selectOne(sql, new FlightRowMappers(), list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flight ;
	}

	/**
	 * 买票
	 */
	@Override
	public void sellFlightTicketNumber(String flightNumber) {
		//先获取余票数量
		Flight flight;
		try {
			flight = this.getByFlightNumber(flightNumber);
			String sql = "update t_flight set t_ticketleft = ? where t_flightnumber = '"+flightNumber+"'" ;
			List<Object> list = new ArrayList<Object>() ;
			//卖一张票
			list.add(flight.getTicketLeft()-1);
			JdbcTemplate.update(sql, list);
		} catch (FlightSysException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 退票
	 */
	public void returnFlightTicketNumber(String flightNumber) {
		//先获取余票数量
		Flight flight;
		try {
			flight = this.getByFlightNumber(flightNumber);
			String sql = "update t_flight set t_ticketleft = ? where t_flightnumber = '"+flightNumber+"'" ;
			List<Object> list = new ArrayList<Object>() ;
			//卖一张票
			list.add(flight.getTicketLeft()+1);
			JdbcTemplate.update(sql, list);
		} catch (FlightSysException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	


}
