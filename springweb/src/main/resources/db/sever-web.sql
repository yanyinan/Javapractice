/*
 Navicat Premium Data Transfer

 Source Server         : sever-web
 Source Server Type    : MySQL
 Source Server Version : 80030 (8.0.30)
 Source Host           : localhost:3306
 Source Schema         : sever-web

 Target Server Type    : MySQL
 Target Server Version : 80030 (8.0.30)
 File Encoding         : 65001

 Date: 31/10/2023 18:16:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `file_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `size` int NOT NULL,
  `downloadLink` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `createBy` bigint NOT NULL COMMENT '文件创建人id',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES (29, 'a.png', 'image/png', 223001, '/web/12/upload/20231031/8128c978696c4b6db9c1ec1c6cf901c5.png', 12, '2023-10-31 17:31:36');
INSERT INTO `sys_file` VALUES (30, 'v2-d5f50efe8c3d39eb0e0c18a94a6065c1_r.jpg', 'image/jpeg', 1377649, '/web/12/upload/20231031/1034e21dce4145559a75ff048abfbfb5.jpg', 12, '2023-10-31 17:32:49');
INSERT INTO `sys_file` VALUES (32, 'v2-3d26994eaaf2ba37a4635b693a6f0747_r.jpg', 'image/jpeg', 145613, '/web/14/upload/20231031/70d0702ab25f463691fb32e772c005b0.jpg', 14, '2023-10-31 17:43:54');
INSERT INTO `sys_file` VALUES (33, 'v2-d5f50efe8c3d39eb0e0c18a94a6065c1_r.jpg', 'image/jpeg', 1377649, '/web/12/upload/20231031/4a2c7d54920d46b89914772a8e8b9b3d.jpg', 12, '2023-10-31 17:47:36');
INSERT INTO `sys_file` VALUES (34, '0.gif', 'image/gif', 19427, '/web/12/upload/20231031/9b45051e5c6249d89c93735c18b4e15a.gif', 12, '2023-10-31 17:47:45');
INSERT INTO `sys_file` VALUES (35, '20190627180311_uxrhl.gif', 'image/gif', 17224, '/web/12/upload/20231031/996639f68e284acfae09b030460152b3.gif', 12, '2023-10-31 17:47:52');
INSERT INTO `sys_file` VALUES (36, '75d32dd35cc09b924eefe7590ae1fbcd47ae341232b80-gon17g.png', 'image/png', 207744, '/web/12/upload/20231031/55c74cc841e642748818e2a12cfc6a2f.png', 12, '2023-10-31 17:47:58');
INSERT INTO `sys_file` VALUES (37, '8db25fdce5e33a8.gif', 'image/gif', 32255, '/web/12/upload/20231031/f8fbc9c37aea4ebfa4244d52630b8efe.gif', 12, '2023-10-31 17:48:04');
INSERT INTO `sys_file` VALUES (38, '9dfe3cc43a173aed49a0e53e47c2db330a5f3eb1277bf-FTmm1X - 副本.png', 'image/png', 161727, '/web/12/upload/20231031/a2d90f0105d34ba09899740e09e9c65b.png', 12, '2023-10-31 17:48:10');
INSERT INTO `sys_file` VALUES (39, '9402ecebb791d2ce5eddab99047658bb63300a8d34de0-AF9BV5 - 副本.png', 'image/png', 216544, '/web/12/upload/20231031/2cf0c6a997124b75ba5648edfbec48c5.png', 12, '2023-10-31 17:48:47');
INSERT INTO `sys_file` VALUES (40, '20190615172632_vf3Y2.gif', 'image/gif', 1067927, '/web/12/upload/20231031/4036b25cb9f149d5950ab02884035218.gif', 12, '2023-10-31 17:50:06');
INSERT INTO `sys_file` VALUES (41, '2aef6d7f66a1f86101fe4ab030d4f3a2d27d798114eb2a-LUxCU5.png', 'image/png', 1370922, '/web/12/upload/20231031/e3fff987e7624266a1544fa83eb88d48.png', 12, '2023-10-31 18:15:09');
INSERT INTO `sys_file` VALUES (42, '去问问.gif', 'image/gif', 762708, '/web/12/upload/20231031/3b0fccbae7944e8fbcfc54918d22dd91.gif', 12, '2023-10-31 18:15:17');
INSERT INTO `sys_file` VALUES (43, '中文.png', 'image/png', 7982, '/web/12/upload/20231031/9165c29aa9e94e2294c4f9f9858e2fe1.png', 12, '2023-10-31 18:15:29');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '昵称',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phonenumber` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
  `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `user_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '用户类型（0管理员，1普通用户）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人的用户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (10, '1213', 'qweer', '5a6ee694e8c79e3e87485e41de5cf231', '0', '1211@qq.com', '121', '1', '/web/1213/avatar//c330f8f134dc42de932f0685ddffbb69.gif', '2', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (11, 'qwer', 'qwer', 'c1987fbb25dd5dc305ab2f00fe465eeb', '0', NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (12, 'kaifamiao', 'kaifamiao', '49c63c9000e716c92f7adf207634b031', '0', NULL, NULL, NULL, '/web/kaifamiao/avatar//98da271ea6da466f9c62f26c23cc58f6.jpg', '1', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (14, 'qsc', 'qsc', 'a19bc5e164cf51b42115667c3b4c3932', '0', NULL, NULL, NULL, NULL, '2', NULL, NULL, NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
