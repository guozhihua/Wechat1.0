

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `p_order`
-- ----------------------------
DROP TABLE IF EXISTS `p_order`;
CREATE TABLE `p_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `project_id` varchar(11) DEFAULT NULL COMMENT '产品id',
  `buy_type` int(2) DEFAULT NULL COMMENT '1:作业辅导；2：考试帮手',
  `out_trade_no` varchar(128) DEFAULT NULL COMMENT '订单号',
  `puserVoucherId` varchar(40) DEFAULT NULL COMMENT '使用的代金券',
  `user_unique_id` varchar(64) DEFAULT NULL COMMENT '用户唯一标识',
  `state` varchar(16) DEFAULT NULL COMMENT '状态 1已购买 0未购买',
  `order_date` datetime DEFAULT NULL COMMENT '下单时间',
  `description` varchar(500) DEFAULT NULL COMMENT '商品描述',
  `cash_amout` varchar(20) DEFAULT NULL COMMENT '现金支付金额',
  `coupon_amout` varchar(20) DEFAULT NULL COMMENT '代金券支付金额',
  `total_amout` varchar(20) DEFAULT NULL COMMENT '总金额',
  `serial_number` varchar(50) DEFAULT NULL COMMENT '流水号',
  `project_name` varchar(20) DEFAULT NULL COMMENT '产品名称',
  `end_time` datetime DEFAULT NULL COMMENT '到期时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1501 DEFAULT CHARSET=utf8 COMMENT='用户商品订单';


INSERT INTO `p_order` VALUES ('1495', '5', '2', '20161205153504000001', 'd8331740-b790-11e6-a225-000c29861d0b', 'o_xRfvweDjAoB8Y16Ipq9_NS0HX4', '1', '2016-12-05 15:35:04', '本产品根据三年级上学期语文人教版教材内容、同步学生当前学习进度，为用户推送单元知识点诊断卷，依据诊断结果为用户提供个性化的知识弱项分析、高效学习计划，针对学习过程中的薄弱知识点精准推送知识点视频、试题等教学资源。购买后获得30天使用有效期', '0', '50', '0', 'YJLT1480923304701946581', '考试帮手三年级语文上册', '2017-01-04 15:35:04', '2016-12-05 15:35:04', '2016-12-05 15:35:04');
INSERT INTO `p_order` VALUES ('1496', '128', '1', '20161205154216000001', 'd83316c5-b790-11e6-a225-000c29861d0b', 'o_xRfvweDjAoB8Y16Ipq9_NS0HX4', '1', '2016-12-05 15:42:16', '本产品根据三年级语文上册人教新课标版教材课后作业，为用户提供启发式的作业辅导服务，不局限于题目本身，通过引导、启发的方式讲解作业，挖掘深层知识点与解题方法，锻炼解题思路，培养学习能力。购买后获得三十天使用有效期。', '0', '30', '0', 'YJLT1480923736826130721', '作业辅导三年级语文上册', '2017-01-04 15:42:16', '2016-12-05 15:42:16', '2016-12-05 15:42:16');
INSERT INTO `p_order` VALUES ('1497', '30', '2', '20161205160820000001', null, 'o_xRfv93K6x8YFsSg_QAT46mxSIM', '0', '2016-12-05 16:08:20', '本产品根据七年级下学期语文人教新课标版教材内容、同步学生当前学习进度，为用户推送单元知识点诊断卷，依据诊断结果为用户提供个性化的知识弱项分析、高效学习计划，针对学习过程中的薄弱知识点精准推送知识点视频、试题等教学资源。购买后获得30天使用有效期', '50', '0', '50', 'YJLT1480925300926509181', null, '2017-01-04 16:08:20', '2016-12-05 16:08:20', '2016-12-05 16:08:20');
INSERT INTO `p_order` VALUES ('1498', '30', '2', '20161205160853000001', 'd6528ade-b799-11e6-a225-000c29861d0b', 'o_xRfv93K6x8YFsSg_QAT46mxSIM', '1', '2016-12-05 16:08:53', '本产品根据七年级下学期语文人教新课标版教材内容、同步学生当前学习进度，为用户推送单元知识点诊断卷，依据诊断结果为用户提供个性化的知识弱项分析、高效学习计划，针对学习过程中的薄弱知识点精准推送知识点视频、试题等教学资源。购买后获得30天使用有效期', '0', '50', '0', 'YJLT1480925333498969575', '考试帮手七年级语文下册', '2017-01-04 16:08:53', '2016-12-05 16:08:53', '2016-12-05 16:08:53');
INSERT INTO `p_order` VALUES ('1499', '128', '1', '20161205170207000001', 'ddca51d2-b929-11e6-a225-000c29861d0b', 'o_xRfv4v5T8izJYC-EVHzdEM1a7o', '1', '2016-12-05 17:02:07', '本产品根据三年级语文上册人教新课标版教材课后作业，为用户提供启发式的作业辅导服务，不局限于题目本身，通过引导、启发的方式讲解作业，挖掘深层知识点与解题方法，锻炼解题思路，培养学习能力。购买后获得三十天使用有效期。', '0', '50', '0', 'YJLT1480928527689303923', '作业辅导三年级语文上册', '2017-01-04 17:02:07', '2016-12-05 17:02:07', '2016-12-05 17:02:07');
INSERT INTO `p_order` VALUES ('1500', '124', '1', '20161205170337000001', 'ddca486f-b929-11e6-a225-000c29861d0b', 'o_xRfv4v5T8izJYC-EVHzdEM1a7o', '1', '2016-12-05 17:03:37', '本产品根据二年级数学上册人教新课标版教材课后作业，为用户提供启发式的作业辅导服务，不局限于题目本身，通过引导、启发的方式讲解作业，挖掘深层知识点与解题方法，锻炼解题思路，培养学习能力。购买后获得三十天使用有效期。', '0', '30', '0', 'YJLT1480928617160766800', '作业辅导二年级数学上册', '2017-01-04 17:03:37', '2016-12-05 17:03:37', '2016-12-05 17:03:37');


-- ----------------------------
-- Table structure for `p_user_base`
-- ----------------------------
DROP TABLE IF EXISTS `p_user_base`;
CREATE TABLE `p_user_base` (
  `user_id` varchar(40) NOT NULL,
  `sex` int(10) DEFAULT NULL COMMENT '1：男，0：女',
  `open_id` varchar(40) NOT NULL COMMENT '微信openId',
  `user_name` varchar(40) DEFAULT NULL COMMENT '用户名',
  `true_name` varchar(40) DEFAULT NULL COMMENT '真实姓名',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `head_img_url` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `first_login_flag` tinyint(1) DEFAULT '0' COMMENT '引导页0-是  1-否',
  `status` tinyint(1) DEFAULT '1' COMMENT '1-正常  0-禁止 ',
  `user_token` varchar(40) DEFAULT NULL COMMENT '用户token',
  `last_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `index_user_openID` (`open_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基本信息';

INSERT INTO `p_user_base` VALUES ('e814e7a8-b5f9-11e6-a225-000c29861d0b', '1', 'o_xRfv51SzBYLiKa2foS5Jury-UU', '发散的无穷积分', null, null, 'http://wx.qlogo.cn/mmopen/TgJ6xplew5HxqYt8yUryLJXMVoHpOm2qHlU01STrja6d3LcQ4IVib4sp4z5fkDgm8EYn2QWibNurDic7VZ09YjXCSEhnU2nGxvU/0', '1', '1', null, null, '2016-11-29 14:06:02', '2016-11-29 14:06:02');
INSERT INTO `p_user_base` VALUES ('f5ffb37c-b897-11e6-a225-000c29861d0b', '1', 'o_xRfvycC0co5JXLZb8rjBk0XiQw', '瓷韵', null, null, 'http://wx.qlogo.cn/mmopen/ajNVdqHZLLCsDYLA1JsJweU1HJ2ld5cIjD4zia4TS5vVSfwQI2PtFgAKFibic6flDzF7GUyEFEynb5JIbdGtcl3Ag/0', '1', '1', null, null, '2016-12-02 22:02:28', '2016-12-02 22:02:28');
INSERT INTO `p_user_base` VALUES ('f734ad33-b937-11e6-a225-000c29861d0b', '1', 'o_xRfv-s94KshbNk9MDap1btBhb0', '老乔', null, null, 'http://wx.qlogo.cn/mmopen/ZgCTvib5A8lAPBibicBTNkcjKAFcF9uf6PicxwPZHNAon7NL8F0f0TtK6IhweQCJlCfbSLdw3OjxMTKHllB2xp2mgqsgByyzl7sX/0', '1', '1', null, null, '2016-12-03 17:07:50', '2016-12-03 17:07:50');
INSERT INTO `p_user_base` VALUES ('f988a8ef-b6e8-11e6-a225-000c29861d0b', '1', 'o_xRfvyrflW6UFvcGpKwjY7HPy88', '→_→', null, null, 'http://wx.qlogo.cn/mmopen/dEtazLia4Q5zwibzo9Cdotia9sauVVptA6cjDia0kuIjXLBEesUclhmubaAxHLftV11098w1jbPd6uJiaypvpJBmtgMvPn71N53sh/0', '1', '1', null, null, '2016-11-30 18:37:21', '2016-11-30 18:37:21');
INSERT INTO `p_user_base` VALUES ('fa5d2015-b860-11e6-a225-000c29861d0b', '2', 'o_xRfv70ed5sgL6Y4HQUeqOn2ey0', '落花☞浅忆', null, null, 'http://wx.qlogo.cn/mmopen/slnib7PqCOEx7Uc1GhBjiaF9eQbwZ1ZeiakDRNdXU7r8ucjIgI4b7bMMyQmg0810EkELdIaD0U0ZiarQiaKlib5Yw6icjo3Vrs0g4dA/0', '1', '1', null, null, '2016-12-02 15:28:53', '2016-12-02 15:28:53');
INSERT INTO `p_user_base` VALUES ('fb6a78ca-b78b-11e6-a225-000c29861d0b', '1', 'o_xRfv1fn-17SrJYFaWIAcgljsUk', '明辉', null, null, 'http://wx.qlogo.cn/mmopen/slnib7PqCOEwHzC6NBFE515oxU1qkAGicq0iaD8OibIclKkVTWoz98nC0vxz0byHVyR4ricn5WYIkvl3M4ibWTI10ibnbeeMgnfvfZb/0', '1', '1', null, null, '2016-12-01 14:04:12', '2016-12-01 14:04:12');
INSERT INTO `p_user_base` VALUES ('fffefe87-b6e8-11e6-a225-000c29861d0b', '1', 'o_xRfv0phsWtgdFsFdjsTxnexBH4', 'Kirbyx', null, null, 'http://wx.qlogo.cn/mmopen/dEtazLia4Q5zwibzo9Cdotia2Vy2AgDFgYHJ1easIpSvOpEXiciaySgO78AokDFmZcFdZd1bVM4RInbNh9j0gq7RjeicEsXZ47qItN/0', '1', '1', null, null, '2016-11-30 18:37:32', '2016-11-30 18:37:32');


-- ----------------------------
-- Table structure for `p_voucher`
-- ----------------------------
DROP TABLE IF EXISTS `p_voucher`;
CREATE TABLE `p_voucher` (
  `voucher_id` int(11) NOT NULL AUTO_INCREMENT,
  `effective_day` int(1) NOT NULL COMMENT '有效天数',
  `descinfo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL,
  `isdel` tinyint(1) NOT NULL DEFAULT '0',
  `amout` int(3) NOT NULL COMMENT '金额',
  PRIMARY KEY (`voucher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='代金券基本信息';

-- ----------------------------
-- Records of p_voucher
-- ----------------------------
INSERT INTO `p_voucher` VALUES ('1', '30', '20元通用代金券', '2016-11-24 21:09:10', '0', '20');
INSERT INTO `p_voucher` VALUES ('2', '30', '25元通用代金券', '2016-11-25 09:13:24', '0', '25');
INSERT INTO `p_voucher` VALUES ('3', '30', '30元通用代金券', '2016-12-01 13:08:43', '0', '30');
INSERT INTO `p_voucher` VALUES ('4', '30', '50元通用代金券', '2016-12-01 13:09:01', '0', '50');
