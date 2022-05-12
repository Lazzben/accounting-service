create table `record` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) unsigned NOT NULL,
    `amount` decimal(18,2) NOT NULL DEFAULT '0.00',
    `note` varchar(200) DEFAULT NULL,
    `category` tinyint(1) unsigned NOT NULL comment '0->outcome, 1->income',
    `status` tinyint(1) unsigned NOT NULL comment '0->ENABLE, 1->DELETED',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT NULL on update CURRENT_TIMESTAMP,
    PRIMARY KEY `pk_id` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;