CREATE DATABASE  IF NOT EXISTS `auctionwebsite` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `auctionwebsite`;
-- MySQL dump 10.13  Distrib 5.7.15, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: auctionwebsite
-- ------------------------------------------------------
-- Server version	5.7.15-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auction`
--

DROP TABLE IF EXISTS `auction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auction` (
  `AuctionID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SellerID` bigint(20) NOT NULL,
  `BuyerID` bigint(20) DEFAULT NULL,
  `Name` varchar(150) NOT NULL,
  `Description` varchar(10000) DEFAULT NULL,
  `LowestBid` double NOT NULL,
  `StartingDate` datetime DEFAULT NULL,
  `EndingDate` datetime DEFAULT NULL,
  `Country` varchar(150) NOT NULL,
  `Location` varchar(150) NOT NULL,
  `Longitude` float DEFAULT NULL,
  `Latitude` float DEFAULT NULL,
  `IsActive` tinyint(1) DEFAULT '0',
  `BuyPrice` int(11) DEFAULT NULL,
  PRIMARY KEY (`AuctionID`),
  KEY `fk_auction_user_idx` (`SellerID`),
  KEY `fk_auction_user1_idx` (`BuyerID`),
  CONSTRAINT `fk_auction_user` FOREIGN KEY (`SellerID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_auction_user1` FOREIGN KEY (`BuyerID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auction`
--

LOCK TABLES `auction` WRITE;
/*!40000 ALTER TABLE `auction` DISABLE KEYS */;
/*!40000 ALTER TABLE `auction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auction_has_category`
--

DROP TABLE IF EXISTS `auction_has_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auction_has_category` (
  `auction_AuctionID` bigint(20) NOT NULL,
  `category_CategoryID` int(11) NOT NULL,
  PRIMARY KEY (`auction_AuctionID`,`category_CategoryID`),
  KEY `fk_auction_has_category_category1_idx` (`category_CategoryID`),
  KEY `fk_auction_has_category_auction1_idx` (`auction_AuctionID`),
  CONSTRAINT `fk_auction_has_category_auction1` FOREIGN KEY (`auction_AuctionID`) REFERENCES `auction` (`AuctionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_auction_has_category_category1` FOREIGN KEY (`category_CategoryID`) REFERENCES `category` (`CategoryID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auction_has_category`
--

LOCK TABLES `auction_has_category` WRITE;
/*!40000 ALTER TABLE `auction_has_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `auction_has_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bid`
--

DROP TABLE IF EXISTS `bid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bid` (
  `Bid` bigint(20) NOT NULL AUTO_INCREMENT,
  `BidderID` bigint(20) NOT NULL,
  `AuctionID` bigint(20) DEFAULT NULL,
  `BidTime` datetime NOT NULL,
  `Amount` float NOT NULL,
  PRIMARY KEY (`Bid`),
  UNIQUE KEY `Bid_UNIQUE` (`Bid`),
  KEY `fk_bid_auction1_idx` (`AuctionID`),
  KEY `fk_bid_user1_idx` (`BidderID`),
  CONSTRAINT `fk_bid_auction1` FOREIGN KEY (`AuctionID`) REFERENCES `auction` (`AuctionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_bid_user1` FOREIGN KEY (`BidderID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bid`
--

LOCK TABLES `bid` WRITE;
/*!40000 ALTER TABLE `bid` DISABLE KEYS */;
/*!40000 ALTER TABLE `bid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `CategoryID` int(11) NOT NULL AUTO_INCREMENT,
  `CategoryName` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`CategoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemimage`
--

DROP TABLE IF EXISTS `itemimage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itemimage` (
  `AuctionId` bigint(20) NOT NULL,
  `ImageFileName` varchar(100) NOT NULL,
  `ItemImageId` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ItemImageId`),
  UNIQUE KEY `ItemImageId_UNIQUE` (`ItemImageId`),
  KEY `fk_itemImage_auction1` (`AuctionId`),
  CONSTRAINT `fk_itemimage_auction1` FOREIGN KEY (`AuctionId`) REFERENCES `auction` (`AuctionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemimage`
--

LOCK TABLES `itemimage` WRITE;
/*!40000 ALTER TABLE `itemimage` DISABLE KEYS */;
/*!40000 ALTER TABLE `itemimage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `MessageID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SenderID` bigint(20) NOT NULL,
  `ReceiverID` bigint(20) NOT NULL,
  `AuctionID` bigint(20) NOT NULL,
  `Message` varchar(1000) NOT NULL,
  `SendDate` datetime NOT NULL,
  `IsRead` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`MessageID`),
  UNIQUE KEY `MessageID_UNIQUE` (`MessageID`),
  KEY `fk_messages_user1_idx` (`SenderID`),
  KEY `fk_messages_user2_idx` (`ReceiverID`),
  KEY `fk_messages_auction1_idx` (`AuctionID`),
  CONSTRAINT `fk_messages_auction1` FOREIGN KEY (`AuctionID`) REFERENCES `auction` (`AuctionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_messages_user1` FOREIGN KEY (`SenderID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_messages_user2` FOREIGN KEY (`ReceiverID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `NotificationID` bigint(20) NOT NULL AUTO_INCREMENT,
  `Type` varchar(45) NOT NULL,
  `AuctionID` bigint(20) NOT NULL,
  `ReceiverID` bigint(20) NOT NULL,
  `ActorID` bigint(20) NOT NULL,
  `isSeen` tinyint(1) DEFAULT '0',
  `DateAdded` datetime NOT NULL,
  `MessageID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`NotificationID`),
  UNIQUE KEY `NotificationID_UNIQUE` (`NotificationID`),
  KEY `fk_notification_auction_idx` (`AuctionID`),
  KEY `fk_notification_user_idx` (`ReceiverID`),
  KEY `fk_notification_user1_idx` (`ActorID`),
  KEY `fk_notification_message` (`MessageID`),
  CONSTRAINT `fk_notification_auction` FOREIGN KEY (`AuctionID`) REFERENCES `auction` (`AuctionID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_notification_message` FOREIGN KEY (`MessageID`) REFERENCES `messages` (`MessageID`) ON DELETE CASCADE,
  CONSTRAINT `fk_notification_user` FOREIGN KEY (`ReceiverID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_notification_user1` FOREIGN KEY (`ActorID`) REFERENCES `user` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rating` (
  `FromID` bigint(20) NOT NULL,
  `ToID` bigint(20) NOT NULL,
  `AuctionID` bigint(20) NOT NULL,
  `Rating` int(11) NOT NULL,
  `isSeller` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`FromID`,`ToID`,`AuctionID`),
  KEY `fk_rating_user1_idx` (`FromID`),
  KEY `fk_rating_user2_idx` (`ToID`),
  KEY `fk_rating_auction1_idx` (`AuctionID`),
  CONSTRAINT `fk_rating_auction1` FOREIGN KEY (`AuctionID`) REFERENCES `auction` (`AuctionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_rating_user1` FOREIGN KEY (`FromID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_rating_user2` FOREIGN KEY (`ToID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `UserID` bigint(20) NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL,
  `Hash` varbinary(512) NOT NULL,
  `Salt` varbinary(256) NOT NULL,
  `Firstname` varchar(45) NOT NULL,
  `Lastname` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `PhoneNumber` varchar(45) NOT NULL,
  `Vat` varchar(45) NOT NULL,
  `HomeAddress` varchar(200) NOT NULL,
  `Latitude` varchar(45) NOT NULL,
  `longitude` varchar(45) NOT NULL,
  `City` varchar(100) NOT NULL,
  `Country` varchar(150) NOT NULL,
  `SignUpDate` date NOT NULL,
  `IsAdmin` tinyint(1) NOT NULL DEFAULT '0',
  `isApproved` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Id_UNIQUE` (`UserID`),
  UNIQUE KEY `Username_UNIQUE` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','∏†ô]õ\‹\›ÒıOüôˇ\’>','∞∂;=Qº\Œ€ø≈ç\ﬁ\»','System','Administrator','admin@admin.com','6986868686','123456','ŒëŒ∏ŒÆŒΩŒ±','37.968196','23.77868710000007','ŒëŒ∏ŒÆŒΩŒ±','ŒïŒªŒªŒ¨Œ¥Œ±','2016-10-02',1,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-10-02 18:04:12
