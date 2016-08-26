CREATE DATABASE  IF NOT EXISTS `auctionwebsite` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `auctionwebsite`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: auctionwebsite
-- ------------------------------------------------------
-- Server version	5.7.12-log

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
  `Description` varchar(1500) DEFAULT NULL,
  `LowestBid` double NOT NULL,
  `FinalPrice` double NOT NULL,
  `StartingDate` date DEFAULT NULL,
  `EndingDate` date DEFAULT NULL,
  `Country` varchar(45) NOT NULL,
  `Location` varchar(45) NOT NULL,
  `Longitude` float DEFAULT NULL,
  `Latitude` float DEFAULT NULL,
  `NumOfBids` int(11) DEFAULT NULL,
  `IsStarted` tinyint(1) DEFAULT '0',
  `BuyPrice` int(11) DEFAULT NULL,
  PRIMARY KEY (`AuctionID`),
  KEY `fk_auction_user_idx` (`SellerID`),
  KEY `fk_auction_user1_idx` (`BuyerID`),
  CONSTRAINT `fk_auction_user` FOREIGN KEY (`SellerID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_auction_user1` FOREIGN KEY (`BuyerID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auction`
--

LOCK TABLES `auction` WRITE;
/*!40000 ALTER TABLE `auction` DISABLE KEYS */;
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (15,5,6,'trelitses','',123,0,'2016-08-24','2016-08-17','Ελλάδα','Κωνσταντινουπόλεως, Ταύρος',23.6971,37.9699,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (17,5,NULL,'trel23','',123,0,'2016-08-14','2016-08-21','Ελλάδα','Τριπόδων, Αθήνα',23.7296,37.9723,NULL,1,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (18,6,NULL,'treleeeees3','',123,0,NULL,NULL,'Ελλάδα','Τριπόδων, Αθήνα',23.7296,37.9723,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (19,6,NULL,'trelasda','',123,0,'2016-08-14','2016-08-18','Ελλάδα','Τριπόδων, Αθήνα',23.7296,37.9723,NULL,1,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (20,5,NULL,'yolyolo','',123,0,'2016-08-17','2016-08-15','Ελλάδα','Κωνσταντινουπόλεως, Ταύρος',23.6971,37.9699,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (21,5,6,'tralalal','',12,0,'2016-08-24','2016-08-17','Ελλάδα','Κωνσταντινουπόλεως, Ταύρος',23.6971,37.9699,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (22,5,NULL,'trelasdasad1023','',12,0,'2016-08-17','2016-08-20','Ελλάδα','Κωνσταντινουπόλεως, Ταύρος',23.6971,37.9699,NULL,1,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (23,5,NULL,'tralala','',123,0,NULL,NULL,'Ελλάδα','Κολωνάκι, Αθήνα',23.743,37.978,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (24,5,6,'testimage3','',12,0,'2016-08-24','2016-08-22','Ελλάδα','Κολωνάκι, Αθήνα',23.743,37.978,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (25,5,NULL,'78787','',123,0,NULL,NULL,'Ελλάδα','Κολωνάκι, Αθήνα',23.743,37.978,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (26,5,NULL,'123','',3,0,NULL,NULL,'Ελλάδα','Kos, Κως, Ελλάδα',27.2877,36.8915,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (29,5,NULL,'peris','',12,0,NULL,NULL,'Ελλάδα','Κολωνάκι, Αθήνα',23.743,37.978,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (30,5,NULL,'peris2','',123,0,NULL,NULL,'Ελλάδα','Κολωνάκι, Αθήνα',23.743,37.978,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (31,5,NULL,'yooooo','',123,0,NULL,NULL,'Ελλάδα','Κολωνάκι, Αθήνα',23.743,37.978,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (32,5,NULL,'mperarors','',123,0,NULL,NULL,'Ελλάδα','46, Αγίας Άννης, Άγιος Ιωάννης Ρέντης',23.6741,37.9671,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (33,5,NULL,'asdasdasdqwe','',123,0,NULL,NULL,'Ελλάδα','Κολωνάκι, Αθήνα',23.743,37.978,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (34,5,NULL,'asdad','',123,0,NULL,NULL,'asda','asda',23.7286,37.9264,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (35,5,NULL,'apdaspo1','',123,0,NULL,NULL,'apsd','asdl',23.7286,37.9264,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (36,5,NULL,'asd123d','',123,0,NULL,NULL,'asd','sd',23.7286,37.9264,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (37,5,NULL,'asdaldk','',123,0,NULL,NULL,'Ελλάδα','8, Θερμοπυλων, Χαλάνδρι',23.7932,38.0296,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (38,5,NULL,'aasd123','',12,0,NULL,NULL,'asd','asd',23.7286,37.9264,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (39,5,NULL,'adasd1','',123,0,NULL,NULL,'Ελλάδα','7, Λεωφόρος Αλίμου, Άλιμος',23.7202,37.9066,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (41,5,NULL,'asdasd','',123,0,NULL,NULL,'Ελλάδα','8, Θερμοπυλων, Χαλάνδρι',23.7932,38.0295,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (42,5,NULL,'dasdasd','',123,0,NULL,NULL,'Υεμένη','Asdas',45.0793,15.7642,NULL,0,-1);
INSERT INTO `auction` (`AuctionID`, `SellerID`, `BuyerID`, `Name`, `Description`, `LowestBid`, `FinalPrice`, `StartingDate`, `EndingDate`, `Country`, `Location`, `Longitude`, `Latitude`, `NumOfBids`, `IsStarted`, `BuyPrice`) VALUES (43,5,NULL,'asdasd','',132,0,'2016-08-22','2016-08-24','Ελλάδα','47, Ακροπόλεως, Περιστέρι',23.6947,38.0085,NULL,1,-1);
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
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (19,12);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (20,12);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (21,12);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (22,12);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (24,12);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (25,12);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (26,12);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (29,12);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (31,12);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (32,12);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (33,12);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (34,12);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (37,12);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (39,12);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (43,12);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (17,13);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (18,13);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (19,13);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (22,13);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (23,13);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (30,13);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (35,13);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (36,13);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (38,13);
INSERT INTO `auction_has_category` (`auction_AuctionID`, `category_CategoryID`) VALUES (41,13);
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
  `AuctionID` bigint(20) NOT NULL,
  `BidTime` datetime NOT NULL,
  `Amount` float NOT NULL,
  PRIMARY KEY (`Bid`),
  UNIQUE KEY `Bid_UNIQUE` (`Bid`),
  KEY `fk_bid_auction1_idx` (`AuctionID`),
  KEY `fk_bid_user1_idx` (`BidderID`),
  CONSTRAINT `fk_bid_auction1` FOREIGN KEY (`AuctionID`) REFERENCES `auction` (`AuctionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_bid_user1` FOREIGN KEY (`BidderID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bid`
--

LOCK TABLES `bid` WRITE;
/*!40000 ALTER TABLE `bid` DISABLE KEYS */;
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (15,6,15,'2016-08-14 14:34:56',123);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (16,6,15,'2016-08-14 14:35:06',124);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (17,6,15,'2016-08-14 14:35:08',125);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (18,6,15,'2016-08-14 14:35:09',126);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (19,6,15,'2016-08-14 14:37:13',127);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (20,6,15,'2016-08-14 14:40:22',128);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (21,6,15,'2016-08-14 14:40:23',129);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (22,6,15,'2016-08-14 14:40:44',130);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (23,6,15,'2016-08-14 14:56:06',131);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (24,6,15,'2016-08-14 14:58:50',132);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (25,6,15,'2016-08-14 14:59:16',133);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (26,6,15,'2016-08-14 15:23:30',134);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (27,6,15,'2016-08-14 15:28:35',135);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (28,6,15,'2016-08-14 15:34:10',136);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (29,6,15,'2016-08-14 15:34:10',137);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (30,6,15,'2016-08-14 15:45:21',138);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (31,6,15,'2016-08-14 15:53:17',139);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (32,6,15,'2016-08-14 15:53:50',140);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (33,6,15,'2016-08-14 15:54:10',141);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (34,6,15,'2016-08-14 15:54:27',142);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (35,6,15,'2016-08-14 16:01:32',143);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (36,6,15,'2016-08-14 16:01:52',144);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (37,6,15,'2016-08-14 16:07:14',145);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (38,6,15,'2016-08-14 16:08:13',146);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (39,6,15,'2016-08-14 16:09:21',147);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (40,6,15,'2016-08-14 16:10:11',148);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (41,6,15,'2016-08-14 16:10:35',149);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (42,6,17,'2016-08-14 16:18:58',123);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (43,6,17,'2016-08-14 16:18:59',124);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (44,6,17,'2016-08-14 16:19:01',125);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (45,6,17,'2016-08-14 16:19:01',126);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (46,6,17,'2016-08-14 16:19:01',127);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (47,6,17,'2016-08-14 16:19:02',128);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (48,6,17,'2016-08-14 16:19:02',129);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (49,6,17,'2016-08-14 16:19:02',130);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (50,6,17,'2016-08-14 16:19:03',131);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (51,6,17,'2016-08-14 16:19:04',132);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (52,6,17,'2016-08-14 16:21:41',133);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (53,6,17,'2016-08-14 16:21:44',134);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (54,6,17,'2016-08-14 16:21:47',135);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (55,6,17,'2016-08-14 16:21:49',136);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (56,6,21,'2016-08-16 08:09:35',12);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (57,6,21,'2016-08-16 08:09:49',13);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (58,6,24,'2016-08-24 12:32:30',12);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (59,6,24,'2016-08-24 12:32:34',13);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (60,6,24,'2016-08-24 12:32:37',14);
INSERT INTO `bid` (`Bid`, `BidderID`, `AuctionID`, `BidTime`, `Amount`) VALUES (61,6,24,'2016-08-24 12:32:41',15);
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
  `CategoryName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CategoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (`CategoryID`, `CategoryName`) VALUES (12,'laptoppaki');
INSERT INTO `category` (`CategoryID`, `CategoryName`) VALUES (13,'smart');
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemimage`
--

LOCK TABLES `itemimage` WRITE;
/*!40000 ALTER TABLE `itemimage` DISABLE KEYS */;
INSERT INTO `itemimage` (`AuctionId`, `ImageFileName`, `ItemImageId`) VALUES (30,'GOPR0140.JPG',3);
INSERT INTO `itemimage` (`AuctionId`, `ImageFileName`, `ItemImageId`) VALUES (39,'GOPR0518.JPG',9);
INSERT INTO `itemimage` (`AuctionId`, `ImageFileName`, `ItemImageId`) VALUES (41,'GOPR0142.JPG',13);
INSERT INTO `itemimage` (`AuctionId`, `ImageFileName`, `ItemImageId`) VALUES (41,'GOPR0144.JPG',14);
INSERT INTO `itemimage` (`AuctionId`, `ImageFileName`, `ItemImageId`) VALUES (41,'GOPR0175.JPG',15);
INSERT INTO `itemimage` (`AuctionId`, `ImageFileName`, `ItemImageId`) VALUES (42,'GOPR0224.JPG',16);
INSERT INTO `itemimage` (`AuctionId`, `ImageFileName`, `ItemImageId`) VALUES (42,'GOPR0519.JPG',17);
INSERT INTO `itemimage` (`AuctionId`, `ImageFileName`, `ItemImageId`) VALUES (42,'GOPR0541.JPG',18);
INSERT INTO `itemimage` (`AuctionId`, `ImageFileName`, `ItemImageId`) VALUES (43,'GOPR0140.JPG',19);
INSERT INTO `itemimage` (`AuctionId`, `ImageFileName`, `ItemImageId`) VALUES (43,'GOPR0143.JPG',20);
INSERT INTO `itemimage` (`AuctionId`, `ImageFileName`, `ItemImageId`) VALUES (43,'GOPR0175.JPG',21);
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
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (13,6,5,15,'talalalalalalal','2016-08-24 17:48:37',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (15,6,5,15,'asdadsa','2016-08-24 17:48:43',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (19,5,6,15,'asdasd123','2016-08-24 19:56:05',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (21,6,5,15,'treleeeeeeeeeeee','2016-08-25 19:50:57',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (22,5,6,15,'popaaaaa','2016-08-26 16:59:01',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (23,5,6,15,'ahahahahhaha','2016-08-26 17:08:28',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (24,5,6,15,'poasdasdaksl','2016-08-26 17:11:28',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (25,5,6,15,'asdasdaqew','2016-08-26 17:12:25',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (26,5,6,15,'sdasdasdas','2016-08-26 17:29:31',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (27,5,6,15,'asdasdasd','2016-08-26 17:29:33',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (28,5,6,15,'zxczxczxc','2016-08-26 17:29:36',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (29,6,5,15,'asdaskdka','2016-08-26 18:27:36',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (30,6,5,15,'asdlaklskda','2016-08-26 18:27:39',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (31,6,5,15,'asdasdqwe','2016-08-26 18:27:43',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (32,6,5,15,'asdad','2016-08-26 19:26:20',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (33,6,5,15,'czxcz','2016-08-26 19:26:21',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (34,6,5,15,'qwefs','2016-08-26 19:26:23',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (35,5,6,15,'dasda','2016-08-26 19:26:32',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (36,5,6,15,'asdasdasd','2016-08-26 20:05:53',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (37,5,6,15,'asdasdasda','2016-08-26 20:07:25',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (38,5,6,15,'asdasda','2016-08-26 20:15:46',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (39,5,6,15,'asdasdasdqwe','2016-08-26 20:17:16',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (40,5,6,15,'asdasda','2016-08-26 20:17:28',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (41,5,6,15,'asdasdas','2016-08-26 20:34:20',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (42,5,6,15,'asd13212','2016-08-26 20:35:56',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (43,5,6,15,'asdioi','2016-08-26 20:41:59',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (44,5,6,15,'cvsdfd','2016-08-26 20:42:45',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (45,5,6,15,'3i1fjad;jfs','2016-08-26 20:44:25',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (46,6,5,15,'trelelele mou. pws kai pws perimena','2016-08-26 20:45:34',0);
INSERT INTO `messages` (`MessageID`, `SenderID`, `ReceiverID`, `AuctionID`, `Message`, `SendDate`, `IsRead`) VALUES (47,6,5,15,'gia pes, pws pane ta kefia','2016-08-26 20:45:40',0);
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
  PRIMARY KEY (`NotificationID`),
  UNIQUE KEY `NotificationID_UNIQUE` (`NotificationID`),
  KEY `fk_notification_auction_idx` (`AuctionID`),
  KEY `fk_notification_user_idx` (`ReceiverID`),
  KEY `fk_notification_user1_idx` (`ActorID`),
  CONSTRAINT `fk_notification_auction` FOREIGN KEY (`AuctionID`) REFERENCES `auction` (`AuctionID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_notification_user` FOREIGN KEY (`ReceiverID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_notification_user1` FOREIGN KEY (`ActorID`) REFERENCES `user` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` (`NotificationID`, `Type`, `AuctionID`, `ReceiverID`, `ActorID`, `isSeen`, `DateAdded`) VALUES (20,'message',15,6,5,1,'2016-08-26 20:44:25');
INSERT INTO `notification` (`NotificationID`, `Type`, `AuctionID`, `ReceiverID`, `ActorID`, `isSeen`, `DateAdded`) VALUES (21,'message',15,5,6,0,'2016-08-26 20:45:34');
INSERT INTO `notification` (`NotificationID`, `Type`, `AuctionID`, `ReceiverID`, `ActorID`, `isSeen`, `DateAdded`) VALUES (22,'message',15,5,6,1,'2016-08-26 20:45:40');
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
  `HomeAddress` varchar(60) NOT NULL,
  `Latitude` varchar(45) NOT NULL,
  `longitude` varchar(45) NOT NULL,
  `City` varchar(60) NOT NULL,
  `Country` varchar(45) NOT NULL,
  `SignUpDate` date NOT NULL,
  `IsAdmin` tinyint(1) NOT NULL DEFAULT '0',
  `isApproved` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Id_UNIQUE` (`UserID`),
  UNIQUE KEY `Username_UNIQUE` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`UserID`, `Username`, `Hash`, `Salt`, `Firstname`, `Lastname`, `Email`, `PhoneNumber`, `Vat`, `HomeAddress`, `Latitude`, `longitude`, `City`, `Country`, `SignUpDate`, `IsAdmin`, `isApproved`) VALUES (5,'pel','��9_�\�+v[\�|�\�Rm','G_;�\r{@�޾���\n!','pel','pel','pel@pel.com','123','123','Κωνσταντινου Παλαιολόγου, Αθήνα','37.9885495','23.722730299999967','Αθήνα','Ελλάδα','2016-08-12',0,1);
INSERT INTO `user` (`UserID`, `Username`, `Hash`, `Salt`, `Firstname`, `Lastname`, `Email`, `PhoneNumber`, `Vat`, `HomeAddress`, `Latitude`, `longitude`, `City`, `Country`, `SignUpDate`, `IsAdmin`, `isApproved`) VALUES (6,'jim','H�\�\�\�ˤI��Q��\�\�','\Z\�\0/\�ό\�o�)%E','123','123','jjimourisa@ads.com','123','123','Κορωπί','37.9011442','23.872650300000032','Κρωπία','Ελλάδα','2016-08-12',0,1);
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

-- Dump completed on 2016-08-26 20:54:22
