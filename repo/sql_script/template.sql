/*
* 测试脚本
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT 'name',
  `user_name` varchar(50) DEFAULT NULL COMMENT 'userName',
  `password` varchar(50) DEFAULT NULL COMMENT 'password',
  `role_codes` json NOT NULL COMMENT 'roleCodes',
  `create_by` bigint DEFAULT NULL COMMENT 'createBy',
  `create_time` datetime DEFAULT NULL COMMENT 'createTime',
  `update_by` bigint DEFAULT NULL COMMENT 'updateBy',
  `update_time` datetime DEFAULT NULL COMMENT 'updateTime',
  `is_banned` tinyint(1) NOT NULL COMMENT 'isBanned',
  `is_super` tinyint(1) NOT NULL COMMENT 'isSuper',
  `is_del` tinyint(1) NOT NULL COMMENT 'isDel',
  `ext` json DEFAULT NULL COMMENT 'ext',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='mb_admin';

-- ----------------------------
-- Records of t_admin
-- ----------------------------
BEGIN;
INSERT INTO `t_admin` (`id`, `name`, `user_name`, `password`, `role_codes`, `create_by`, `create_time`, `update_by`, `update_time`, `is_banned`, `is_super`, `is_del`, `ext`) VALUES (1, '系统管理员', 'admin', '96e79218965eb72c92a549dd5a330112', '[]', 0, '2023-09-07 15:39:01', 0, '2023-09-07 15:39:06', 0, 1, 0, NULL);
COMMIT;

-- ----------------------------
-- Table structure for t_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_role`;
CREATE TABLE `t_admin_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_banned` tinyint(1) DEFAULT NULL,
  `is_sys_role` tinyint(1) DEFAULT NULL,
  `is_del` tinyint(1) DEFAULT NULL COMMENT 'isDel',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='mb_admin_role';

-- ----------------------------
-- Records of t_admin_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_auth_role_idx
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_role_idx`;
CREATE TABLE `t_auth_role_idx` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_code` varchar(50) DEFAULT NULL COMMENT 'roleCode',
  `auth_code` varchar(50) DEFAULT NULL COMMENT 'authCode',
  `create_by` bigint DEFAULT NULL COMMENT 'createBy',
  `create_time` datetime DEFAULT NULL COMMENT 'createTime',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='mb_auth_role_idx';

-- ----------------------------
-- Records of t_auth_role_idx
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_authorization
-- ----------------------------
DROP TABLE IF EXISTS `t_authorization`;
CREATE TABLE `t_authorization` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `authority` varchar(50) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `parent_code` varchar(50) DEFAULT NULL,
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `is_del` tinyint(1) DEFAULT NULL COMMENT 'isDel',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='mb_authorization';

-- ----------------------------
-- Records of t_authorization
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
