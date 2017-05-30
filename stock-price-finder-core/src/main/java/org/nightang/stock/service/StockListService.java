package org.nightang.stock.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nightang.db.stock.data.StockInfoMapper;
import org.nightang.db.stock.model.StockInfo;
import org.nightang.db.stock.model.StockInfoExample;
import org.nightang.stock.listfinder.HKEXListFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockListService {

	private static final Log log = LogFactory.getLog(StockListService.class);
	
	@Autowired
	private StockInfoMapper stockInfoMapper;

	public void updateStockList() throws IOException, GeneralSecurityException {
		long ot = System.currentTimeMillis();
		log.info("Start Overall Stock List Update Process.");

		// Get OLD stock list
		StockInfoExample example = new StockInfoExample();
		example.createCriteria().andActiveEqualTo(true);
		List<StockInfo> oldList = stockInfoMapper.selectByExample(example);
		log.info("Original Active Number of Stock : " + oldList.size());
		
		// Check time between now and last update date, if more than 12 hour, update the list
		StockInfo test = oldList.get(0);
		if(test.getLastModifiedDate().getTime() + 12*60*60*1000 > (new Date()).getTime()) {
			log.info("Stock List is up to date. No Update Required. Last Update Time: " + test.getLastModifiedDate());
			return;
		}
		
		// Get NEW stock list
		List<StockInfo> newList;
		try (HKEXListFinder finder = new HKEXListFinder()) {
			newList = finder.findStockList();
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
		saveDB(deleteMap, updateMap, createMap);

		log.info(">>> Overall Stock List Update Process Finished. Duration(ms): " + (System.currentTimeMillis() - ot));
	}

	@Transactional
	private void saveDB(Map<String, StockInfo> deleteMap, Map<String, StockInfo> updateMap, 
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
