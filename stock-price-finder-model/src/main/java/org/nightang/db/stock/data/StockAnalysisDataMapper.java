package org.nightang.db.stock.data;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.nightang.db.stock.model.StockAnalysisData;
import org.nightang.db.stock.model.StockAnalysisDataExample;

public interface StockAnalysisDataMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_ANALYSIS_DATA
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    long countByExample(StockAnalysisDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_ANALYSIS_DATA
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    int deleteByExample(StockAnalysisDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_ANALYSIS_DATA
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    int insert(StockAnalysisData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_ANALYSIS_DATA
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    int insertSelective(StockAnalysisData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_ANALYSIS_DATA
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    List<StockAnalysisData> selectByExample(StockAnalysisDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_ANALYSIS_DATA
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    int updateByExampleSelective(@Param("record") StockAnalysisData record, @Param("example") StockAnalysisDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCK_ANALYSIS_DATA
     *
     * @mbg.generated Thu May 25 15:10:59 CST 2017
     */
    int updateByExample(@Param("record") StockAnalysisData record, @Param("example") StockAnalysisDataExample example);
}