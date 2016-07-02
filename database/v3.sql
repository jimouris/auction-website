-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema auction-website
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema auction-website
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `auction-website` DEFAULT CHARACTER SET utf8 ;
USE `auction-website` ;

-- -----------------------------------------------------
-- Table `auction-website`.`auction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auction-website`.`auction` (
  `ItemID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(150) NOT NULL,
  `Description` VARCHAR(1500) NULL DEFAULT NULL,
  `LowestBid` DOUBLE NOT NULL,
  `CurrentBid` DOUBLE NOT NULL,
  `FinalPrice` DOUBLE NOT NULL,
  `StartingDate` DATE NOT NULL,
  `EndingDate` DATE NOT NULL,
  `Country` VARCHAR(45) NOT NULL,
  `Location` VARCHAR(45) NOT NULL,
  `SellerID` BIGINT(20) NOT NULL,
  `NumOfBids` INT(11) NULL DEFAULT NULL,
  `Longtitude` FLOAT NULL DEFAULT NULL,
  `Latitude` FLOAT NOT NULL,
  `IsStarted` TINYINT(1) NULL DEFAULT NULL,
  `BuyPrice` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ItemID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auction-website`.`bid`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auction-website`.`bid` (
  `BidID` INT(11) NOT NULL AUTO_INCREMENT,
  `BidderID` INT(11) NOT NULL,
  `AuctionID` INT(11) NOT NULL,
  `BidTime` DATE NOT NULL,
  `Amount` INT(11) NOT NULL,
  PRIMARY KEY (`BidID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auction-website`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auction-website`.`category` (
  `CategoryID` INT(11) NOT NULL AUTO_INCREMENT,
  `CategoryName` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`CategoryID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auction-website`.`itemcategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auction-website`.`itemcategory` (
  `AuctionId` INT(11) NOT NULL,
  `CategroryId` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`AuctionId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auction-website`.`itemimg`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auction-website`.`itemimg` (
  `AuctionId` INT(11) NOT NULL,
  `ImageFile` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`AuctionId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auction-website`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auction-website`.`user` (
  `UserID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(50) NOT NULL,
  `Password` VARCHAR(150) NOT NULL,
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
  `bidderRating` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE INDEX `Id_UNIQUE` (`UserID` ASC),
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 25
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auction-website`.`items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auction-website`.`items` (
  `ItemID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(150) NOT NULL,
  `Description` VARCHAR(100) NULL DEFAULT NULL,
  `StartingPrice` DOUBLE NOT NULL,
  `CurrentBid` DOUBLE NOT NULL,
  `FinalPrice` DOUBLE NOT NULL,
  `StartingDate` DATE NOT NULL,
  `EndingDate` DATE NOT NULL,
  `Country` VARCHAR(45) NOT NULL,
  `Location` VARCHAR(45) NOT NULL,
  `UserID (Seller)` BIGINT(20) NOT NULL,
  PRIMARY KEY (`ItemID`),
  UNIQUE INDEX `ItemId_UNIQUE` (`ItemID` ASC),
  INDEX `fk_Items_1_idx` (`UserID (Seller)` ASC),
  CONSTRAINT `fk_Items_1`
    FOREIGN KEY (`UserID (Seller)`)
    REFERENCES `auction-website`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auction-website`.`messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auction-website`.`messages` (
  `MessageID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `SenderID` INT(11) NOT NULL,
  `Message` VARCHAR(1000) NOT NULL,
  `ReceivedDate` DATE NOT NULL,
  `IsRead` TINYINT(1) NOT NULL DEFAULT '0',
  `ReceiverID` INT(11) NOT NULL,
  `AuctionID` INT(11) NOT NULL,
  PRIMARY KEY (`MessageID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auction-website`.`rating`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auction-website`.`rating` (
  `RatingID` INT(11) NOT NULL AUTO_INCREMENT,
  `FromID` INT(11) NULL DEFAULT NULL,
  `ToID` INT(11) NULL DEFAULT NULL,
  `Rate` INT(10) NULL DEFAULT NULL,
  PRIMARY KEY (`RatingID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auction-website`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auction-website`.`transaction` (
  `TransactionID` INT(11) NOT NULL AUTO_INCREMENT,
  `BuyerID` INT(11) NULL DEFAULT NULL,
  `SellerID` INT(11) NULL DEFAULT NULL,
  `Price` FLOAT NULL DEFAULT NULL,
  `TransactionDate` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`TransactionID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auction-website`.`userinbox`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auction-website`.`userinbox` (
  `MessageID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `SenderID` INT(11) NOT NULL,
  `Message` VARCHAR(1000) NOT NULL,
  `ReceivedDate` DATE NOT NULL,
  `IsRead` TINYINT(1) NOT NULL DEFAULT '0',
  `ReceiverID` INT(11) NOT NULL,
  `AuctionID` INT(11) NOT NULL,
  PRIMARY KEY (`MessageID`),
  UNIQUE INDEX `MessageID_UNIQUE` (`MessageID` ASC),
  UNIQUE INDEX `ConversationID_UNIQUE` (`SenderID` ASC),
  INDEX `fk_UserInbox_1_idx` (`SenderID` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
