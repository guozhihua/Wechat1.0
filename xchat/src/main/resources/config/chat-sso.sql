/*
Navicat MySQL Data Transfer

Source Server         : apple localhost
Source Server Version : 50632
Source Host           : localhost:3306
Source Database       : chat-sso

Target Server Type    : MYSQL
Target Server Version : 50632
File Encoding         : 65001

Date: 2018-01-25 18:03:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `device_info`
-- ----------------------------
DROP TABLE IF EXISTS `device_info`;
CREATE TABLE `device_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `iid` varchar(40) DEFAULT NULL,
  `uuid` varchar(40) DEFAULT NULL,
  `openid` varchar(40) DEFAULT NULL,
  `deviceid` varchar(40) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device_info
-- ----------------------------

-- ----------------------------
-- Table structure for `huo_shan_mobile_info`
-- ----------------------------
DROP TABLE IF EXISTS `huo_shan_mobile_info`;
CREATE TABLE `huo_shan_mobile_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` char(11) NOT NULL,
  `status` tinyint(4) unsigned zerofill NOT NULL DEFAULT '0000' COMMENT '是否 已经注册过了 0-没有 1注册完成',
  `invite_code` varchar(20) DEFAULT '' COMMENT '填写的邀请码是什么',
  `password` varchar(20) DEFAULT '' COMMENT '密码 默认 adm123',
  `update_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_mobile` (`mobile`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of huo_shan_mobile_info
-- ----------------------------
INSERT INTO `huo_shan_mobile_info` VALUES ('28', '15629534740', '0001', null, null, '2018-01-22 14:16:11', '2018-01-22 14:16:11');
INSERT INTO `huo_shan_mobile_info` VALUES ('29', '15629551749', '0001', null, null, '2018-01-22 14:27:48', '2018-01-22 14:27:48');
INSERT INTO `huo_shan_mobile_info` VALUES ('30', '15629534049', '0001', null, null, '2018-01-22 15:19:43', '2018-01-22 15:19:43');
INSERT INTO `huo_shan_mobile_info` VALUES ('31', '15102846427', '0001', null, null, '2018-01-22 15:31:35', '2018-01-22 15:31:35');
INSERT INTO `huo_shan_mobile_info` VALUES ('32', '15884454287', '0001', null, null, '2018-01-22 15:56:44', '2018-01-22 15:56:44');
INSERT INTO `huo_shan_mobile_info` VALUES ('33', '15992494057', '0002', null, null, '2018-01-22 16:16:47', '2018-01-22 16:16:47');
INSERT INTO `huo_shan_mobile_info` VALUES ('34', '13105849637', '0001', null, null, '2018-01-22 16:23:37', '2018-01-22 16:23:37');
INSERT INTO `huo_shan_mobile_info` VALUES ('35', '18478354076', '0002', null, null, '2018-01-23 15:25:38', '2018-01-23 15:25:38');
INSERT INTO `huo_shan_mobile_info` VALUES ('36', '18224017406', '0001', null, null, '2018-01-23 16:06:51', '2018-01-23 16:06:51');
INSERT INTO `huo_shan_mobile_info` VALUES ('37', '18384205156', '0002', null, null, '2018-01-23 16:07:59', '2018-01-23 16:07:59');
INSERT INTO `huo_shan_mobile_info` VALUES ('38', '15765911416', '0002', null, null, '2018-01-23 16:09:57', '2018-01-23 16:09:57');
INSERT INTO `huo_shan_mobile_info` VALUES ('39', '18304148703', '0001', null, null, '2018-01-23 16:11:39', '2018-01-23 16:11:39');

-- ----------------------------
-- Table structure for `invite_code_statics`
-- ----------------------------
DROP TABLE IF EXISTS `invite_code_statics`;
CREATE TABLE `invite_code_statics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `invite_code` varchar(20) NOT NULL,
  `num` int(11) unsigned zerofill NOT NULL DEFAULT '00000000000' COMMENT '成功充值数量',
  `order_num` varchar(255) DEFAULT NULL COMMENT '淘宝最后8位号码',
  `app_name` char(20) NOT NULL COMMENT '1-火山 2-芝士 -3西瓜 4-冲顶',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_ttime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of invite_code_statics
-- ----------------------------

-- ----------------------------
-- Table structure for `pages`
-- ----------------------------
DROP TABLE IF EXISTS `pages`;
CREATE TABLE `pages` (
  `page_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单表',
  `page_name` varchar(40) NOT NULL,
  `page_url` varchar(200) DEFAULT NULL,
  `page_level` tinyint(1) NOT NULL DEFAULT '0',
  `parent_id` int(1) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`page_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pages
-- ----------------------------
INSERT INTO `pages` VALUES ('1', '用户中心', null, '0', '0', '2018-01-25 16:31:39');
INSERT INTO `pages` VALUES ('2', '系统设置', null, '0', '0', '2018-01-25 16:32:05');
INSERT INTO `pages` VALUES ('3', '卡商管理', null, '0', '0', '2018-01-25 16:32:21');
INSERT INTO `pages` VALUES ('4', '财务统计', null, '0', '0', '2018-01-25 16:32:41');
INSERT INTO `pages` VALUES ('5', '卡券充值', null, '0', '0', '2018-01-25 16:32:58');
INSERT INTO `pages` VALUES ('6', '用户列表', null, '2', '14', '2018-01-25 16:33:20');
INSERT INTO `pages` VALUES ('7', '管理员列表', null, '2', '14', '2018-01-25 16:33:56');
INSERT INTO `pages` VALUES ('8', '菜单配置', null, '1', '2', '2018-01-25 16:34:32');
INSERT INTO `pages` VALUES ('9', '授权管理', null, '1', '2', '2018-01-25 16:35:13');
INSERT INTO `pages` VALUES ('10', '订单查询', null, '1', '4', '2018-01-25 16:35:33');
INSERT INTO `pages` VALUES ('11', '每日账单', null, '1', '4', '2018-01-25 16:35:53');
INSERT INTO `pages` VALUES ('13', '卡商列表', null, '1', '3', '2018-01-25 16:37:03');
INSERT INTO `pages` VALUES ('14', '用户管理', null, '1', '1', '2018-01-25 16:38:05');
INSERT INTO `pages` VALUES ('15', '用户统计', null, '1', '1', '2018-01-25 16:42:29');
INSERT INTO `pages` VALUES ('16', '日增统计', null, '2', '15', '2018-01-25 16:42:32');
INSERT INTO `pages` VALUES ('17', '月增统计', null, '2', '15', '2018-01-25 16:42:33');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` char(40) NOT NULL COMMENT '用户',
  `user_name` char(40) NOT NULL,
  `password` char(40) NOT NULL,
  `real_name` varchar(40) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('admin12738yizncy4e7823asd2139u', 'admin', 'admin123', '郭志华', '1', '2018-01-25 13:16:29');

-- ----------------------------
-- Table structure for `user_ticket`
-- ----------------------------
DROP TABLE IF EXISTS `user_ticket`;
CREATE TABLE `user_ticket` (
  `user_id` varchar(40) NOT NULL,
  `ticket` varchar(40) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_ticket
-- ----------------------------
INSERT INTO `user_ticket` VALUES ('admin12738yizncy4e7823asd2139u', '1603781DFC29D6B54ACEE0D7061306AD');

-- ----------------------------
-- Table structure for `video`
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `video_name` varchar(255) DEFAULT NULL,
  `video_id` bigint(20) DEFAULT NULL,
  `video_uuid` int(11) DEFAULT NULL,
  `video_html` varchar(255) DEFAULT NULL,
  `video_type` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video
-- ----------------------------
