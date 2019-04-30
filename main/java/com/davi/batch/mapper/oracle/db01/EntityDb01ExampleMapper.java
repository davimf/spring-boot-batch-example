package com.davi.batch.mapper.oracle.db01;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import com.davi.batch.entity.oracle.EntityDb01Example;


@Mapper
public interface EntityDb01ExampleMapper {
	
	static String EXIST_ENTITY = 
		  " SELECT DECODE(COUNT(*), 0, 0, 1) "
		+ " FROM TABLE_DB01 "
		+ " WHERE ID = #{code} ";
	
	static String UPDATE_FIELD = 
		  " UPDATE TABLE_DB01 "
		+ " SET TEXT = #{text} "
		+ " WHERE ID = #{code} ";
	
	static String INSERT_ENTITY = 
		  " INSERT INTO TABLE_DB01 "
		+ " (ID, TEXT) "
		+ " VALUES (#{code}, #{text}) ";
	
	static String EXECUTE_TASK = " CALL DB01_PROCEDURE (A, B) ";
	
	@Select(EXIST_ENTITY)
	boolean existEntity(final EntityDb01Example entity);
	
	@Update(UPDATE_FIELD)
	void updateField(final EntityDb01Example entity);
	
	@Insert(INSERT_ENTITY)
	void insertEntity(final EntityDb01Example entity);
	
	@Select(EXECUTE_TASK)
	@Options(statementType = StatementType.CALLABLE)
	void executeProcedure();

}
