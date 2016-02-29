package com.msp.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Main dashboard
 */
@WebServlet( "/Dashboard" )
public class Dashboard extends MspServlet
{
	private static final long serialVersionUID = 1L;

	public Dashboard()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		response.setContentType( "text/html" );
		PrintWriter out = response.getWriter();
		
		//Check for session sign in
		checkSignIn( request, response );
		
		//Redirect to dashboard.html
		request.getRequestDispatcher( "dashboard.html" ).include( request, response );
		out.close();
	}
}
