package com.msp.servlets;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * This returns all the companies which user added to monitor
 */
@WebServlet( "/details/CompanyDetails" )
public class CompanyDetails extends MspServlet
{
	private static final long serialVersionUID = 1L;

	public CompanyDetails()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		Connection conn = getDatabaseConnection();

		//Query
		String query = "select id, companySym from company where userid=" + request.getSession().getAttribute( "uid" ) + " and deleteFl = 0";
		Statement st;
		try
		{
			st = conn.createStatement();
			ResultSet rs = st.executeQuery( query );

			//Build Json
			ArrayList<String> list = new ArrayList<String>();
			ArrayList<Integer> ids = new ArrayList<Integer>();
			while ( rs.next() )
			{
				list.add( rs.getString( "companySym" ) );
				ids.add( rs.getInt( "id" ) );
			}
			conn.close();
			
			if ( list.isEmpty() )
			{
				response.getWriter().append( "empty" );
				return;
			}

			String syms[] = list.toArray( new String[0] );

			Map<String, Stock> results = YahooFinance.get( syms );
			JSONArray result = new JSONArray();
			int count = 1;
			for ( Entry<String, Stock> entry : results.entrySet() )
			{
				Stock value = entry.getValue();
				JSONObject res = new JSONObject();
				res.put( "index", count++ );
				res.put( "name", value.getName() );
				res.put( "price", value.getQuote().getPrice() );
				res.put( "change", value.getQuote().getChange() );
				res.put( "symbol", entry.getKey() );
				res.put( "id", ids.get( list.indexOf( entry.getKey() ) ) );
				result.put( res );
			}
			JSONObject returnJson = new JSONObject();
			returnJson.put( "result", result );
			response.getWriter().append( result.toString() );
		}
		catch ( SQLException e )
		{
			e.printStackTrace();
		}
	}
}
