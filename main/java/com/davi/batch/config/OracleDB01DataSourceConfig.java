package com.davi.batch.config;

import java.sql.SQLException;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ConfigurationProperties("spring.oracle.db01.datasource")
@MapperScan(
	basePackages = "com.davi.batch.mapper.oracle.db01", 
	sqlSessionFactoryRef = OracleDB01DataSourceConfig.SQL_SESSION_FACTORY_NAME)
public class OracleDB01DataSourceConfig {
	
	public static final String SQL_SESSION_FACTORY_NAME = "oraclePpwSessionFactory";
	
	@NotNull private String username;
	@NotNull private String password;
	@NotNull private String url;
	@NotNull private String driverClassName;

	@Bean(name = "oracleDb01DataSource")
	@Primary
	public DataSource oracleDb01DataSource() throws SQLException {
		return DataSourceBuilder.create().driverClassName(driverClassName)
				.url(url)
				.username(username)
				.password(password)
				.build();
	}

	@Bean(name = OracleDB01DataSourceConfig.SQL_SESSION_FACTORY_NAME)
	// The Spring requires (only) one DataSource as Primary
	@Primary
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

		org.apache.ibatis.session.Configuration ibatisConfiguration = new org.apache.ibatis.session.Configuration();
		ibatisConfiguration.setJdbcTypeForNull(JdbcType.NULL);

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setConfiguration(ibatisConfiguration);
		sqlSessionFactoryBean.setDataSource(oracleDb01DataSource());

		return sqlSessionFactoryBean.getObject();
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

}
