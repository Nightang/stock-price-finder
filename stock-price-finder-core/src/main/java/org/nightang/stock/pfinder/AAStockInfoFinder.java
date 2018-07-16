package org.nightang.stock.pfinder;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nightang.db.stock.model.StockInfo;
import org.nightang.ws.HttpClientWrapper;

public class AAStockInfoFinder implements AutoCloseable {
	
	private static final Log log = LogFactory.getLog(AAStockPriceFinder.class);
	
	private HttpClientWrapper hw;
	
	public AAStockInfoFinder() throws GeneralSecurityException, IOException {
		hw = new HttpClientWrapper("network.ini", null, null);
	}

	@Override
	public void close() throws IOException {
		hw.close();
	}
	
	public StockInfo findInfo(String stockNum) throws IOException {		
		// Stock Number shuold be padded to 5 digit
		String url = "http://www.aastocks.com/tc/stocks/analysis/company-fundamental/basic-information?symbol=" + paddingStockNum(stockNum);
		String data = hw.doGet(url);
		//log.info("RAW: " + data);
		
		StockInfo stock = new StockInfo();
		stock.setStockNum(stockNum);
		stock.setLastModifiedDate(new Date());
		
		Matcher m = Pattern.compile("公司名稱</td> <td class=\"mcFont \">([^<]+)<").matcher(data);
		if(m.find()) {
			String result = m.group(1);
			//log.info("result: " + result);
			stock.setChiName(result);
		}
		
		m = Pattern.compile("上市日期</td> <td class=\"mcFont cls\">([^<]+)<").matcher(data);
		if(m.find()) {
			String result = m.group(1);
			//log.info("result: " + result);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			try {
				stock.setListingDate(sdf.parse(result));
			} catch (ParseException e) {
				log.error(e, e);
			}
		}
		
		m = Pattern.compile("總市值</td> <td class=\"mcFont cls\">([^<]+)億<").matcher(data);
		if(m.find()) {
			String result = m.group(1);
			result = result.replaceAll(",", "");
			double d = Double.parseDouble(result);
			long l = (long) (d * 100L);
			//log.info("result: " + result);
			stock.setMktCap(l);
		} else {
			m = Pattern.compile("總市值</td> <td class=\"mcFont cls\">([^<]+)千萬<").matcher(data);
			if(m.find()) {
				String result = m.group(1);
				result = result.replaceAll(",", "");
				double d = Double.parseDouble(result);
				long l = (long) (d * 10L);
				//log.info("result: " + result);
				stock.setMktCap(l);
			} else {
				m = Pattern.compile("總市值</td> <td class=\"mcFont cls\">([^<]+)百萬<").matcher(data);
				if(m.find()) {
					String result = m.group(1);
					result = result.replaceAll(",", "");
					double d = Double.parseDouble(result);
					long l = (long) (d * 1L);
					//log.info("result: " + result);
					stock.setMktCap(l);
				}
			}
		}
		
		m = Pattern.compile("恒指成份").matcher(data);
		stock.setIsHsi(m.find());
		
		return stock;
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
