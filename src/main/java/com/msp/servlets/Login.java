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
import javax.servlet.http.HttpSession;

import com.msp.misc.Audit;

/**
 * Login
 */
@WebServlet( "/Login" )
public class Login extends MspServlet implements Audit
{
	private static final long serialVersionUID = 1L;

	public Login()
	{
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		Connection conn = getDatabaseConnection();
		String query = "select id, email, password from users where email='" + request.getParameter( "email" ) + "' and password = '" + request.getParameter( "password" ) + "'";
		try
		{
			Statement st = conn.createStatement();
			//Perform email and password match
			ResultSet rs = st.executeQuery( query );

			//Save information in session
			if ( rs.next() )
			{
				int id = rs.getInt( "id" );
				HttpSession session = request.getSession();
				session.setAttribute( "uid", id );
				response.getWriter().append( "success" );
				//Audit
				saveActionInfo( id, "login" );
			}
			else
			{
				response.getWriter().append( "User does not exist or wrong username and password" );
			}
			conn.close();
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
			response.getWriter().append( "Fail" );
		}
	}

	@Override
	public void saveActionInfo( int userId, String data )
	{
		Connection conn = getDatabaseConnection();
		try
		{
			Statement st = conn.createStatement();
			String query = "insert into audit (userId, action, result) values (" + userId + ", 'Login','" + data + "')";
			st.executeUpdate( query );
			conn.close();
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}
		
	}
}
