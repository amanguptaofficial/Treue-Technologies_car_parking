package com.car.userparking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/park")
public class Park extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = null;
		ServletContext sc = null;
		PreparedStatement psmt = null;
		// set the content type::
		resp.setContentType("text/html");
		// get the writer object::
		pw = resp.getWriter();
		// get the values from request object:::
		long vehicleNumber = Long.parseLong(req.getParameter("vehicleNumber"));
		String vehicleType = req.getParameter("vt");
		sc = getServletContext();
		UserDetails ud = (UserDetails) sc.getAttribute("userdetails");
		String name = ud.getFname() + " " + ud.getLname();
		long mobileNum = ud.getMobile();

		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String parkTime = currentDateTime.format(timeFormatter);
		String parkDate = currentDateTime.format(dateFormatter);
		try {
			Connection con = (Connection) sc.getAttribute("oracle");
			// query::
			String park_query = "insert into parkedVehicle values(?,?,?,?,?,?,?)";
			psmt = con.prepareStatement(park_query);
			psmt.setString(1, name);
			psmt.setLong(2, mobileNum);
			psmt.setLong(3, vehicleNumber);
			psmt.setString(4, parkTime);
			psmt.setString(5, "parked");
			psmt.setString(6, parkDate);
			psmt.setString(7, vehicleType);

			psmt.execute();
			String successMessage = "Your vehicle parked successfully. Thanks for using our parking system.";

			pw.println("<!DOCTYPE html>");
			pw.println("<html>");
			pw.println("<head>");
			pw.println("<meta charset=\"UTF-8\">");
			pw.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
			pw.println("<tittle>Parking Success</tittle>");
			pw.println("</head>");
			pw.println(
					"<body style=\"background-image: url(images/opa1.png);background-size:cover;background-repeat: no-repeat;\\\">\"");
			pw.println(
					"<div style=\"margin:200px 150px; background-color:pink; border:5px solid black; border-radius:10px; height:150px;width:1200px; text-align:center;\\\">\"");
			pw.println("<h2>" + successMessage + "</h2>");
			pw.println("<a href='Login.html'>CLICK HERE TO LOG OUT</a>");
			pw.println("</div>");
			pw.println("</body>");
			pw.println("</html>");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
