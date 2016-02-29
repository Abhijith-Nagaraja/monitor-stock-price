package com.msp.servlets;

import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;

/**
 * This gives the Historical view of a stock
 */
@WebServlet( "/details/history" )
public class History extends MspServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		String symbol = request.getParameter( "symbol" );

		//Get the historical data
		Stock stock = YahooFinance.get( symbol, true );
		List<HistoricalQuote> quotes = stock.getHistory();

		//Build json
		JSONArray results = new JSONArray();
		for ( HistoricalQuote quote : quotes )
		{
			System.out.println( quote.toString() );
			JSONArray result = new JSONArray();
			result.put( quote.getDate().getTime().getTime() );
			result.put( quote.getClose().floatValue() );
			results.put( result );
		}
		response.setContentType( "application/json" );
		response.getWriter().append( results.toString() );
	}

}
