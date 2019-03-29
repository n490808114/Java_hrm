# Host: 127.0.01  (Version: 5.5.15)
# Date: 2019-03-15 17:09:33
# Generator: MySQL-Front 5.3  (Build 4.269)

/*!40101 SET NAMES gb2312 */;

#
# Structure for table "dept_inf"
#

DROP TABLE IF EXISTS `dept_inf`;
CREATE TABLE `dept_inf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `remark` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

#
# Data for table "dept_inf"
#

INSERT INTO `dept_inf` VALUES (1,'技术部','技术部'),(2,'运营部','运营部'),(3,'财务部','财务部'),(5,'总工办','总工办'),(6,'市场部','市场部'),(7,'教学部','教学部');

#
# Structure for table "job_inf"
#

DROP TABLE IF EXISTS `job_inf`;
CREATE TABLE `job_inf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

#
# Data for table "job_inf"
#

INSERT INTO `job_inf` VALUES (1,'职员','职员'),(2,'Java 开发工程师','Java 开发工程师'),(3,'Java 中级开发工程师','Java 中级开发工程师'),(4,'Java 高级开发工程师','Java 高级开发工程师'),(5,'系统管理员','系统管理员'),(6,'架构师','架构师'),(7,'主管','主管'),(8,'经理','经理'),(9,'总经理','总经理');

#
# Structure for table "employee_inf"
#

DROP TABLE IF EXISTS `employee_inf`;
CREATE TABLE `employee_inf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_id` int(11) NOT NULL,
  `job_id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `card_id` varchar(18) DEFAULT NULL,
  `address` varchar(50) NOT NULL,
  `post_code` varchar(50) DEFAULT NULL,
  `tel` varchar(16) DEFAULT NULL,
  `phone` varchar(11) NOT NULL,
  `qq_num` varchar(12) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `sex` int(11) NOT NULL DEFAULT '1',
  `party` varchar(10) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `race` varchar(100) DEFAULT NULL,
  `education` varchar(10) DEFAULT NULL,
  `speciality` varchar(10) DEFAULT NULL,
  `hobby` varchar(100) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_EMP_DEPT` (`dept_id`),
  KEY `FK_EMP_JOB` (`job_id`),
  CONSTRAINT `FK_EMP_DEPT` FOREIGN KEY (`dept_id`) REFERENCES `dept_inf` (`id`),
  CONSTRAINT `FK_EMP_JOB` FOREIGN KEY (`job_id`) REFERENCES `job_inf` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Data for table "employee_inf"
#

INSERT INTO `employee_inf` VALUES (1,1,8,'爱莉丝','4328011988','广州天河','510000','020-77777777','13902001111','3675006','251425887@qq.com',0,'党员','1980-01-01 00:00:00','满','本科','美声','唱歌','四大天王','2019-03-05 12:14:52'),(2,2,1,'杰克','22623','43234','42427424','4242','4247242','42424','251425887@qq.com',2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2019-03-05 12:14:52'),(3,1,2,'bb','432801197711251038','广州','510000','020-99999999','13907351532','36750064','36750064@qq.com',1,'党员','1977-11-25 00:00:00','汉','本科','计算机','爬山','无','2019-03-05 12:14:52');

#
# Structure for table "user_inf"
#

DROP TABLE IF EXISTS `user_inf`;
CREATE TABLE `user_inf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(20) NOT NULL,
  `password` varchar(16) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username` varchar(20) DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

#
# Data for table "user_inf"
#

INSERT INTO `user_inf` VALUES (1,'admin','0000000',2,'2019-01-01 00:00:00','超级管理员','');

#
# Structure for table "notice_inf"
#

DROP TABLE IF EXISTS `notice_inf`;
CREATE TABLE `notice_inf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_NOTICE_USER` (`user_id`),
  CONSTRAINT `FK_NOTICE_USER` FOREIGN KEY (`user_id`) REFERENCES `user_inf` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "notice_inf"
#


#
# Structure for table "document_inf"
#

DROP TABLE IF EXISTS `document_inf`;
CREATE TABLE `document_inf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `filename` varchar(300) NOT NULL,
  `remark` varchar(300) DEFAULT NULL,
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_DOCUMENT_USER` (`user_id`),
  CONSTRAINT `FK_DOCUMENT_USER` FOREIGN KEY (`user_id`) REFERENCES `user_inf` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "document_inf"
#

