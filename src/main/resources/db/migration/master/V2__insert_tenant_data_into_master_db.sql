INSERT INTO tenant (`tenant_id`, `database_name`,`enabled`,`tenant_name`) 
VALUES (1,"tenant_1", true, "tenant_1");

INSERT INTO tenant (`tenant_id`, `database_name`,`enabled`,`tenant_name`) 
VALUES (2,"tenant_2", true, "tenant_2");

INSERT INTO user_tenant (`user_name`, `enabled`, `tenant_id`)
VALUES ("user_1_1", true, 1);

INSERT INTO user_tenant (`user_name`, `enabled`, `tenant_id`)
VALUES ("user_1_2", true, 1);

INSERT INTO user_tenant (`user_name`, `enabled`, `tenant_id`)
VALUES ("user_2_1", true, 2);

INSERT INTO user_tenant (`user_name`, `enabled`, `tenant_id`)
VALUES ("user_2_2", true, 2);