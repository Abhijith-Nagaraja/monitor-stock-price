package com.msp;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;

import com.msp.servlets.CurrentTrends;

public class StockJsonTest
{

	@Test
	public void test()
	{
		CurrentTrends trendsTest = new CurrentTrends();
		String trendsIndex[] =
		{ "^GSPC", "^IXIC" };
		try
		{
			assertNotNull( trendsTest.getStockJson( trendsIndex ) );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}

}
