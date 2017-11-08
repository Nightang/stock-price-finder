package org.nightang.stock;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nightang.stock.service.StockListService;
import org.nightang.stock.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class StockPriceFinderLauncher {
	
	private static final Log log = LogFactory.getLog(StockPriceFinderLauncher.class);

	@Autowired
	private StockListService stockListService;

	@Autowired
	private StockPriceService stockPriceService;
	
	public void doTask(String[] args) throws IOException, GeneralSecurityException {
		for(String arg : args) {
			if("updateList".equals(arg)) {
				stockListService.updateStockList();
			} else if("updatePrice".equals(arg)) {
				stockPriceService.updateStockPriceData();
			}
		}		
	}

	public static void main(String[] args) throws IOException, GeneralSecurityException {
		try {
			ApplicationContext context = (ApplicationContext) new ClassPathXmlApplicationContext("context-application.xml");
			StockPriceFinderLauncher launcher = (StockPriceFinderLauncher) context.getBean("stockPriceFinderLauncher");
			launcher.doTask(args);
		} catch(Exception e) {
			log.error(e, e);
		}
	}

}
