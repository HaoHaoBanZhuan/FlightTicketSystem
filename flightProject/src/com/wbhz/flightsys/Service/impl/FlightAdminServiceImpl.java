package com.wbhz.flightsys.Service.impl;

import java.sql.SQLException;
import java.util.Iterator;
//import java.util.List;
import java.util.List;
import java.util.Scanner;

import com.wbhz.flightsys.Dao.FlightDao;
import com.wbhz.flightsys.Exception.FlightSysException;
import com.wbhz.flightsys.Service.FlightAdminService;
import com.wbhz.flightsys.entity.Flight;
import com.wbhz.flightsys.jdbc.Transaction;

/**
 * 管理员功能
 * 
 * @author Mr.t
 *
 */
public class FlightAdminServiceImpl implements FlightAdminService {
	private FlightDao flightDao;
	private Transaction tx;

	public void setFlightDao(FlightDao flightDao) {
		this.flightDao = flightDao;
	}

	public void setTx(Transaction tx) {
		this.tx = tx;
	}

	/**
	 * 添加航班
	 * 
	 * @throws FlightSysException
	 */
	@Override
	public void add(Flight flight) throws FlightSysException {
		// 航班编号、出发时间不同时重复

		// 是否存在的标记
		boolean isExist = false;
		// 查询已有的航班
		List<Flight> flightList = null;
		try {
			flightList = flightDao.getBycondition(new Flight());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// 数据库为空就不用判断了
		if (flightList.size() != 0) {
			Iterator<Flight> it = flightList.iterator();
			while (it.hasNext()) {
				Flight f = it.next();
				if (f.getFlightNumber().equalsIgnoreCase(flight.getFlightNumber())
						|| f.getTakeOffTime().equalsIgnoreCase(flight.getTakeOffTime())) {
					isExist = true;
				}
			}
			// 若存在相同，抛出异常
			if (isExist) {
				throw new FlightSysException("航班编号、出发时间不可以相同");
			}
		}
		// 航班信息正确，存入数据库
		try {
			tx.begin();
			flightDao.add(flight);
			tx.commit();
		} catch (SQLException e) {
			tx.rollBack();
			e.printStackTrace();
			throw new FlightSysException("添加失败");
		}
	}

	/**
	 * 按航班号删除航班
	 */
	@Override
	public void deleteByFlightNumber(String flightNumber) throws FlightSysException {
		// 判断飞机上是否有人
		Scanner input = new Scanner(System.in);
		boolean hasPeople = false;
		try {
			tx.begin();
			Flight flight = flightDao.getByFlightNumber(flightNumber);
			if (flight == null) {
				throw new FlightSysException("不存在此航班");
			}
			if (flight.getTicketLeft() < flight.getTicketmax()) {
				hasPeople = true;
			}
			if (hasPeople) {
				throw new FlightSysException("此航班已有乘客预定");
			} else {
				flightDao.delete(flightNumber);
				System.out.println("成功删除");
			}
			tx.commit();
		} catch (SQLException e) {
			tx.rollBack();
			e.printStackTrace();
		}
	}

	/**
	 * 多字段查找
	 * 
	 * @throws FlightSysException
	 */
	@Override
	public List<Flight> getBycondition(Flight flight) throws FlightSysException {
		List<Flight> list = null;
		try {
			list = flightDao.getBycondition(flight);
			if (list.size() == 0) {
				throw new FlightSysException("航班不存在");
			}
			Iterator<Flight> it = list.iterator();
			while (it.hasNext()) {
				Flight flight2 = (Flight) it.next();
				System.out.println(flight2.toString());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 按照航班号查找
	 */
	public Flight getByFlightNumber(String flightNumber) throws NullPointerException {
		Flight flight = null;
		try {
			flight = flightDao.getByFlightNumber(flightNumber);
			System.out.println(flight.toString());
		} catch (FlightSysException e) {
			System.out.println("找不到航班号");
			this.getByFlightNumber(flightNumber);
		}
		return flight;
	}

	/**
	 * 根据航班号修改飞行时间或者票价
	 * 
	 * @param flight
	 */
	public void updata(Flight flight, String flightNumber) {
		try {
			tx.begin();
			flightDao.updata(flight, flightNumber);
			tx.commit();
		} catch (SQLException e) {
			tx.rollBack();
			e.printStackTrace();
		}
	}

}
