package com.wbhz.flightsys.Service;

import java.util.List;

import com.wbhz.flightsys.Exception.FlightSysException;
import com.wbhz.flightsys.entity.Flight;

public interface FlightAdminService {
	/**
	 * 添加航班
	 * @param flight
	 * @throws FlightSysException 
	 */
	public abstract void add(Flight flight) throws FlightSysException;
	public abstract void deleteByFlightNumber(String flightNumber) throws FlightSysException ;
	public abstract List<Flight> getBycondition(Flight flight) throws FlightSysException ;
	public abstract Flight getByFlightNumber(String flightNumber) throws Exception ;
	public abstract void updata(Flight flight , String flightNumber) ;
	

}
