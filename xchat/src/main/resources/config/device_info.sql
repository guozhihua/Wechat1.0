/*
Navicat MySQL Data Transfer

Source Server         : apple localhost
Source Server Version : 50632
Source Host           : localhost:3306
Source Database       : chat-sso

Target Server Type    : MYSQL
Target Server Version : 50632
File Encoding         : 65001

Date: 2018-01-23 18:01:35
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
