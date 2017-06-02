package org.nightang.stock.service;

import java.util.Date;
import java.util.List;

import org.nightang.db.stock.data.StockAnalysisDataMapper;
import org.nightang.db.stock.data.ext.StatisticDataMapper;
import org.nightang.db.stock.model.StockAnalysisData;
import org.nightang.db.stock.model.StockAnalysisDataExample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class StockStatisticDataService {

	private static final Log log = LogFactory.getLog(StockStatisticDataService.class);

	@Autowired
	private StockAnalysisDataMapper stockAnalysisDataMapper;

	@Autowired
	private StatisticDataMapper statisticDataMapper;
	
	public void saveData(List<StockAnalysisData> dataList) {
		log.info("Start Save Statistic Data, Size: " + dataList.size());
		Date now = new Date();

		for (StockAnalysisData data : dataList) {
			data.setLastModifiedDate(now);
			StockAnalysisDataExample ex = new StockAnalysisDataExample();
			ex.createCriteria().andStockNumEqualTo(data.getStockNum())
					.andStockDateEqualTo(data.getStockDate())
					.andAdTypeEqualTo(data.getAdType());
			int rc = stockAnalysisDataMapper.updateByExample(data, ex);
			if (rc == 0) {
				stockAnalysisDataMapper.insert(data);
			}
		}
	}
	
	public void deleteData(String stockNum, String adType) {
		log.info("Delete Statistic Data, Stock Num: " + stockNum + ", AD Type: " + adType);
		
		StockAnalysisDataExample ex = new StockAnalysisDataExample();
		ex.createCriteria().andStockNumEqualTo(stockNum).andAdTypeEqualTo(adType);
		
		stockAnalysisDataMapper.deleteByExample(ex);		
	}
	
	public void updateMA(String stockNum, int maParam) {
		log.info("Update MA-" + maParam + ", Stock Num: " + stockNum);
		String adType = "MA" + maParam;

		// Delete Old Record
		StockAnalysisDataExample ex = new StockAnalysisDataExample();
		ex.createCriteria().andStockNumEqualTo(stockNum).andAdTypeEqualTo(adType);
		stockAnalysisDataMapper.deleteByExample(ex);

		// Insert New Record
		int rs = statisticDataMapper.insertMA(stockNum, adType, maParam);
		log.info("Updated Record Num: " + rs);	
	}
	
	/*
	public void updateAllStockMA(int maParam, int dayRange) {
		log.info("Delete MA-" + maParam + " for All Stock, Range: " + dayRange);
		String adType = "MA" + maParam;
		String range = "-" + dayRange + " day";

		if(dayRange > 0) {
			// Delete Old Record
			int rs = statisticDataMapper.deleteAllStockMA(adType, range);
			log.info("Delete Record Num: " + rs);			
		} else {
			StockAnalysisDataExample ex = new StockAnalysisDataExample();
			ex.createCriteria().andAdTypeEqualTo(adType);
			stockAnalysisDataMapper.deleteByExample(ex);
			log.info("Delete All Record");			
		}

		// Insert New Record
		int rs2 = statisticDataMapper.insertAllStockMAForNullOnly(adType, maParam);
		log.info("Updated Record Num: " + rs2);
	}
	*/
	
}
