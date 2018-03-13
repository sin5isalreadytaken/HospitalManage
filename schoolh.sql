/*
Navicat MySQL Data Transfer

Source Server         : 192.168.26.113_3306
Source Server Version : 50547
Source Host           : 192.168.26.113:3306
Source Database       : schoolh

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2015-12-27 19:26:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor` (
  `Dno` int(11) NOT NULL DEFAULT '0',
  `Dname` varchar(16) NOT NULL,
  `Dsex` varchar(2) DEFAULT NULL,
  `Dage` int(11) DEFAULT NULL,
  `Dtitle` varchar(16) NOT NULL,
  `Ono` int(11) DEFAULT NULL,
  `Dpwd` int(11) NOT NULL,
  PRIMARY KEY (`Dno`),
  KEY `Ono` (`Ono`),
  CONSTRAINT `doctor_ibfk_1` FOREIGN KEY (`Ono`) REFERENCES `office` (`Ono`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of doctor
-- ----------------------------

-- ----------------------------
-- Table structure for hoslist
-- ----------------------------
DROP TABLE IF EXISTS `hoslist`;
CREATE TABLE `hoslist` (
  `Dno` int(11) DEFAULT NULL,
  `Pno` int(11) NOT NULL DEFAULT '0',
  `Hospitalized` int(11) NOT NULL,
  PRIMARY KEY (`Pno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hoslist
-- ----------------------------

-- ----------------------------
-- Table structure for hospitalize
-- ----------------------------
DROP TABLE IF EXISTS `hospitalize`;
CREATE TABLE `hospitalize` (
  `Pno` int(11) NOT NULL DEFAULT '0',
  `Pname` varchar(16) NOT NULL,
  `Rno` int(11) DEFAULT NULL,
  `Bno` int(11) DEFAULT NULL,
  `Intime` varchar(255) NOT NULL,
  PRIMARY KEY (`Pno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hospitalize
-- ----------------------------

-- ----------------------------
-- Table structure for medicine
-- ----------------------------
DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine` (
  `Mno` int(11) NOT NULL DEFAULT '0',
  `Mname` varchar(32) NOT NULL,
  `Mnum` int(11) DEFAULT NULL,
  `Mprice` double DEFAULT NULL,
  PRIMARY KEY (`Mno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of medicine
-- ----------------------------

-- ----------------------------
-- Table structure for nurse
-- ----------------------------
DROP TABLE IF EXISTS `nurse`;
CREATE TABLE `nurse` (
  `Nno` int(11) NOT NULL DEFAULT '0',
  `Nname` varchar(16) NOT NULL,
  `Nsex` varchar(2) DEFAULT NULL,
  `Nage` int(11) DEFAULT NULL,
  `Ntitle` char(16) NOT NULL,
  PRIMARY KEY (`Nno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of nurse
-- ----------------------------

-- ----------------------------
-- Table structure for office
-- ----------------------------
DROP TABLE IF EXISTS `office`;
CREATE TABLE `office` (
  `Ono` int(11) NOT NULL DEFAULT '0',
  `Oname` varchar(16) NOT NULL,
  `Otype` varchar(16) NOT NULL,
  PRIMARY KEY (`Ono`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of office
-- ----------------------------

-- ----------------------------
-- Table structure for patient
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient` (
  `Pno` int(11) NOT NULL DEFAULT '0',
  `Pname` varchar(16) NOT NULL,
  `Psex` varchar(2) DEFAULT NULL,
  `Page` int(11) DEFAULT NULL,
  `Pmh` varchar(255) NOT NULL,
  PRIMARY KEY (`Pno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of patient
-- ----------------------------

-- ----------------------------
-- Table structure for prescription
-- ----------------------------
DROP TABLE IF EXISTS `prescription`;
CREATE TABLE `prescription` (
  `Pno` int(11) DEFAULT NULL,
  `Mno` int(11) DEFAULT NULL,
  `Mnum` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of prescription
-- ----------------------------

-- ----------------------------
-- Table structure for register
-- ----------------------------
DROP TABLE IF EXISTS `register`;
CREATE TABLE `register` (
  `Pno` int(11) NOT NULL DEFAULT '0',
  `Ono` int(11) NOT NULL DEFAULT '0',
  `Time` varchar(255) NOT NULL,
  PRIMARY KEY (`Pno`,`Ono`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of register
-- ----------------------------

-- ----------------------------
-- Table structure for sickroom
-- ----------------------------
DROP TABLE IF EXISTS `sickroom`;
CREATE TABLE `sickroom` (
  `Rno` int(11) DEFAULT NULL,
  `Bno` int(11) NOT NULL DEFAULT '0',
  `Bstate` int(1) DEFAULT NULL,
  `PriceperDay` double NOT NULL,
  PRIMARY KEY (`Bno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sickroom
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userid` varchar(255) NOT NULL,
  `pwd` varchar(255) NOT NULL,
  `usermax` int(11) NOT NULL,
  `usernum` int(11) NOT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('medicine', 'medicine', '3', '0');
INSERT INTO `user` VALUES ('register', 'register', '3', '0');
INSERT INTO `user` VALUES ('sickroom', 'sickroom', '3', '0');
DROP TRIGGER IF EXISTS `adddoctor`;
DELIMITER ;;
CREATE TRIGGER `adddoctor` AFTER INSERT ON `doctor` FOR EACH ROW INSERT INTO user VALUES(new.Dno,new.Dpwd,'1','0')
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `deldoctor`;
DELIMITER ;;
CREATE TRIGGER `deldoctor` BEFORE DELETE ON `doctor` FOR EACH ROW DELETE FROM user WHERE userid = old.Dno
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `addhospitalize`;
DELIMITER ;;
CREATE TRIGGER `addhospitalize` AFTER INSERT ON `hospitalize` FOR EACH ROW UPDATE sickroom SET Bstate = 1 WHERE Bno = new.Bno
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `delhospitalize`;
DELIMITER ;;
CREATE TRIGGER `delhospitalize` BEFORE DELETE ON `hospitalize` FOR EACH ROW UPDATE sickroom SET Bstate = 0 WHERE Bno = old.Bno
;;
DELIMITER ;
