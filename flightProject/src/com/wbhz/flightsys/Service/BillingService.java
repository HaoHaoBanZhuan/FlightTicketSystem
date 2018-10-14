package com.wbhz.flightsys.Service;

import java.util.List;

import com.wbhz.flightsys.Exception.FlightSysException;
import com.wbhz.flightsys.entity.Billing;

public interface BillingService {
	public abstract void add(Billing billing, String flightNumber) throws FlightSysException;
	public abstract void delete(int orderNumber) throws FlightSysException;
	public abstract List<Billing> queryAll();
	public abstract Billing getBillingInfromtionByOrderNumber(int flightNumber);
	
	

}
