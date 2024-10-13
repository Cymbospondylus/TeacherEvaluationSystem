/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.101.65_3306
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 192.168.101.65:3306
 Source Schema         : db_teacher_evaluation_system

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 13/10/2024 18:57:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'NULL' COMMENT '菜单名',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT 0 COMMENT '是否删除（0未删除 1已删除）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '管理员管理', '/manage/admin', NULL, '0', '0', 'management:admin', '#', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (2, '修改密码', NULL, NULL, '0', '0', 'admin:password', '#', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (3, '老师管理', NULL, NULL, '0', '0', 'management:tercher', '#', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (4, '学生管理', NULL, NULL, '0', '0', 'management:student', '#', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (5, '评价指标管理', NULL, NULL, '0', '0', 'management:indicator', '#', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (6, '评价结果', NULL, NULL, '0', '0', 'access:evaluation-result', '#', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (7, '管理学生个人信息', NULL, NULL, '0', '0', 'management:individual', '#', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (8, '评价老师', NULL, NULL, '0', '0', 'evaluate:teacher', '#', NULL, NULL, 0, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色权限字符串',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `del_flag` int(1) NULL DEFAULT 0 COMMENT 'del_flag',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', '0', 0, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (2, '学生', 'student', '0', 0, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(200) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `menu_id` bigint(200) NOT NULL DEFAULT 0 COMMENT '菜单id',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (2, 7);
INSERT INTO `sys_role_menu` VALUES (2, 8);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(200) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `role_id` bigint(200) NOT NULL DEFAULT 0 COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  UNIQUE INDEX `unique_idx_user_id_role_id`(`user_id`, `role_id`) USING BTREE COMMENT '用户和权限的唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (13, 2);
INSERT INTO `sys_user_role` VALUES (14, 2);
INSERT INTO `sys_user_role` VALUES (17, 2);
INSERT INTO `sys_user_role` VALUES (18, 2);
INSERT INTO `sys_user_role` VALUES (20, 1);
INSERT INTO `sys_user_role` VALUES (21, 2);

-- ----------------------------
-- Table structure for tb_indicator
-- ----------------------------
DROP TABLE IF EXISTS `tb_indicator`;
CREATE TABLE `tb_indicator`  (
  `indicator_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评价指标ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评价指标名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '评价指标描述',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`indicator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评价指标表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_indicator
-- ----------------------------
INSERT INTO `tb_indicator` VALUES (1, '教学质量', '评价教师在教学过程中的整体教学质量', '2024-10-12 10:26:10', '2024-10-12 10:26:10');
INSERT INTO `tb_indicator` VALUES (2, '课堂纪律', '评价教师在课堂上的纪律管理能力', '2024-10-12 10:26:10', '2024-10-12 10:26:10');
INSERT INTO `tb_indicator` VALUES (3, '教学态度222', '评价教师的教学态度是否认真', '2024-10-12 10:26:10', '2024-10-13 18:32:45');

-- ----------------------------
-- Table structure for tb_student
-- ----------------------------
DROP TABLE IF EXISTS `tb_student`;
CREATE TABLE `tb_student`  (
  `student_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '学生ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户表的外键（tb_user.user_id）',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '学生姓名',
  `student_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '学号，唯一标识学生',
  PRIMARY KEY (`student_id`) USING BTREE,
  UNIQUE INDEX `idx_student_number`(`student_number`) USING BTREE COMMENT '学号唯一索引',
  INDEX `fk_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_student
-- ----------------------------
INSERT INTO `tb_student` VALUES (2, 13, '1234', '1235');
INSERT INTO `tb_student` VALUES (4, 17, '李同学', '222222');
INSERT INTO `tb_student` VALUES (6, 21, '学生二', '100002');

-- ----------------------------
-- Table structure for tb_student_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `tb_student_evaluation`;
CREATE TABLE `tb_student_evaluation`  (
  `evaluation_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评价记录ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID（外键，关联用户表）',
  `teacher_id` bigint(20) NOT NULL COMMENT '教师ID（外键，关联教师表）',
  `indicator_id` bigint(20) NOT NULL COMMENT '评价指标ID（外键，关联评价指标表）',
  `score` int(11) NOT NULL COMMENT '学生给出的分数（例如1-5分）',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '评价时间',
  PRIMARY KEY (`evaluation_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '学生对教师的评价表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_student_evaluation
-- ----------------------------
INSERT INTO `tb_student_evaluation` VALUES (1, 1, 3, 2, 5, '2024-10-12 18:51:40');
INSERT INTO `tb_student_evaluation` VALUES (2, 1, 3, 3, 3, '2024-10-12 18:51:40');
INSERT INTO `tb_student_evaluation` VALUES (3, 1, 3, 1, 5, '2024-10-12 18:51:40');
INSERT INTO `tb_student_evaluation` VALUES (4, 1, 3, 4, 4, '2024-10-12 18:51:40');
INSERT INTO `tb_student_evaluation` VALUES (5, 1, 3, 1, 1, '2024-10-12 18:52:45');
INSERT INTO `tb_student_evaluation` VALUES (6, 1, 3, 4, 5, '2024-10-12 18:52:45');
INSERT INTO `tb_student_evaluation` VALUES (7, 1, 3, 2, 3, '2024-10-12 18:52:45');
INSERT INTO `tb_student_evaluation` VALUES (8, 1, 3, 3, 4, '2024-10-12 18:52:45');
INSERT INTO `tb_student_evaluation` VALUES (9, 1, 3, 3, 4, '2024-10-12 18:52:51');
INSERT INTO `tb_student_evaluation` VALUES (10, 1, 3, 2, 4, '2024-10-12 18:52:51');
INSERT INTO `tb_student_evaluation` VALUES (11, 1, 3, 4, 5, '2024-10-12 18:52:51');
INSERT INTO `tb_student_evaluation` VALUES (12, 1, 3, 1, 5, '2024-10-12 18:52:51');
INSERT INTO `tb_student_evaluation` VALUES (13, 1, 5, 1, 3, '2024-10-12 18:54:28');
INSERT INTO `tb_student_evaluation` VALUES (14, 1, 5, 7, 4, '2024-10-12 18:54:28');
INSERT INTO `tb_student_evaluation` VALUES (15, 1, 5, 2, 4, '2024-10-12 18:54:28');
INSERT INTO `tb_student_evaluation` VALUES (16, 1, 5, 3, 3, '2024-10-12 18:54:28');
INSERT INTO `tb_student_evaluation` VALUES (17, 1, 3, 1, 5, '2024-10-12 18:54:45');
INSERT INTO `tb_student_evaluation` VALUES (18, 1, 5, 4, 5, '2024-10-12 18:56:12');
INSERT INTO `tb_student_evaluation` VALUES (19, 1, 5, 1, 4, '2024-10-12 18:56:12');
INSERT INTO `tb_student_evaluation` VALUES (20, 1, 5, 7, 5, '2024-10-12 18:56:12');
INSERT INTO `tb_student_evaluation` VALUES (21, 1, 5, 2, 5, '2024-10-12 18:56:12');
INSERT INTO `tb_student_evaluation` VALUES (22, 1, 5, 3, 4, '2024-10-12 18:56:12');
INSERT INTO `tb_student_evaluation` VALUES (23, 1, 6, 3, 5, '2024-10-13 12:39:49');
INSERT INTO `tb_student_evaluation` VALUES (24, 1, 6, 1, 5, '2024-10-13 12:39:49');
INSERT INTO `tb_student_evaluation` VALUES (25, 1, 6, 7, 5, '2024-10-13 12:39:49');
INSERT INTO `tb_student_evaluation` VALUES (26, 1, 6, 2, 4, '2024-10-13 12:39:49');
INSERT INTO `tb_student_evaluation` VALUES (27, 1, 6, 4, 3, '2024-10-13 12:39:49');
INSERT INTO `tb_student_evaluation` VALUES (28, 1, 6, 2, 4, '2024-10-13 12:40:03');
INSERT INTO `tb_student_evaluation` VALUES (29, 1, 6, 7, 5, '2024-10-13 12:40:03');
INSERT INTO `tb_student_evaluation` VALUES (30, 1, 6, 1, 3, '2024-10-13 12:40:03');
INSERT INTO `tb_student_evaluation` VALUES (31, 1, 6, 4, 3, '2024-10-13 12:40:03');
INSERT INTO `tb_student_evaluation` VALUES (32, 1, 6, 3, 5, '2024-10-13 12:40:03');
INSERT INTO `tb_student_evaluation` VALUES (33, 1, 6, 4, 3, '2024-10-13 12:40:16');
INSERT INTO `tb_student_evaluation` VALUES (34, 1, 6, 1, 2, '2024-10-13 12:40:16');
INSERT INTO `tb_student_evaluation` VALUES (35, 1, 6, 2, 3, '2024-10-13 12:40:16');
INSERT INTO `tb_student_evaluation` VALUES (36, 1, 6, 3, 4, '2024-10-13 12:40:16');
INSERT INTO `tb_student_evaluation` VALUES (37, 1, 6, 7, 5, '2024-10-13 12:40:16');
INSERT INTO `tb_student_evaluation` VALUES (38, 1, 6, 4, 4, '2024-10-13 12:40:35');
INSERT INTO `tb_student_evaluation` VALUES (39, 1, 6, 2, 4, '2024-10-13 12:40:35');
INSERT INTO `tb_student_evaluation` VALUES (40, 1, 6, 3, 3, '2024-10-13 12:40:35');
INSERT INTO `tb_student_evaluation` VALUES (41, 1, 6, 7, 4, '2024-10-13 12:40:35');
INSERT INTO `tb_student_evaluation` VALUES (42, 1, 6, 1, 3, '2024-10-13 12:40:35');
INSERT INTO `tb_student_evaluation` VALUES (43, 1, 3, 1, 3, '2024-10-13 12:42:41');
INSERT INTO `tb_student_evaluation` VALUES (44, 1, 3, 3, 3, '2024-10-13 12:42:41');
INSERT INTO `tb_student_evaluation` VALUES (45, 1, 3, 2, 4, '2024-10-13 12:42:41');
INSERT INTO `tb_student_evaluation` VALUES (46, 1, 3, 4, 5, '2024-10-13 12:42:41');
INSERT INTO `tb_student_evaluation` VALUES (47, 1, 3, 7, 3, '2024-10-13 12:42:41');
INSERT INTO `tb_student_evaluation` VALUES (48, 18, 6, 7, 4, '2024-10-13 18:13:21');
INSERT INTO `tb_student_evaluation` VALUES (49, 18, 6, 4, 3, '2024-10-13 18:13:21');
INSERT INTO `tb_student_evaluation` VALUES (50, 18, 6, 1, 1, '2024-10-13 18:13:21');
INSERT INTO `tb_student_evaluation` VALUES (51, 18, 6, 3, 2, '2024-10-13 18:13:21');
INSERT INTO `tb_student_evaluation` VALUES (52, 18, 6, 2, 2, '2024-10-13 18:13:21');
INSERT INTO `tb_student_evaluation` VALUES (53, 18, 3, 2, 4, '2024-10-13 18:26:46');
INSERT INTO `tb_student_evaluation` VALUES (54, 18, 3, 7, 5, '2024-10-13 18:26:46');
INSERT INTO `tb_student_evaluation` VALUES (55, 18, 3, 4, 5, '2024-10-13 18:26:46');
INSERT INTO `tb_student_evaluation` VALUES (56, 18, 3, 3, 5, '2024-10-13 18:26:46');
INSERT INTO `tb_student_evaluation` VALUES (57, 18, 3, 1, 4, '2024-10-13 18:26:46');
INSERT INTO `tb_student_evaluation` VALUES (58, 21, 5, 2, 4, '2024-10-13 18:32:08');
INSERT INTO `tb_student_evaluation` VALUES (59, 21, 5, 8, 5, '2024-10-13 18:32:08');
INSERT INTO `tb_student_evaluation` VALUES (60, 21, 5, 4, 3, '2024-10-13 18:32:08');
INSERT INTO `tb_student_evaluation` VALUES (61, 21, 5, 1, 5, '2024-10-13 18:32:08');
INSERT INTO `tb_student_evaluation` VALUES (62, 21, 5, 3, 5, '2024-10-13 18:32:08');
INSERT INTO `tb_student_evaluation` VALUES (63, 21, 7, 1, 5, '2024-10-13 18:32:13');
INSERT INTO `tb_student_evaluation` VALUES (64, 21, 7, 3, 3, '2024-10-13 18:32:13');
INSERT INTO `tb_student_evaluation` VALUES (65, 21, 7, 8, 5, '2024-10-13 18:32:13');
INSERT INTO `tb_student_evaluation` VALUES (66, 21, 7, 2, 4, '2024-10-13 18:32:13');
INSERT INTO `tb_student_evaluation` VALUES (67, 21, 7, 4, 4, '2024-10-13 18:32:13');
INSERT INTO `tb_student_evaluation` VALUES (68, 21, 7, 2, 3, '2024-10-13 18:32:56');
INSERT INTO `tb_student_evaluation` VALUES (69, 21, 7, 3, 5, '2024-10-13 18:32:56');
INSERT INTO `tb_student_evaluation` VALUES (70, 21, 7, 1, 4, '2024-10-13 18:32:56');

-- ----------------------------
-- Table structure for tb_teacher
-- ----------------------------
DROP TABLE IF EXISTS `tb_teacher`;
CREATE TABLE `tb_teacher`  (
  `teacher_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '教师ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '教师姓名',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '教师邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '教师联系电话',
  `status` int(11) NULL DEFAULT 0 COMMENT '状态(0：在职；1：离职)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除（0:未删除;1:已删除）',
  PRIMARY KEY (`teacher_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_teacher
-- ----------------------------
INSERT INTO `tb_teacher` VALUES (3, '王老师', '123', '123', 0, '2024-10-12 14:45:29', '2024-10-12 14:45:29', 0);
INSERT INTO `tb_teacher` VALUES (5, '王老师', '12344', '45644', 0, '2024-10-12 14:56:16', '2024-10-13 18:29:47', 0);
INSERT INTO `tb_teacher` VALUES (7, '陈老师', '1211111', '222222', 0, '2024-10-13 18:29:57', '2024-10-13 18:29:57', 0);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '状态（0：正常，1：禁用）',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除（0:未删除;1:已删除）',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `idx_username_unique`(`username`) USING BTREE COMMENT '用户名唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'bzyl', '$2a$10$WM.nKK.HtW/k43GHYWZOzuLWsI2WzUXtdiwXebpgbgyiPowqEiJCS', 'https://th.bing.com/th/id/OIP.khbwpM_nB0w-T_fIhO9Z_QHaHa?rs=1&pid=ImgDetMain', 0, 'cymbospondylus@foxmail.com', '15583406885', '2024-10-09 16:55:25', '2024-10-09 16:55:28', 0);
INSERT INTO `tb_user` VALUES (13, '123', '$2a$10$kM1hlMCVtF6wzcB6y3cAnOGqem8xu324h.cAdnuXFGSdf1lDJXlOC', 'https://th.bing.com/th/id/OIP.khbwpM_nB0w-T_fIhO9Z_QHaHa?rs=1&pid=ImgDetMain', 0, '1236', '1237', '2024-10-12 15:46:40', '2024-10-12 16:06:00', 0);
INSERT INTO `tb_user` VALUES (17, 'site', '$2a$10$LfTHUth79qIW/wtmYIJ3pOirst9j.uoSNU.rCRi9etvvGApykuxU6', 'https://th.bing.com/th/id/OIP.khbwpM_nB0w-T_fIhO9Z_QHaHa?rs=1&pid=ImgDetMain', 0, '10000@qq.com', '123456', '2024-10-13 17:15:11', '2024-10-13 18:30:16', 0);
INSERT INTO `tb_user` VALUES (20, 'admin', '$2a$10$2OVx.dl1IsXog2c.jXRcmezXIva3pQ6iVhihA2l8khCZYbWXwo9Ia', 'https://th.bing.com/th/id/OIP.khbwpM_nB0w-T_fIhO9Z_QHaHa?rs=1&pid=ImgDetMain', 0, 'wueiee@163.com', '15531241524', '2024-10-13 18:29:03', '2024-10-13 18:31:32', 0);
INSERT INTO `tb_user` VALUES (21, 'student', '$2a$10$1v6AQuFzGEKBLhDjlSbp2uVh1zdF5ZsEWm7qAa03EfcVyqkwxmY7u', 'https://th.bing.com/th/id/OIP.khbwpM_nB0w-T_fIhO9Z_QHaHa?rs=1&pid=ImgDetMain', 0, '22222', '33333', '2024-10-13 18:30:36', '2024-10-13 18:32:00', 0);

SET FOREIGN_KEY_CHECKS = 1;
