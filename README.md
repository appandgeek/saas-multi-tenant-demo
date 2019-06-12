# saas-multi-tenant-demo
How to create SaaS style multi tenant web app with Spring Boot 2 and Spring Security 5 and MySQL

Notes:
1. The master db query is using jdbc template and the tenant db is using JPA 
2. We use flyway to do the migration
3. We dont configure 2 seperate db configuration classes or separate EntityManagerFactory  or TransactionManager. Only the datasources are separate
4. TenantSpecificData is the model object in tenant db 
