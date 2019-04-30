package com.davi.batch.mapper.informix.db01;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.davi.batch.entity.informix.EntityLegacyDb01Example;


@Mapper
public interface EntityLegacyDb01ExampleMapper {
	
	static String GET_ALL_ACTIVE = 
		" SELECT "
				+ " CODE, VALUE "
			+ " FROM "
				+ " LEGACY_DB_01_TABLE "
			+ " WHERE "
				+ " START_DATE >= TODAY ";
	
	@Select(GET_ALL_ACTIVE)
	@Results ({
		@Result(property = "code", column = "CODE"),
		@Result(property = "value", column = "VALUE")
	})
	List<EntityLegacyDb01Example> getAllActive();
	
}
