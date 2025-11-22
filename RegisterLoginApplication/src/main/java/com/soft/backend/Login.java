package com.soft.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class Login extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 response.setContentType("text/html");
		
		String userEmail = request.getParameter("email");
		
		String userPass = request.getParameter("pass");
		
		PrintWriter out = response.getWriter();
		
		try {
			
			Connection con = DBConnect.getMyConnection();
			
			PreparedStatement ps = con.prepareStatement("Select * from register_details where userEmail=? and userPass=?");
			ps.setString(1, userEmail);
			ps.setString(2, userPass);
	
			ResultSet rs = ps.executeQuery();
		
			 if(rs.next()) {
				 HttpSession session = request.getSession();
				 session.setAttribute("Session_Name", rs.getString("userName"));
				 
				 out.print("<h1 style='color:green'>User Login Successfully..!</h1>");
				 
				 RequestDispatcher rd = request.getRequestDispatcher("/response.jsp");
				 rd.include(request, response);
			 }else {
				 out.print("<h1 style='color:red'>EmailId and password didn't matched...!</h1>");
				 RequestDispatcher rd = request.getRequestDispatcher("/userDashboard.jsp");
				 rd.include(request, response);
			 }
			  
			
			con.close();
			System.out.println("Connection close successfully...!");
		} catch (Exception e) {
			e.printStackTrace();
			out.print("<h1 styel='color:red'> Exception Occured: " +e.getMessage() +"</h1>");
			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
			rd.include(request, response);
		}
	
	}
	
}
