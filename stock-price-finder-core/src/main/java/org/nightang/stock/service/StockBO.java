package org.nightang.stock.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nightang.db.stock.data.PatternResultMapper;
import org.nightang.db.stock.data.StockAnalysisDataMapper;
import org.nightang.db.stock.data.StockInfoMapper;
import org.nightang.db.stock.data.StockPriceMapper;
import org.nightang.db.stock.data.ext.StatisticDataMapper;
import org.nightang.db.stock.model.PatternResultExample;
import org.nightang.db.stock.model.StockAnalysisDataExample;
import org.nightang.db.stock.model.StockInfo;
import org.nightang.db.stock.model.StockInfoExample;
import org.nightang.db.stock.model.StockPrice;
import org.nightang.db.stock.model.StockPriceExample;
import org.nightang.stock.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class StockBO {

	private static final Log log = LogFactory.getLog(StockPriceService.class);
	
	private CommonUtils commonUtils = new CommonUtils();

	@Value("${retention.days.stock.price}")
	private int retentionDaysForStockPrice;

	@Value("${retention.days.stock.analysis.ma}")
	private int retentionDaysForStockDataMA;

	@Autowired
	private StockInfoMapper stockInfoMapper;

	@Autowired
	private StockPriceMapper stockPriceMapper;

	@Autowired
	private PatternResultMapper patternResultMapper;

	@Autowired
	private StatisticDataMapper statisticDataMapper;

	@Autowired
	private StockAnalysisDataMapper stockAnalysisDataMapper;
	
	@Transactional
	public void deleteStockPricesAfterMapData(Map<String, StockPrice> stockPricesMap) {
		long t = System.currentTimeMillis();
		int deleteCount = 0;
		for(Entry<String, StockPrice> entry : stockPricesMap.entrySet()) {
			String stockNum = entry.getKey();
			StockPrice sp = entry.getValue();
			
			// Delete Pattern Result after the date in the map
			PatternResultExample prExample = new PatternResultExample();
			if(sp != null) {
				prExample.createCriteria().andStockNumEqualTo(stockNum).andStockDateGreaterThan(sp.getStockDate());
			} else {
				prExample.createCriteria().andStockNumEqualTo(stockNum);
			}
			patternResultMapper.deleteByExample(prExample);
			
			// Delete Stock Analyst Data after the date in the map
			StockAnalysisDataExample saExample = new StockAnalysisDataExample();
			if(sp != null) {
				saExample.createCriteria().andStockNumEqualTo(stockNum).andStockDateGreaterThan(sp.getStockDate());
			} else {
				saExample.createCriteria().andStockNumEqualTo(stockNum);
			}
			stockAnalysisDataMapper.deleteByExample(saExample);
			
			// Delete Stock Price after the date in the map
			StockPriceExample spExample = new StockPriceExample();
			if(sp != null) {
				spExample.createCriteria().andStockNumEqualTo(stockNum).andStockDateGreaterThan(sp.getStockDate());
			} else {
				spExample.createCriteria().andStockNumEqualTo(stockNum);				
			}
			
			deleteCount += stockPriceMapper.deleteByExample(spExample);
		}
		log.info("Number of Stock Price Deleted: " + deleteCount + ", Duration(ms): " + (System.currentTimeMillis() - t));
	}
	
	@Transactional
	public void insertStockPrice(List<StockPrice> insertList) {
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
		int rs = statisticDataMapper.insertAllStockMAForNullOnly(adType, maParam, retentionDaysForStockDataMA);
		log.info("("+adType+") Inserted Number: " + rs + ", Duration(ms): " + (System.currentTimeMillis() - t));
	}

	@Transactional
	public void housekeepStockDataMA() {
		long t = System.currentTimeMillis();
		String dateStr = commonUtils.getDateDBStringBeforeToday(retentionDaysForStockDataMA);
		log.info("Deleted MA Data before: " + dateStr);
		int rs = statisticDataMapper.deleteStockMABeforeDate(dateStr);
		log.info("Deleted Number: " + rs + ", Duration(ms): " + (System.currentTimeMillis() - t));
	}
	
	@Transactional
	public void housekeepStockPrice() {
		long t = System.currentTimeMillis();
		String dateStr = commonUtils.getDateDBStringBeforeToday(retentionDaysForStockPrice);
		log.info("Deleted Price Record before: " + dateStr);
		int rs = statisticDataMapper.deleteStockPriceBeforeDate(dateStr);
		log.info("Deleted Number: " + rs + ", Duration(ms): " + (System.currentTimeMillis() - t));
		t = System.currentTimeMillis();		
		statisticDataMapper.updateStockPriceSequence();
		log.info("Update Stock Price Sequence, Duration(ms): " + (System.currentTimeMillis() - t));
	}
	
	@Transactional
	public void saveStockList(Map<String, StockInfo> deleteMap, Map<String, StockInfo> updateMap, 
			Map<String, StockInfo> createMap) {
		// Save into DB
		Date now = new Date();

		// Insert
		long t = System.currentTimeMillis();
		log.info("Created Number of Stock : " + createMap.size());
		if (createMap.size() > 0) {
			for (Entry<String, StockInfo> entry : createMap.entrySet()) {
				StockInfo record = entry.getValue();
				record.setLastModifiedDate(now);
				stockInfoMapper.insert(record);
			}
		}
		log.info("Insert Duration(ms): " + (System.currentTimeMillis() - t));

		// Update
		t = System.currentTimeMillis();
		log.info("Updated Number of Stock : " + updateMap.size());
		if (updateMap.size() > 0) {
			for (Entry<String, StockInfo> entry : updateMap.entrySet()) {
				StockInfo record = entry.getValue();
				record.setLastModifiedDate(now);
				StockInfoExample example = new StockInfoExample();
				example.createCriteria().andStockNumEqualTo(
						record.getStockNum());
				stockInfoMapper.updateByExampleSelective(record, example);
			}
		}
		log.info("Update Duration(ms): " + (System.currentTimeMillis() - t));

		// Delete (Just set to inactive, not actual delete)
		t = System.currentTimeMillis();
		log.info("Deleted Number of Stock : " + deleteMap.size());
		if (deleteMap.size() > 0) {
			for (Entry<String, StockInfo> entry : deleteMap.entrySet()) {
				StockInfo record = entry.getValue();
				record.setActive(false);
				record.setLastModifiedDate(now);
				StockInfoExample example = new StockInfoExample();
				example.createCriteria().andStockNumEqualTo(record.getStockNum());
				stockInfoMapper.updateByExampleSelective(record, example);
			}
		}
		log.info("Deleted Duration(ms): " + (System.currentTimeMillis() - t));
	}

}
