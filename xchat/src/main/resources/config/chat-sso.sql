/*
Navicat MySQL Data Transfer

Source Server         : apple localhost
Source Server Version : 50632
Source Host           : localhost:3306
Source Database       : chat-sso

Target Server Type    : MYSQL
Target Server Version : 50632
File Encoding         : 65001

Date: 2018-01-19 18:05:33
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of huo_shan_mobile_info
-- ----------------------------
INSERT INTO `huo_shan_mobile_info` VALUES ('21', '13438201570', '0000', null, null, '2018-01-19 10:38:35', '2018-01-19 10:38:35');
INSERT INTO `huo_shan_mobile_info` VALUES ('23', '13024394369', '0000', null, null, '2018-01-19 10:52:43', '2018-01-19 10:52:43');
INSERT INTO `huo_shan_mobile_info` VALUES ('24', '13024396439', '0000', null, null, '2018-01-19 10:58:06', '2018-01-19 10:58:06');
INSERT INTO `huo_shan_mobile_info` VALUES ('25', '15629543649', '0000', null, null, '2018-01-19 11:02:24', '2018-01-19 11:02:24');
INSERT INTO `huo_shan_mobile_info` VALUES ('26', '13438375348', '0000', null, null, '2018-01-19 17:52:30', '2018-01-19 17:52:30');

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
