package org.nightang.stock.listfinder;

import java.io.Serializable;
import java.util.List;

public class HKEXStockQueryObject implements Serializable {

	private static final long serialVersionUID = -6110165741272779654L;
	
	private HKEXStockQueryData data;
		
	public HKEXStockQueryData getData() {
		return data;
	}

	public void setData(HKEXStockQueryData data) {
		this.data = data;
	}

	public class HKEXStockQueryData {		
		private List<HKEXStockQueryItem> stocklist;

		public List<HKEXStockQueryItem> getStocklist() {
			return stocklist;
		}
		public void setStocklist(List<HKEXStockQueryItem> stocklist) {
			this.stocklist = stocklist;
		}		
	}

	public class HKEXStockQueryItem {

		private String sym;
		private String ric;
		private String nm;
		private String asset_subtype;
		private String product_subtype;
		private String mktcap;
		private String mktcap_u;
		private boolean suspend;
				
		@Override
		public String toString() {
			return "HKEXStockQueryItem [sym=" + sym + ", ric=" + ric + ", nm="
					+ nm + ", asset_subtype=" + asset_subtype
					+ ", product_subtype=" + product_subtype + ", mktcap="
					+ mktcap + ", mktcap_u=" + mktcap_u + ", suspend="
					+ suspend + "]";
		}
		
		public String getSym() {
			return sym;
		}
		public void setSym(String sym) {
			this.sym = sym;
		}
		public String getRic() {
			return ric;
		}
		public boolean isSuspend() {
			return suspend;
		}

		public void setSuspend(boolean suspend) {
			this.suspend = suspend;
		}

		public void setRic(String ric) {
			this.ric = ric;
		}
		public String getNm() {
			return nm;
		}
		public void setNm(String nm) {
			this.nm = nm;
		}
		public String getAsset_subtype() {
			return asset_subtype;
		}
		public void setAsset_subtype(String asset_subtype) {
			this.asset_subtype = asset_subtype;
		}
		public String getProduct_subtype() {
			return product_subtype;
		}
		public void setProduct_subtype(String product_subtype) {
			this.product_subtype = product_subtype;
		}
		public String getMktcap() {
			return mktcap;
		}
		public void setMktcap(String mktcap) {
			this.mktcap = mktcap;
		}
		public String getMktcap_u() {
			return mktcap_u;
		}
		public void setMktcap_u(String mktcap_u) {
			this.mktcap_u = mktcap_u;
		}	
		
	}
}
