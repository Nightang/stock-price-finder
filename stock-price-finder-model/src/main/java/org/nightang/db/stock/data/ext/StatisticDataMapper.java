package org.nightang.db.stock.data.ext;

import org.apache.ibatis.annotations.Param;

public interface StatisticDataMapper {
	
	int updateStockPriceSequence();
	
	int updateStockPriceSequenceForNullOnly();

    int insertMA(@Param("stockNum") String stockNum, @Param("adType") String adType, @Param("maParam") int maParam);

    int insertAllStockMA(@Param("adType") String adType, @Param("maParam") int maParam);

    int insertAllStockMAForNullOnly(@Param("adType") String adType, @Param("maParam") int maParam);

    int deleteAllStockMA(@Param("adType") String adType, @Param("dayRange") String dayRange);

}
