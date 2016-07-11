CREATE DATABASE  IF NOT EXISTS `sm24soft_v1` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sm24soft_v1`;
-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: sm24soft_v1
-- ------------------------------------------------------
-- Server version	5.7.12

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `username` varchar(256) NOT NULL,
  `encrypted_password` varchar(512) NOT NULL,
  `role` varchar(1) NOT NULL,
  `status` varchar(1) NOT NULL,
  `confirm_code` varchar(30) DEFAULT NULL,
  `staff_or_customer` varchar(1) NOT NULL DEFAULT '0',
  `staff_or_customer_id` int(8) unsigned zerofill NOT NULL,
  `store_id` int(8) unsigned zerofill DEFAULT NULL,
  `signed_up_with_facebook` varchar(1) NOT NULL DEFAULT '0',
  `signed_up_with_google` varchar(1) NOT NULL DEFAULT '0',
  `share_blog_url1` varchar(512) DEFAULT NULL,
  `share_blog_url2` varchar(512) DEFAULT NULL,
  `share_blog_url3` varchar(512) DEFAULT NULL,
  `delete_flg` varchar(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `created_user_id` int(8) unsigned zerofill NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_user_id` int(8) unsigned zerofill NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `short_name` varchar(100) NOT NULL,
  `full_name` varchar(256) NOT NULL,
  `birthday` datetime DEFAULT NULL,
  `address1` varchar(256) NOT NULL,
  `address2` varchar(256) DEFAULT NULL,
  `email_address` varchar(60) NOT NULL,
  `national` varchar(100) DEFAULT NULL,
  `identity_card_number` varchar(15) DEFAULT NULL,
  `telephone_number1` varchar(15) NOT NULL,
  `telephone_number2` varchar(15) DEFAULT NULL,
  `telephone_number3` varchar(15) DEFAULT NULL,
  `tax_registration_number` varchar(15) DEFAULT NULL,
  `loyalty_point` int(8) unsigned DEFAULT '0',
  `delete_flg` varchar(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `created_user_id` int(8) unsigned zerofill NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_user_id` int(8) unsigned zerofill NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_address_UNIQUE` (`email_address`),
  UNIQUE KEY `telephone_number1_UNIQUE` (`telephone_number1`),
  UNIQUE KEY `telephone_number2_UNIQUE` (`telephone_number2`),
  UNIQUE KEY `telephone_number3_UNIQUE` (`telephone_number3`),
  UNIQUE KEY `identity_card_number_UNIQUE` (`identity_card_number`),
  UNIQUE KEY `tax_registration_number_UNIQUE` (`tax_registration_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice` (
  `id` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `invoice_number` int(8) unsigned zerofill NOT NULL,
  `template_number` varchar(8) DEFAULT NULL,
  `date_of_issue` datetime NOT NULL,
  `store_id` int(8) unsigned zerofill DEFAULT NULL,
  `customer_id` int(8) unsigned zerofill DEFAULT NULL,
  `origin_invoice_number` int(8) unsigned zerofill DEFAULT NULL,
  `returned_invoice_flg` varchar(1) NOT NULL,
  `total_price_before_vat` double NOT NULL,
  `value_added_tax` varchar(2) NOT NULL,
  `total_value_added_tax` double NOT NULL,
  `total_price_after_vat` double NOT NULL,
  `total_price_after_vat_as_string` varchar(400) NOT NULL,
  `date_of_sale` datetime NOT NULL,
  `seller_person_id` int(8) unsigned zerofill DEFAULT NULL,
  `payment_code` varchar(1) NOT NULL,
  `actual_invoice_status` varchar(1) NOT NULL,
  `expected_date_of_shipping` datetime NOT NULL,
  `expected_time_of_shipping` datetime NOT NULL,
  `delete_flg` varchar(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `created_user_id` int(8) unsigned zerofill NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_user_id` int(8) unsigned zerofill NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `invoice_number_UNIQUE` (`invoice_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `plu_code1` varchar(30) DEFAULT NULL,
  `plu_code2` varchar(30) DEFAULT NULL,
  `plu_code3` varchar(30) DEFAULT NULL,
  `short_name` varchar(100) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `description` text,
  `item_category_id` int(4) unsigned zerofill NOT NULL,
  `store_id` int(4) unsigned zerofill DEFAULT NULL,
  `supplier_id` int(4) unsigned zerofill NOT NULL,
  `import_date` datetime NOT NULL,
  `price` double NOT NULL DEFAULT '0',
  `sale_price` double NOT NULL DEFAULT '0',
  `old_of_sale_price` double NOT NULL DEFAULT '0',
  `unit_of_price` varchar(10) NOT NULL,
  `discount` double NOT NULL DEFAULT '0',
  `unit_of_discount` varchar(10) NOT NULL,
  `tax_id` int(2) unsigned zerofill DEFAULT NULL,
  `saleable_flag` varchar(1) NOT NULL DEFAULT '0',
  `manufacture_date` datetime NOT NULL,
  `expire_date` datetime NOT NULL,
  `weight` float NOT NULL DEFAULT '0',
  `weight_of_one_box` text NOT NULL,
  `total_weight` float NOT NULL DEFAULT '0',
  `total_remaining_weight_after_sell` float NOT NULL DEFAULT '0',
  `unit_of_weight` varchar(5) NOT NULL,
  `thumbnail_url` varchar(300) DEFAULT NULL,
  `preview_image_url` varchar(300) DEFAULT NULL,
  `delete_flg` varchar(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `created_user_id` int(8) unsigned zerofill NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_user_id` int(8) unsigned zerofill NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `plu_code1_UNIQUE` (`plu_code1`),
  UNIQUE KEY `plu_code2_UNIQUE` (`plu_code2`),
  UNIQUE KEY `plu_code3_UNIQUE` (`plu_code3`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_category`
--

DROP TABLE IF EXISTS `item_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_category` (
  `id` int(4) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  `parent_category_id` int(4) unsigned zerofill NOT NULL,
  `supplier_id` int(4) unsigned zerofill NOT NULL,
  `delete_flg` varchar(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `created_user_id` int(8) unsigned zerofill NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_user_id` int(8) unsigned zerofill NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_category`
--

LOCK TABLES `item_category` WRITE;
/*!40000 ALTER TABLE `item_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_detail`
--

DROP TABLE IF EXISTS `item_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_detail` (
  `id` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `purchased_item_id` int(8) unsigned zerofill NOT NULL,
  `unit_of_sale` varchar(10) NOT NULL,
  `price` double NOT NULL DEFAULT '0',
  `number_of_item` int(11) NOT NULL DEFAULT '0',
  `total_price` double NOT NULL DEFAULT '0',
  `unit_of_total_price` varchar(10) NOT NULL,
  `invoice_number` int(8) unsigned zerofill NOT NULL,
  `delete_flg` varchar(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `created_user_id` int(8) unsigned zerofill NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_user_id` int(8) unsigned zerofill NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_detail`
--

LOCK TABLES `item_detail` WRITE;
/*!40000 ALTER TABLE `item_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` int(2) unsigned zerofill NOT NULL,
  `full_name_of_menu_item` varchar(100) CHARACTER SET utf8 NOT NULL,
  `role_of_menu_item` int(1) NOT NULL DEFAULT '0',
  `order_number` int(2) NOT NULL DEFAULT '0',
  `order_type` varchar(4) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `full_path_of_menu_item` varchar(150) CHARACTER SET utf8 NOT NULL DEFAULT '/',
  `actual_status` int(1) NOT NULL DEFAULT '0',
  `delete_flg` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `created_user_id` int(8) unsigned zerofill NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_user_id` int(8) unsigned zerofill NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `full_name_UNIQUE` (`full_name_of_menu_item`),
  UNIQUE KEY `full_path_of_menu_item_UNIQUE` (`full_path_of_menu_item`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `representative_or_contact_person`
--

DROP TABLE IF EXISTS `representative_or_contact_person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `representative_or_contact_person` (
  `id` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `short_name` varchar(100) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `birthday` datetime DEFAULT NULL,
  `address1` varchar(255) NOT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `email_address` varchar(60) NOT NULL,
  `national` varchar(100) DEFAULT NULL,
  `identity_card_number` varchar(15) DEFAULT NULL,
  `telephone_number1` varchar(15) NOT NULL,
  `telephone_number2` varchar(15) DEFAULT NULL,
  `telephone_number3` varchar(15) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `created_user_id` int(8) unsigned zerofill NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_user_id` int(8) unsigned zerofill NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_address_UNIQUE` (`email_address`),
  UNIQUE KEY `telephone_number1_UNIQUE` (`telephone_number1`),
  UNIQUE KEY `identity_card_number_UNIQUE` (`identity_card_number`),
  UNIQUE KEY `telephone_number2_UNIQUE` (`telephone_number2`),
  UNIQUE KEY `telephone_number3_UNIQUE` (`telephone_number3`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `representative_or_contact_person`
--

LOCK TABLES `representative_or_contact_person` WRITE;
/*!40000 ALTER TABLE `representative_or_contact_person` DISABLE KEYS */;
/*!40000 ALTER TABLE `representative_or_contact_person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff` (
  `id` int(8) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `short_name` varchar(100) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `birthday` datetime DEFAULT NULL,
  `address1` varchar(255) NOT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `email_address` varchar(60) NOT NULL,
  `national` varchar(100) DEFAULT NULL,
  `identity_card_number` varchar(15) DEFAULT NULL,
  `telephone_number1` varchar(15) NOT NULL,
  `telephone_number2` varchar(15) DEFAULT NULL,
  `telephone_number3` varchar(15) DEFAULT NULL,
  `signed_contract_date` datetime NOT NULL,
  `kind_of_contract` varchar(1) NOT NULL,
  `working_time` varchar(1) NOT NULL,
  `delete_flg` varchar(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `created_user_id` int(8) unsigned zerofill NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_user_id` int(8) unsigned zerofill NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_address_UNIQUE` (`email_address`),
  UNIQUE KEY `telephone_number1_UNIQUE` (`telephone_number1`),
  UNIQUE KEY `identity_card_number_UNIQUE` (`identity_card_number`),
  UNIQUE KEY `telephone_number2_UNIQUE` (`telephone_number2`),
  UNIQUE KEY `telephone_number3_UNIQUE` (`telephone_number3`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store` (
  `id` int(8) unsigned zerofill NOT NULL,
  `name` varchar(255) NOT NULL,
  `trading_name` varchar(255) NOT NULL,
  `address1` varchar(255) NOT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `email_address` varchar(60) NOT NULL,
  `telephone_number1` varchar(15) NOT NULL,
  `telephone_number2` varchar(15) DEFAULT NULL,
  `telephone_number3` varchar(15) DEFAULT NULL,
  `fax_number1` varchar(15) DEFAULT NULL,
  `fax_number2` varchar(15) DEFAULT NULL,
  `fax_number3` varchar(15) DEFAULT NULL,
  `tax_registration_number` varchar(15) DEFAULT NULL,
  `business_license_number` varchar(45) DEFAULT NULL,
  `date_of_issue` datetime DEFAULT NULL,
  `representative_person_id` int(8) unsigned zerofill NOT NULL,
  `business_registered_fields` varchar(300) NOT NULL,
  `operating_status` varchar(1) NOT NULL,
  `website_url` varchar(45) DEFAULT NULL,
  `logo_url` varchar(255) DEFAULT NULL,
  `bank_number1` varchar(45) DEFAULT NULL,
  `bank_account_name1` varchar(200) DEFAULT NULL,
  `bank_number2` varchar(45) DEFAULT NULL,
  `bank_account_name2` varchar(200) DEFAULT NULL,
  `bank_number3` varchar(45) DEFAULT NULL,
  `bank_account_name3` varchar(200) DEFAULT NULL,
  `delete_flg` varchar(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `created_user_id` int(8) unsigned zerofill NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_user_id` int(8) unsigned zerofill NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_address_UNIQUE` (`email_address`),
  UNIQUE KEY `telephone_number1_UNIQUE` (`telephone_number1`),
  UNIQUE KEY `telephone_number2_UNIQUE` (`telephone_number2`),
  UNIQUE KEY `telephone_number3_UNIQUE` (`telephone_number3`),
  UNIQUE KEY `tax_registration_number_UNIQUE` (`tax_registration_number`),
  UNIQUE KEY `business_license_number_UNIQUE` (`business_license_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supplier` (
  `id` int(4) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `supplier_name` varchar(255) NOT NULL,
  `supplier_trading_name` varchar(255) NOT NULL,
  `representative_person_id` int(8) unsigned zerofill DEFAULT NULL,
  `contact_person_id` int(8) unsigned zerofill DEFAULT NULL,
  `address1` varchar(255) NOT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `email_address` varchar(60) NOT NULL,
  `telephone_number1` varchar(15) NOT NULL,
  `telephone_number2` varchar(15) DEFAULT NULL,
  `telephone_number3` varchar(15) DEFAULT NULL,
  `fax_number1` varchar(15) DEFAULT NULL,
  `fax_number2` varchar(15) DEFAULT NULL,
  `fax_number3` varchar(15) DEFAULT NULL,
  `delete_flg` varchar(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `created_user_id` int(8) unsigned zerofill DEFAULT NULL,
  `updated_at` datetime NOT NULL,
  `updated_user_id` int(8) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_address_UNIQUE` (`email_address`),
  UNIQUE KEY `telephone_number1_UNIQUE` (`telephone_number1`),
  UNIQUE KEY `telephone_number2_UNIQUE` (`telephone_number2`),
  UNIQUE KEY `telephone_number3_UNIQUE` (`telephone_number3`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tax`
--

DROP TABLE IF EXISTS `tax`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tax` (
  `id` int(2) unsigned zerofill NOT NULL,
  `description` varchar(100) NOT NULL,
  `calculation` varchar(45) NOT NULL,
  `delete_flg` varchar(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `created_user_id` int(8) unsigned zerofill NOT NULL,
  `updated_at` datetime NOT NULL,
  `updated_user_id` int(8) unsigned zerofill NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `calculation_UNIQUE` (`calculation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tax`
--

LOCK TABLES `tax` WRITE;
/*!40000 ALTER TABLE `tax` DISABLE KEYS */;
/*!40000 ALTER TABLE `tax` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-06  1:06:44
