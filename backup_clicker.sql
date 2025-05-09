-- MySQL dump 10.13  Distrib 9.2.0, for Win64 (x86_64)
--
-- Host: localhost    Database: clicker
-- ------------------------------------------------------
-- Server version	9.2.0

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
-- Current Database: `clicker`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `clicker` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `clicker`;

--
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) DEFAULT NULL,
  `question_id` int DEFAULT NULL,
  `selected_option` char(1) DEFAULT NULL,
  `submitted_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
INSERT INTO `answers` VALUES (1,'mobile_user',1,'A','2025-04-14 11:57:36'),(2,'mobile_user',1,'A','2025-04-14 12:05:30'),(3,'mobile_user',1,'D','2025-04-14 12:06:01'),(4,'mobile_user',1,'A','2025-04-14 12:30:27'),(5,'mobile_user',1,'C','2025-04-14 12:30:29'),(6,'mobile_user',1,'D','2025-04-14 12:30:30'),(7,'mobile_user',1,'B','2025-04-14 12:30:38'),(8,'mobile_user',1,'B','2025-04-14 12:30:38'),(9,'mobile_user',1,'B','2025-04-14 12:30:39'),(10,'mobile_user',1,'A','2025-04-15 05:00:45'),(11,'mobile_user',2,'A','2025-04-15 05:36:04'),(12,'mobile_user',3,'A','2025-04-15 05:47:18'),(13,'mobile_user',3,'B','2025-04-15 05:47:19'),(14,'mobile_user',3,'C','2025-04-15 05:47:20'),(15,'mobile_user',2,'B','2025-04-15 05:54:23'),(16,'mobile_user',2,'B','2025-04-15 05:54:31'),(17,'mobile_user',2,'C','2025-04-15 05:54:32'),(18,'mobile_user',1,'C','2025-04-15 05:54:57'),(19,'mobile_user',4,'A','2025-04-15 05:55:08'),(20,'mobile_user',4,'B','2025-04-15 05:55:17'),(21,'mobile_user',1,'D','2025-04-15 07:48:24'),(22,'mobile_user',1,'B','2025-04-16 06:29:16'),(23,'mobile_user',1,'B','2025-04-16 06:29:30'),(24,'mobile_user',1,'B','2025-04-16 06:29:41'),(25,'mobile_user',1,'B','2025-04-16 06:29:42'),(26,'mobile_user',1,'D','2025-04-16 06:29:57'),(27,'mobile_user',1,'A','2025-04-16 06:38:37'),(28,'mobile_user',2,'A','2025-04-16 08:30:43'),(29,'mobile_user',4,'A','2025-04-16 08:57:58'),(30,'mobile_user',1,'A','2025-04-16 08:58:04'),(31,'mobile_user',1,'A','2025-04-16 08:58:05'),(32,'mobile_user',1,'B','2025-04-16 08:58:06'),(33,'mobile_user',1,'A','2025-04-16 08:58:14'),(34,'mobile_user',1,'A','2025-04-16 09:00:20'),(35,'mobile_user',1,'A','2025-04-16 09:00:21'),(36,'mobile_user',1,'A','2025-04-16 09:00:21'),(37,'mobile_user',1,'A','2025-04-16 09:00:21'),(38,'mobile_user',1,'D','2025-04-16 09:19:19'),(39,'mobile_user',1,'D','2025-04-16 09:19:20'),(40,'mobile_user',1,'A','2025-04-16 09:21:17'),(41,'mobile_user',1,'A','2025-04-16 09:21:18'),(42,'mobile_user',1,'A','2025-04-16 09:21:19'),(43,'mobile_user',1,'D','2025-04-16 09:21:22'),(44,'mobile_user',1,'D','2025-04-16 09:21:32'),(45,'mobile_user',1,'D','2025-04-16 09:22:12'),(46,'mobile_user',1,'D','2025-04-16 09:22:19'),(47,'mobile_user',4,'A','2025-04-17 02:29:36'),(48,'mobile_user',4,'B','2025-04-17 02:29:39'),(49,'mobile_user',4,'C','2025-04-17 02:29:41'),(50,'mobile_user',4,'D','2025-04-17 02:29:44'),(51,'mobile_user',5,'A','2025-04-17 02:30:08'),(52,'mobile_user',5,'B','2025-04-17 02:30:09'),(53,'mobile_user',5,'C','2025-04-17 02:30:10'),(54,'mobile_user',5,'D','2025-04-17 02:30:11'),(55,'mobile_user',7,'A','2025-04-17 02:40:19'),(56,'mobile_user',7,'B','2025-04-17 02:40:24'),(57,'mobile_user',7,'C','2025-04-17 02:40:25'),(58,'mobile_user',7,'D','2025-04-17 02:40:26'),(59,'mobile_user',6,'D','2025-04-17 03:20:54'),(60,'mobile_user',6,'A','2025-04-17 03:44:21'),(61,'mobile_user',6,'B','2025-04-17 03:44:27'),(62,'mobile_user',6,'C','2025-04-17 03:44:28'),(63,'mobile_user',6,'B','2025-04-17 03:44:37');
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `question_text` text NOT NULL,
  `option_a` varchar(255) DEFAULT NULL,
  `option_b` varchar(255) DEFAULT NULL,
  `option_c` varchar(255) DEFAULT NULL,
  `option_d` varchar(255) DEFAULT NULL,
  `correct_option` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (7,'What is the chemical symbol for oxygen?','O','Ox','Og','On','a'),(8,'What is the standard unit of measurement for temperature in the metric system?','Fahrenheit','Kelvin','Celsius','Rankine','c'),(9,'Which of the following is NOT a state of matter?','Solid','Liquid','Gas','Energy','d'),(10,'What force pulls objects towards the Earth?','Magnetism','Gravity','Friction','Tension','b'),(11,'What is the process by which plants convert light energy into chemical energy?','Respiration','Photosynthesis','Digestion','Fermentation','b'),(12,'What is the smallest unit of an element that retains the chemical properties of that element?','Molecule','Atom','Ion','Compound','b'),(13,'Which gas do humans primarily exhale?','Oxygen','Carbon Dioxide','Nitrogen','Hydrogen','b');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `responses`
--

DROP TABLE IF EXISTS `responses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `responses` (
  `questionNo` int DEFAULT NULL,
  `choice` varchar(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `responses`
--

LOCK TABLES `responses` WRITE;
/*!40000 ALTER TABLE `responses` DISABLE KEYS */;
INSERT INTO `responses` VALUES (8,'?');
/*!40000 ALTER TABLE `responses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session_codes`
--

DROP TABLE IF EXISTS `session_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session_codes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(4) NOT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session_codes`
--

LOCK TABLES `session_codes` WRITE;
/*!40000 ALTER TABLE `session_codes` DISABLE KEYS */;
INSERT INTO `session_codes` VALUES (1,'1234',1,'2025-04-16 07:02:13');
/*!40000 ALTER TABLE `session_codes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'testuser','testpassword',NULL,'2025-04-15 07:27:17'),(2,'1','1',NULL,'2025-04-15 07:29:38'),(3,'qh','qh','98765432','2025-04-15 09:54:32'),(4,'12345','12345','88008800','2025-04-15 10:00:43'),(5,'123','123','80923234','2025-04-15 10:09:15'),(6,'tan','tan','12341234','2025-04-17 03:41:33');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-17 17:20:42
