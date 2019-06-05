package com.appngeek.saas_multi_tenant_demo.config.database;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.appngeek.saas_multi_tenant_demo.repo.util.DBUtil;
import com.appngeek.saas_multi_tenant_demo.repo.util.TenantContext;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class TenantAwareDataSource extends AbstractRoutingDataSource {

	private Map<Object, DataSource> resolvedDataSources = new HashMap<>();

	@Autowired
	@Qualifier("masterDataSource")
	public DataSource masterDataSource;

	@Autowired
	@Qualifier("masterConfig")
	private HikariConfig hikariConfig;

	@Autowired
	private TenantDatabaseMigrationService tenantDatabaseMigrationService;

	@Autowired
	private TenantResolver tenantResolver;

	@Override
	public void afterPropertiesSet() {
		super.setDefaultTargetDataSource(masterDataSource);
		super.setTargetDataSources(new HashMap<>());
		super.afterPropertiesSet();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return TenantContext.getCurrentTenant();
	}

	@Override
	protected DataSource determineTargetDataSource() {
		Long tenantId = (Long) determineCurrentLookupKey();
		if (tenantId == null)
			return masterDataSource;

		DataSource tenantDataSource = resolvedDataSources.get(tenantId);
		if (tenantDataSource == null) {
			tenantDataSource = createDataSourceForTenantId(tenantId);
			tenantDatabaseMigrationService.flywayMigrate(tenantDataSource);
			resolvedDataSources.put(tenantId, tenantDataSource);
		}

		return tenantDataSource;
	}

	private DataSource createDataSourceForTenantId(Long tenantId) {
		String tenantDatabaseName = tenantResolver.findDataBaseNameByTenantId(tenantId);
		if (tenantDatabaseName == null)
			throw new IllegalArgumentException("Given tenant id is not valid : " + tenantId);

		HikariConfig tenantHikariConfig = new HikariConfig();
		hikariConfig.copyStateTo(tenantHikariConfig);
		String tenantJdbcURL = DBUtil.databaseURLFromMYSQLJdbcUrl(hikariConfig.getJdbcUrl(), tenantDatabaseName);
		tenantHikariConfig.setJdbcUrl(tenantJdbcURL);
		tenantHikariConfig.setPoolName(tenantDatabaseName + "-db-pool");
		return new HikariDataSource(tenantHikariConfig);
	}

}