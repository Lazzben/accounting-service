create TABLE `tag` (
	`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	`user_id` bigint(20) NOT NULL,
	`description` varchar(64) NOT NULL,
	`status` varchar(10) NOT NULL,
	`create_time` datetime NOT NULL,
	`update_time` datetime DEFAULT NULL ON update CURRENT_TIMESTAMP,
	PRIMARY KEY `pk_id` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into `tag` (user_id, description, status, create_time) values (1, 'shopping', 'ENABLE', now());
insert into `tag` (user_id, description, status, create_time) values (1, 'house', 'ENABLE', now());
insert into `tag` (user_id, description, status, create_time) values (1, 'read', 'ENABLE', now());