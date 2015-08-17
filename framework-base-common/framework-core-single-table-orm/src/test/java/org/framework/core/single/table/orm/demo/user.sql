/*
Navicat MySQL Data Transfer

Source Server         : WDYX_TEST
Source Server Version : 50617
Source Host           : 127.0.0.1:3306
Source Database       : dxsjjb

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2015-08-10 21:23:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'mobangjack', 'mobangjacklove33');
INSERT INTO `user` VALUES ('2', 'mobangjack', 'mobangjacklove33');
INSERT INTO `user` VALUES ('3', 'mobangjack', 'mobangjacklove33');
INSERT INTO `user` VALUES ('5', 'mobangjack', 'mobangjacklove33');
INSERT INTO `user` VALUES ('6', 'mobangjack', 'mobangjacklove33');
INSERT INTO `user` VALUES ('7', 'mobangjack', 'mobangjacklove33');
INSERT INTO `user` VALUES ('8', 'mobangjack1', 'mobangjacklove331');
INSERT INTO `user` VALUES ('9', 'mobangjack1', 'mobangjacklove331');
INSERT INTO `user` VALUES ('10', 'username', 'password');
INSERT INTO `user` VALUES ('11', 'username', 'password');
