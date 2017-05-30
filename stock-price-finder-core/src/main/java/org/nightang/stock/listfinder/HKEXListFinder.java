package org.nightang.stock.listfinder;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nightang.db.stock.model.StockInfo;
import org.nightang.stock.pfinder.AAStockPriceFinder;
import org.nightang.ws.HttpClientWrapper;

public class HKEXListFinder implements AutoCloseable {
	
	private static final Log log = LogFactory.getLog(AAStockPriceFinder.class);
	
	private HttpClientWrapper hw;
	
	public HKEXListFinder() throws GeneralSecurityException, IOException {
		hw = new HttpClientWrapper("network.ini", null, null);
	}
	
	@Override
	public void close() throws IOException {
		hw.close();
	}
	
	public List<StockInfo> findStockList() throws IOException {
		Map<String, StockInfo> map = new HashMap<String, StockInfo>();
		
		// Build and Update Stock Map
		buildStockList(map);
		try {
			updateChiName(map);
		} catch(Exception e) {
			log.error("Fail to update chinese name.", e);
		}		
		try {
			updateHSIFlag(map);
		} catch(Exception e) {
			log.error("Fail to update HSI flag.", e);
		}	
		updateMarketCap(map);
		
		// Build Stock List from Map
		List<StockInfo> list = new ArrayList<StockInfo>();
		for(Entry<String, StockInfo> entry : map.entrySet()) {
			list.add(entry.getValue());			
		}
		list.sort(new Comparator<StockInfo>() {
			@Override
			public int compare(StockInfo arg0, StockInfo arg1) {
				return arg0.getStockNum().compareTo(arg1.getStockNum());
			}			
		});
		
		return list;
	}
	
	private void buildStockList(Map<String, StockInfo> map) throws IOException {
		String url = "http://www.hkex.com.hk/eng/market/sec_tradinfo/stockcode/eisdeqty.htm";
		String data = hw.doGet(url);
		//log.info("RAW: " + data);
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
			map.put(stock.getStockNum(), stock);
		}
		log.info("Number of Stocks: " + map.size());			
	}

	private void updateChiName(Map<String, StockInfo> map) throws IOException {
		String url = "http://www.hkex.com.hk/chi/market/sec_tradinfo/stockcode/eisdeqty_c.htm";
		String data = hw.doGet(url);
		//log.info("RAW: " + data);
		// Extract Format Same as "buildStockList"
		String pStr = "<td[^>]*>" + "(\\d{5})" + "</td>\\s*"
				+ "<td[^>]*><a[^>]*>" + "([^<]*)" + "</a></td>\\s*"
				+ "<td[^>]*>" + "([^<]*)" + "</td>\\s*";
		Pattern p = Pattern.compile(pStr);
		Matcher m = p.matcher(data);
		while (m.find()) {
			MatchResult mr = m.toMatchResult();	
			String stockNum = mr.group(1);
			String chiName = mr.group(2);
			StockInfo stock = map.get(stockNum);
			if(stock != null) {
				stock.setChiName(chiName);
			}
		}
	}
	
	private void updateHSIFlag(Map<String, StockInfo> map) throws IOException {
		String url = "https://www.hsi.com.hk/HSI-Net/HSI-Net?cmd=nxgenindex&index=00001&code=HSI";
		String data = hw.doGet(url);
		//log.info("RAW: " + data);
		// Format: hcode="1"
		String pStr = "hcode=\"(\\d+)\"";
		Pattern p = Pattern.compile(pStr);
		Matcher m = p.matcher(data);
		Set<String> hsiSet = new HashSet<String>();
		while (m.find()) {
			MatchResult mr = m.toMatchResult();	
			String stockNum = paddingStockNum(mr.group(1));
			if(!hsiSet.contains(stockNum)) {
				hsiSet.add(stockNum);
			}
		}
		log.info("hsiSet.size()> "+hsiSet.size());

		for(Entry<String, StockInfo> entry : map.entrySet()) {
			StockInfo record = entry.getValue();
			if(hsiSet.contains(record.getStockNum())) {
				record.setIsHsi(true);
			} else {
				record.setIsHsi(false);				
			}			
		}
	}

	private void updateMarketCap(Map<String, StockInfo> map) throws IOException {
		int pageCount = 1;
		int pageSize = -1;
		int totalCount = -1;
		
		for(; pageCount < 60 ; pageCount++) { // 40 is hard code value. Just for avoid inf loop 
			String url = "https://www.hkex.com.hk/chi/csm/ws/Result.asmx/GetData"
					+ "?location=companySearch&SearchMethod=2&LangCode=tc&StockCode=&StockName=&Ranking=ByMC&StockType=MB&mkt=hk"
					+ "&PageNo="+pageCount+"&ATypeSHEx=&AType=&FDD=&FMM=&FYYYY=&TDD=&TMM=&TYYYY=";
			String data = hw.doGet(url);
			//log.info("RAW: " + data);
			
			// Find Market Cap
			// Format: {"thead":false,"td":[["2","941 ","中國移動有限公司","HKD 1,763,963"]],"link":"company.htm?LangCode=tc&mkt=hk&StockCode=941"}
			String pStr = "\"HKD ([0-9,]+)\"]],\"link\":\"company\\.htm\\?LangCode=tc\\&mkt=hk\\&StockCode=(\\d+)\"";
			Pattern p = Pattern.compile(pStr);
			Matcher m = p.matcher(data);
			while (m.find()) {
				MatchResult mr = m.toMatchResult();	
				//log.info("FIND2> " + mr.group(1));
				String marketCapStr = mr.group(1).replace(",", "");
				String stockNum = paddingStockNum(mr.group(2));
				StockInfo record = map.get(stockNum);
				if(record != null) {
					record.setMktCap(Long.parseLong(marketCapStr)*1000000);
				}				
			}
			
			if(pageSize == -1) {
				// Check Page End
				// Format: "PageSize":40,"TotalCount":1762,
				pStr = "\"PageSize\":(\\d+),\"TotalCount\":(\\d+)";
				p = Pattern.compile(pStr);
				m = p.matcher(data);
				while (m.find()) {
					MatchResult mr = m.toMatchResult();	
					pageSize = Integer.parseInt(mr.group(1));
					totalCount = Integer.parseInt(mr.group(2));
					log.info("pageSize: " + pageSize + "|totalCount:" + totalCount);
				}
			} else {
				log.info("Load> " + (pageCount*pageSize) + "/" + totalCount);
				if(pageCount*pageSize >= totalCount) {
					break;
				}
			}
		}
		
	}

	/*
	private void updateMarketCapFromHKEXProfile(Map<String, StockInfo> map) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for(Entry<String, StockInfo> entry : map.entrySet()) {
			StockInfo stock = entry.getValue();
			String stockNum = stock.getStockNum();
			try {
				String url = "http://www.hkex.com.hk/eng/invest/company/profile_page_e.asp?WidCoID="+stockNum+"&WidCoAbbName=&Month=&langcode=e";
				String data = hw.doGet(url);
				//log.info("RAW: " + data);
	
				// Find Listing Date:
				// Extract Format: XXX
				String pStr = "<td[^>]*>" + "(\\d{5})" + "</td>\\s*"
						+ "<td[^>]*><a[^>]*>" + "([^<]*)" + "</a></td>\\s*"
						+ "<td[^>]*>" + "([^<]*)" + "</td>\\s*";
				Pattern p = Pattern.compile(pStr);
				Matcher m = p.matcher(data);
				while (m.find()) {
					MatchResult mr = m.toMatchResult();	
					try {
						stock.setListingDate(sdf.parse(mr.group(1)));
					} catch (ParseException e) {
						log.error("Fail to parse Listing Date. StockNum: " + stockNum, e);
					}
				}
	
				// Find Market Cap:
				// Extract Format: XXX
				pStr = "Market Capitalisation[^<]*</font></b></td>\\s*"
						+ "<td[^>]*><b><font[^>]*>\\s*"
						+ "[\\D]*([\\d,]+)";
				p = Pattern.compile(pStr);
				m = p.matcher(data);
				if(m.find()) {
					MatchResult mr = m.toMatchResult();
					//log.info("FIND2> " + mr.group(1));
					stock.setMktCap(Long.parseLong(mr.group(1).replace(",", "")));
				}
			} catch(Exception e) {
				log.error("Fail to update market cap. StockNum: " + stockNum, e);
			}
		}
				
	}
	*/
	
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
