package org.nightang.stock.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nightang.db.stock.data.StockAnalysisDataMapper;
import org.nightang.db.stock.data.StockInfoMapper;
import org.nightang.db.stock.data.StockPriceMapper;
import org.nightang.db.stock.data.ext.StatisticDataMapper;
import org.nightang.db.stock.model.StockAnalysisDataExample;
import org.nightang.db.stock.model.StockInfo;
import org.nightang.db.stock.model.StockInfoExample;
import org.nightang.db.stock.model.StockPrice;
import org.nightang.db.stock.model.StockPriceExample;
import org.nightang.stock.pfinder.AAStockPriceFinder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockPriceService {

	private static final Log log = LogFactory.getLog(StockPriceService.class);

	@Autowired
	private StockInfoMapper stockInfoMapper;

	@Autowired
	private StockPriceMapper stockPriceMapper;

	@Autowired
	private StockAnalysisDataMapper stockAnalysisDataMapper;

	@Autowired
	private StatisticDataMapper statisticDataMapper;

	public void updateStockPriceData() throws GeneralSecurityException, IOException {
		long ot = System.currentTimeMillis();
		log.info("Start Overall Stock Price Update Process.");
		
		// Get Active Stock List
		StockInfoExample example = new StockInfoExample();
		example.createCriteria().andActiveEqualTo(true);
		List<StockInfo> stockList = stockInfoMapper.selectByExample(example);
		log.info("Active Number of Stock : " + stockList.size());
		
		// Get Latest Valid Stock Price Date
		List<StockPrice> oldPriceList = statisticDataMapper.getLatestValidStockPriceList();
		Map<String, Date> checkOldDateMap = new HashMap<String, Date>();
		Map<String, Integer> checkDateSeqMap = new HashMap<String, Integer>();
		for(StockPrice oldPrice : oldPriceList) {
			checkOldDateMap.put(oldPrice.getStockNum(), oldPrice.getStockDate());
			checkDateSeqMap.put(oldPrice.getStockNum(), oldPrice.getStockDateSeq());
		}
		
		// Save DB - Delete Invalid Record
		deleteStockPriceByDateMap(checkOldDateMap);
		
		// Find New Price
		int totalCount = stockList.size();
		int count = 1;
		int packageCount = 1;
		List<StockPrice> insertList = new ArrayList<StockPrice>();
		try(AAStockPriceFinder finder = new AAStockPriceFinder()) {
			for(StockInfo stock : stockList) {
				int updateCount = 0;
				String stockNum = stock.getStockNum();
				log.info("Update Stock Price (" + (count++) + "/" + totalCount + "): " + stockNum);
				try {
					long t = System.currentTimeMillis();
					List<StockPrice> newList = finder.findPrices(stockNum);
					Date oldPriceDate = checkOldDateMap.get(stockNum);
					for(StockPrice newPrice : newList) {
						// Only Insert New Stock Price
	
						if(oldPriceDate == null || newPrice.getStockDate().after(oldPriceDate)) {
							Integer stockDateSeq = checkDateSeqMap.get(stockNum);
							if(stockDateSeq == null) stockDateSeq = 1;
							newPrice.setStockDateSeq(stockDateSeq++);
							insertList.add(newPrice);
							checkDateSeqMap.put(stockNum, stockDateSeq);
							updateCount++;
						}
					}
					log.info("StockNum: " + stockNum + ", Number of Update: " + updateCount + ", Duration(ms): " + (System.currentTimeMillis() - t));
					
					if(packageCount++ >= 50) {
						packageCount = 1;
						// Save DB	- Insert Record.
						insertStockPrice(insertList);
						insertList = new ArrayList<StockPrice>();
					}
				} catch(Exception e) {
					log.error("Fail to update stock price, stockNum: " + stockNum, e);
				}
			}
		}
		// Flush Last Record
		insertStockPrice(insertList);

		// Update Analysis Data
		log.info("Start Update Analysis Data.");
		updateAllStockMA(10);
		updateAllStockMA(20);
		updateAllStockMA(50);
		updateAllStockMA(100);
		updateAllStockMA(250);
		
		log.info(">>> Overall Stock Price Update Process Finished. Duration(ms): " + (System.currentTimeMillis() - ot));
	}

	@Transactional
	private void deleteStockPriceByDateMap(Map<String, Date> stockNumVsDateMap) {
		long t = System.currentTimeMillis();
		int deleteCount = 0;
		for(Entry<String, Date> entry : stockNumVsDateMap.entrySet()) {
			// Delete Stock Price
			StockPriceExample spExample = new StockPriceExample();
			spExample.createCriteria().andStockNumEqualTo(entry.getKey()).andStockDateGreaterThan(entry.getValue());
			deleteCount += stockPriceMapper.deleteByExample(spExample);
			// Delete Stock Price Analyst Data
			StockAnalysisDataExample saExample = new StockAnalysisDataExample();
			saExample.createCriteria().andStockNumEqualTo(entry.getKey()).andStockDateGreaterThan(entry.getValue());
			stockAnalysisDataMapper.deleteByExample(saExample);
		}
		log.info("Number of Stock Price Deleted: " + deleteCount + ", Duration(ms): " + (System.currentTimeMillis() - t));
	}

	@Transactional
	private void insertStockPrice(List<StockPrice> insertList) {
		long t = System.currentTimeMillis();
		log.info("Number of Stock Price Inserted: " + insertList.size());
		for(StockPrice record : insertList) {
			stockPriceMapper.insert(record);
		}
		log.info("Duration(ms): " + (System.currentTimeMillis() - t));
	}

	@Transactional
	public void updateAllStockMA(int maParam) {
		long t = System.currentTimeMillis();
		String adType = "MA" + maParam;
		int rs = statisticDataMapper.insertAllStockMAForNullOnly(adType, maParam);
		log.info("Updated Statistic Data ("+adType+"), Number of Record: " + rs + ", Duration(ms): " + (System.currentTimeMillis() - t));
	}
	
}
