package com.wbhz.flightsys.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnectDB {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Connection con = null ;
		try {
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project","root","root");
			System.out.println(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
