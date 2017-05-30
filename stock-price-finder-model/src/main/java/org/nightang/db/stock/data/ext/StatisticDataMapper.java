package org.nightang.db.stock.data.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.nightang.db.stock.model.StockPrice;

public interface StatisticDataMapper {
	
	int updateStockPriceSequence();
	
	int updateStockPriceSequenceForNullOnly();

    int insertMA(@Param("stockNum") String stockNum, @Param("adType") String adType, @Param("maParam") int maParam);

    int insertAllStockMA(@Param("adType") String adType, @Param("maParam") int maParam);

    int insertAllStockMAForNullOnly(@Param("adType") String adType, @Param("maParam") int maParam);

    int deleteAllStockMA(@Param("adType") String adType, @Param("dayRange") String dayRange);

    List<StockPrice> getLatestValidStockPriceList();
    
}
