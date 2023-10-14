
-- d_taiwan.da_sign_in_conf definition
CREATE TABLE `da_sign_in_conf` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                  `config_name` varchar(32) NOT NULL,
                                  `config_date` date NOT NULL,
                                  `config_json` text NOT NULL,
                                  `remark` varchar(100) DEFAULT NULL,
                                  `create_id` int(11) DEFAULT NULL,
                                  `create_time` datetime DEFAULT NULL,
                                  `update_time` datetime DEFAULT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='签到配置';


-- d_taiwan.da_sign_in_log definition

CREATE TABLE `da_sign_in_log` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `config_id` bigint(20) NOT NULL,
                                 `data_json` text,
                                 `sign_in_user_id` int(11) DEFAULT NULL,
                                 `sign_in_role_id` int(11) DEFAULT NULL,
                                 `create_time` datetime DEFAULT NULL,
                                 `update_time` datetime DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='签到记录';
