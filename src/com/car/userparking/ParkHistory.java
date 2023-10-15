package com.car.userparking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/history")
public class ParkHistory extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			PrintWriter pw = resp.getWriter();
			ServletContext sc = getServletContext();
			Connection con = (Connection) sc.getAttribute("oracle");
			UserDetails ud = (UserDetails) sc.getAttribute("userdetails");
			String fullName = ud.getFname() + " " + ud.getLname();
			String query = "select*from parkedVehicle where name=?";
			PreparedStatement psmt = con.prepareCall(query, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			psmt.setString(1, fullName);
			ResultSet rs = psmt.executeQuery();
			String upperFullName = fullName.toUpperCase();

			pw.println(
					"<html><head><style>div{margin:100px 400px; padding: 10px  30px; background-color:pink  text-align:center; border-radius:5px; width:650px;} body{background-image:url(images/opa1.png);\r\n}"
							+ "background-size:cover;\r\n"
							+ "	background-repeat: no-repeat;}h1{ marging:200px; color:black; font-family:forte;}td {\r\n"
							+ "  text-align: center;\r\n" + "}</style></head><body>");
			pw.println("<center><h1 color=red>PARKING HISTORY FOR " + upperFullName + " </h1></center>");
			pw.println("<div><table border=3px>");
			pw.println(
					"<tr><th>SR NO</th><th>DATE</th> <th>VEHICLE NO</th><th>VEHICLE TYPE</th><th>CHECK IN TIME</th><th>CHECK OUT TIME</th></tr>");
			int i = 1;
			while (rs.next()) {
				Long vn = rs.getLong(3);
				String pd = rs.getString(6);
				String vt = rs.getString(7);
				String pit = rs.getString(4);
				String pot = rs.getString(5);

				pw.println("<tr><td>" + i + "</td><td>" + pd + "</td><td>" + vn + "</td><td>" + vt + "</td><td>" + pit
						+ "</td><td>" + pot + "</td></tr>");

				i++;
			}
			pw.println("<table>");
			pw.println("<center><a href='Login.html'>CLICK HERE TO LOG-OUT</a></center></div>");
			pw.println("</body></html>");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
