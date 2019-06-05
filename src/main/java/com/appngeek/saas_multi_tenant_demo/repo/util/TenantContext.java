package com.appngeek.saas_multi_tenant_demo.repo.util;

public class TenantContext {
	
    private static ThreadLocal<Long> currentTenant = new ThreadLocal<>();

    public static void setCurrentTenant(Long tenantId) {
        currentTenant.set(tenantId);
    }

    public static Long getCurrentTenant() {
        return currentTenant.get();
    }
}