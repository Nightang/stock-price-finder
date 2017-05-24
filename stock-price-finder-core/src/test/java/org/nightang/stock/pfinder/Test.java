package org.nightang.stock.pfinder;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nightang.stock.listfinder.HKEXListFinder;
import org.nightang.ws.HttpClientWrapper;

public class Test {
	
	private static final Log log = LogFactory.getLog(HttpClientWrapper.class);

	public static void main(String[] args) throws Exception {
//		HttpClientWrapper hw = new HttpClientWrapper("network.ini", null, null);
//		
//		String url = "http://chartdata1.internet.aastocks.com/servlet/iDataServlet/getdaily";
//		url += "?id=00939.HK&type=24&market=1&level=1&period=56&encoding=utf8";
//		
//		String str = hw.doGet(url);
//		hw.close();
//		
//		log.info(">> " + str);
		
		//AAStockPriceFinder pf = new AAStockPriceFinder();		
		//pf.findPrices("00939");
		//pf.close();
		
		HKEXListFinder lf = new HKEXListFinder();
		lf.findStockList();
		lf.close();
		
	}

}
