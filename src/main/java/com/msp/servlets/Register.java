package com.msp.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is used for signup
 */
@WebServlet( "/Register" )
public class Register extends MspServlet
{
	private static final long serialVersionUID = 1L;

	public Register()
	{
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		Connection conn = getDatabaseConnection();
		String query = "select id from users where email='" + request.getParameter( "email" )+"'";
		Statement st;
		try
		{
			st = conn.createStatement();
			ResultSet rs = st.executeQuery( query );
			//Check whether the user exists
			if(rs.next())
			{
				response.getWriter().append( "User alreaday exists. Try to login" );
			}
			query = "insert into users (email, password) values ('"+request.getParameter( "email" )+"', '" + request.getParameter( "password" ) + "')";
			st.executeUpdate( query );
			conn.close();
			response.getWriter().append("success");
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}
	}

}
