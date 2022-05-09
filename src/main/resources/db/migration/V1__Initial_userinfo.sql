create TABLE `userinfo` (
	`id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
	`username` varchar(64) NOT NULL,
	`password` varchar(64) NOT NULL,
	`salt` varchar(64) NOT NULL,
	`create_time` datetime NOT NULL,
	`update_time` datetime DEFAULT NULL ON update CURRENT_TIMESTAMP,
	PRIMARY KEY `pk_id` (`id`),
	UNIQUE KEY `UK_username` (`username`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into userinfo (username, password, salt, create_time) values ('lazyben', 'SN5XbOYo7U+RxiTfm3tgzggz9DicNctyt43+IVw9mFs=', '9daecb59-fa8a-426a-996a-3d411f362764', now());