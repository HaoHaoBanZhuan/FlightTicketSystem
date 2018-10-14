package com.wbhz.flightsys.Dao.RowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import com.wbhz.flightsys.component.util.DateUtil;
import com.wbhz.flightsys.entity.Flight;
import com.wbhz.flightsys.jdbc.RowMapper;

public class FlightRowMappers implements RowMapper<Flight> {

	@Override
	public Flight mapperObject(ResultSet rs) throws SQLException, ParseException {
		Flight flight = new Flight();
		flight.setFlightNumber(rs.getString("t_flightnumber"));
		flight.setTakeOffTime(DateUtil.date2Str(rs.getTimestamp("t_takeofftime"),  "yyyy-MM-dd HH:mm:ss"));
		flight.setFlyingTime(rs.getString("t_flyingtime"));
		flight.setStartPlace(rs.getString("t_startplace"));
		flight.setEndPlace(rs.getString("t_endplace"));
		flight.setTicketmax(rs.getInt("t_ticketmax"));
		flight.setTicketLeft(rs.getInt("t_ticketleft"));
		flight.setPrice(rs.getDouble("t_price"));
		return flight;
	}

}
