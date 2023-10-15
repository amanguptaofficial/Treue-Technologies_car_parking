package com.car.ConnectionListner;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ConnectionListner implements ServletContextListener {

	Connection con = null;

	public void contextInitialized(ServletContextEvent sce) {
		try {
			// load the driver class::
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "akashgupta", "akashgupta");
			ServletContext sc = sce.getServletContext();
			sc.setAttribute("oracle", con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
