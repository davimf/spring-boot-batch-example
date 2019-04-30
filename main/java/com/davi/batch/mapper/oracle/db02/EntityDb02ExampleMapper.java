package com.davi.batch.mapper.oracle.db02;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.davi.batch.entity.oracle.EntityDb02Example;


@Mapper
public interface EntityDb02ExampleMapper {
	
	static String EXIST_ENTITY = 
		  " SELECT DECODE(COUNT(*), 0, 0, 1) "
		+ " FROM TABLE_DB02 "
		+ " WHERE ID = #{code} ";
	
	static String UPDATE_FIELD = 
		  " UPDATE TABLE_DB02 "
		+ " SET DATE = #{exampleDate} "
		+ " WHERE ID = #{code} ";
	
	static String INSERT_ENTITY = 
		  " INSERT INTO TABLE_DB02 "
		+ " (ID, TEXT) "
		+ " VALUES (#{code}, #{text}) ";
	
	@Select(EXIST_ENTITY)
	boolean existEntity(final EntityDb02Example entity);
	
	@Update(UPDATE_FIELD)
	void updateField(final EntityDb02Example entity);
	
	@Insert(INSERT_ENTITY)
	void insertEntity(final EntityDb02Example entity);

}
