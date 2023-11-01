/*
 Navicat Premium Data Transfer

 Source Server         : sever-web
 Source Server Type    : MySQL
 Source Server Version : 80030 (8.0.30)
 Source Host           : localhost:3306
 Source Schema         : mybatis_demo

 Target Server Type    : MySQL
 Target Server Version : 80030 (8.0.30)
 File Encoding         : 65001

 Date: 01/11/2023 22:27:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo`  (
  `id` varchar(36) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '主\r\n键',
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '加密后的密码',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `gender` bit(1) NULL DEFAULT NULL COMMENT '性别，1男，0女',
  `birthday` date NULL DEFAULT NULL COMMENT '出生年月',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'QQ',
  `img` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `state` int NULL DEFAULT 1 COMMENT '账号状态，1为正常，默认为1',
  `is_del` bit(1) NULL DEFAULT b'0' COMMENT '1为已删除，0为未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('34a2d2ab2ee946a49ab4a87e7f16cfb6', '李四', 'e10adc3949ba59abbe56e057f20f883e', '张三', b'1', '2021-11-01', '13212311231', '1@qq.com', '123123', 'static/img/default_header.png', '2019-11-28 10:34:22', '2019-12-09 10:58:22', 1, b'1');
INSERT INTO `userinfo` VALUES ('7afea3df1a3b4cc5bdf1', 'ldz1', 'e10adc3949ba59abbe56e057f20f883e', '刘大喆1', b'0', '1998-04-22', '13212312311', '123@163.com', '1234567', 'static/img/default_header.png', '2019-11-28 09:37:10', '2019-12-03 11:47:08', 1, b'0');
INSERT INTO `userinfo` VALUES ('7afea3df1a3b4cc5bdfcd57bfee62046', 'ldz', 'e10adc3949ba59abbe56e057f20f883e', '刘大喆', b'1', '1998-04-22', '13212312311', '123@163.com', '1234567', 'static/img/default_header.png', '2019-11-28 09:37:10', '2019-11-29 14:51:58', 1, b'0');

SET FOREIGN_KEY_CHECKS = 1;
