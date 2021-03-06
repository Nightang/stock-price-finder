package org.nightang.db.stock.data;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.nightang.db.stock.model.StockPrice;
import org.nightang.db.stock.model.StockPriceExample;

public interface StockPriceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_PRICE
     *
     * @mbg.generated Mon May 29 11:20:17 CST 2017
     */
    long countByExample(StockPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_PRICE
     *
     * @mbg.generated Mon May 29 11:20:17 CST 2017
     */
    int deleteByExample(StockPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_PRICE
     *
     * @mbg.generated Mon May 29 11:20:17 CST 2017
     */
    int insert(StockPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_PRICE
     *
     * @mbg.generated Mon May 29 11:20:17 CST 2017
     */
    int insertSelective(StockPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_PRICE
     *
     * @mbg.generated Mon May 29 11:20:17 CST 2017
     */
    List<StockPrice> selectByExample(StockPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_PRICE
     *
     * @mbg.generated Mon May 29 11:20:17 CST 2017
     */
    int updateByExampleSelective(@Param("record") StockPrice record, @Param("example") StockPriceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_PRICE
     *
     * @mbg.generated Mon May 29 11:20:17 CST 2017
     */
    int updateByExample(@Param("record") StockPrice record, @Param("example") StockPriceExample example);
}