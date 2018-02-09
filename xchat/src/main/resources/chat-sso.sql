/*
Navicat MySQL Data Transfer

Source Server         : apple localhost
Source Server Version : 50632
Source Host           : localhost:3306
Source Database       : chat-sso

Target Server Type    : MYSQL
Target Server Version : 50632
File Encoding         : 65001

Date: 2018-02-09 17:38:07
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pages
-- ----------------------------
INSERT INTO `pages` VALUES ('1', '用户中心', null, '0', '0', '2018-01-25 16:31:39');
INSERT INTO `pages` VALUES ('2', '系统设置', null, '0', '0', '2018-01-25 16:32:05');
INSERT INTO `pages` VALUES ('3', '卡商管理', null, '0', '0', '2018-01-25 16:32:21');
INSERT INTO `pages` VALUES ('4', '财务统计', null, '0', '0', '2018-01-25 16:32:41');
INSERT INTO `pages` VALUES ('5', '卡券充值', null, '0', '0', '2018-01-25 16:32:58');
INSERT INTO `pages` VALUES ('6', '用户列表', 'http://dev.edu.cn/coder/user/userlist.html', '2', '14', '2018-01-25 16:33:20');
INSERT INTO `pages` VALUES ('7', '管理员列表', 'http://dev.edu.cn/coder/user/adminlist.html', '2', '14', '2018-01-25 16:33:56');
INSERT INTO `pages` VALUES ('8', '菜单配置', '', '1', '2', '2018-01-25 16:34:32');
INSERT INTO `pages` VALUES ('9', '授权管理', 'https://www.baidu.com', '2', '8', '2018-01-25 16:35:13');
INSERT INTO `pages` VALUES ('10', '订单管理', null, '1', '4', '2018-01-25 16:35:33');
INSERT INTO `pages` VALUES ('11', '每日账单', null, '1', '4', '2018-01-25 16:35:53');
INSERT INTO `pages` VALUES ('13', '卡商列表', null, '1', '3', '2018-01-25 16:37:03');
INSERT INTO `pages` VALUES ('14', '用户管理', null, '1', '1', '2018-01-25 16:38:05');
INSERT INTO `pages` VALUES ('15', '用户统计', null, '1', '1', '2018-01-25 16:42:29');
INSERT INTO `pages` VALUES ('16', '日增统计', 'http://dev.edu.cn/coder/user/day.html', '2', '15', '2018-01-25 16:42:32');
INSERT INTO `pages` VALUES ('17', '月增统计', 'http://dev.edu.cn/coder/user/month.html', '2', '15', '2018-01-25 16:42:33');
INSERT INTO `pages` VALUES ('18', '人工智能', null, '0', '0', null);
INSERT INTO `pages` VALUES ('19', '智能答题', '', '1', '18', '2018-02-06 16:57:27');
INSERT INTO `pages` VALUES ('20', '黄金答人', 'http://dev.edu.cn/coder/answer/HJDR.html', '2', '19', null);
INSERT INTO `pages` VALUES ('21', '其他答题', 'http://wenda.woyaoxiege.com', '2', '19', null);

-- ----------------------------
-- Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `options` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES ('1', '200', '1+2+4+5+6等于多少？', 'A 12 #B 18 #C17', null);
INSERT INTO `question` VALUES ('15', '200', '上海的简称是？ ', 'A 赣#B 沪#C 闽', '2018-02-08 12:33:22');
INSERT INTO `question` VALUES ('16', '200', '聚美优品的CEO是？ ', 'A 马化腾#B 陈欧#C 沈亚', '2018-02-08 12:34:25');
INSERT INTO `question` VALUES ('17', '200', '《当》是哪部电视剧的主题曲？', 'A《情深深雨蒙蒙》#B《还珠格格》#C《再见阿郎》', '2018-02-08 12:35:07');
INSERT INTO `question` VALUES ('18', '200', '直角三角形的直角边长为3和4，斜边长为？', 'A 5#B 11#C 20', '2018-02-08 12:36:19');
INSERT INTO `question` VALUES ('19', '200', '人体正常体温大概是多少度？ ', 'A 41℃ ～47℃#B 28℃ ～30℃#C 36℃ ～37℃', '2018-02-08 12:37:19');
INSERT INTO `question` VALUES ('20', '200', '苹果系统的语音助手叫什么名字？', 'A Siri#B 小冰#C Bixby', '2018-02-08 12:38:14');
INSERT INTO `question` VALUES ('21', '200', '激流三部曲《家》《春》《秋》的作者是？\n', 'A巴金#B陀思妥耶夫斯基#C莫言', '2018-02-08 12:39:54');
INSERT INTO `question` VALUES ('22', '200', '歌词“命运就算颠沛流离”出自哪首歌曲？ ', 'A 《沧海一声笑》#B 《亲密爱人》#C 《红日》', '2018-02-08 12:40:47');
INSERT INTO `question` VALUES ('23', '200', '夏洛克·福尔摩斯住在哪条街？\n', 'A 贝克街#B 耶路撒冷#C 香榭丽舍大街', '2018-02-08 12:41:49');
INSERT INTO `question` VALUES ('24', '200', '荷兰豆在荷兰叫什么豆? ', 'A 中国豆#B 黄豆#C 荷兰豆', '2018-02-08 12:42:46');
INSERT INTO `question` VALUES ('25', '200', '沙县小吃源自哪里？ ', 'A 四川#B 北京#C 福建', '2018-02-08 12:43:49');
INSERT INTO `question` VALUES ('26', '200', '猫屎咖啡是由哪种猫的便便提炼出来的？', 'A 波斯猫#B 麝香猫#C 无毛猫', '2018-02-08 12:46:24');
INSERT INTO `question` VALUES ('28', '200', '以麻辣辛香调料闻名的菜系是？', 'A 鲁菜#B 粤菜#C 川菜', '2018-02-08 20:04:15');
INSERT INTO `question` VALUES ('29', '200', '马头琴是哪个民族的乐器？ ', 'A 蒙古族#B 哈萨克族#C 苗族', '2018-02-08 20:08:08');
INSERT INTO `question` VALUES ('30', '200', '著名画家齐白石善于画哪种动物？ ', 'A 虾#B 马#C 牛', '2018-02-08 20:08:54');
INSERT INTO `question` VALUES ('31', '200', '在十九世纪的航海时代流行的坏血病是由于？ ', 'A 缺乏维生素A#B 缺乏维生素C#C 缺乏维生素B', '2018-02-08 20:09:55');
INSERT INTO `question` VALUES ('32', '200', '牛仔裤是在哪个国家发明的？ ', 'A 美国#B 印度#C 冰岛', '2018-02-08 20:12:14');
INSERT INTO `question` VALUES ('33', '200', '“塞上长城空自许，镜中衰鬓已先斑”出自哪位诗人之手？ ', 'A 陆游#B 苏轼#C 辛弃疾', '2018-02-08 20:13:04');
INSERT INTO `question` VALUES ('34', '200', '中国功夫的首位全球推广者是？ ', 'A 成龙#B 李小龙#C 李连杰', '2018-02-08 20:14:14');
INSERT INTO `question` VALUES ('35', '200', '下列哪个场所没有对外开放的卫生间？', 'A 银行#B 公园#C 餐厅', '2018-02-08 20:15:09');
INSERT INTO `question` VALUES ('36', '200', '下列哪款游戏不是网易出品？', 'A 阴阳师#B 荒野行动#C 王者荣耀', '2018-02-08 20:16:07');
INSERT INTO `question` VALUES ('37', '200', '被称为“老怪”的著名武侠导演是？ ', 'A 成龙#B 陈凯歌#C 徐克', '2018-02-08 20:17:47');

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
INSERT INTO `user_ticket` VALUES ('admin12738yizncy4e7823asd2139u', '4B0BDE70B0EFC03DCAFFB5656A649EB6');

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
