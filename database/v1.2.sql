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
-- Table `auction-website`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auction-website`.`user` (
  `UserID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(50) NOT NULL,
  `Password` VARCHAR(150) NOT NULL,
  `FirstName` VARCHAR(45) NOT NULL,
  `Lastname` VARCHAR(45) NOT NULL,
  `mail` VARCHAR(45) NOT NULL,
  `PhoneNumber` VARCHAR(45) NOT NULL,
  `AFM` VARCHAR(45) NOT NULL,
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
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC),
  UNIQUE INDEX `AFM_UNIQUE` (`AFM` ASC),
  UNIQUE INDEX `e-mail_UNIQUE` (`mail` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 18
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
-- Table `auction-website`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auction-website`.`transaction` (
  `TransactionID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `SellerID` BIGINT(20) NOT NULL,
  `BuyerID` BIGINT(20) NOT NULL,
  `ItemName` VARCHAR(150) NOT NULL,
  `ItemPrice` DOUBLE NOT NULL,
  PRIMARY KEY (`TransactionID`),
  UNIQUE INDEX `TransactionID_UNIQUE` (`TransactionID` ASC),
  UNIQUE INDEX `SellerID_UNIQUE` (`SellerID` ASC),
  UNIQUE INDEX `BuyerID_UNIQUE` (`BuyerID` ASC),
  INDEX `fk_Transaction_2_idx` (`SellerID` ASC),
  CONSTRAINT `fk_Transaction_1`
    FOREIGN KEY (`BuyerID`)
    REFERENCES `auction-website`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Transaction_2`
    FOREIGN KEY (`SellerID`)
    REFERENCES `auction-website`.`user` (`UserID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auction-website`.`sellerinbox`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auction-website`.`sellerinbox` (
  `MessageID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `ConversationID` BIGINT(20) NOT NULL,
  `Message` VARCHAR(1000) NOT NULL,
  `SendDate` DATE NOT NULL,
  `isRead` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`MessageID`),
  UNIQUE INDEX `MessageId_UNIQUE` (`MessageID` ASC),
  UNIQUE INDEX `ConversationID_UNIQUE` (`ConversationID` ASC),
  INDEX `fk_SellerInbox_1_idx` (`ConversationID` ASC),
  CONSTRAINT `fk_SellerInbox_1`
    FOREIGN KEY (`ConversationID`)
    REFERENCES `auction-website`.`transaction` (`TransactionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `auction-website`.`userinbox`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `auction-website`.`userinbox` (
  `MessageID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `ConversationID` BIGINT(20) NOT NULL,
  `Message` VARCHAR(1000) NOT NULL,
  `ReceivedDate` DATE NOT NULL,
  `isRead` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`MessageID`),
  UNIQUE INDEX `MessageID_UNIQUE` (`MessageID` ASC),
  UNIQUE INDEX `ConversationID_UNIQUE` (`ConversationID` ASC),
  INDEX `fk_UserInbox_1_idx` (`ConversationID` ASC),
  CONSTRAINT `fk_UserInbox_1`
    FOREIGN KEY (`ConversationID`)
    REFERENCES `auction-website`.`transaction` (`TransactionID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
