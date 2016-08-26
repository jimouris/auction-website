-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema auctionwebsite
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema auctionwebsite
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `auctionwebsite` DEFAULT CHARACTER SET utf8 ;
USE `auctionwebsite` ;

-- -----------------------------------------------------
-- Table `auctionwebsite`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`user` (
  `UserID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(50) NOT NULL,
  `Hash` VARBINARY(512) NOT NULL,
  `Salt` VARBINARY(256) NOT NULL,
  `Firstname` VARCHAR(45) NOT NULL,
  `Lastname` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  `PhoneNumber` VARCHAR(45) NOT NULL,
  `Vat` VARCHAR(45) NOT NULL,
  `HomeAddress` VARCHAR(60) NOT NULL,
  `Latitude` VARCHAR(45) NOT NULL,
  `longitude` VARCHAR(45) NOT NULL,
  `City` VARCHAR(60) NOT NULL,
  `Country` VARCHAR(45) NOT NULL,
  `SignUpDate` DATE NOT NULL,
  `IsAdmin` TINYINT(1) NOT NULL DEFAULT '0',
  `isApproved` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`UserID`),
  UNIQUE INDEX `Id_UNIQUE` (`UserID` ASC),
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auctionwebsite`.`auction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`auction` (
  `AuctionID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `SellerID` BIGINT(20) NOT NULL,
  `BuyerID` BIGINT(20) NULL DEFAULT NULL,
  `Name` VARCHAR(150) NOT NULL,
  `Description` VARCHAR(1500) NULL DEFAULT NULL,
  `LowestBid` DOUBLE NOT NULL,
  `FinalPrice` DOUBLE NOT NULL,
  `StartingDate` DATE NULL DEFAULT NULL,
  `EndingDate` DATE NULL DEFAULT NULL,
  `Country` VARCHAR(45) NOT NULL,
  `Location` VARCHAR(45) NOT NULL,
  `Longitude` FLOAT NULL DEFAULT NULL,
  `Latitude` FLOAT NULL DEFAULT NULL,
  `NumOfBids` INT(11) NULL DEFAULT NULL,
  `IsStarted` TINYINT(1) NULL DEFAULT '0',
  `BuyPrice` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`AuctionID`),
  INDEX `fk_auction_user_idx` (`SellerID` ASC),
  INDEX `fk_auction_user1_idx` (`BuyerID` ASC),
  CONSTRAINT `fk_auction_user`
    FOREIGN KEY (`SellerID`)
    REFERENCES `auctionwebsite`.`user` (`UserID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_auction_user1`
    FOREIGN KEY (`BuyerID`)
    REFERENCES `auctionwebsite`.`user` (`UserID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 44
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auctionwebsite`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`category` (
  `CategoryID` INT(11) NOT NULL AUTO_INCREMENT,
  `CategoryName` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`CategoryID`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auctionwebsite`.`auction_has_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`auction_has_category` (
  `auction_AuctionID` BIGINT(20) NOT NULL,
  `category_CategoryID` INT(11) NOT NULL,
  PRIMARY KEY (`auction_AuctionID`, `category_CategoryID`),
  INDEX `fk_auction_has_category_category1_idx` (`category_CategoryID` ASC),
  INDEX `fk_auction_has_category_auction1_idx` (`auction_AuctionID` ASC),
  CONSTRAINT `fk_auction_has_category_auction1`
    FOREIGN KEY (`auction_AuctionID`)
    REFERENCES `auctionwebsite`.`auction` (`AuctionID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_auction_has_category_category1`
    FOREIGN KEY (`category_CategoryID`)
    REFERENCES `auctionwebsite`.`category` (`CategoryID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auctionwebsite`.`bid`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`bid` (
  `Bid` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `BidderID` BIGINT(20) NOT NULL,
  `AuctionID` BIGINT(20) NOT NULL,
  `BidTime` DATETIME NOT NULL,
  `Amount` FLOAT NOT NULL,
  PRIMARY KEY (`Bid`),
  UNIQUE INDEX `Bid_UNIQUE` (`Bid` ASC),
  INDEX `fk_bid_auction1_idx` (`AuctionID` ASC),
  INDEX `fk_bid_user1_idx` (`BidderID` ASC),
  CONSTRAINT `fk_bid_auction1`
    FOREIGN KEY (`AuctionID`)
    REFERENCES `auctionwebsite`.`auction` (`AuctionID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_bid_user1`
    FOREIGN KEY (`BidderID`)
    REFERENCES `auctionwebsite`.`user` (`UserID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 62
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auctionwebsite`.`itemimage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`itemimage` (
  `AuctionId` BIGINT(20) NOT NULL,
  `ImageFileName` VARCHAR(100) NOT NULL,
  `ItemImageId` BIGINT(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ItemImageId`),
  UNIQUE INDEX `ItemImageId_UNIQUE` (`ItemImageId` ASC),
  INDEX `fk_itemImage_auction1` (`AuctionId` ASC),
  CONSTRAINT `fk_itemimage_auction1`
    FOREIGN KEY (`AuctionId`)
    REFERENCES `auctionwebsite`.`auction` (`AuctionID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 22
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auctionwebsite`.`messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`messages` (
  `MessageID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `SenderID` BIGINT(20) NOT NULL,
  `ReceiverID` BIGINT(20) NOT NULL,
  `AuctionID` BIGINT(20) NOT NULL,
  `Message` VARCHAR(1000) NOT NULL,
  `SendDate` DATETIME NOT NULL,
  `IsRead` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`MessageID`),
  UNIQUE INDEX `MessageID_UNIQUE` (`MessageID` ASC),
  INDEX `fk_messages_user1_idx` (`SenderID` ASC),
  INDEX `fk_messages_user2_idx` (`ReceiverID` ASC),
  INDEX `fk_messages_auction1_idx` (`AuctionID` ASC),
  CONSTRAINT `fk_messages_auction1`
    FOREIGN KEY (`AuctionID`)
    REFERENCES `auctionwebsite`.`auction` (`AuctionID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_messages_user1`
    FOREIGN KEY (`SenderID`)
    REFERENCES `auctionwebsite`.`user` (`UserID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_messages_user2`
    FOREIGN KEY (`ReceiverID`)
    REFERENCES `auctionwebsite`.`user` (`UserID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 48
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auctionwebsite`.`notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`notification` (
  `NotificationID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `Type` VARCHAR(45) NOT NULL,
  `AuctionID` BIGINT(20) NOT NULL,
  `ReceiverID` BIGINT(20) NOT NULL,
  `ActorID` BIGINT(20) NOT NULL,
  `isSeen` TINYINT(1) NULL DEFAULT '0',
  `DateAdded` DATETIME NOT NULL,
  PRIMARY KEY (`NotificationID`),
  UNIQUE INDEX `NotificationID_UNIQUE` (`NotificationID` ASC),
  INDEX `fk_notification_auction_idx` (`AuctionID` ASC),
  INDEX `fk_notification_user_idx` (`ReceiverID` ASC),
  INDEX `fk_notification_user1_idx` (`ActorID` ASC),
  CONSTRAINT `fk_notification_auction`
    FOREIGN KEY (`AuctionID`)
    REFERENCES `auctionwebsite`.`auction` (`AuctionID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_notification_user`
    FOREIGN KEY (`ReceiverID`)
    REFERENCES `auctionwebsite`.`user` (`UserID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_notification_user1`
    FOREIGN KEY (`ActorID`)
    REFERENCES `auctionwebsite`.`user` (`UserID`))
ENGINE = InnoDB
AUTO_INCREMENT = 23
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auctionwebsite`.`rating`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`rating` (
  `FromID` BIGINT(20) NOT NULL,
  `ToID` BIGINT(20) NOT NULL,
  `AuctionID` BIGINT(20) NOT NULL,
  `Rating` INT(11) NOT NULL,
  PRIMARY KEY (`FromID`, `ToID`, `AuctionID`),
  INDEX `fk_rating_user1_idx` (`FromID` ASC),
  INDEX `fk_rating_user2_idx` (`ToID` ASC),
  INDEX `fk_rating_auction1_idx` (`AuctionID` ASC),
  CONSTRAINT `fk_rating_auction1`
    FOREIGN KEY (`AuctionID`)
    REFERENCES `auctionwebsite`.`auction` (`AuctionID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_rating_user1`
    FOREIGN KEY (`FromID`)
    REFERENCES `auctionwebsite`.`user` (`UserID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_rating_user2`
    FOREIGN KEY (`ToID`)
    REFERENCES `auctionwebsite`.`user` (`UserID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
