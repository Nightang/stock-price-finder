package org.nightang.db.stock.model;

import java.util.Date;

public class StockAnalysisData {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STOCK_ANALYSIS_DATA.AD_TYPE
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    private String adType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STOCK_ANALYSIS_DATA.STOCK_NUM
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    private String stockNum;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STOCK_ANALYSIS_DATA.STOCK_DATE
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    private Date stockDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STOCK_ANALYSIS_DATA.AD_VALUE
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    private Double adValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STOCK_ANALYSIS_DATA.LAST_MODIFIED_DATE
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    private Date lastModifiedDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STOCK_ANALYSIS_DATA.AD_TYPE
     *
     * @return the value of STOCK_ANALYSIS_DATA.AD_TYPE
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public String getAdType() {
        return adType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STOCK_ANALYSIS_DATA.AD_TYPE
     *
     * @param adType the value for STOCK_ANALYSIS_DATA.AD_TYPE
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public void setAdType(String adType) {
        this.adType = adType == null ? null : adType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STOCK_ANALYSIS_DATA.STOCK_NUM
     *
     * @return the value of STOCK_ANALYSIS_DATA.STOCK_NUM
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public String getStockNum() {
        return stockNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STOCK_ANALYSIS_DATA.STOCK_NUM
     *
     * @param stockNum the value for STOCK_ANALYSIS_DATA.STOCK_NUM
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public void setStockNum(String stockNum) {
        this.stockNum = stockNum == null ? null : stockNum.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STOCK_ANALYSIS_DATA.STOCK_DATE
     *
     * @return the value of STOCK_ANALYSIS_DATA.STOCK_DATE
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public Date getStockDate() {
        return stockDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STOCK_ANALYSIS_DATA.STOCK_DATE
     *
     * @param stockDate the value for STOCK_ANALYSIS_DATA.STOCK_DATE
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public void setStockDate(Date stockDate) {
        this.stockDate = stockDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STOCK_ANALYSIS_DATA.AD_VALUE
     *
     * @return the value of STOCK_ANALYSIS_DATA.AD_VALUE
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public Double getAdValue() {
        return adValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STOCK_ANALYSIS_DATA.AD_VALUE
     *
     * @param adValue the value for STOCK_ANALYSIS_DATA.AD_VALUE
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public void setAdValue(Double adValue) {
        this.adValue = adValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STOCK_ANALYSIS_DATA.LAST_MODIFIED_DATE
     *
     * @return the value of STOCK_ANALYSIS_DATA.LAST_MODIFIED_DATE
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STOCK_ANALYSIS_DATA.LAST_MODIFIED_DATE
     *
     * @param lastModifiedDate the value for STOCK_ANALYSIS_DATA.LAST_MODIFIED_DATE
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}