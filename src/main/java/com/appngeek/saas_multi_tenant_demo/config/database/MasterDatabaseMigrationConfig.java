package com.appngeek.saas_multi_tenant_demo.config.database;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MasterDatabaseMigrationConfig {

	@Autowired
	@Qualifier("masterDataSource")
	public DataSource masterDataSource;

	@Bean
	public Flyway flyway(DataSource theDataSource) {
		Flyway flyway = Flyway.configure().dataSource(masterDataSource).locations("classpath:db/migration/master").baselineOnMigrate(true)
				.outOfOrder(true).load();
		flyway.repair();
		flyway.migrate();
		return flyway;
	}
}
