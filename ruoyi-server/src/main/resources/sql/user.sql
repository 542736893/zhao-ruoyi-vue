-- 创建数据库
CREATE DATABASE IF NOT EXISTS zhaoruoyi DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE zhaoruoyi;

-- 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(30) NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phone_number` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- 插入测试数据
INSERT INTO `user` VALUES 
(1, 'admin', '管理员', 'admin@ruoyi.com', '15888888888', '1', '', '$2a$10$7JB720yubVSOfvam/RtmyO3RFwEYLWKVRdUb/sXpGkeyBqH.a/ANG', '0', '0', '127.0.0.1', '2024-01-01 00:00:00', 'admin', '2024-01-01 00:00:00', '', NULL, '管理员'),
(2, 'ruoyi', '若依', 'ruoyi@qq.com', '15666666666', '1', '', '$2a$10$7JB720yubVSOfvam/RtmyO3RFwEYLWKVRdUb/sXpGkeyBqH.a/ANG', '0', '0', '127.0.0.1', '2024-01-01 00:00:00', 'admin', '2024-01-01 00:00:00', '', NULL, '测试员');
