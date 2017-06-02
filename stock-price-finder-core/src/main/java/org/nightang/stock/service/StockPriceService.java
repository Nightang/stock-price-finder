package org.nightang.stock.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nightang.db.stock.data.StockInfoMapper;
import org.nightang.db.stock.data.ext.StatisticDataMapper;
import org.nightang.db.stock.model.StockInfo;
import org.nightang.db.stock.model.StockInfoExample;
import org.nightang.db.stock.model.StockPrice;
import org.nightang.stock.CommonUtils;
import org.nightang.stock.pfinder.AAStockPriceFinder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
@Scope
@Service
public class StockPriceService {

	private static final Log log = LogFactory.getLog(StockPriceService.class);

	private CommonUtils commonUtils = new CommonUtils();
	
	@Value("${retention.days.stock.price}")
	private int retentionDaysForStockPrice;

	@Autowired
	private StockInfoMapper stockInfoMapper;

	@Autowired
	private StatisticDataMapper statisticDataMapper;

	@Autowired
	private StockBO stockBO;

	public void updateStockPriceData() throws GeneralSecurityException, IOException {
		long ot = System.currentTimeMillis();
		log.info("Start Overall Stock Price Update Process.");
		
		// Get Active Stock List
		StockInfoExample example = new StockInfoExample();
		example.createCriteria().andActiveEqualTo(true);
		List<StockInfo> stockList = stockInfoMapper.selectByExample(example);
		log.info("Active Number of Stock : " + stockList.size());
		
		// Get Latest Valid Stock Price Date
		long t = System.currentTimeMillis();
		List<StockPrice> oldPriceList = statisticDataMapper.getLatestValidStockPriceList();
		Map<String, Date> checkOldDateMap = new HashMap<String, Date>();
		Map<String, Integer> checkDateSeqMap = new HashMap<String, Integer>();
		for(StockPrice oldPrice : oldPriceList) {
			checkOldDateMap.put(oldPrice.getStockNum(), oldPrice.getStockDate());
			checkDateSeqMap.put(oldPrice.getStockNum(), oldPrice.getStockDateSeq());
		}
		log.info("Get Latest Valid Stock Price List, Duration(ms): " + (System.currentTimeMillis() - t));
		
		// Save DB - Delete Invalid Record (Date after latest valid date)
		stockBO.deleteStockDataAfterDateMap(checkOldDateMap);
		
		// Find New Price
		int totalCount = stockList.size();
		int count = 1;
		int packageCount = 1;
		Date effectiveDate = commonUtils.getDateBeforeToday(retentionDaysForStockPrice);
		List<StockPrice> insertList = new ArrayList<StockPrice>();
		try(AAStockPriceFinder finder = new AAStockPriceFinder()) {
			for(StockInfo stock : stockList) {
				int updateCount = 0;
				String stockNum = stock.getStockNum();
				log.info("Update Stock Price (" + (count++) + "/" + totalCount + "): " + stockNum);
				try {
					t = System.currentTimeMillis();
					List<StockPrice> newList = finder.findPrices(stockNum);
					Date oldPriceDate = checkOldDateMap.get(stockNum);
					for(StockPrice newPrice : newList) {
						// Insert New Stock Price						
						// Condition 1: Price Date is after retention period (Today - retention days)
						if(newPrice.getStockDate().after(effectiveDate)) {							
							// Condition 2: Price Date is after latest valid date
							if(oldPriceDate == null || newPrice.getStockDate().after(oldPriceDate)) {
								Integer stockDateSeq = checkDateSeqMap.get(stockNum);
								if(stockDateSeq == null) stockDateSeq = 1;
								newPrice.setStockDateSeq(stockDateSeq++);
								insertList.add(newPrice);
								checkDateSeqMap.put(stockNum, stockDateSeq);
								updateCount++;
							}
						}
					}
					log.info("StockNum: " + stockNum + ", Number of Update: " + updateCount + ", Duration(ms): " + (System.currentTimeMillis() - t));
					
					if(packageCount++ >= 50) {
						packageCount = 1;
						// Save DB	- Insert Record.
						try {
							stockBO.insertStockPrice(insertList);
						} finally {
							insertList = new ArrayList<StockPrice>();
						}
					}
				} catch(Exception e) {
					log.error("Fail to update stock price, stockNum: " + stockNum, e);
				}
			}
		}
		// Flush Last Record
		stockBO.insertStockPrice(insertList);

		// Update Analysis Data
		log.info("Start Update Analysis Data.");
		stockBO.updateAllStockMA(10);
		stockBO.updateAllStockMA(20);
		stockBO.updateAllStockMA(50);
		stockBO.updateAllStockMA(100);
		stockBO.updateAllStockMA(250);
		
		log.info(">>> Overall Stock Price Update Process Finished. Duration(ms): " + (System.currentTimeMillis() - ot));
	}
	
}
