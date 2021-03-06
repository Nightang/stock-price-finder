package org.nightang.stock.pfinder;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nightang.db.stock.model.StockPrice;
import org.nightang.ws.HttpClientWrapper;

public class AAStockPriceFinder implements AutoCloseable {
	
	private static final Log log = LogFactory.getLog(AAStockPriceFinder.class);
	
	private HttpClientWrapper hw;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	private String dataMode;
	
	private static final Map<String, String> AA_DATA_MODE_MAP = new HashMap<String, String>();
	static {
		AA_DATA_MODE_MAP.put("D", "56");
		AA_DATA_MODE_MAP.put("M", "68");
	}
	
	public AAStockPriceFinder(String dataMode) throws GeneralSecurityException, IOException {
		hw = new HttpClientWrapper("network.ini", null, null);
		this.dataMode = dataMode;
	}

	@Override
	public void close() throws IOException {
		hw.close();
	}
	
	public List<StockPrice> findPrices(String stockNum) throws IOException {
		List<StockPrice> list = new ArrayList<StockPrice>();
		
		// Stock Number shuold be padded to 5 digit
		String url = "http://chartdata1.internet.aastocks.com/servlet/iDataServlet/getdaily";
		url += "?id="+paddingStockNum(stockNum)+".HK&type=24&market=1&level=1"
				+ "&period="+AA_DATA_MODE_MAP.get(dataMode)+"&encoding=utf8";
		String data = hw.doGet(url);
		//log.info("RAW: " + data);
		String[] dataSet = data.split("\\|");
		
		// History data start from "Index-2", "Index-0" is stock name, "Index-1" is real-time price
		Date now = new Date();
		for(int i = 2; i < dataSet.length; i++) {
			try { 
				// Sample: 05/24/2012;5.12;5.17;5.1;5.11;253204.186;1297370282
				String[] raws = dataSet[i].split(";");
				StockPrice sp = new StockPrice();
				sp.setStockNum(stockNum);
				sp.setStockDate(sdf.parse(raws[0].startsWith("!") ? raws[0].substring(1) : raws[0]));
				sp.setPriceOpen(Double.parseDouble(raws[1]));
				sp.setPriceHighest(Double.parseDouble(raws[2]));
				sp.setPriceLowest(Double.parseDouble(raws[3]));
				sp.setPriceClosing(Double.parseDouble(raws[4]));
				sp.setVolume((long)Double.parseDouble(raws[5])*1000);
				if(sp.getStockDate().getTime() + 20*60*60*1000 >= now.getTime()) {
					sp.setIsValid(false);
				} else {
					sp.setIsValid(true);					
				}
				sp.setLastModifiedDate(now);
				list.add(sp);
			} catch (ParseException e) {
				log.warn("Fail Parse: ["+i+"][" + dataSet[i] + "]");
			}
		}
		list.sort(new Comparator<StockPrice>() {
			@Override
			public int compare(StockPrice arg0, StockPrice arg1) {				
				return arg0.getStockDate().compareTo(arg1.getStockDate());
			}
		});
		log.info("Days Found: " + (dataSet.length - 2) + " (Fail Parse: " + ((dataSet.length - 2) - list.size()) + ")");
		
		return list;
	}

	private String paddingStockNum(String str) {
		String num = str;
		if(num != null) {
			if(num.length() < 5) {
				for(int i = num.length(); i < 5; i++) {
					num = "0" + num;
				}
			}
		}
		return num;
	}
}
