package org.nightang.stock.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nightang.db.stock.data.StockInfoMapper;
import org.nightang.db.stock.data.StockPriceMapper;
import org.nightang.db.stock.data.ext.StatisticDataMapper;
import org.nightang.db.stock.model.StockInfo;
import org.nightang.db.stock.model.StockInfoExample;
import org.nightang.db.stock.model.StockPrice;
import org.nightang.db.stock.model.StockPriceExample;
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
	
	private SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	
	private SimpleDateFormat sdf_yyyyMM = new SimpleDateFormat("yyyyMM");
	
	@Value("${data.mode}")
	private String dataMode;
	
	@Value("${retention.days.stock.price}")
	private int retentionDaysForStockPrice;

	@Value("${enable.debug.filter}")
	private String enableDebugFilter;

	@Value("${enable.debug.filter.stock.num}")
	private String debugStockNum;

	@Autowired
	private StockInfoMapper stockInfoMapper;

	@Autowired
	private StockPriceMapper stockPriceMapper;

	@Autowired
	private StatisticDataMapper statisticDataMapper;

	@Autowired
	private StockBO stockBO;
	
	public void updateStockPriceData() throws GeneralSecurityException, IOException {
		long ot = System.currentTimeMillis();
		log.info("Start Overall Stock Price Update Process.");
		
		// Get Active Stock List
		List<StockInfo> stockList = getActiveStockList();
		
		// Get Latest Valid Stock Price Date
		Map<String, StockPrice> dbPricesMap = verifyAvaliableDBData(stockList);
		
		// Save DB - Delete Invalid Record (Date after latest valid date)
		stockBO.deleteStockPricesAfterMapData(dbPricesMap);
		
		// Find and Save New Price
		findAndSaveNewPrices(stockList, dbPricesMap);
		
		// Update Analysis Data
		log.info("Start Update Analysis Data.");
		stockBO.updateAllStockMA(10);
		stockBO.updateAllStockMA(20);
		stockBO.updateAllStockMA(50);
		stockBO.updateAllStockMA(100);
		stockBO.updateAllStockMA(250);
		
		log.info(">>> Overall Stock Price Update Process Finished. Duration(ms): " + (System.currentTimeMillis() - ot));
	}
	
	public List<StockInfo> getActiveStockList() {
		StockInfoExample example = new StockInfoExample();
		example.setOrderByClause("STOCK_NUM asc");
		if("TRUE".equalsIgnoreCase(enableDebugFilter)) {
			log.info("Debug Mode is Turned On, Trace Stock: " + debugStockNum);
			example.createCriteria().andStockNumEqualTo(debugStockNum);
		} else {
			example.createCriteria().andActiveEqualTo(true);
		}
		List<StockInfo> stockList = stockInfoMapper.selectByExample(example);
		log.info("Active Number of Stock : " + stockList.size());
		return stockList;
	}
	
	public StockPrice findLatestAvailableDBPrice(String stockNum) {
		StockPrice availablePrice = null;
		
		// Get Stock Price List
		StockPriceExample spex = new StockPriceExample();
		spex.createCriteria().andStockNumEqualTo(stockNum);
		spex.setOrderByClause("STOCK_DATE asc, STOCK_DATE_SEQ asc");
		List<StockPrice> splist = stockPriceMapper.selectByExample(spex);

		// Check Point 2: Found Any Repeat Date (Data Mode Dependency)
		SimpleDateFormat sdf = "M".equals(dataMode) ? sdf_yyyyMM : sdf_yyyyMMdd;
		StockPrice prevPrice = null;
		String prevDateStr = null;
		for (StockPrice sp : splist) {
			String dateStr = sdf.format(sp.getStockDate());
			if (prevDateStr != null && prevDateStr.equals(dateStr)) {
				break;
			} else {
				prevDateStr = dateStr;
			}
			availablePrice = prevPrice;
			prevPrice = sp;
		}

		// Put Cut Off Date Result into Map
		//Date date = availablePrice != null ? availablePrice.getStockDate() : null;
		//int seq = availablePrice != null ? availablePrice.getStockDateSeq() : 0;
		//log.info(">>> " + stockNum + ", " + date + ", " + seq);
		
		return availablePrice;
	}
	
	public Map<String, StockPrice> verifyAvaliableDBData(List<StockInfo> stockList) {
		long t = System.currentTimeMillis();

		Map<String, StockPrice> dbPricesMap = new HashMap<String, StockPrice>();
		
		for(StockInfo si : stockList) {
			String stockNum = si.getStockNum();
			
			StockPrice availablePrice = findLatestAvailableDBPrice(stockNum); 
			
			// Put Cut Off Date Result into Map
			dbPricesMap.put(stockNum, availablePrice);
			//Date date = availablePrice != null ? availablePrice.getStockDate() : null ;
			//int seq = availablePrice != null ? availablePrice.getStockDateSeq() : 0 ;
			//log.info(stockNum + "," + date + "," + seq);
		}
		
		log.info("Get Latest Valid Stock Price List, Duration(ms): " + (System.currentTimeMillis() - t));
		
		return dbPricesMap;
	}
	
	public List<StockPrice> findNewPrices(AAStockPriceFinder finder, String stockNum, StockPrice dbPrice) {
		List<StockPrice> insertList = new ArrayList<StockPrice>();
				
		// If Data Mode is Monthly, then multiple the param by 30
		Date effectiveDate = commonUtils.getDateBeforeToday("M".equals(dataMode) ? 
													retentionDaysForStockPrice*30 : retentionDaysForStockPrice);
						
		Date latestDate = dbPrice != null ? dbPrice.getStockDate() : null;
		int latestSeq = dbPrice != null ? dbPrice.getStockDateSeq() : 0;
		
		try {
			List<StockPrice> newList = finder.findPrices(stockNum);
			for(StockPrice newPrice : newList) {
				// Insert New Stock Price						
				// Condition 1: Price Date is after retention period (Today - retention days)
				if(newPrice.getStockDate().after(effectiveDate)) {							
					// Condition 2: Price Date is after latest valid date
					if(latestDate == null || newPrice.getStockDate().after(latestDate)) {
						newPrice.setStockDateSeq(++latestSeq);
						insertList.add(newPrice);
					}
				}
			}
			
		} catch(Exception e) {
			log.error("Fail to update stock price, stockNum: " + stockNum, e);
		}
		
		return insertList;
	}
	
	public void findAndSaveNewPrices(List<StockInfo> stockList, Map<String, StockPrice> dbPricesMap) 
			throws IOException, GeneralSecurityException {
		
		int totalCount = stockList.size();
		int count = 1;
		try(AAStockPriceFinder finder = new AAStockPriceFinder(dataMode)) {
			
			int packageCount = 1;
			List<StockPrice> packageList = new ArrayList<StockPrice>();
			
			for(StockInfo stock : stockList) {
				long t = System.currentTimeMillis();
				
				String stockNum = stock.getStockNum();
				StockPrice dbPrice = dbPricesMap.get(stockNum);
				
				log.info("Update Prices of Stock: " + stockNum + "(" + (count++) + "/" + totalCount + ")");
				
				// Find New Prices
				List<StockPrice> insertList = findNewPrices(finder, stockNum, dbPrice);
				int newCount = insertList.size();
				packageList.addAll(insertList);
				
				// Save New Prices
				// Save DB every update 20 stock
				if(packageCount++ >= 20) {
					stockBO.insertStockPrice(packageList);
					packageList = new ArrayList<StockPrice>();
				}				

				log.info("StockNum: " + stockNum + ", Number of New Prices: " + newCount + ", Duration(ms): " + (System.currentTimeMillis() - t));
			}
			
			// Flush remaining
			if(packageList.size() > 0) {
				stockBO.insertStockPrice(packageList);				
			}
			
		}
	}
	
}
