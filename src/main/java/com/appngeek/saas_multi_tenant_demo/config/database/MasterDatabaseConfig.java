package com.appngeek.saas_multi_tenant_demo.config.database;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class MasterDatabaseConfig {

	@Bean("masterConfig")
	@ConfigurationProperties(prefix = "spring.master.datasource")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}

	@Bean(name = "masterDataSource")
	public DataSource masterDataSource() {
		return new HikariDataSource(hikariConfig());
	}

}