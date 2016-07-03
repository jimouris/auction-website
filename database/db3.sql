-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

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
  PRIMARY KEY (`UserID`),
  UNIQUE INDEX `Id_UNIQUE` (`UserID` ASC),
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 25
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auctionwebsite`.`auction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`auction` (
  `AuctionID` BIGINT(20) NOT NULL,
  `SellerID` BIGINT(20) NOT NULL,
  `BuyerID` BIGINT(20) NULL,
  `Name` VARCHAR(150) NOT NULL,
  `Description` VARCHAR(1500) NULL DEFAULT NULL,
  `LowestBid` DOUBLE NOT NULL,
  `CurrentBid` DOUBLE NOT NULL,
  `FinalPrice` DOUBLE NOT NULL,
  `StartingDate` DATE NOT NULL,
  `EndingDate` DATE NOT NULL,
  `Country` VARCHAR(45) NOT NULL,
  `Location` VARCHAR(45) NOT NULL,
  `NumOfBids` INT(11) NULL DEFAULT NULL,
  `Longtitude` FLOAT NULL DEFAULT NULL,
  `Latitude` FLOAT NOT NULL,
  `IsStarted` TINYINT(1) NULL DEFAULT NULL,
  `BuyPrice` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`AuctionID`),
  INDEX `fk_auction_user_idx` (`SellerID` ASC),
  INDEX `fk_auction_user1_idx` (`BuyerID` ASC),
  CONSTRAINT `fk_auction_user`
    FOREIGN KEY (`SellerID`)
    REFERENCES `auctionwebsite`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_auction_user1`
    FOREIGN KEY (`BuyerID`)
    REFERENCES `auctionwebsite`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auctionwebsite`.`bid`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`bid` (
  `BidderID` BIGINT(20) NOT NULL,
  `AuctionID` BIGINT(20) NOT NULL,
  `BidTime` DATE NOT NULL,
  `Amount` INT NOT NULL,
  INDEX `fk_bid_auction1_idx` (`AuctionID` ASC),
  INDEX `fk_bid_user1_idx` (`BidderID` ASC),
  PRIMARY KEY (`BidderID`, `AuctionID`),
  CONSTRAINT `fk_bid_auction1`
    FOREIGN KEY (`AuctionID`)
    REFERENCES `auctionwebsite`.`auction` (`AuctionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bid_user1`
    FOREIGN KEY (`BidderID`)
    REFERENCES `auctionwebsite`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auctionwebsite`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`category` (
  `CategoryID` INT(11) NOT NULL AUTO_INCREMENT,
  `CategoryName` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`CategoryID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auctionwebsite`.`auctionCategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`auctionCategory` (
  `AuctionId` BIGINT(20) NOT NULL,
  `CategroryId` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`AuctionId`),
  INDEX `fk_auctionCategory_category1_idx` (`CategroryId` ASC),
  CONSTRAINT `fk_auctionCategory_category1`
    FOREIGN KEY (`CategroryId`)
    REFERENCES `auctionwebsite`.`category` (`CategoryID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_auctionCategory_auction1`
    FOREIGN KEY (`AuctionId`)
    REFERENCES `auctionwebsite`.`auction` (`AuctionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auctionwebsite`.`itemImage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`itemImage` (
  `AuctionId` BIGINT(20) NOT NULL,
  `ImageFileName` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ImageFileName`, `AuctionId`),
  CONSTRAINT `fk_itemImage_auction1`
    FOREIGN KEY (`AuctionId`)
    REFERENCES `auctionwebsite`.`auction` (`AuctionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auctionwebsite`.`messages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`messages` (
  `MessageID` BIGINT(20) NOT NULL,
  `SenderID` BIGINT(20) NOT NULL,
  `ReceiverID` BIGINT(20) NOT NULL,
  `AuctionID` BIGINT(20) NOT NULL,
  `Message` VARCHAR(1000) NOT NULL,
  `ReceivedDate` DATE NOT NULL,
  `IsRead` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`MessageID`),
  INDEX `fk_messages_user1_idx` (`SenderID` ASC),
  INDEX `fk_messages_user2_idx` (`ReceiverID` ASC),
  UNIQUE INDEX `MessageID_UNIQUE` (`MessageID` ASC),
  INDEX `fk_messages_auction1_idx` (`AuctionID` ASC),
  CONSTRAINT `fk_messages_user1`
    FOREIGN KEY (`SenderID`)
    REFERENCES `auctionwebsite`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_messages_user2`
    FOREIGN KEY (`ReceiverID`)
    REFERENCES `auctionwebsite`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_messages_auction1`
    FOREIGN KEY (`AuctionID`)
    REFERENCES `auctionwebsite`.`auction` (`AuctionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auctionwebsite`.`rating`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auctionwebsite`.`rating` (
  `FromID` BIGINT(20) NOT NULL,
  `ToID` BIGINT(20) NOT NULL,
  `Rate` INT NOT NULL,
  INDEX `fk_rating_user1_idx` (`FromID` ASC),
  INDEX `fk_rating_user2_idx` (`ToID` ASC),
  PRIMARY KEY (`FromID`, `ToID`),
  CONSTRAINT `fk_rating_user1`
    FOREIGN KEY (`FromID`)
    REFERENCES `auctionwebsite`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rating_user2`
    FOREIGN KEY (`ToID`)
    REFERENCES `auctionwebsite`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
