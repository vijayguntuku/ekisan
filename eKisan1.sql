/*
SQLyog Community v13.1.9 (64 bit)
MySQL - 8.0.29 : Database - ekisan
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ekisan` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `ekisan`;

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address1` varchar(250) DEFAULT NULL,
  `address2` varchar(250) DEFAULT NULL,
  `city` varchar(150) DEFAULT NULL,
  `state` varchar(150) DEFAULT NULL,
  `country` varchar(150) DEFAULT NULL,
  `pincode` float DEFAULT NULL,
  `updatedAt` date DEFAULT NULL,
  `updatedBy` int DEFAULT NULL,
  `address_type` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ADDRESS_TYPE_ADDRESS` (`address_type`),
  CONSTRAINT `FK_ADDRESS_TYPE_ADDRESS` FOREIGN KEY (`address_type`) REFERENCES `address_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `address` */

insert  into `address`(`id`,`address1`,`address2`,`city`,`state`,`country`,`pincode`,`updatedAt`,`updatedBy`,`address_type`) values 
(1,'hyd',NULL,NULL,NULL,NULL,0,NULL,0,2),
(2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `address_type` */

DROP TABLE IF EXISTS `address_type`;

CREATE TABLE `address_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `decription` varchar(255) DEFAULT NULL,
  `updatedAt` date DEFAULT NULL,
  `updatedBy` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `address_type` */

insert  into `address_type`(`id`,`name`,`decription`,`updatedAt`,`updatedBy`) values 
(1,'PRESENT ADDRESS','Present Address',NULL,NULL),
(2,'PERMANENT ADDRESS','Permanent Address',NULL,NULL),
(3,'BILLING ADDRESS','Billing Address',NULL,NULL),
(4,'DELIVERY ADDRESS','Delivery Address',NULL,NULL);

/*Table structure for table `cart` */

DROP TABLE IF EXISTS `cart`;

CREATE TABLE `cart` (
  `userId` int DEFAULT NULL,
  `productId` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  KEY `Fk_userId` (`userId`),
  KEY `Fk_productId` (`productId`),
  CONSTRAINT `Fk_productId` FOREIGN KEY (`productId`) REFERENCES `product` (`id`),
  CONSTRAINT `Fk_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `cart` */

insert  into `cart`(`userId`,`productId`,`quantity`) values 
(1,1,2),
(1,2,11),
(2,1,7),
(2,4,6),
(2,8,7),
(2,5,3),
(2,9,2),
(2,12,2),
(2,11,2);

/*Table structure for table `checkout` */

DROP TABLE IF EXISTS `checkout`;

CREATE TABLE `checkout` (
  `user_id` int DEFAULT NULL,
  `order_id` int DEFAULT NULL,
  `seller_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `total_quantity` int NOT NULL,
  `total_amont` float DEFAULT NULL,
  PRIMARY KEY (`total_quantity`),
  KEY `fk_user_id` (`user_id`),
  KEY `fk_order_id` (`order_id`),
  CONSTRAINT `fk_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `cart` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `checkout` */

/*Table structure for table `order_details` */

DROP TABLE IF EXISTS `order_details`;

CREATE TABLE `order_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `product_name` varchar(250) DEFAULT NULL,
  `item_quantity` int DEFAULT NULL,
  `item_price` float DEFAULT NULL,
  `item_total` float DEFAULT NULL,
  `updatedBy` int DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_prodct` (`product_id`),
  CONSTRAINT `fk_order_prodct` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `order_details` */

insert  into `order_details`(`id`,`order_id`,`product_id`,`product_name`,`item_quantity`,`item_price`,`item_total`,`updatedBy`,`updatedAt`) values 
(1,1,2,NULL,NULL,NULL,NULL,NULL,NULL),
(2,1,1,'rice',100,1000,20000,NULL,NULL);

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `total_items` int NOT NULL,
  `total_amount` float DEFAULT NULL,
  `order_date` datetime DEFAULT NULL,
  `delivery_address_id` int DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL,
  `updatedBy` int DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `sellerId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_order_address` (`delivery_address_id`),
  KEY `Fk_updatedBy` (`updatedBy`),
  KEY `Fk_sellerId` (`sellerId`),
  CONSTRAINT `FK_order_address` FOREIGN KEY (`delivery_address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `Fk_sellerId` FOREIGN KEY (`sellerId`) REFERENCES `user` (`id`),
  CONSTRAINT `Fk_updatedBy` FOREIGN KEY (`updatedBy`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `orders` */

insert  into `orders`(`id`,`total_items`,`total_amount`,`order_date`,`delivery_address_id`,`updatedAt`,`updatedBy`,`status`,`sellerId`) values 
(1,10,1000,'2022-09-01 11:18:12',1,'2022-09-01 11:18:19',1,NULL,1),
(2,2,2000,NULL,2,NULL,2,NULL,2),
(3,11,2200,NULL,1,NULL,3,NULL,1);

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `image` varchar(250) DEFAULT NULL,
  `product_category_id` int NOT NULL,
  `updatedBy` int DEFAULT NULL,
  `status` varchar(150) DEFAULT NULL,
  `updatedAt` datetime DEFAULT NULL,
  `status_desc` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_category_id` (`product_category_id`),
  KEY `user_id` (`updatedBy`),
  KEY `FK_PRODCT_PRODSTatus` (`status`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`product_category_id`) REFERENCES `product_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `product` */

insert  into `product`(`id`,`name`,`description`,`price`,`image`,`product_category_id`,`updatedBy`,`status`,`updatedAt`,`status_desc`) values 
(1,'Sona Masur','Sona Masur',4000,NULL,1,3,'APPROVED',NULL,NULL),
(2,'Wheat','Wheat',2000,NULL,2,3,NULL,NULL,NULL),
(4,'rice',NULL,0,NULL,1,3,NULL,NULL,NULL),
(5,'Carrot',NULL,0,NULL,1,3,NULL,NULL,NULL),
(8,'Rice',NULL,1000,NULL,1,4,NULL,NULL,NULL),
(9,'abc',NULL,1,NULL,1,4,NULL,NULL,NULL),
(10,'dfadsfa',NULL,1,NULL,1,4,NULL,NULL,NULL),
(11,'dfa',NULL,2,NULL,1,4,NULL,NULL,NULL),
(12,'Rice',NULL,1000,NULL,2,4,NULL,NULL,NULL);

/*Table structure for table `product_category` */

DROP TABLE IF EXISTS `product_category`;

CREATE TABLE `product_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `product_category` */

insert  into `product_category`(`id`,`name`,`description`) values 
(1,'rice','rice'),
(2,'vegitables','vegitables');

/*Table structure for table `product_status` */

DROP TABLE IF EXISTS `product_status`;

CREATE TABLE `product_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `product_status` */

insert  into `product_status`(`id`,`name`,`description`) values 
(1,'NEW','NEW'),
(2,'SUBMITTED','SUBMITTED'),
(3,'IN REVIEW','IN REVIEW'),
(4,'APPROVED','APPROVED'),
(5,'REJECTED','REJECTED');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `desccription` varchar(250) DEFAULT NULL,
  `updateAt` datetime DEFAULT NULL,
  `updatedBy` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `role` */

insert  into `role`(`id`,`name`,`desccription`,`updateAt`,`updatedBy`) values 
(1,'admin','admin',NULL,NULL),
(2,'buyer','buyer',NULL,NULL),
(3,'seller','seller',NULL,NULL);

/*Table structure for table `sales_sectors` */

DROP TABLE IF EXISTS `sales_sectors`;

CREATE TABLE `sales_sectors` (
  `SEC_ID` int DEFAULT NULL,
  `SEC_NAME` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `sales_sectors` */

insert  into `sales_sectors`(`SEC_ID`,`SEC_NAME`) values 
(2,'CLOTHES');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `role` int DEFAULT NULL,
  `disabled` bit(1) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_ROLE` (`role`),
  CONSTRAINT `FK_USER_ROLE` FOREIGN KEY (`role`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`email`,`password`,`role`,`disabled`,`name`) values 
(1,'admin@gmail.com','admin123',1,NULL,'admin'),
(2,'buyer@gmail.com','buyer123',2,NULL,'buyer'),
(3,'seller@gmail.com','seller123',3,NULL,'Thirupathi Organic Foods'),
(4,'seller1@gmail.com','123',3,NULL,'Swagruha Foods');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
