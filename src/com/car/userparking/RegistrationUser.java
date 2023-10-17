package com.car.userparking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/registration")

public class RegistrationUser extends HttpServlet {

	private static final long serialVersionUID = 1L;
	PrintWriter pw = null;
	ServletContext sc = null;
	Connection con = null;
	PreparedStatement psmt = null;
	int ans = 0;

	public static final String USER_REGISTRATION = "insert into parkUser values(?,?,?,?,?,?,?,?)";

	public RegistrationUser() {
		super();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// set the content type::
			resp.setContentType("text/html");
			// get the printwriter object::
			pw = resp.getWriter();
			// create servletContext Object::
			sc = getServletContext();
			// get the values from reqobject:::
			String fname = req.getParameter("firstname");
			String lname = req.getParameter("lastname");
			String dob = req.getParameter("dob");
			String gender = req.getParameter("gender");
			String licenceNo = req.getParameter("licenceNumber");
			String mobNo = req.getParameter("mobile");
			String email = req.getParameter("email");
			String password1 = req.getParameter("password");
			String conPass = req.getParameter("conpassword");

			// convert String date to Sql date:::
			SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
			java.util.Date utildate = sdf.parse(dob);
			long ms = utildate.getTime();
			java.sql.Date sqldate = new java.sql.Date(ms);

			Connection con = (Connection) sc.getAttribute("oracle");
			psmt = con.prepareStatement(USER_REGISTRATION, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			psmt.setString(1, fname);
			psmt.setString(2, lname);
			psmt.setDate(3, sqldate);
			psmt.setString(4, gender);
			psmt.setLong(5, Long.parseLong(licenceNo));
			psmt.setLong(6, Long.parseLong(mobNo));
			psmt.setString(7, email);
			psmt.setString(8, conPass);

			
	  ans= psmt.executeUpdate();
	  resp.sendRedirect("Account_Created.html");
	} 
	  catch(SQLIntegrityConstraintViolationException ex) 
	  {
		 pw.println("<html><body><h3 text=red><center>EMAIL ID ALREADY EXITS USE ANOTHER EMAILID.</center</h3></body><html>");
	     RequestDispatcher rd= sc.getRequestDispatcher("/Register.html");
	     rd.include(req, resp);
	  }
	   catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
