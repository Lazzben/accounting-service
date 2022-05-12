create table `record_tag_mapping` (
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `record_id` bigint(20) unsigned NOT NULL,
    `tag_id` bigint(20) unsigned NOT NULL,
    `status` tinyint(1) unsigned NOT NULL comment '0->ENABLE, 1->DELETED',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT NULL on update CURRENT_TIMESTAMP,
    PRIMARY KEY `pk_id` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;