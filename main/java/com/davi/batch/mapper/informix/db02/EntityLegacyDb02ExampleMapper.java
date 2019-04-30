package com.davi.batch.mapper.informix.db02;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface EntityLegacyDb02ExampleMapper {
	
	static String IS_VALID  = 
	  " SELECT DECODE(COUNT(*), 0, 0, 1) "
				+ " FROM LEGACY_DB_02_TABLE "
				+ " WHERE ID = #{code} ";
	
	@Select(IS_VALID)
	boolean isValid(@Param(value = "code") final Integer code);
	
}
