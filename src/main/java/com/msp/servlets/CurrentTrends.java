package com.msp.servlets;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
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
 * This gives the current trends of the indices
 */
@WebServlet( "/details/current-trends" )
public class CurrentTrends extends MspServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CurrentTrends()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		// Indices
		String trendsIndex[] =
		{ "^GSPC", "^IXIC" };
		JSONArray result = getStockJson( trendsIndex );
		response.getWriter().append( result.toString() );
	}

	//Making it public only to show Unit test
	public JSONArray getStockJson( String[] trendsIndex ) throws IOException
	{
		//Use Yahoo Finance Api
		Map<String, Stock> results = YahooFinance.get( trendsIndex );
		
		//Build json
		JSONArray result = new JSONArray();
		for ( Entry<String, Stock> entry : results.entrySet() )
		{
			Stock value = entry.getValue();
			JSONObject res = new JSONObject();
			res.put( "name", value.getName() );
			res.put( "price", value.getQuote().getPrice() );
			res.put( "change", value.getQuote().getChange() );
			result.put( res );
		}
		return result;
	}
}
