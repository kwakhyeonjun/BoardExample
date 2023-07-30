-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: board
-- ------------------------------------------------------
-- Server version	11.0.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `deleted` bit(1) NOT NULL,
  `like_count` int(11) NOT NULL,
  `unlike_count` int(11) NOT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `modified_date` datetime(6) DEFAULT NULL,
  `view_count` bigint(20) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `hashtag` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `writer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (_binary '\0',0,0,'2023-07-30 12:28:33.130383',1,'2023-07-30 12:28:33.396371',1,'안녕하세요','ㅎㅇ','$2a$10$Shp1UezP2vYqE4pLccV7wOt0xm13JU38BShYtr/qGm84r8tDQCBum','안녕하세요','ㅇㅇ'),(_binary '\0',0,0,'2023-07-30 12:29:27.512640',2,'2023-07-30 12:29:27.539642',1,'게시글 내용입니다!','안녕','$2a$10$qskHoy68MJ/z/.Ex7VAEjeeSaJ7piUuuQDr.IlFqWZY19mmzfdiSO','게시글','작성자'),(_binary '\0',0,0,'2023-07-30 12:29:46.151595',3,'2023-07-30 12:29:46.183113',1,'드디어..','하','$2a$10$HhxdqyBYA3at6dWUpOAdS.6ydhHqjRv2hbEojHShg2I/lQucw4pFO','완성했습니다','ㅇㅇ'),(_binary '\0',1,0,'2023-07-30 12:45:25.097982',4,'2023-07-30 12:48:02.293892',2,'게시판입니다.','해시,태그','$2a$10$Xw4TkIX08ki8i3V9Rlld8e7ua4X1j4nkdhXiTyAwhD8oBjsdaRMte','안녕하세요.','곽현준');
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `deleted` bit(1) NOT NULL,
  `board_id` bigint(20) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `modified_date` datetime(6) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `writer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlij9oor1nav89jeat35s6kbp1` (`board_id`),
  CONSTRAINT `FKlij9oor1nav89jeat35s6kbp1` FOREIGN KEY (`board_id`) REFERENCES `board` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (_binary '\0',1,'2023-07-30 12:28:52.002322',1,'2023-07-30 12:28:52.002322','안녕하세요!!','$2a$10$7vCSyD.eOgBBbp5BZ0K4uuDvUhhxtcy0e1UxpMJMkKhFnFfFH7Z/K','ㅇㅇ'),(_binary '\0',1,'2023-07-30 12:29:01.526048',2,'2023-07-30 12:29:01.526048','안녕하세요!!','$2a$10$lMpQyw23zB.F4RP0oIhETuoJIM0sdXmGPeJqNrh9NrrY1RV2kx6om','와'),(_binary '\0',4,'2023-07-30 12:49:15.907706',3,'2023-07-30 12:49:15.907706','완성했습니다','$2a$10$Mh7IMQoGhA6USyex7fUyge7nmmkYhyyyc/eVzA.Ji/022RVd5IGrm','곽현준');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'board'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-30 13:01:56
