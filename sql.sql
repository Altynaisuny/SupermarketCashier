/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.0.16 : Database - syt_supermarket
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`syt_supermarket` /*!40100 DEFAULT CHARACTER SET gb2312 */;

USE `syt_supermarket`;

/*Table structure for table `money` */

DROP TABLE IF EXISTS `money`;

CREATE TABLE `money` (
  `date` char(20) NOT NULL default '',
  `income` varchar(10) default NULL,
  `profit` varchar(20) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Data for the table `money` */

insert  into `money`(`date`,`income`,`profit`) values ('2015-06-09','2.5','0.5'),('2015-06-09','5.5','1.0'),('2015-06-10','4.0','0.0'),('2015-06-10','6.0','0.0'),('2015-06-10','5.5','1.0'),('2015-06-10','5.5','1.0'),('2015-06-10','8.5','1.5'),('2015-06-10','8.0','1.5'),('2015-06-11','5.5','1.0'),('2015-06-11','8.5','1.5'),('2015-06-11','2.0','0.0'),('2015-06-14','5.0','1.0'),('2015-06-14','0.0','0.0'),('2015-06-14','0.0','0.0'),('2015-06-14','0.0','0.0'),('2015-06-14','0.0','0.0'),('2015-06-15','11.5','2.0'),('2015-06-15','0.0','0.0'),('2015-06-15','10.0','2.0'),('2015-06-16','10.0','2.0'),('2015-06-16','2.5','0.5'),('2015-06-16','2.5','0.5'),('2015-06-16','2.0','0.0'),('2015-06-16','10.40','-0.09999999999999964'),('2015-06-16','10.80','-0.1999999999999993'),('2015-06-18','10.5','2.0'),('2015-06-18','2.5','0.5');

/*Table structure for table `purchase` */

DROP TABLE IF EXISTS `purchase`;

CREATE TABLE `purchase` (
  `date` char(20) NOT NULL default '',
  `id` int(10) default NULL,
  `name` char(10) default NULL,
  `number` int(10) default NULL,
  PRIMARY KEY  (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Data for the table `purchase` */

insert  into `purchase`(`date`,`id`,`name`,`number`) values ('2015-06-11 20:52:18',2,'可口可乐',50),('2015-06-11 20:53:32',2,'可口可乐',20),('2015-06-11 20:58:38',3,'雪碧',50),('2015-06-11 21:10:52',10,'农夫山泉天然水',50),('2015-06-14 13:55:06',1,'王老吉凉茶',3),('2015-06-14 15:40:11',9,'250ml红牛',50),('2015-06-18 14:17:23',1,'王老吉凉茶',10);

/*Table structure for table `shopping` */

DROP TABLE IF EXISTS `shopping`;

CREATE TABLE `shopping` (
  `id` int(5) NOT NULL,
  `name` varchar(20) NOT NULL,
  `address` varchar(10) NOT NULL,
  `price` varchar(10) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Data for the table `shopping` */

insert  into `shopping`(`id`,`name`,`address`,`price`) values (1,'王老吉凉茶','广州','2.5'),(2,'可口可乐','杭州','3'),(3,'雪碧','上海','3'),(4,'雀巢咖啡（盒装）','杭州','12.80'),(5,'480ml 康师傅冰红茶','山东','3'),(6,'72g相约奶茶','山东','2.5'),(7,'500ml哇哈哈营养快线','山东','4.5'),(8,'550ml尖叫纤维饮料','上海','4'),(9,'250ml红牛','北京','5.5'),(10,'农夫山泉天然水','山东','6'),(11,'50g乐事（原味）','上海','2.9'),(12,'50g乐事（烤肉）','上海','2.9');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(5) NOT NULL,
  `name` varchar(5) NOT NULL,
  `power` int(1) NOT NULL,
  `password` varchar(10) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`power`,`password`) values (1,'syt',1,'123'),(2,'sun',2,'456');

/*Table structure for table `vip` */

DROP TABLE IF EXISTS `vip`;

CREATE TABLE `vip` (
  `id` int(5) NOT NULL auto_increment,
  `name` varchar(10) NOT NULL,
  `score` char(10) default NULL,
  `grade` char(4) default NULL,
  `qq` int(15) default NULL,
  `phone` int(11) default NULL,
  `address` char(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Data for the table `vip` */

insert  into `vip`(`id`,`name`,`score`,`grade`,`qq`,`phone`,`address`) values (1,'詹姆斯邦德','5520.0','钻石',NULL,NULL,NULL),(2,'汤姆克鲁斯','500','青铜',NULL,NULL,NULL),(3,'施瓦辛格','1050.0','钻石',NULL,NULL,NULL),(4,'ss','0',NULL,123456,66,'山东');

/*Table structure for table `warehouse` */

DROP TABLE IF EXISTS `warehouse`;

CREATE TABLE `warehouse` (
  `id` int(5) NOT NULL,
  `name` varchar(20) NOT NULL default '',
  `address` varchar(10) NOT NULL,
  `price` varchar(10) NOT NULL,
  `number` int(5) NOT NULL,
  `openingprice` varchar(10) NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gb2312;

/*Data for the table `warehouse` */

insert  into `warehouse`(`id`,`name`,`address`,`price`,`number`,`openingprice`) values (1,'王老吉凉茶','广州','2.5',68,'2'),(2,'可口可乐','杭州','3',306,'2.5'),(3,'雪碧','上海','3',149,'2.5'),(4,'雀巢咖啡（盒装）','杭州','12.80',100,'12'),(5,'480ml康师傅冰红','山东','3',93,'2.5'),(6,'72g相约奶茶','山东','2.5',19,'2'),(7,'500ml哇哈哈营养','山东','4.5',100,'4'),(8,'550ml尖叫纤维饮','上海','4',100,'3.5'),(9,'250ml红牛','北京','5.5',150,'4.5'),(10,'农夫山泉天然水','山东','6',150,'5'),(11,'50g乐事（原味）','上海','2.9',100,'2.5'),(12,'50g乐事（烤肉）','上海','2.9',99,'2.5'),(13,'50g乐事（玉米）','上海','2.9',100,'2.5'),(14,'50g乐事（番茄）','上海','2.9',100,'2.5'),(15,'美年达','江苏','3',100,'2.5'),(16,'可乐','江苏','4',100,'12');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
