package org.nightang.stock.listfinder;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nightang.db.stock.model.StockInfo;
import org.nightang.stock.pfinder.AAStockPriceFinder;
import org.nightang.ws.HttpClientWrapper;

public class HKEXListFinder {
	
	private static final Log log = LogFactory.getLog(AAStockPriceFinder.class);
	
	private HttpClientWrapper hw;
	
	public HKEXListFinder() throws GeneralSecurityException, IOException {
		hw = new HttpClientWrapper("network.ini", null, null);
	}
	
	public void close() throws IOException {
		hw.close();
	}
	
	public List<StockInfo> findStockList() throws IOException {
		List<StockInfo> list = new ArrayList<StockInfo>();

		String url = "http://www.hkex.com.hk/eng/market/sec_tradinfo/stockcode/eisdeqty.htm";
		String data = hw.doGet(url);
		//log.info("RAW: " + data);

		Date now = new Date();
		try {
			
			/*
			 * 	Sample:
				<tr class="tr_normal">
                	<td class="verd_black12" width="18%">03813</td>
                	<td class="verd_black12" width="42%"><a href="../../../invest/company/profile_page_e.asp?WidCoID=03813&WidCoAbbName=&Month=&langcode=e" target="_parent">POU SHENG INT'L</a></td>
                	<td class="verd_black12" width="19%">1,000</td>
                	<td class="verd_black12" width="3%" align="center">#</td>
                	<td class="verd_black12" width="3%">H</td>
                	<td class="verd_black12" width="3%">O</td>
                	<td class="verd_black12" width="3%">&nbsp;</td>
                </tr>
			 */
			String pStr = "<td[^>]*>" + "(\\d{5})" + "</td>\\s*"
						+ "<td[^>]*><a[^>]*>" + "([^<]*)" + "</a></td>\\s*"
						+ "<td[^>]*>" + "([^<]*)" + "</td>\\s*";
			Pattern p = Pattern.compile(pStr);
			Matcher m = p.matcher(data);
			
			while (m.find()) {
				MatchResult mr = m.toMatchResult();				
				StockInfo stock = new StockInfo();
				stock.setStockNum(mr.group(1));
				stock.setEngName(mr.group(2));
				stock.setBoardLot(Integer.parseInt(mr.group(3).replace(",", "")));
				stock.setActive(true);
				stock.setLastModifiedDate(now);
				list.add(stock);
			}
			log.info("Number of Stocks: " + list.size());
			
		} catch (RuntimeException re) {
			log.error("ErrMsg: " + re);
		}
		
		return list;
	}
	
}