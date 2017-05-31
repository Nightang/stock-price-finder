package org.nightang.stock;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.nightang.stock.service.StockListService;
import org.nightang.stock.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class StockPriceFinderLauncher {

	@Autowired
	private StockListService stockListService;

	@Autowired
	private StockPriceService stockPriceService;
	
	public void doTask() throws IOException, GeneralSecurityException {
		stockListService.updateStockList();		
		stockPriceService.updateStockPriceData();		
	}

	public static void main(String[] args) throws IOException, GeneralSecurityException {
		ApplicationContext context = (ApplicationContext) new ClassPathXmlApplicationContext("context-application.xml");
		StockPriceFinderLauncher launcher = (StockPriceFinderLauncher) context.getBean("stockPriceFinderLauncher");
		launcher.doTask();		
	}

}
