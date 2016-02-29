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

import com.msp.misc.Audit;

/**
 * Servlet to add a company
 */
@WebServlet( "/crud/AddCompany" )
public class AddCompany extends MspServlet implements Audit
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddCompany()
	{
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		Connection conn = getDatabaseConnection();
		try
		{
			Statement st = conn.createStatement();

			//create the query
			String query = " insert into company (userid, companySym, deleteFl) values (";
			int userId = ( int ) request.getSession().getAttribute( "uid" );
			query += userId;
			query += ", '";
			String symbol = request.getParameter( "sym" );
			query += symbol;
			query += "', 0)";

			//execute
			st.executeUpdate( query );
			response.getWriter().append( "success" );
			conn.close();

			//Save audit info
			saveActionInfo( userId, symbol );

		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}

	}

	@Override
	public void saveActionInfo( int userId, String data )
	{
		Connection conn = getDatabaseConnection();
		try
		{
			Statement st = conn.createStatement();
			String query = "insert into audit (userId, action, result) values (" + userId + ", 'Add Company','" + data + "')";
			st.executeUpdate( query );
			conn.close();
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}
	}

}
