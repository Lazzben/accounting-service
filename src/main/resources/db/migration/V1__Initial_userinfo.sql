create TABLE `userinfo` (
	`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	`username` varchar(64) NOT NULL,
	`password` varchar(64) NOT NULL,
	`create_time` datetime NOT NULL,
	`update_time` datetime DEFAULT NULL ON update CURRENT_TIMESTAMP,
	PRIMARY KEY `pk_id` (`id`),
	UNIQUE KEY `UK_username` (`username`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert userinfo value (null, 'lazyben', 'lazyben', now(), null);