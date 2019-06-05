package com.appngeek.saas_multi_tenant_demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.appngeek.saas_multi_tenant_demo.config.database.TenantResolver;
import com.appngeek.saas_multi_tenant_demo.domain.TenantSpecificData;
import com.appngeek.saas_multi_tenant_demo.repo.TenantSpecificDataRepository;
import com.appngeek.saas_multi_tenant_demo.repo.util.TenantContext;

@RestController
@RequestMapping("/api/")
public class TenantSpecificDataController {

	@Autowired
	private TenantSpecificDataRepository tenantSpecificDataRepository;

	@Autowired
	private TenantResolver tenantResolver;

	@GetMapping("/find/tenantId")
	public List<TenantSpecificData> findByCurrentTenantId(@PathVariable Long tenantId) {
		TenantContext.setCurrentTenant(tenantId);
		return tenantSpecificDataRepository.findByCurrentTenantId(tenantId);
	}

	@GetMapping("/find/username")
	public List<TenantSpecificData> findByUsername(@PathVariable String username) {
		Long tenantId = tenantResolver.findTenantIdByUsername(username);
		TenantContext.setCurrentTenant(tenantId);
		return tenantSpecificDataRepository.findByUsername(username);
	}

	/**
	 * Push data to specific tenant db based on tenantId passed as path variable
	 * @param tenantId
	 * @param username
	 * @param tenantSpecificString
	 * @return
	 */
	@PostMapping("/create/{tenantId}/{username}")
	@ResponseStatus(HttpStatus.CREATED)
	public TenantSpecificData createTenantSpecificData(@PathVariable Long tenantId, @PathVariable String username,
			@RequestBody String tenantSpecificString) {
		TenantContext.setCurrentTenant(tenantId);
		TenantSpecificData tenantSpecificData = new TenantSpecificData();
		tenantSpecificData.setCurrentTenantId(tenantId);
		tenantSpecificData.setUsername(username);
		tenantSpecificData.setSampleData(tenantSpecificString);

		return tenantSpecificDataRepository.save(tenantSpecificData);
	}

	@DeleteMapping("/delete/{tenantId}/{username}")
	public void delete(@PathVariable Long tenantId, @PathVariable String username, @RequestBody String tenantSpecificString) {
		TenantContext.setCurrentTenant(tenantId);
		List<TenantSpecificData> tenantSpecificDataList = tenantSpecificDataRepository.findByUsername(username);
		tenantSpecificDataRepository.deleteAll(tenantSpecificDataList);
	}

}
