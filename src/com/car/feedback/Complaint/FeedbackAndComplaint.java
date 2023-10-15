package com.car.feedback.Complaint;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/feedback")
public class FeedbackAndComplaint extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// set the content type::
			resp.setContentType("text/html");
			// get the printWriter object:::
			PrintWriter pw = resp.getWriter();
			// get the values from req object:::
			String name = req.getParameter("name");
			String email = req.getParameter("email");
			String subject = req.getParameter("subject");
			String message = req.getParameter("message");
			ServletContext sc = getServletContext();
			Connection con = (Connection) sc.getAttribute("oracle");
			String query = "insert into Parkingfeedback values(?,?,?,?)";
			PreparedStatement psmt = con.prepareStatement(query);
			psmt.setString(1, name);
			psmt.setString(2, email);
			psmt.setString(3, subject);
			psmt.setString(4, message);
			psmt.executeUpdate();
			pw.println(
					"<html><head><style> div{ margin:200px 150px; background-color:pink; border:5px solid black; border-radius:10px; height:150px width:1200px; text-align:center;}"
							+ "body{background-image: url(images/opa1.png);background-size:cover;background-repeat: no-repeat;}</style></head><body><div>");
			pw.print("<h1>THANKS! YOUR RESPONSE HAS BEEN SAVED SUCCESSFULLY..</h1>");
			pw.print("<p>Our team will contact you shortly..</p>");
			pw.print("<a href='HomePage.html'>HOMEPAGE</a>");
			pw.print("</div></body></html>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
