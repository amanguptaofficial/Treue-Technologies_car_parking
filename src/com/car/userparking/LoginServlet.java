package com.car.userparking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	PrintWriter pw = null;
	ServletContext sc = null;
	PreparedStatement psmt = null;
	Connection con = null;
	ResultSet rs = null;
	private static final String login_details = "select*from parkUser where email=? and password=?";

	public LoginServlet() {
		super();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// set the content type:::
			resp.setContentType("text/html");
			// get the printwriterObject::
			pw = resp.getWriter();
			// get the value from request object:::
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			sc = getServletContext();
			con = (Connection) sc.getAttribute("oracle");
			psmt = con.prepareStatement(login_details, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			psmt.setString(1, username);
			psmt.setString(2, password);

			rs = psmt.executeQuery();
			
				if (rs.next()) {
					UserDetails ud = new UserDetails();
					ud.setFname(rs.getString(1));
					ud.setLname(rs.getString(2));
					java.sql.Date sqldate = rs.getDate(3);
					long ms1 = sqldate.getTime();
					java.util.Date utildate = new java.util.Date(ms1);
					SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
					String stringdate = sdf.format(utildate);

					ud.setDob(stringdate);
					ud.setGender(rs.getString(4));
					ud.setLicence(rs.getLong(5));
					ud.setMobile(rs.getLong(6));
					ud.setEmail(rs.getString(7));
					ud.setPassword(rs.getString(8));

					sc.setAttribute("userdetails", ud);
					pw.println("<!DOCTYPE html>");
					pw.println("<html>");
					pw.println("<head>");
					pw.println("<tittle>Customer HomePage</tittle>");
					pw.println(
							"<style> div{margin:200px 500px; height:300px; widht:500px; border-radius:10px;border:5px solid black;background-color:pink;text-align:center; }"
									+ "body{background-image: url(images/opa1.png);background-size:cover;background-repeat: no-repeat; } a{text-decoration:none;} a:hover{color:red;font-size:30px;}</style>");
					pw.println("</head>");
					pw.println("<body><div>");
					pw.println("<h1>Welcome," + ud.getFname() + " " + ud.getLname() + "!</h1>");
					pw.println("<p>Date of Birth: " + ud.getDob() + " &nbsp&nbsp&nbsp&nbsp&nbsp          Email:"
							+ ud.getEmail() + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp       Mobile:"
							+ ud.getMobile() + "</p>");
					pw.println("<a href='park.html'>PARK-IN VEHICLE </a><br><br>");
					pw.println("<a href='VechileCheckOut.html'>CHECK-OUT VEHICLE</a><br><br>");
					pw.println("<a href='history'>WATCH PARKING HISTORY </a><br><br>");
					pw.println("<a href='login.html'> LOG OUT </a>");
					pw.println("</div></body>");
					pw.println("</html>");
				
			} else {
				pw.println("<html><body><h3 text=red><center>INVALID CREDENTIALS....</center></h3></body></html>");
				RequestDispatcher rd = sc.getRequestDispatcher("/login");
				rd.include(req, resp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
