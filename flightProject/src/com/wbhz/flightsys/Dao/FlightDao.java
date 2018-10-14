package com.wbhz.flightsys.Dao;

import java.sql.SQLException;
import java.util.List;

import com.wbhz.flightsys.Exception.FlightSysException;
import com.wbhz.flightsys.entity.Flight;

public interface FlightDao {
	public abstract void add(Flight flight) throws SQLException;
	public abstract void delete(String flightNumber) throws SQLException;
	public abstract void updata(Flight flight , String flightNumber) throws SQLException;
	public abstract List<Flight> getBycondition(Flight flight)throws SQLException ;
	public abstract Flight getByFlightNumber(String flightNumber) throws FlightSysException;
	public abstract void sellFlightTicketNumber(String flightNumber) throws FlightSysException;
	public abstract void returnFlightTicketNumber(String flightNumber) throws FlightSysException;

}
