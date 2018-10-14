package com.wbhz.flightsys.Dao.RowMappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import com.wbhz.flightsys.entity.User;
import com.wbhz.flightsys.jdbc.RowMapper;

public class UserRowMappers implements RowMapper<User> {

	@Override
	public User mapperObject(ResultSet rs) throws SQLException, ParseException {
		User user = new User();
		user.setUserID(rs.getString("t_userid"));
		user.setUserName(rs.getString("t_username"));
		user.setName(rs.getString("t_name"));
		user.setPwd(rs.getString("t_pwd"));
		user.setConfirmPwd(rs.getString("t_confirmpwd"));
		user.setSex(rs.getString("t_sex"));
		user.setAddress(rs.getString("t_address"));
		user.setPhoneNumber(rs.getString("t_phonenumber"));
		user.setLoginTimes(rs.getInt("t_logintimes"));
		user.setLocktime(rs.getString("t_locktime"));
		user.setLoginDate(rs.getString("t_logindate"));
		return user;
	}

}
