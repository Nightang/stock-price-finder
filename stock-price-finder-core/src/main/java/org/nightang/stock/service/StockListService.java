package org.nightang.stock.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nightang.db.stock.data.StockInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class StockListService {

	private static final Log log = LogFactory.getLog(StockListService.class);
	
	@Autowired
	private StockInfoMapper stockInfoMapper;

}
