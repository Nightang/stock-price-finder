package org.nightang.stock.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nightang.db.stock.data.StockAnalysisDataMapper;
import org.nightang.db.stock.data.StockInfoMapper;
import org.nightang.db.stock.data.StockPriceMapper;
import org.nightang.db.stock.data.ext.StatisticDataMapper;
import org.nightang.db.stock.model.StockAnalysisDataExample;
import org.nightang.db.stock.model.StockPrice;
import org.nightang.db.stock.model.StockPriceExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class StockBO {

	private static final Log log = LogFactory.getLog(StockPriceService.class);

	@Autowired
	private StockInfoMapper stockInfoMapper;

	@Autowired
	private StockPriceMapper stockPriceMapper;

	@Autowired
	private StatisticDataMapper statisticDataMapper;

	@Autowired
	private StockAnalysisDataMapper stockAnalysisDataMapper;
	
	@Transactional
	public void deleteStockPriceByDateMap(Map<String, Date> stockNumVsDateMap) {
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
		int rs = statisticDataMapper.insertAllStockMAForNullOnly(adType, maParam);
		log.info("Updated Statistic Data ("+adType+"), Number of Record: " + rs + ", Duration(ms): " + (System.currentTimeMillis() - t));
	}

}
