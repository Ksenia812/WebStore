-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema web_store
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema web_store
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `web_store` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `web_store` ;

-- -----------------------------------------------------
-- Table `web_store`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `town` VARCHAR(45) NULL DEFAULT NULL,
  `street` VARCHAR(45) NULL DEFAULT NULL,
  `house_number` VARCHAR(45) NULL DEFAULT NULL,
  `flat_number` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `web_store`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `surname` VARCHAR(45) NULL DEFAULT NULL,
  `login` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(100) NULL DEFAULT NULL,
  `balance` DOUBLE NULL DEFAULT '0',
  `address_id` INT NOT NULL,
  `active` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `address_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_user_address1_idx` (`address_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_address1`
    FOREIGN KEY (`address_id`)
    REFERENCES `web_store`.`address` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `web_store`.`booking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`booking` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `order_date` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `user_id`),
  UNIQUE INDEX `idBooking_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  INDEX `fk_booking_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_booking_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `web_store`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `web_store`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idCategory_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `web_store`.`product_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`product_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`, `category_id`),
  UNIQUE INDEX `idproduct_type_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_product_type_category1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_type_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `web_store`.`category` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `web_store`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `amount` INT NULL DEFAULT NULL,
  `price` DOUBLE NULL DEFAULT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  `product_type_id` INT NOT NULL,
  PRIMARY KEY (`id`, `product_type_id`),
  UNIQUE INDEX `idGoods_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_product_product_type1_idx` (`product_type_id` ASC) VISIBLE,
  CONSTRAINT `fk_product_product_type1`
    FOREIGN KEY (`product_type_id`)
    REFERENCES `web_store`.`product_type` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 23
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `web_store`.`booking_products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`booking_products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `booking_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`id`, `booking_id`, `product_id`),
  UNIQUE INDEX `idBookingGoods_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_booking_goods_booking1_idx` (`booking_id` ASC) VISIBLE,
  INDEX `fk_booking_goods_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_booking_goods_booking1`
    FOREIGN KEY (`booking_id`)
    REFERENCES `web_store`.`booking` (`id`),
  CONSTRAINT `fk_booking_goods_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `web_store`.`product` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 33
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `web_store`.`persistent_logins`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`persistent_logins` (
  `username` VARCHAR(64) NOT NULL,
  `series` VARCHAR(64) NOT NULL,
  `token` VARCHAR(64) NOT NULL,
  `last_used` TIMESTAMP NOT NULL,
  PRIMARY KEY (`series`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `web_store`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idRole_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `web_store`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `web_store`.`user_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`, `role_id`, `user_id`),
  UNIQUE INDEX `idUserRole_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  INDEX `fk_user_role_role_idx` (`role_id` ASC) VISIBLE,
  INDEX `fk_user_role_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_role_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `web_store`.`role` (`id`),
  CONSTRAINT `fk_user_role_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `web_store`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
