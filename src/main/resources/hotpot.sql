/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : hotpot

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 13/05/2024 18:31:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限代码',
  `introduce` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限介绍',
  `deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除:0=未删除,1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permission
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色代码',
  `introduce` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色介绍',
  `deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除:0=未删除,1=已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint UNSIGNED NOT NULL COMMENT '角色ID',
  `permission_id` bigint UNSIGNED NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_permission_ibfk_1`(`role_id`) USING BTREE,
  INDEX `role_permission_ibfk_2`(`permission_id`) USING BTREE,
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录密码',
  `deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除:0=未删除,1=已删除',
  `introduce` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个性签名',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`(nickname, email, password, avatar_url) VALUES ('土豆饼oTATo', 'tanghao.null@qq.com', 'e10adc3949ba59abbe56e057f20f883e', 'https://bbs-static.miyoushe.com/static/2024/04/18/92c8ac852286ad55eb52a65efb669a20_4228166679527276625.gif');-- 密码为123456经过MD5加密

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户ID',
  `role_id` bigint UNSIGNED NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_role_ibfk_1`(`user_id`) USING BTREE,
  INDEX `user_role_ibfk_2`(`role_id`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
