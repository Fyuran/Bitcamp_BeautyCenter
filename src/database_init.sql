
CREATE DATABASE IF NOT EXISTS beauty_centerdb;

CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`beauty_center` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `operating_office` varchar(100) NOT NULL,
  `registered_office` varchar(100) NOT NULL,
  `certified_mail` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `mail` varchar(100) DEFAULT NULL,
  `REA` varchar(10) NOT NULL,
  `P_IVA` varchar(12) NOT NULL,
  `opening_hour` time DEFAULT NULL,
  `closing_hour` time DEFAULT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`)
);
  
CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`vat` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `amount` float NOT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`)
);

   CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`user_credentials` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NULL,
  `password` VARCHAR(100) NULL,
  `mail` VARCHAR(100) NULL,
  `iban` VARCHAR(40) NULL,
  `phone` VARCHAR(20) NULL,
  `is_enabled` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `mail_UNIQUE` (`mail` ASC) ,
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC));
  
CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`customer` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `is_female` tinyint DEFAULT '0',
  `birthday` date NOT NULL,
  `birthplace` varchar(200) NOT NULL,
  `eu_tin` varchar(20) NOT NULL,
  `credentials_id` int unsigned NOT NULL,
  `VAT` varchar(12) DEFAULT NULL,
  `recipient_code` varchar(8) DEFAULT NULL,
  `notes` text,
  `loyalty_points` int NOT NULL DEFAULT '0',
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`),
  CONSTRAINT `customer_FK1` FOREIGN KEY (`credentials_id`) REFERENCES `beauty_centerdb`.`user_credentials` (`id`) ON UPDATE CASCADE
);
  
CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`transaction` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL,
  `datetime` DATETIME NOT NULL,
  `payment_method` ENUM('CURRENCY','CARD') NULL,
  `vat_id` int unsigned DEFAULT NULL,
  `customer_id` int unsigned DEFAULT NULL,
  `beauty_id` int unsigned DEFAULT NULL,
  `services` text NOT NULL,
  `is_enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `transactionFK_idx` (`customer_id`),
  KEY `transactionFK2_idx` (`vat_id`),
  KEY `transactionFK3` (`beauty_id`),
  CONSTRAINT `transactionFK1` FOREIGN KEY (`customer_id`) REFERENCES `beauty_centerdb`.`customer` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `transactionFK2` FOREIGN KEY (`vat_id`) REFERENCES `beauty_centerdb`.`vat` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `transactionFK3` FOREIGN KEY (`beauty_id`) REFERENCES `beauty_centerdb`.`beauty_center` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`product` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `amount` INT NOT NULL,
  `minimum` INT NULL DEFAULT 0,
  `price` FLOAT NOT NULL,
  `vat_id` INT UNSIGNED NULL,
  `type` ENUM('ORAL_CARE', 'SKIN_CARE', 'HAIR_CARE', 'BODY_CARE', 'COSMETICS', 'PERFUMES', 'OTHER') NULL,
  `is_enabled` TINYINT NOT NULL DEFAULT 1,
  INDEX `vat_id_idx` (`id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `vat_id`
    FOREIGN KEY (`vat_id`)
    REFERENCES `beauty_centerdb`.`vat` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);
    
    CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`treatment` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(100) NOT NULL,
  `price` FLOAT NULL,
  `vat_id` INT UNSIGNED NULL,
  `duration` TIME NULL,
  `is_enabled` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  INDEX `vat_id_idx` (`vat_id` ASC),
  CONSTRAINT `vat_id_treatment`
    FOREIGN KEY (`vat_id`)
    REFERENCES `beauty_centerdb`.`vat` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);
    
CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`producttreatment` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `product_id` int unsigned NOT NULL,
  `treatment_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `pt_fk1_idx` (`product_id`),
  KEY `pt_fk2_idx` (`treatment_id`),
  CONSTRAINT `pt_fk1` FOREIGN KEY (`product_id`) REFERENCES `beauty_centerdb`.`product` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `pt_fk2` FOREIGN KEY (`treatment_id`) REFERENCES `beauty_centerdb`.`treatment` (`id`) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`employee` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `surname` VARCHAR(100) NOT NULL,
  `is_female` TINYINT NULL DEFAULT 0,
  `birthday` DATE NULL,
  `birthplace` VARCHAR(200) NOT NULL,
  `role` ENUM('PERSONNEL', 'SECRETARY', 'ADMIN') NULL,
  `hired` DATE NULL,
  `termination` DATE NULL,
  `credentials_id` INT UNSIGNED NULL,
  `notes` TEXT NULL,
  `is_enabled` TINYINT NOT NULL DEFAULT 1,
  `treatment_id` INT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  INDEX `employee_fk1_idx` (`credentials_id` ASC),
  INDEX `employee_fk2_idx` (`treatment_id` ASC),
  CONSTRAINT `employee_fk1`
    FOREIGN KEY (`credentials_id`)
    REFERENCES `beauty_centerdb`.`user_credentials` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `employee_fk2`
    FOREIGN KEY (`treatment_id`)
    REFERENCES `beauty_centerdb`.`treatment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);
    
    
CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`reservation` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `time` TIME NOT NULL,
  `is_paid` TINYINT NOT NULL DEFAULT 0,
  `treatment_id` INT UNSIGNED NULL,
  `customer_id` INT UNSIGNED NULL,
  `employee_id` INT UNSIGNED NULL,
  `state` ENUM('CREATED', 'IN_PROGRESS', 'CANCELLED') NULL,
  `is_enabled` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  INDEX `reservation_fk1_idx` (`treatment_id` ASC),
  INDEX `reservation_fk2_idx` (`customer_id` ASC),
  INDEX `reservation_fk3_idx` (`employee_id` ASC),
  CONSTRAINT `reservation_fk1`
    FOREIGN KEY (`treatment_id`)
    REFERENCES `beauty_centerdb`.`treatment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `reservation_fk2`
    FOREIGN KEY (`customer_id`)
    REFERENCES `beauty_centerdb`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `reservation_fk3`
    FOREIGN KEY (`employee_id`)
    REFERENCES `beauty_centerdb`.`employee` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);
  
  
    
    CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`shift` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `start` DATE NOT NULL,
  `end` DATE NOT NULL,
  `type` ENUM('WORK', 'HOLYDAYS') NULL,
  `is_enabled` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`));
  
  CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`shiftemployee` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `shift_id` INT UNSIGNED NOT NULL,
  `employee_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `shiftemployee_fk2_idx` (`employee_Id` ASC),
  INDEX `shiftemployee_fk1_idx` (`shift_id` ASC),
  CONSTRAINT `shiftemployee_fk1`
    FOREIGN KEY (`shift_id`)
    REFERENCES `beauty_centerdb`.`shift` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `shiftemployee_fk2`
    FOREIGN KEY (`employee_id`)
    REFERENCES `beauty_centerdb`.`employee` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);
    
    /*CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`reservationemployee` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `reservation_id` INT UNSIGNED NOT NULL,
  `employee_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `reservationemployee_fk1_idx` (`reservation_id` ASC),
  INDEX `reservationemployee_fk2_idx` (`employee_id` ASC),
  CONSTRAINT `reservationemployee_fk1`
    FOREIGN KEY (`reservation_id`)
    REFERENCES `beauty_centerdb`.`reservation` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `reservationemployee_fk2`
    FOREIGN KEY (`employee_id`)
    REFERENCES `beauty_centerdb`.`employee` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);*/
  
  CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`subscription` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `subperiod` ENUM('MONTHLY', 'QUARTERLY', 'HALF_YEAR', 'YEARLY') NOT NULL,
  `price` FLOAT NOT NULL,
  `vat_id` INT UNSIGNED NULL,
  `discount` FLOAT NOT NULL,
  `is_enabled` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  INDEX `subscription_FK1_idx` (`vat_id` ASC),
  CONSTRAINT `subscription_FK1`
    FOREIGN KEY (`vat_id`)
    REFERENCES `beauty_centerdb`.`vat` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);
    
    CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`customersubscription` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `customer_id` INT UNSIGNED NOT NULL,
  `subscription_id` INT UNSIGNED NOT NULL,
  `start` DATE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `customersubscription_FK1_idx` (`customer_id` ASC),
  INDEX `customersubscription_FK2_idx` (`subscription_id` ASC),
  CONSTRAINT `customersubscription_FK1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `beauty_centerdb`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `customersubscription_FK2`
    FOREIGN KEY (`subscription_id`)
    REFERENCES `beauty_centerdb`.`subscription` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);
    
    CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`prize` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `threshold` INT NOT NULL DEFAULT 0,
  `type` ENUM('TREATMENT', 'PRODUCT', 'DISCOUNT', 'SUBSCRIPTION') NULL,
  `amount` FLOAT NULL DEFAULT 0,
  `is_enabled` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`id`));
  
  CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`customerprize` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `customer_id` INT UNSIGNED NOT NULL,
  `prize_id` INT UNSIGNED NOT NULL,
  `expiration` DATE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `customerprize_FK1_idx` (`customer_id` ASC),
  INDEX `customerprize_FK2_idx` (`prize_id` ASC),
  CONSTRAINT `customerprize_FK1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `beauty_centerdb`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `customerprize_FK2`
    FOREIGN KEY (`prize_id`)
    REFERENCES `beauty_centerdb`.`prize` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);
    
    /*CREATE TABLE IF NOT EXISTS `beauty_centerdb`.`treatmentemployee` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `employee_id` INT UNSIGNED NOT NULL,
  `treatment_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `treatmentemployee_FK1_idx` (`employee_id` ASC) VISIBLE,
  INDEX `treatmentemployee_FK2_idx` (`treatment_id` ASC) VISIBLE,
  CONSTRAINT `treatmentemployee_FK1`
    FOREIGN KEY (`employee_id`)
    REFERENCES `beauty_centerdb`.`employee` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `treatmentemployee_FK2`
    FOREIGN KEY (`treatment_id`)
    REFERENCES `beauty_centerdb`.`treatment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE);*/