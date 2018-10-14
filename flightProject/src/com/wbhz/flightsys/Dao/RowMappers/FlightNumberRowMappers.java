package com.wbhz.flightsys.Dao.RowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import com.wbhz.flightsys.entity.FlightNumber;
import com.wbhz.flightsys.jdbc.RowMapper;

public class FlightNumberRowMappers implements RowMapper<FlightNumber> {

	@Override
	public FlightNumber mapperObject(ResultSet rs) throws SQLException, ParseException {
		FlightNumber flightNumber = new FlightNumber();
		flightNumber.setFlightNumber(rs.getInt("t_ordernumber"));
		return flightNumber;
	}

}
