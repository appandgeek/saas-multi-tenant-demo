CREATE TABLE IF NOT EXISTS `tenant` (
  `tenant_id` bigint(20) NOT NULL,
  `database_name` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `tenant_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tenant_id`),
  UNIQUE KEY `UK_database_name` (`database_name`),
  UNIQUE KEY `UK_tenant_name` (`tenant_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `user_tenant` (
  `user_name` varchar(255) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `tenant_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_name`),
  KEY `FK_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

