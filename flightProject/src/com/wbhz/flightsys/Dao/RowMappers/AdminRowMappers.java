package com.wbhz.flightsys.Dao.RowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import com.wbhz.flightsys.entity.Admin;
import com.wbhz.flightsys.jdbc.RowMapper;

public class AdminRowMappers implements RowMapper<Admin> {

	@Override
	public Admin mapperObject(ResultSet rs) throws SQLException, ParseException {
		Admin admin = new Admin();
		admin.setId(rs.getString("t_id"));
		admin.setName(rs.getString("t_name"));
		admin.setUserName(rs.getString("t_username"));
		admin.setPwd(rs.getString("t_pwd"));
		admin.setPhoneNunber(rs.getString("t_phonenumber"));
		admin.setLogintimes(rs.getInt("t_times"));
		admin.setLockTime(rs.getString("t_locktime"));
		return admin;
	}

}
