package com.davi.batch.config;

import java.sql.SQLException;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ConfigurationProperties("spring.informix.db02.datasource")
@MapperScan(
	basePackages = "com.davi.batch.mapper.informix.db02", 
	sqlSessionFactoryRef = InformixDB02DataSourceConfig.SQL_SESSION_FACTORY_NAME)
public class InformixDB02DataSourceConfig {
	
	public static final String SQL_SESSION_FACTORY_NAME = "informixU18DataSourceSessionFactory";
	
	@NotNull private String dataSourceClassName;
	@NotNull private String username;
	@NotNull private String password;
	@NotNull private String serverName;
	@NotNull private String IfxIFXHost;
	@NotNull private Integer portNumber;
	@NotNull private String dataBaseName;

	@Bean(name = "informixDb02DataSource")
	public DataSource informixDb02DataSource() throws SQLException {
		HikariConfig config = new HikariConfig();
		config.setPoolName("POOL-IFXDB02");
		config.setDataSourceClassName(dataSourceClassName);
		config.addDataSourceProperty("serverName", serverName);
		config.addDataSourceProperty("IfxIFXHOST", IfxIFXHost);
		config.addDataSourceProperty("PortNumber", portNumber);
		config.addDataSourceProperty("databaseName", dataBaseName);
		config.addDataSourceProperty("user", username);
		config.addDataSourceProperty("password", password);
		config.setMaximumPoolSize(10);
		config.setAutoCommit(false);
		config.setReadOnly(true);
		config.setConnectionTestQuery(" SELECT FIRST 1 * FROM TABLE ");
		
		return new HikariDataSource(config);
	}

	@Bean(name = InformixDB02DataSourceConfig.SQL_SESSION_FACTORY_NAME)
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

		org.apache.ibatis.session.Configuration ibatisConfiguration = new org.apache.ibatis.session.Configuration();
		ibatisConfiguration.setJdbcTypeForNull(JdbcType.NULL);

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setConfiguration(ibatisConfiguration);
		sqlSessionFactoryBean.setDataSource(informixDb02DataSource());

		return sqlSessionFactoryBean.getObject();
	}

	public void setDataSourceClassName(String dataSourceClassName) {
		this.dataSourceClassName = dataSourceClassName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public void setIfxIFXHost(String ifxIFXHost) {
		IfxIFXHost = ifxIFXHost;
	}

	public void setPortNumber(Integer portNumber) {
		this.portNumber = portNumber;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

}
