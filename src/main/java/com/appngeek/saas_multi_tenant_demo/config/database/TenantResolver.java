package com.appngeek.saas_multi_tenant_demo.config.database;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TenantResolver {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("masterDataSource")
	public DataSource masterDataSource;

	@PostConstruct
	private void init() {
		jdbcTemplate = new JdbcTemplate(masterDataSource);
	}

	public String findDataBaseNameByTenantId(Long tenantId) {
		if (tenantId == null)
			return null;
		try {
			return jdbcTemplate.queryForObject("SELECT database_name FROM tenant WHERE tenant_id = ?", String.class, tenantId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public String findDataBaseNameByUsername(String username) {
		if (username == null)
			return null;

		try {
			return jdbcTemplate.queryForObject(
					"SELECT t.database_name FROM user_tenant ut INNER JOIN tenant t on t.id = ut.tenant_id WHERE ut.user_name =  ?",
					String.class, username);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	public Long findTenantIdByUsername(String username) {
		if (username == null)
			return null;
		try {
			return jdbcTemplate.queryForObject(
					"SELECT t.tenant_id FROM user_tenant ut INNER JOIN tenant t on t.id = ut.tenant_id WHERE ut.user_name =  ?", Long.class,
					username);

		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

}