package com.appngeek.saas_multi_tenant_demo.config.database;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MasterDatabaseMigrationConfig {

	private static Logger LOG = LoggerFactory.getLogger(MasterDatabaseMigrationConfig.class);

	@Autowired
	@Qualifier("masterDataSource")
	public DataSource masterDataSource;

	@Bean
	public Flyway flyway(DataSource theDataSource) {
		Flyway flyway = Flyway.configure().dataSource(masterDataSource).locations("classpath:db/migration/master").baselineOnMigrate(true)
				.outOfOrder(true).load();
		MigrationInfoService migrationInfoService = flyway.info();
		if (migrationInfoService.current() != null)
			LOG.info("Before Master Migration : {}", migrationInfoService.current().getDescription());
		flyway.repair();
		flyway.migrate();
		migrationInfoService = flyway.info();
		if (migrationInfoService.current() != null)
			LOG.info("After Master Migration : {}", migrationInfoService.current().getDescription());

		return flyway;
	}
}
