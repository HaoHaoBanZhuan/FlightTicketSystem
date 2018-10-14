package com.wbhz.flightsys.Dao.RowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import com.wbhz.flightsys.entity.Billing;
import com.wbhz.flightsys.jdbc.RowMapper;

public class BillingRowMapper implements RowMapper<Billing> {

	@Override
	public Billing mapperObject(ResultSet rs) throws SQLException, ParseException {
		Billing billing = new Billing();
		billing.setOrderNumber(rs.getInt("t_ordernumber"));
		billing.setFlightNumber(rs.getString("t_flightnumber"));
		billing .setTakeOffTime(rs.getString("t_takeofftime"));
		billing.setStartPlace(rs.getString("t_startplace"));
		billing.setEndPlace(rs.getString("t_endplace"));
		billing.setPrice(rs.getDouble("t_price"));
		billing.setName(rs.getString("t_name"));
		billing.setUserName(rs.getString("t_username"));
		billing.setUserID(rs.getString("t_userID"));
		return billing;
	}

}
