-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.28 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for apistore
CREATE DATABASE IF NOT EXISTS `apistore` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `apistore`;

-- Dumping structure for table apistore.products
CREATE TABLE IF NOT EXISTS `products` (
  `id` bigint NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

-- Dumping data for table apistore.products: 3 rows
DELETE FROM `products`;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` (`id`, `description`, `name`, `price`) VALUES
	(1, 'Milka', 'Ciocolata', 20.9),
	(52, 'Heineken', 'Bere', 1.45),
	(153, '7Days', 'Corn', 8);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;

-- Dumping structure for table apistore.prod_seq
CREATE TABLE IF NOT EXISTS `prod_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

-- Dumping data for table apistore.prod_seq: 1 rows
DELETE FROM `prod_seq`;
/*!40000 ALTER TABLE `prod_seq` DISABLE KEYS */;
INSERT INTO `prod_seq` (`next_val`) VALUES
	(251);
/*!40000 ALTER TABLE `prod_seq` ENABLE KEYS */;

-- Dumping structure for table apistore.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

-- Dumping data for table apistore.roles: 3 rows
DELETE FROM `roles`;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`, `name`) VALUES
	(1, 'admin'),
	(2, 'manager'),
	(3, 'user');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

-- Dumping structure for table apistore.role_seq
CREATE TABLE IF NOT EXISTS `role_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

-- Dumping data for table apistore.role_seq: 1 rows
DELETE FROM `role_seq`;
/*!40000 ALTER TABLE `role_seq` DISABLE KEYS */;
INSERT INTO `role_seq` (`next_val`) VALUES
	(101);
/*!40000 ALTER TABLE `role_seq` ENABLE KEYS */;

-- Dumping structure for table apistore.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL,
  `email` varchar(100) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

-- Dumping data for table apistore.users: 3 rows
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `email`, `first_name`, `last_name`, `password`) VALUES
	(1, 'user1@yahoo.com', 'firstName1', 'lastName1', 'password1'),
	(2, 'user2@yahoo.com', 'firstName2', 'lastName2', 'password2'),
	(3, 'user3@yahoo.com', 'firstName3', 'lastName3', 'password3');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Dumping structure for table apistore.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `id_user` bigint NOT NULL,
  `id_role` bigint NOT NULL,
  KEY `FK2yqlxhjhgilevh7qvt2ve6udh` (`id_role`),
  KEY `FKr53t650tbjk5yipcm228wf1nc` (`id_user`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

-- Dumping data for table apistore.user_role: 3 rows
DELETE FROM `user_role`;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`id_user`, `id_role`) VALUES
	(1, 1),
	(2, 2),
	(3, 3);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

-- Dumping structure for table apistore.user_seq
CREATE TABLE IF NOT EXISTS `user_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

-- Dumping data for table apistore.user_seq: 1 rows
DELETE FROM `user_seq`;
/*!40000 ALTER TABLE `user_seq` DISABLE KEYS */;
INSERT INTO `user_seq` (`next_val`) VALUES
	(101);
/*!40000 ALTER TABLE `user_seq` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
