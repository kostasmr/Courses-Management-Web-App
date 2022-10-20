-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: codejavadb
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `course_id` bigint NOT NULL AUTO_INCREMENT,
  `course` varchar(45) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `professor` varchar(45) NOT NULL,
  `project_percentage` float DEFAULT NULL,
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `UK_j61e6f5j5le3nl7gktuu1fbh0` (`course`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'Operating Systems','Εισαγωγή και ιστορία υπολογιστικών συστημάτων, διεργασίες, νήματα, συγχρονισμός, αδιέξοδο, χρονοδρομολόγηση επεξεργαστή, διαχείριση μνήμης, εικονική μνήμη, διαχείριση συσκευών, συστήματα αρχείων, ασφάλεια.','Anastasiadis@gmail.com',0.3),(2,'Mathematics','Συνθήκες βελτιστότητας.Χωρίς πρότζεκτ.','Parsopoulos@gmail.com',0),(3,'Java','Αντικειμενοστραφής γλώσσα προγραμματισμού, βασικά στοιχεία της Java.','Mamoulis@gmail.com',0.6),(4,'C++',' Eίναι μία γενικού σκοπού γλώσσα προγραμματισμού Η/Υ. Θεωρείται μέσου επιπέδου γλώσσα.','Mamoulis@gmail.com',0.5),(5,'Python','Η Python είναι διερμηνευόμενη (interpreted), γενικού σκοπού (general-purpose) και υψηλού επιπέδου, γλώσσα προγραμματισμού.Ανήκει στις γλώσσες προστακτικού προγραμματισμού.','Mamoulis@gmail.com',0.3);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `students_id` bigint NOT NULL AUTO_INCREMENT,
  `grade` float DEFAULT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `Professor` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `course` varchar(45) NOT NULL,
  `Exams_Grade` float DEFAULT NULL,
  `Project_Grade` float DEFAULT NULL,
  PRIMARY KEY (`students_id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,7,'Maria','Papadopoulou','Mamoulis@gmail.com','papadopoulou@gmail.com','Java',7.1,7),(2,3.4,'Dimitra','Antoniou','Mamoulis@gmail.com','antoniou@gmail.com','Java',1,5),(3,3.2,'Christina','Morkou','Mamoulis@gmail.com','morkou@gmail.com','Java',5,2),(4,4.5,'Dimitra','Antoniou','Mamoulis@gmail.com','antoniou@gmail.com','C++',3,6),(5,9.2,'Konstantinos','Katsabos','Mamoulis@gmail.com','katsabos@gmail.com','Java',8,10),(6,5,'Georgios','Markou','Anastasiadis@gmail.com','markou@gmail.com','Operating Systems',5,5),(7,5.3,'Ioannis','Paulou','Anastasiadis@gmail.com','paulou@gmail.com','Operating Systems',5,6),(8,7,'Ioannis','Paulou','Parsopoulos@gmail.com','paulou@gmail.com','Mathematics',7,0),(9,10,'Dimitra','Antoniou','Parsopoulos@gmail.com','antoniou@gmail.com','Mathematics',10,0),(10,7,'Christina','Morkou','Mamoulis@gmail.com','morkou@gmail.com','Python',7,7),(11,7.8,'Konstantinos','Katsabos','Mamoulis@gmail.com','katsabos@gmail.com','Python',9,5),(12,8.7,'Ioannis','Paulou','Mamoulis@gmail.com','paulou@gmail.com','Python',9,8),(13,6.5,'Ioannis','Markou','Mamoulis@gmail.com','markou@gmail.com','C++',6,7),(14,9,'Konstantinos','Katsabos','Parsopoulos@gmail.com','katsabos@gmail.com','Mathematics',9,0);
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `password` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Parsopoulos@gmail.com','Konstantinos','Parsopoulos','$2a$10$KgnVyjkBw2.XBOcTxJQ9H.n/EHanoRAGS4vhU2u4da01M9NNIuK92'),(2,'Anastasiadis@gmail.com','Stergios','Anastasiadis','$2a$10$fHYUQ/m4EoYBB7vl/AtUBuSmedgWfRxSEMqAH1/YKiDK9./gdQGTG'),(3,'Mamoulis@gmail.com','Nikolaos','Mamoulis','$2a$10$2Il6wgKb2pl9W2zKSxE.v.flEriz1qbTVrtzYzLelMrvASMIl1Ww.');
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

-- Dump completed on 2022-05-16 19:28:44
