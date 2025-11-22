package com.soft.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class Register extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
		int id = Integer.parseInt(request.getParameter("userId"));
		String name = request.getParameter("userName");
		String email = request.getParameter("userEmail");
		String address = request.getParameter("userAddress");
		String pass = request.getParameter("userPass");
		
		
		try {
			Connection con = DBConnect.getMyConnection();
			
			PreparedStatement ps = con.prepareStatement("INSERT INTO register_details VALUES(?,?,?,?,?)");
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setString(3, email);
			ps.setString(4, address);
			ps.setString(5, pass);
			
			int result = ps.executeUpdate();
			
			if(result > 0) {
			
				out.print("<h3>User Register Successfully</h3>");

				RequestDispatcher rd = request.getRequestDispatcher("/login.html");
				rd.include(request, response);
			
			}else {
				out.print("<h3>Email id and Password didn't matched</h3>");
				
				RequestDispatcher rd = request.getRequestDispatcher("/userDashboard.jsp");
				rd.include(request, response);
				
			}
			
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		
			out.print("<h3>Exception Occured:"+ e.getMessage()+"</h3>");
			RequestDispatcher rd = request.getRequestDispatcher("/userDashboard.jsp");
			rd.include(request, response);
		}
 	}
}

