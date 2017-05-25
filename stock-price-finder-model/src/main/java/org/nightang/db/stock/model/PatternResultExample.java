package org.nightang.db.stock.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatternResultExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public PatternResultExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
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
     * This method corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> stockDateCriteria;

        protected List<Criterion> lastModifiedDateCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            stockDateCriteria = new ArrayList<Criterion>();
            lastModifiedDateCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getStockDateCriteria() {
            return stockDateCriteria;
        }

        protected void addStockDateCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            stockDateCriteria.add(new Criterion(condition, value, "nightang.ibatis.DateOnlyToTextTypeHandler"));
            allCriteria = null;
        }

        protected void addStockDateCriterion(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            stockDateCriteria.add(new Criterion(condition, value1, value2, "nightang.ibatis.DateOnlyToTextTypeHandler"));
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
                || stockDateCriteria.size() > 0
                || lastModifiedDateCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(stockDateCriteria);
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

        public Criteria andPatternTypeIsNull() {
            addCriterion("PATTERN_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPatternTypeIsNotNull() {
            addCriterion("PATTERN_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPatternTypeEqualTo(String value) {
            addCriterion("PATTERN_TYPE =", value, "patternType");
            return (Criteria) this;
        }

        public Criteria andPatternTypeNotEqualTo(String value) {
            addCriterion("PATTERN_TYPE <>", value, "patternType");
            return (Criteria) this;
        }

        public Criteria andPatternTypeGreaterThan(String value) {
            addCriterion("PATTERN_TYPE >", value, "patternType");
            return (Criteria) this;
        }

        public Criteria andPatternTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PATTERN_TYPE >=", value, "patternType");
            return (Criteria) this;
        }

        public Criteria andPatternTypeLessThan(String value) {
            addCriterion("PATTERN_TYPE <", value, "patternType");
            return (Criteria) this;
        }

        public Criteria andPatternTypeLessThanOrEqualTo(String value) {
            addCriterion("PATTERN_TYPE <=", value, "patternType");
            return (Criteria) this;
        }

        public Criteria andPatternTypeLike(String value) {
            addCriterion("PATTERN_TYPE like", value, "patternType");
            return (Criteria) this;
        }

        public Criteria andPatternTypeNotLike(String value) {
            addCriterion("PATTERN_TYPE not like", value, "patternType");
            return (Criteria) this;
        }

        public Criteria andPatternTypeIn(List<String> values) {
            addCriterion("PATTERN_TYPE in", values, "patternType");
            return (Criteria) this;
        }

        public Criteria andPatternTypeNotIn(List<String> values) {
            addCriterion("PATTERN_TYPE not in", values, "patternType");
            return (Criteria) this;
        }

        public Criteria andPatternTypeBetween(String value1, String value2) {
            addCriterion("PATTERN_TYPE between", value1, value2, "patternType");
            return (Criteria) this;
        }

        public Criteria andPatternTypeNotBetween(String value1, String value2) {
            addCriterion("PATTERN_TYPE not between", value1, value2, "patternType");
            return (Criteria) this;
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

        public Criteria andStockDateIsNull() {
            addCriterion("STOCK_DATE is null");
            return (Criteria) this;
        }

        public Criteria andStockDateIsNotNull() {
            addCriterion("STOCK_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andStockDateEqualTo(Date value) {
            addStockDateCriterion("STOCK_DATE =", value, "stockDate");
            return (Criteria) this;
        }

        public Criteria andStockDateNotEqualTo(Date value) {
            addStockDateCriterion("STOCK_DATE <>", value, "stockDate");
            return (Criteria) this;
        }

        public Criteria andStockDateGreaterThan(Date value) {
            addStockDateCriterion("STOCK_DATE >", value, "stockDate");
            return (Criteria) this;
        }

        public Criteria andStockDateGreaterThanOrEqualTo(Date value) {
            addStockDateCriterion("STOCK_DATE >=", value, "stockDate");
            return (Criteria) this;
        }

        public Criteria andStockDateLessThan(Date value) {
            addStockDateCriterion("STOCK_DATE <", value, "stockDate");
            return (Criteria) this;
        }

        public Criteria andStockDateLessThanOrEqualTo(Date value) {
            addStockDateCriterion("STOCK_DATE <=", value, "stockDate");
            return (Criteria) this;
        }

        public Criteria andStockDateLike(Date value) {
            addStockDateCriterion("STOCK_DATE like", value, "stockDate");
            return (Criteria) this;
        }

        public Criteria andStockDateNotLike(Date value) {
            addStockDateCriterion("STOCK_DATE not like", value, "stockDate");
            return (Criteria) this;
        }

        public Criteria andStockDateIn(List<Date> values) {
            addStockDateCriterion("STOCK_DATE in", values, "stockDate");
            return (Criteria) this;
        }

        public Criteria andStockDateNotIn(List<Date> values) {
            addStockDateCriterion("STOCK_DATE not in", values, "stockDate");
            return (Criteria) this;
        }

        public Criteria andStockDateBetween(Date value1, Date value2) {
            addStockDateCriterion("STOCK_DATE between", value1, value2, "stockDate");
            return (Criteria) this;
        }

        public Criteria andStockDateNotBetween(Date value1, Date value2) {
            addStockDateCriterion("STOCK_DATE not between", value1, value2, "stockDate");
            return (Criteria) this;
        }

        public Criteria andFlagHVolumeIsNull() {
            addCriterion("FLAG_H_VOLUME is null");
            return (Criteria) this;
        }

        public Criteria andFlagHVolumeIsNotNull() {
            addCriterion("FLAG_H_VOLUME is not null");
            return (Criteria) this;
        }

        public Criteria andFlagHVolumeEqualTo(Double value) {
            addCriterion("FLAG_H_VOLUME =", value, "flagHVolume");
            return (Criteria) this;
        }

        public Criteria andFlagHVolumeNotEqualTo(Double value) {
            addCriterion("FLAG_H_VOLUME <>", value, "flagHVolume");
            return (Criteria) this;
        }

        public Criteria andFlagHVolumeGreaterThan(Double value) {
            addCriterion("FLAG_H_VOLUME >", value, "flagHVolume");
            return (Criteria) this;
        }

        public Criteria andFlagHVolumeGreaterThanOrEqualTo(Double value) {
            addCriterion("FLAG_H_VOLUME >=", value, "flagHVolume");
            return (Criteria) this;
        }

        public Criteria andFlagHVolumeLessThan(Double value) {
            addCriterion("FLAG_H_VOLUME <", value, "flagHVolume");
            return (Criteria) this;
        }

        public Criteria andFlagHVolumeLessThanOrEqualTo(Double value) {
            addCriterion("FLAG_H_VOLUME <=", value, "flagHVolume");
            return (Criteria) this;
        }

        public Criteria andFlagHVolumeIn(List<Double> values) {
            addCriterion("FLAG_H_VOLUME in", values, "flagHVolume");
            return (Criteria) this;
        }

        public Criteria andFlagHVolumeNotIn(List<Double> values) {
            addCriterion("FLAG_H_VOLUME not in", values, "flagHVolume");
            return (Criteria) this;
        }

        public Criteria andFlagHVolumeBetween(Double value1, Double value2) {
            addCriterion("FLAG_H_VOLUME between", value1, value2, "flagHVolume");
            return (Criteria) this;
        }

        public Criteria andFlagHVolumeNotBetween(Double value1, Double value2) {
            addCriterion("FLAG_H_VOLUME not between", value1, value2, "flagHVolume");
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
     * This class corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated do_not_delete_during_merge Fri May 26 16:59:24 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table PATTERN_RESULT
     *
     * @mbg.generated Fri May 26 16:59:24 CST 2017
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