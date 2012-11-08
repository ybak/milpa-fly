-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.1.57-community - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2012-11-08 17:59:16
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for milpa_fly
DROP DATABASE IF EXISTS `milpa_fly`;
CREATE DATABASE IF NOT EXISTS `milpa_fly` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `milpa_fly`;


-- Dumping structure for table milpa_fly.account
DROP TABLE IF EXISTS `account`;
CREATE TABLE IF NOT EXISTS `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(30) DEFAULT NULL,
  `hashed_password` varchar(64) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `current_player` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `FKB9D38A2D6C37FD4F` (`current_player`),
  CONSTRAINT `FKB9D38A2D6C37FD4F` FOREIGN KEY (`current_player`) REFERENCES `player` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table milpa_fly.account: ~1 rows (approximately)
DELETE FROM `account`;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` (`id`, `email`, `hashed_password`, `username`, `version`, `current_player`) VALUES
    (1, 'test@mail.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'test', 1, 1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;


-- Dumping structure for table milpa_fly.admin_account
DROP TABLE IF EXISTS `admin_account`;
CREATE TABLE IF NOT EXISTS `admin_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table milpa_fly.admin_account: ~0 rows (approximately)
DELETE FROM `admin_account`;
/*!40000 ALTER TABLE `admin_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin_account` ENABLE KEYS */;


-- Dumping structure for table milpa_fly.player
DROP TABLE IF EXISTS `player`;
CREATE TABLE IF NOT EXISTS `player` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `display_name` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `account` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC53E9AE1BA3E6333` (`account`),
  CONSTRAINT `FKC53E9AE1BA3E6333` FOREIGN KEY (`account`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table milpa_fly.player: ~1 rows (approximately)
DELETE FROM `player`;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` (`id`, `display_name`, `version`, `account`) VALUES
    (1, 'newPlayer', 0, 1);
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
