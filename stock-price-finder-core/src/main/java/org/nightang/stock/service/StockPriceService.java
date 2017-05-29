package org.nightang.stock.service;

import java.util.Date;
import java.util.List;

import org.nightang.db.stock.data.StockPriceMapper;
import org.nightang.db.stock.data.ext.StatisticDataMapper;
import org.nightang.db.stock.model.StockPrice;
import org.nightang.db.stock.model.StockPriceExample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class StockPriceService {

	private static final Log log = LogFactory.getLog(StockPriceService.class);

	@Autowired
	private StockPriceMapper stockPriceMapper;

	@Autowired
	private StatisticDataMapper statisticDataMapper;

	public void savePrices(List<StockPrice> prices) {
		log.info("Start Save Srock Price, Size: " + prices.size());
		for (StockPrice p : prices) {
			savePrices(p);
		}
	}

	public void savePrices(StockPrice p) {
		// log.info("Save Srock Price: " + p.getStockNum() + " (" + p.getStockDate() + ")");
		Date now = new Date();
		p.setLastModifiedDate(now);
		StockPriceExample ex = new StockPriceExample();
		ex.createCriteria().andStockNumEqualTo(p.getStockNum()).andStockDateEqualTo(p.getStockDate());
		int rc = stockPriceMapper.updateByExample(p, ex);
		if (rc == 0) {
			stockPriceMapper.insert(p);
		}
	}
	
	public void updateStockPriceSequence() {
		log.info("Update Stock Price Sequence...");
		statisticDataMapper.updateStockPriceSequence();
		log.info("Finished.");
	}
	
	public void updateStockPriceSequenceForNullOnly() {
		log.info("Update Stock Price Sequence (Null Only)...");
		statisticDataMapper.updateStockPriceSequenceForNullOnly();
		log.info("Finished.");
	}

}
