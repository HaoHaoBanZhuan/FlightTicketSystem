package com.wbhz.flightsys.Service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import com.wbhz.flightsys.Dao.BillingDao;
import com.wbhz.flightsys.Dao.FlightDao;
import com.wbhz.flightsys.Exception.FlightSysException;
import com.wbhz.flightsys.Service.BillingService;
import com.wbhz.flightsys.component.constant.UserSysConstant;
import com.wbhz.flightsys.component.util.DateUtil;
import com.wbhz.flightsys.entity.Billing;
import com.wbhz.flightsys.entity.Flight;
import com.wbhz.flightsys.jdbc.Transaction;

public class BillingServiceImpl implements BillingService {
	private BillingDao billingDao;
	private FlightDao flightDao;
	private Transaction tx;

	public void setTx(Transaction tx) {
		this.tx = tx;
	}

	public void setFlightDao(FlightDao flightDao) {
		this.flightDao = flightDao;
	}

	public void setBillingDao(BillingDao billingDao) {
		this.billingDao = billingDao;
	}

	/**
	 * 订票
	 * 
	 * @throws FlightSysException
	 */
	@Override
	public void add(Billing billing, String flightNumber) throws FlightSysException {
		Flight flight = flightDao.getByFlightNumber(flightNumber);
		if (!(flight.getTicketLeft() > 0)) {
			throw new FlightSysException("余票不足");
		} else if (DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss").compareTo(flight.getTakeOffTime()) > 0) {
			throw new FlightSysException("航班已出发，无法预订");
		} else {
			tx.begin();
			billingDao.insert(billing);
			flightDao.sellFlightTicketNumber(flightNumber);
			tx.commit();
		}
	}

	/**
	 * 查询所有订单
	 */
	@Override
	public List<Billing> queryAll() {
		List<Billing> list = new ArrayList<Billing>();
		List<Billing> resultList = new ArrayList<Billing>();
		list = billingDao.queryAll();
		Iterator<Billing> it = list.iterator();
		while (it.hasNext()) {
			Billing billing = (Billing) it.next();
			if (billing.getUserName().equals(UserSysConstant.userName)) {
				resultList.add(billing);
			}
		}
		return resultList;
	}
	

	/**
	 * 订单号查询订单
	 * 
	 * @param orderName
	 * @return
	 */
	@Override
	public Billing getBillingInfromtionByOrderNumber(int orderNumber) {
		Billing billing = billingDao.getBillingByOrderNumber(orderNumber);
		return billing;
	}

	/**
	 * 删除订单
	 * 
	 * @throws FlightSysException
	 */
	@SuppressWarnings("static-access")
	@Override
	public void delete(int orderNumber) throws FlightSysException {
		String flightNumber = billingDao.getBillingByOrderNumber(orderNumber).getFlightNumber();
		Flight flight = flightDao.getByFlightNumber(flightNumber);
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.HOUR, 2);
		date = calendar.getTime();
		if (DateUtil.date2Str(date, "yyyy-MM-dd HH:mm:ss").compareTo(flight.getTakeOffTime()) < 0) {
			tx.begin();
			billingDao.deleteByOrderNumber(orderNumber);
			flightDao.returnFlightTicketNumber(flightNumber);
			tx.commit();
		} else {
			throw new FlightSysException("起飞前两小时不能退票");
		}

	}

}
