package org.nightang.stock.pfinder;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nightang.db.stock.model.StockInfo;
import org.nightang.stock.listfinder.HKEXListFinder;
import org.nightang.stock.service.StockListService;
import org.nightang.stock.service.StockPriceService;
import org.nightang.ws.HttpClientWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class Test {
	
	private static final Log log = LogFactory.getLog(HttpClientWrapper.class);
	
	@Autowired
	private StockListService stockListService;

	@Autowired
	private StockPriceService stockPriceService;
	
	public void doTest() throws Exception {
//		HttpClientWrapper hw = new HttpClientWrapper("network.ini", null, null);
//		
//		String url = "http://chartdata1.internet.aastocks.com/servlet/iDataServlet/getdaily";
//		url += "?id=00939.HK&type=24&market=1&level=1&period=56&encoding=utf8";
//		
//		String str = hw.doGet(url);
//		hw.close();
//		
//		log.info(">> " + str);

		//try(AAStockPriceFinder finder = new AAStockPriceFinder()) {
		//	finder.test();
		//}
		
		//try(HKEXListFinder finder = new HKEXListFinder()) {
			//List<StockInfo> list = finder.findStockList();
			//log.info(list.size());
		//}
		
		stockListService.updateStockList();
		
		//stockPriceService.updateStockPriceData();
	}
	
	public static void main(String[] args) throws Exception {		
		ApplicationContext context = (ApplicationContext) new ClassPathXmlApplicationContext("context-application.xml");
		Test test = (Test) context.getBean("test");
		test.doTest();		
	}

}
