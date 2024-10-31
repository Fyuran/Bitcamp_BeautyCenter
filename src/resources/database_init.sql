
CREATE DATABASE IF NOT EXISTS `beauty_centerdb`;

USE `beauty_centerdb`;
CREATE TABLE `beauty_center` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
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
  `is_enabled` tinyint(4) DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `customer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `is_female` tinyint(4) DEFAULT 0,
  `birthday` date NOT NULL,
  `birthplace` varchar(200) NOT NULL,
  `eu_tin` varchar(20) NOT NULL,
  `credentials_id` int(10) unsigned NOT NULL,
  `VAT` varchar(12) DEFAULT NULL,
  `recipient_code` varchar(8) DEFAULT NULL,
  `notes` text DEFAULT NULL,
  `loyalty_points` int(11) NOT NULL DEFAULT 0,
  `is_enabled` tinyint(4) DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `customer_FK1` (`credentials_id`),
  CONSTRAINT `customer_FK1` FOREIGN KEY (`credentials_id`) REFERENCES `user_credentials` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `customerprize` (
  `customer_id` int(10) unsigned NOT NULL,
  `prize_id` int(10) unsigned NOT NULL,
  `expiration` date NOT NULL,
  PRIMARY KEY (`customer_id`,`prize_id`),
  KEY `customerprize_FK1_idx` (`customer_id`),
  KEY `customerprize_FK2_idx` (`prize_id`),
  CONSTRAINT `customerprize_FK1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `customerprize_FK2` FOREIGN KEY (`prize_id`) REFERENCES `prize` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `customersubscription` (
  `customer_id` int(10) unsigned NOT NULL,
  `subscription_id` int(10) unsigned NOT NULL,
  `start` date NOT NULL,
  PRIMARY KEY (`customer_id`,`subscription_id`),
  KEY `customersubscription_FK1_idx` (`customer_id`),
  KEY `customersubscription_FK2_idx` (`subscription_id`),
  CONSTRAINT `customersubscription_FK1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `customersubscription_FK2` FOREIGN KEY (`subscription_id`) REFERENCES `subscription` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `employee` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `is_female` tinyint(4) DEFAULT 0,
  `birthday` date DEFAULT NULL,
  `birthplace` varchar(200) NOT NULL,
  `role` enum('PERSONNEL','SECRETARY','ADMIN') DEFAULT NULL,
  `hired` date DEFAULT NULL,
  `termination` date DEFAULT NULL,
  `credentials_id` int(10) unsigned DEFAULT NULL,
  `notes` text DEFAULT NULL,
  `is_enabled` tinyint(4) NOT NULL DEFAULT 1,
  `serial` mediumtext NOT NULL,
  `treatment_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employee_fk1_idx` (`credentials_id`),
  KEY `employee_fk2_idx` (`treatment_id`),
  CONSTRAINT `employee_fk1` FOREIGN KEY (`credentials_id`) REFERENCES `user_credentials` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `employee_fk2` FOREIGN KEY (`treatment_id`) REFERENCES `treatment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `prize` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `threshold` int(11) NOT NULL DEFAULT 0,
  `type` enum('TREATMENT','PRODUCT','DISCOUNT','SUBSCRIPTION') DEFAULT NULL,
  `amount` float DEFAULT 0,
  `is_enabled` tinyint(4) DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `amount` int(11) NOT NULL,
  `minimum` int(11) DEFAULT 0,
  `price` float NOT NULL,
  `vat_id` int(10) unsigned DEFAULT NULL,
  `type` enum('ORAL_CARE','SKIN_CARE','HAIR_CARE','BODY_CARE','COSMETICS','PERFUMES','OTHER') DEFAULT NULL,
  `is_enabled` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `vat_id_idx` (`id`),
  KEY `vat_id` (`vat_id`),
  CONSTRAINT `vat_id` FOREIGN KEY (`vat_id`) REFERENCES `vat` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `producttreatment` (
  `product_id` int(10) unsigned NOT NULL,
  `treatment_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`product_id`,`treatment_id`),
  KEY `pt_fk1_idx` (`product_id`),
  KEY `pt_fk2_idx` (`treatment_id`),
  CONSTRAINT `pt_fk1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pt_fk2` FOREIGN KEY (`treatment_id`) REFERENCES `treatment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `reservation` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `is_paid` tinyint(4) NOT NULL DEFAULT 0,
  `treatment_id` int(10) unsigned DEFAULT NULL,
  `customer_id` int(10) unsigned DEFAULT NULL,
  `employee_id` int(10) unsigned DEFAULT NULL,
  `state` enum('CREATED','IN_PROGRESS','CANCELLED') DEFAULT NULL,
  `is_enabled` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `reservation_fk1_idx` (`treatment_id`),
  KEY `reservation_fk2_idx` (`customer_id`),
  KEY `reservation_fk3_idx` (`employee_id`),
  CONSTRAINT `reservation_fk1` FOREIGN KEY (`treatment_id`) REFERENCES `treatment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reservation_fk2` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reservation_fk3` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `reservationemployee` (
  `reservation_id` int(10) unsigned NOT NULL,
  `employee_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`reservation_id`,`employee_id`),
  KEY `reservationemployee_fk1_idx` (`reservation_id`),
  KEY `reservationemployee_fk2_idx` (`employee_id`),
  CONSTRAINT `reservationemployee_fk1` FOREIGN KEY (`reservation_id`) REFERENCES `reservation` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reservationemployee_fk2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `shift` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  `type` enum('WORK','HOLIDAYS') DEFAULT NULL,
  `is_enabled` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `shiftemployee` (
  `shift_id` int(10) unsigned NOT NULL,
  `employee_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`shift_id`,`employee_id`),
  KEY `shiftemployee_fk2_idx` (`employee_id`),
  KEY `shiftemployee_fk1_idx` (`shift_id`),
  CONSTRAINT `shiftemployee_fk1` FOREIGN KEY (`shift_id`) REFERENCES `shift` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `shiftemployee_fk2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `subscription` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `subperiod` enum('MONTHLY','QUARTERLY','HALF_YEAR','YEARLY') NOT NULL,
  `price` float NOT NULL,
  `vat_id` int(10) unsigned DEFAULT NULL,
  `discount` float NOT NULL,
  `is_enabled` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `subscription_FK1_idx` (`vat_id`),
  CONSTRAINT `subscription_FK1` FOREIGN KEY (`vat_id`) REFERENCES `vat` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `transaction` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL,
  `datetime` datetime NOT NULL,
  `payment_method` enum('CURRENCY','CARD') DEFAULT NULL,
  `vat_id` int(10) unsigned DEFAULT NULL,
  `customer_id` int(10) unsigned DEFAULT NULL,
  `beauty_id` int(10) unsigned DEFAULT NULL,
  `services` text NOT NULL,
  `is_enabled` tinyint(4) DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `transactionFK_idx` (`customer_id`),
  KEY `transactionFK2_idx` (`vat_id`),
  KEY `transactionFK3` (`beauty_id`),
  CONSTRAINT `transactionFK1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `transactionFK2` FOREIGN KEY (`vat_id`) REFERENCES `vat` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `transactionFK3` FOREIGN KEY (`beauty_id`) REFERENCES `beauty_center` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `treatment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(100) NOT NULL,
  `price` float DEFAULT NULL,
  `vat_id` int(10) unsigned DEFAULT NULL,
  `duration` time DEFAULT NULL,
  `is_enabled` tinyint(4) DEFAULT 1,
  PRIMARY KEY (`id`),
  KEY `vat_id_idx` (`vat_id`),
  CONSTRAINT `vat_id_treatment` FOREIGN KEY (`vat_id`) REFERENCES `vat` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `treatmentemployee` (
  `employee_id` int(10) unsigned NOT NULL,
  `treatment_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`employee_id`,`treatment_id`),
  KEY `treatmentemployee_FK1_idx` (`employee_id`),
  KEY `treatmentemployee_FK2_idx` (`treatment_id`),
  CONSTRAINT `treatmentemployee_FK1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `treatmentemployee_FK2` FOREIGN KEY (`treatment_id`) REFERENCES `treatment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `user_credentials` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `iban` varchar(40) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `mail` varchar(100) DEFAULT NULL,
  `is_enabled` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mail_UNIQUE` (`mail`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `vat` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `amount` double unsigned NOT NULL,
  `is_enabled` tinyint(4) DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `amount_UNIQUE` (`amount`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
