package com.car.userparking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/checkOut")
public class CheckOut extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public CheckOut() {
		super();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext sc = getServletContext();
		PrintWriter pw = resp.getWriter();
		Connection con = (Connection) sc.getAttribute("oracle");
		UserDetails ud = (UserDetails) sc.getAttribute("userdetails");
		long mobno = ud.getMobile();
		long vn = Long.parseLong(req.getParameter("vechileNumber"));

		LocalTime now = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		String parkOutTime = now.format(formatter);

		try {
			String query = "select result from parkedVehicle where vehichleNumber=? and result=? and mobileNo=?";
			PreparedStatement psmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			psmt.setLong(1, vn);
			psmt.setString(2, "parked");
			psmt.setLong(3, mobno);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				rs.updateString("result", parkOutTime);
				rs.updateRow();
				pw.println("<!DOCTYPE html>");
				pw.println("<html>");
				pw.println("<head>");
				pw.println("<meta charset=\"UTF-8\">");
				pw.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
				pw.println("<title>Parking Success</title>");
				pw.println("</head>");
				pw.println(
						"<body style=\"background-image: url(images/opa1.png);background-size:cover;background-repeat: no-repeat;\" >");
				pw.println(
						"<div style=\"margin:200px 150px; background-color:pink; border:5px solid black; border-radius:10px; height:150px;width:1200px; text-align:center;\">");
				pw.println("<h2>YOU CAN TAKE YOUR VEHICLE. THANKS FOR USING OUR PARKING SERVICE.</h2>");
				pw.println("<a href='Login.html'>CLICK HERE TO LOG-OUT</a>");
				pw.println("</div>");
				pw.println("</body>");
				pw.println("</html>");
			} else {
				pw.println("<html><body><h3 text=red><center>VEHICLE IS NOT FOUND..</center></h3></body></html>");
				RequestDispatcher rd = sc.getRequestDispatcher("/VechileCheckOur.html");
				rd.include(req, resp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
