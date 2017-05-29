package org.nightang.stock;

import org.nightang.stock.service.StockListService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

public class StockPriceFinderLauncher {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("context-application.xml");
		StockListService spfl = (StockListService) context.getBean("StockListService");
		

	}

}
