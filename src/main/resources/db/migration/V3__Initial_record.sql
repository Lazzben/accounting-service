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

insert into `record` (user_id, amount, note, category, status, create_time) values (1, '16.10', '买房', 0, 1, now());
insert into `record` (user_id, amount, note, category, status, create_time) values (1, '20.10', '购物和买书', 0, 1, now());
insert into `record` (user_id, amount, note, category, status, create_time) values (1, '50.10', '买书', 0, 1, now());