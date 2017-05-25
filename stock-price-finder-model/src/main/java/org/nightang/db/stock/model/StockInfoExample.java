package org.nightang.db.stock.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockInfoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table STOCK_INFO
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table STOCK_INFO
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table STOCK_INFO
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_INFO
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    public StockInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_INFO
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_INFO
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_INFO
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_INFO
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_INFO
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_INFO
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_INFO
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_INFO
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_INFO
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_INFO
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table STOCK_INFO
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> listingDateCriteria;

        protected List<Criterion> lastModifiedDateCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            listingDateCriteria = new ArrayList<Criterion>();
            lastModifiedDateCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getListingDateCriteria() {
            return listingDateCriteria;
        }

        protected void addListingDateCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            listingDateCriteria.add(new Criterion(condition, value, "nightang.ibatis.DateOnlyToTextTypeHandler"));
            allCriteria = null;
        }

        protected void addListingDateCriterion(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            listingDateCriteria.add(new Criterion(condition, value1, value2, "nightang.ibatis.DateOnlyToTextTypeHandler"));
            allCriteria = null;
        }

        public List<Criterion> getLastModifiedDateCriteria() {
            return lastModifiedDateCriteria;
        }

        protected void addLastModifiedDateCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            lastModifiedDateCriteria.add(new Criterion(condition, value, "nightang.ibatis.DateToTextTypeHandler"));
            allCriteria = null;
        }

        protected void addLastModifiedDateCriterion(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            lastModifiedDateCriteria.add(new Criterion(condition, value1, value2, "nightang.ibatis.DateToTextTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || listingDateCriteria.size() > 0
                || lastModifiedDateCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(listingDateCriteria);
                allCriteria.addAll(lastModifiedDateCriteria);
            }
            return allCriteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
            allCriteria = null;
        }

        public Criteria andStockNumIsNull() {
            addCriterion("STOCK_NUM is null");
            return (Criteria) this;
        }

        public Criteria andStockNumIsNotNull() {
            addCriterion("STOCK_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andStockNumEqualTo(String value) {
            addCriterion("STOCK_NUM =", value, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumNotEqualTo(String value) {
            addCriterion("STOCK_NUM <>", value, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumGreaterThan(String value) {
            addCriterion("STOCK_NUM >", value, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumGreaterThanOrEqualTo(String value) {
            addCriterion("STOCK_NUM >=", value, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumLessThan(String value) {
            addCriterion("STOCK_NUM <", value, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumLessThanOrEqualTo(String value) {
            addCriterion("STOCK_NUM <=", value, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumLike(String value) {
            addCriterion("STOCK_NUM like", value, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumNotLike(String value) {
            addCriterion("STOCK_NUM not like", value, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumIn(List<String> values) {
            addCriterion("STOCK_NUM in", values, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumNotIn(List<String> values) {
            addCriterion("STOCK_NUM not in", values, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumBetween(String value1, String value2) {
            addCriterion("STOCK_NUM between", value1, value2, "stockNum");
            return (Criteria) this;
        }

        public Criteria andStockNumNotBetween(String value1, String value2) {
            addCriterion("STOCK_NUM not between", value1, value2, "stockNum");
            return (Criteria) this;
        }

        public Criteria andEngNameIsNull() {
            addCriterion("ENG_NAME is null");
            return (Criteria) this;
        }

        public Criteria andEngNameIsNotNull() {
            addCriterion("ENG_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andEngNameEqualTo(String value) {
            addCriterion("ENG_NAME =", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameNotEqualTo(String value) {
            addCriterion("ENG_NAME <>", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameGreaterThan(String value) {
            addCriterion("ENG_NAME >", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameGreaterThanOrEqualTo(String value) {
            addCriterion("ENG_NAME >=", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameLessThan(String value) {
            addCriterion("ENG_NAME <", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameLessThanOrEqualTo(String value) {
            addCriterion("ENG_NAME <=", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameLike(String value) {
            addCriterion("ENG_NAME like", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameNotLike(String value) {
            addCriterion("ENG_NAME not like", value, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameIn(List<String> values) {
            addCriterion("ENG_NAME in", values, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameNotIn(List<String> values) {
            addCriterion("ENG_NAME not in", values, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameBetween(String value1, String value2) {
            addCriterion("ENG_NAME between", value1, value2, "engName");
            return (Criteria) this;
        }

        public Criteria andEngNameNotBetween(String value1, String value2) {
            addCriterion("ENG_NAME not between", value1, value2, "engName");
            return (Criteria) this;
        }

        public Criteria andMktCapIsNull() {
            addCriterion("MKT_CAP is null");
            return (Criteria) this;
        }

        public Criteria andMktCapIsNotNull() {
            addCriterion("MKT_CAP is not null");
            return (Criteria) this;
        }

        public Criteria andMktCapEqualTo(Long value) {
            addCriterion("MKT_CAP =", value, "mktCap");
            return (Criteria) this;
        }

        public Criteria andMktCapNotEqualTo(Long value) {
            addCriterion("MKT_CAP <>", value, "mktCap");
            return (Criteria) this;
        }

        public Criteria andMktCapGreaterThan(Long value) {
            addCriterion("MKT_CAP >", value, "mktCap");
            return (Criteria) this;
        }

        public Criteria andMktCapGreaterThanOrEqualTo(Long value) {
            addCriterion("MKT_CAP >=", value, "mktCap");
            return (Criteria) this;
        }

        public Criteria andMktCapLessThan(Long value) {
            addCriterion("MKT_CAP <", value, "mktCap");
            return (Criteria) this;
        }

        public Criteria andMktCapLessThanOrEqualTo(Long value) {
            addCriterion("MKT_CAP <=", value, "mktCap");
            return (Criteria) this;
        }

        public Criteria andMktCapIn(List<Long> values) {
            addCriterion("MKT_CAP in", values, "mktCap");
            return (Criteria) this;
        }

        public Criteria andMktCapNotIn(List<Long> values) {
            addCriterion("MKT_CAP not in", values, "mktCap");
            return (Criteria) this;
        }

        public Criteria andMktCapBetween(Long value1, Long value2) {
            addCriterion("MKT_CAP between", value1, value2, "mktCap");
            return (Criteria) this;
        }

        public Criteria andMktCapNotBetween(Long value1, Long value2) {
            addCriterion("MKT_CAP not between", value1, value2, "mktCap");
            return (Criteria) this;
        }

        public Criteria andBoardLotIsNull() {
            addCriterion("BOARD_LOT is null");
            return (Criteria) this;
        }

        public Criteria andBoardLotIsNotNull() {
            addCriterion("BOARD_LOT is not null");
            return (Criteria) this;
        }

        public Criteria andBoardLotEqualTo(Integer value) {
            addCriterion("BOARD_LOT =", value, "boardLot");
            return (Criteria) this;
        }

        public Criteria andBoardLotNotEqualTo(Integer value) {
            addCriterion("BOARD_LOT <>", value, "boardLot");
            return (Criteria) this;
        }

        public Criteria andBoardLotGreaterThan(Integer value) {
            addCriterion("BOARD_LOT >", value, "boardLot");
            return (Criteria) this;
        }

        public Criteria andBoardLotGreaterThanOrEqualTo(Integer value) {
            addCriterion("BOARD_LOT >=", value, "boardLot");
            return (Criteria) this;
        }

        public Criteria andBoardLotLessThan(Integer value) {
            addCriterion("BOARD_LOT <", value, "boardLot");
            return (Criteria) this;
        }

        public Criteria andBoardLotLessThanOrEqualTo(Integer value) {
            addCriterion("BOARD_LOT <=", value, "boardLot");
            return (Criteria) this;
        }

        public Criteria andBoardLotIn(List<Integer> values) {
            addCriterion("BOARD_LOT in", values, "boardLot");
            return (Criteria) this;
        }

        public Criteria andBoardLotNotIn(List<Integer> values) {
            addCriterion("BOARD_LOT not in", values, "boardLot");
            return (Criteria) this;
        }

        public Criteria andBoardLotBetween(Integer value1, Integer value2) {
            addCriterion("BOARD_LOT between", value1, value2, "boardLot");
            return (Criteria) this;
        }

        public Criteria andBoardLotNotBetween(Integer value1, Integer value2) {
            addCriterion("BOARD_LOT not between", value1, value2, "boardLot");
            return (Criteria) this;
        }

        public Criteria andIsHsiIsNull() {
            addCriterion("IS_HSI is null");
            return (Criteria) this;
        }

        public Criteria andIsHsiIsNotNull() {
            addCriterion("IS_HSI is not null");
            return (Criteria) this;
        }

        public Criteria andIsHsiEqualTo(Boolean value) {
            addCriterion("IS_HSI =", value, "isHsi");
            return (Criteria) this;
        }

        public Criteria andIsHsiNotEqualTo(Boolean value) {
            addCriterion("IS_HSI <>", value, "isHsi");
            return (Criteria) this;
        }

        public Criteria andIsHsiGreaterThan(Boolean value) {
            addCriterion("IS_HSI >", value, "isHsi");
            return (Criteria) this;
        }

        public Criteria andIsHsiGreaterThanOrEqualTo(Boolean value) {
            addCriterion("IS_HSI >=", value, "isHsi");
            return (Criteria) this;
        }

        public Criteria andIsHsiLessThan(Boolean value) {
            addCriterion("IS_HSI <", value, "isHsi");
            return (Criteria) this;
        }

        public Criteria andIsHsiLessThanOrEqualTo(Boolean value) {
            addCriterion("IS_HSI <=", value, "isHsi");
            return (Criteria) this;
        }

        public Criteria andIsHsiIn(List<Boolean> values) {
            addCriterion("IS_HSI in", values, "isHsi");
            return (Criteria) this;
        }

        public Criteria andIsHsiNotIn(List<Boolean> values) {
            addCriterion("IS_HSI not in", values, "isHsi");
            return (Criteria) this;
        }

        public Criteria andIsHsiBetween(Boolean value1, Boolean value2) {
            addCriterion("IS_HSI between", value1, value2, "isHsi");
            return (Criteria) this;
        }

        public Criteria andIsHsiNotBetween(Boolean value1, Boolean value2) {
            addCriterion("IS_HSI not between", value1, value2, "isHsi");
            return (Criteria) this;
        }

        public Criteria andListingDateIsNull() {
            addCriterion("LISTING_DATE is null");
            return (Criteria) this;
        }

        public Criteria andListingDateIsNotNull() {
            addCriterion("LISTING_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andListingDateEqualTo(Date value) {
            addListingDateCriterion("LISTING_DATE =", value, "listingDate");
            return (Criteria) this;
        }

        public Criteria andListingDateNotEqualTo(Date value) {
            addListingDateCriterion("LISTING_DATE <>", value, "listingDate");
            return (Criteria) this;
        }

        public Criteria andListingDateGreaterThan(Date value) {
            addListingDateCriterion("LISTING_DATE >", value, "listingDate");
            return (Criteria) this;
        }

        public Criteria andListingDateGreaterThanOrEqualTo(Date value) {
            addListingDateCriterion("LISTING_DATE >=", value, "listingDate");
            return (Criteria) this;
        }

        public Criteria andListingDateLessThan(Date value) {
            addListingDateCriterion("LISTING_DATE <", value, "listingDate");
            return (Criteria) this;
        }

        public Criteria andListingDateLessThanOrEqualTo(Date value) {
            addListingDateCriterion("LISTING_DATE <=", value, "listingDate");
            return (Criteria) this;
        }

        public Criteria andListingDateLike(Date value) {
            addListingDateCriterion("LISTING_DATE like", value, "listingDate");
            return (Criteria) this;
        }

        public Criteria andListingDateNotLike(Date value) {
            addListingDateCriterion("LISTING_DATE not like", value, "listingDate");
            return (Criteria) this;
        }

        public Criteria andListingDateIn(List<Date> values) {
            addListingDateCriterion("LISTING_DATE in", values, "listingDate");
            return (Criteria) this;
        }

        public Criteria andListingDateNotIn(List<Date> values) {
            addListingDateCriterion("LISTING_DATE not in", values, "listingDate");
            return (Criteria) this;
        }

        public Criteria andListingDateBetween(Date value1, Date value2) {
            addListingDateCriterion("LISTING_DATE between", value1, value2, "listingDate");
            return (Criteria) this;
        }

        public Criteria andListingDateNotBetween(Date value1, Date value2) {
            addListingDateCriterion("LISTING_DATE not between", value1, value2, "listingDate");
            return (Criteria) this;
        }

        public Criteria andActiveIsNull() {
            addCriterion("ACTIVE is null");
            return (Criteria) this;
        }

        public Criteria andActiveIsNotNull() {
            addCriterion("ACTIVE is not null");
            return (Criteria) this;
        }

        public Criteria andActiveEqualTo(Boolean value) {
            addCriterion("ACTIVE =", value, "active");
            return (Criteria) this;
        }

        public Criteria andActiveNotEqualTo(Boolean value) {
            addCriterion("ACTIVE <>", value, "active");
            return (Criteria) this;
        }

        public Criteria andActiveGreaterThan(Boolean value) {
            addCriterion("ACTIVE >", value, "active");
            return (Criteria) this;
        }

        public Criteria andActiveGreaterThanOrEqualTo(Boolean value) {
            addCriterion("ACTIVE >=", value, "active");
            return (Criteria) this;
        }

        public Criteria andActiveLessThan(Boolean value) {
            addCriterion("ACTIVE <", value, "active");
            return (Criteria) this;
        }

        public Criteria andActiveLessThanOrEqualTo(Boolean value) {
            addCriterion("ACTIVE <=", value, "active");
            return (Criteria) this;
        }

        public Criteria andActiveIn(List<Boolean> values) {
            addCriterion("ACTIVE in", values, "active");
            return (Criteria) this;
        }

        public Criteria andActiveNotIn(List<Boolean> values) {
            addCriterion("ACTIVE not in", values, "active");
            return (Criteria) this;
        }

        public Criteria andActiveBetween(Boolean value1, Boolean value2) {
            addCriterion("ACTIVE between", value1, value2, "active");
            return (Criteria) this;
        }

        public Criteria andActiveNotBetween(Boolean value1, Boolean value2) {
            addCriterion("ACTIVE not between", value1, value2, "active");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateIsNull() {
            addCriterion("LAST_MODIFIED_DATE is null");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateIsNotNull() {
            addCriterion("LAST_MODIFIED_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateEqualTo(Date value) {
            addLastModifiedDateCriterion("LAST_MODIFIED_DATE =", value, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateNotEqualTo(Date value) {
            addLastModifiedDateCriterion("LAST_MODIFIED_DATE <>", value, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateGreaterThan(Date value) {
            addLastModifiedDateCriterion("LAST_MODIFIED_DATE >", value, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateGreaterThanOrEqualTo(Date value) {
            addLastModifiedDateCriterion("LAST_MODIFIED_DATE >=", value, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateLessThan(Date value) {
            addLastModifiedDateCriterion("LAST_MODIFIED_DATE <", value, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateLessThanOrEqualTo(Date value) {
            addLastModifiedDateCriterion("LAST_MODIFIED_DATE <=", value, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateLike(Date value) {
            addLastModifiedDateCriterion("LAST_MODIFIED_DATE like", value, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateNotLike(Date value) {
            addLastModifiedDateCriterion("LAST_MODIFIED_DATE not like", value, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateIn(List<Date> values) {
            addLastModifiedDateCriterion("LAST_MODIFIED_DATE in", values, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateNotIn(List<Date> values) {
            addLastModifiedDateCriterion("LAST_MODIFIED_DATE not in", values, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateBetween(Date value1, Date value2) {
            addLastModifiedDateCriterion("LAST_MODIFIED_DATE between", value1, value2, "lastModifiedDate");
            return (Criteria) this;
        }

        public Criteria andLastModifiedDateNotBetween(Date value1, Date value2) {
            addLastModifiedDateCriterion("LAST_MODIFIED_DATE not between", value1, value2, "lastModifiedDate");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table STOCK_INFO
     *
     * @mbg.generated do_not_delete_during_merge Thu May 25 15:10:59 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table STOCK_INFO
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}