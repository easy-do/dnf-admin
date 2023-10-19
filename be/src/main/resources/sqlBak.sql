-- d_taiwan.da_item definition

CREATE TABLE `da_item` (
                           `id` bigint(20) NOT NULL COMMENT '装备id',
                           `name` varchar(64) NOT NULL COMMENT '名称',
                           `type` varchar(12) DEFAULT NULL COMMENT '类型',
                           `rarity` varchar(6) DEFAULT NULL COMMENT '稀有度'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物品缓存';


-- d_taiwan.da_sign_in_conf definition

CREATE TABLE `da_sign_in_conf` (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                   `config_name` varchar(32) NOT NULL COMMENT '配置名称',
                                   `config_date` date NOT NULL COMMENT '签到时间',
                                   `config_json` text NOT NULL COMMENT '配置数据',
                                   `remark` varchar(100) DEFAULT NULL COMMENT '备注',
                                   `create_id` int(11) DEFAULT NULL,
                                   `create_time` datetime DEFAULT NULL,
                                   `update_time` datetime DEFAULT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='签到配置';


-- d_taiwan.da_sign_in_log definition

CREATE TABLE `da_sign_in_log` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                  `config_id` bigint(20) NOT NULL,
                                  `data_json` text COMMENT '签到信息',
                                  `sign_in_user_id` int(11) DEFAULT NULL COMMENT '签到账号',
                                  `sign_in_role_id` int(11) DEFAULT NULL COMMENT '签到角色',
                                  `create_time` datetime DEFAULT NULL COMMENT '签到时间',
                                  `update_time` datetime DEFAULT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COMMENT='签到记录';
