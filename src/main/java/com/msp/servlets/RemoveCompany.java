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
 * This deletes the company from the user list
 */
@WebServlet( "/crud/RemoveCompany" )
public class RemoveCompany extends MspServlet implements Audit
{
	private static final long serialVersionUID = 1L;

	public RemoveCompany()
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

			//Note this is just a soft delete. Not a hard delete
			String query = " UPDATE company SET deleteFl = 1 where id=";
			int companyId = Integer.parseInt( request.getParameter( "id" ) );
			query += companyId;
			
			st.executeUpdate( query );
			
			//Audit
			int userId = ( int ) request.getSession().getAttribute( "uid" );
			saveActionInfo( userId, "Company id = "+companyId +" deleteFl=1" );
			response.getWriter().append( "success" );
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
			String query = "insert into audit (userId, action, result) values (" + userId + ", 'Remove Company','" + data + "')";
			st.executeUpdate( query );
			conn.close();
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}
	}

}
