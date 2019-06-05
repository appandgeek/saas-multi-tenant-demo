package com.appngeek.saas_multi_tenant_demo.config.database;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TenantDatabaseMigrationService {

	private static Logger LOG = LoggerFactory.getLogger(TenantDatabaseMigrationService.class);

	public Boolean flywayMigrate(DataSource tenantDataSource) {
		try {
			Flyway flyway = Flyway.configure().dataSource(tenantDataSource).locations("classpath:db/migration/tenant")
					.baselineOnMigrate(true).outOfOrder(true).load();
			MigrationInfoService migrationInfoService = flyway.info();
			if (migrationInfoService.current() != null)
				LOG.info("Before Tenant Migration : {}", migrationInfoService.current().getDescription());
			flyway.repair();
			flyway.migrate();
			migrationInfoService = flyway.info();
			if (migrationInfoService.current() != null)
				LOG.info("After Tenant Migration : {}", migrationInfoService.current().getDescription());
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
