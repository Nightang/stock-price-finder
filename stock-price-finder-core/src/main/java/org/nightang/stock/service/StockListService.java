package org.nightang.stock.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nightang.db.stock.data.StockInfoMapper;
import org.nightang.db.stock.model.StockInfo;
import org.nightang.db.stock.model.StockInfoExample;
import org.nightang.stock.pfinder.AAStockInfoFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StockListService {

	private static final Log log = LogFactory.getLog(StockListService.class);

	@Value("${hkex.token}")
	private String toekn;
	
	@Autowired
	private StockInfoMapper stockInfoMapper;

	@Autowired
	private StockBO stockBO;

	public void updateStockList() throws IOException, GeneralSecurityException {
		long ot = System.currentTimeMillis();
		log.info("Start Overall Stock List Update Process.");

		// Get OLD stock list
		StockInfoExample example = new StockInfoExample();
		example.setOrderByClause("STOCK_NUM asc");
		List<StockInfo> oldList = stockInfoMapper.selectByExample(example);
		log.info("Number of Stock (Include Suspended Stock) : " + oldList.size());
		
		int count = 1;
		try (AAStockInfoFinder finder = new AAStockInfoFinder()) {
			for(StockInfo stock : oldList) {
				log.info("Update Process> " + count++ + "/" + oldList.size());
				
				String stockNum = stock.getStockNum();
				
				StockInfo newInfo = finder.findInfo(stockNum);

				StockInfoExample pk = new StockInfoExample();
				pk.createCriteria().andStockNumEqualTo(stockNum);
				stockInfoMapper.updateByExampleSelective(newInfo, pk);				
			}
		}		

		log.info(">>> Overall Stock List Update Process Finished. Duration(ms): " + (System.currentTimeMillis() - ot));
	}
	
}
