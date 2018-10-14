package com.wbhz.flightsys.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * 行记录映射器
 * @author Administrator
 *
 */
public interface RowMapper<T> {
	public T mapperObject(ResultSet rs) throws SQLException, ParseException;
}
