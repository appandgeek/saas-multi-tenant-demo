CREATE TABLE IF NOT EXISTS `tenant_specific_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `current_tenant_id` bigint(20) DEFAULT NULL,
  `sample_data` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


