package com.msp.servlets;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This gives the lookup for finding the stocks in order to add
 */
@WebServlet( "/details/stocks" )
public class GetStocks extends MspServlet
{
	private static final long serialVersionUID = 1L;

	public GetStocks()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings( "unused" )
	protected void doGet( HttpServletRequest request, HttpServletResponse resp ) throws ServletException, IOException
	{
		//build url
		String url = "https://s.yimg.com/aq/autoc?query=";
		url += request.getParameter( "term[term]" );
		url += "&region=US&lang=en-US&callback=YAHOO.util.UHScriptNodeDataSource.callbacks";

		//Get the lookups
		URL obj = new URL( url );
		HttpURLConnection con = ( HttpURLConnection ) obj.openConnection();
		con.setRequestMethod( "GET" );
		int responseCode = con.getResponseCode();

		//build json
		BufferedReader in = new BufferedReader( new InputStreamReader( con.getInputStream() ) );
		String inputLine = in.readLine();
		in.close();
		inputLine = inputLine.split( "\"Result\":" )[1];
		inputLine = inputLine.substring( 0, inputLine.length() - 4 );
		JSONArray jsonA = new JSONArray( inputLine );
		resp.getWriter().append( jsonA.toString() );
	}

}
