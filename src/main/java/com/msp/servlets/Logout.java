package com.msp.servlets;

import java.io.IOException;
import java.sql.Connection;
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
 * Servlet implementation class Logout
 */
@WebServlet( "/logout" )
public class Logout extends MspServlet implements Audit
{
	private static final long serialVersionUID = 1L;

	public Logout()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		//Invalidate session and redirect
		saveActionInfo( ( int ) request.getSession().getAttribute( "uid" ), "Logout" );
		request.getSession().invalidate();
		response.sendRedirect( "index.html" );
	}

	@Override
	public void saveActionInfo( int userId, String data )
	{
		Connection conn = getDatabaseConnection();
		try
		{
			Statement st = conn.createStatement();
			String query = "insert into audit (userId, action, result) values (" + userId + ", 'Logout','" + data + "')";
			st.executeUpdate( query );
			conn.close();
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}
	}

}
