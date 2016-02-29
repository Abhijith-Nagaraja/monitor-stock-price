package com.msp.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.msp.misc.DbModel;

/**
 * Superclass for all the servlets
 */
public abstract class MspServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public MspServlet()
	{
		super();
	}

	//Session based login
	protected void checkSignIn( HttpServletRequest request, HttpServletResponse response )
	{
		HttpSession session = request.getSession( false );
		//if login information is not saved in the session. Redirect to index.html
		if ( session == null )
		{
			try
			{
				response.sendRedirect( "index.html" );
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
		}
	}

	//Create database connection
	public Connection getDatabaseConnection()
	{
		Connection connection = null;
		try
		{
			DbModel model = getDbModel();

			Class.forName( "com.mysql.jdbc.Driver" );
			connection = DriverManager.getConnection( model.getUrl(), model.getUsername(), model.getPassword() );
		}
		catch ( ClassNotFoundException e )
		{
			System.out.println( "MySql driver not loaded" );
		}
		catch ( SQLException e )
		{
			System.out.println( "Cannot connect the database!" );
		}
		return connection;
	}

	//Get mysql login information
	private DbModel getDbModel()
	{
		DbModel model = new DbModel();
		model.setUrl( getServletContext().getInitParameter( "url" ) );
		model.setUsername( getServletContext().getInitParameter( "username" ) );
		model.setPassword( getServletContext().getInitParameter( "password" ) );
		return model;
	}
}
