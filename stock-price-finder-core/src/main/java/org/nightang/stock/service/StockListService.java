package org.nightang.stock.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nightang.db.stock.data.StockInfoMapper;
import org.nightang.db.stock.model.StockInfo;
import org.nightang.db.stock.model.StockInfoExample;
import org.nightang.stock.listfinder.HKEXListFinder;
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
		List<StockInfo> oldList = stockInfoMapper.selectByExample(example);
		log.info("Original Number of Stock (Include Suspended Stock) : " + oldList.size());
		
		// Check time between now and last update date, if more than 12 hour, update the list
		//StockInfo test = oldList.size() > 0 ? oldList.get(0) : null;
		//if(test != null && test.getLastModifiedDate().getTime() + 12*60*60*1000 > (new Date()).getTime()) {
		//	log.info("Stock List is up to date. No Update Required. Last Update Time: " + test.getLastModifiedDate());
		//	return;
		//}
		
		// Get NEW stock list
		List<StockInfo> newList;
		try (HKEXListFinder finder = new HKEXListFinder()) {
			newList = finder.findStockList(toekn);
			//newList = new ArrayList<StockInfo>();
			log.info("Current Active Number of Stock : " + newList.size());
		}

		// Put into Map to compare
		Map<String, StockInfo> deleteMap = new HashMap<String, StockInfo>();
		Map<String, StockInfo> updateMap = new HashMap<String, StockInfo>();
		Map<String, StockInfo> createMap = new HashMap<String, StockInfo>();
		for(StockInfo item : newList) {
			createMap.put(item.getStockNum(), item);	
		}
		for(StockInfo item : oldList) {
			if(createMap.containsKey(item.getStockNum())) {
				StockInfo updatedItem = createMap.remove(item.getStockNum());
				updateMap.put(updatedItem.getStockNum(), updatedItem);
			} else {
				deleteMap.put(item.getStockNum(), item);				
			}
		}
				
		// Save into DB
		stockBO.saveStockList(deleteMap, updateMap, createMap);

		log.info(">>> Overall Stock List Update Process Finished. Duration(ms): " + (System.currentTimeMillis() - ot));
	}
	
}
